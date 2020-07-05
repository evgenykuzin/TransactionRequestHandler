package com.alpha.io;

import com.alpha.entities.clients.Client;
import com.alpha.entities.clients.ClientsSet;
import com.alpha.entities.requests.Request;
import com.alpha.entities.requests.RequestsQueue;
import com.alpha.exceptions.IllegalDataException;
import com.alpha.entities.reports.Report;
import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class CsvManager {
    private final String buyOperationName, sellOperationName;
    private final char delimiter;
    private String pathToDir = null;

    public CsvManager(String buyOperationName, String sellOperationName, char delimiter) {
        this.buyOperationName = buyOperationName;
        this.sellOperationName = sellOperationName;
        this.delimiter = delimiter;
    }

    private Iterable<CSVRecord> getRecords(String path) throws IOException {
        if (pathToDir == null) {
            String[] words = path.split("[/\\\\]");
            pathToDir = path.replace(words[words.length - 1], "");
        }
        Reader in = new FileReader(path);
        return CSVFormat.EXCEL.withDelimiter(delimiter).withHeader().parse(in);
    }

    public ClientsSet getClients(String path) throws IOException {
        final ClientsSet clients = new ClientsSet();
        final Map<Integer, Client> clientIdsMap = new HashMap<>();
        Iterable<CSVRecord> records = getRecords(path);
        int i = 0;
        for (CSVRecord record : records) {
            i++;
            if (record.size() != 3) throw new IllegalDataException("wrong number of fields on raw " + i);
            if (record.get(0).isEmpty())
                throw new IllegalDataException("client id field must not be empty on row " + i);
            int clientId = Integer.parseInt(record.get(0));
            String asset = record.get(1);
            int assetCount = 0;
            if (!record.get(2).isEmpty()) assetCount = Integer.parseInt(record.get(2));
            if (clientId <= 0) throw new IllegalDataException("client id must be positive and not zero on raw " + i);
            if (assetCount < 0) throw new IllegalDataException("asset count must be positive on raw " + i);
            Client client;
            if (!asset.isEmpty() && assetCount > 0) {
                client = new Client(clientId, asset, assetCount);
            } else {
                client = new Client(clientId);
            }
            clients.add(client);
        }
        return clients;
    }

    public RequestsQueue getRequests(String path) throws IOException {
        final RequestsQueue requests = new RequestsQueue();
        Iterable<CSVRecord> records = getRecords(path);
        int i = 0;
        for (CSVRecord record : records) {
            i++;
            if (record.size() != 5) throw new IllegalDataException("wrong number of fields on raw " + i);
            String requestId = record.get(0);
            if (record.get(0).isEmpty())
                throw new IllegalDataException("the client id field must not be empty on row " + i);
            int clientId = Integer.parseInt(record.get(1));
            if (clientId <= 0) throw new IllegalDataException("client id must be positive and not zero on row " + i);
            String operation = record.get(2);
            if (operation.isEmpty())
                throw new IllegalDataException("the operation field must not be empty on row " + i);
            String asset = record.get(3);
            if (asset.isEmpty()) throw new IllegalDataException("the asset field must not be empty on row " + i);
            if (record.get(4).isEmpty())
                throw new IllegalDataException("the asset count field must not be empty on row " + i);
            int assetCount = Integer.parseInt(record.get(4));
            if (assetCount < 0) throw new IllegalDataException("client id must be positive on row " + i);
            if (!operation.equals(buyOperationName) && !operation.equals(sellOperationName)) {
                throw new IllegalArgumentException("Wrong operation name! Enter a default operation names ('buy' or 'sell') " +
                        "or enter an options in command line: -b [your buy operation name] and -s [your sell operation name] on row " + i);
            }
            Request request = new Request(requestId, clientId, operation, asset, assetCount);
            requests.add(request);
        }
        return requests;
    }

    public void createCsvFiles(List<Report> reports, ClientsSet clientsSet) throws IOException {
        String reportsPath = pathToDir + "reports.csv";
        String clientsPath = pathToDir + "updatedBalance.csv";
        CSVWriter reportsWriter = new CSVWriter(new FileWriter(reportsPath));
        CSVWriter clientsWriter = new CSVWriter(new FileWriter(clientsPath));
        writeReportsRecords(reports, reportsWriter);
        writeClientsRecords(clientsSet, clientsWriter);
        reportsWriter.close();
        clientsWriter.close();
    }

    private void writeReportsRecords(List<Report> reports, CSVWriter writer) {
        writer.writeNext(new String[]{"reportId", "fromRequestId", "toRequestId", "assetCount"});
        for (Report report : reports) {
            writer.writeNext(new String[]{
                    String.valueOf(report.getReportId()),
                    report.getBuyRequestId(),
                    report.getSellRequestId(),
                    String.valueOf(report.getAssetsCount())
            }, false);
        }
    }

    private void writeClientsRecords(ClientsSet clientsSet, CSVWriter writer) throws IOException {
        writer.writeNext(new String[]{"clientId", "asset", "assetCount"});
        for (Client client : clientsSet) {
            String id = String.valueOf(client.getId());
            for (Map.Entry<String, Integer> entry : client.getAssetsMap().entrySet()) {
                String asset = entry.getKey();
                String assetCount = String.valueOf(entry.getValue());
                writer.writeNext(new String[]{id, asset, assetCount}, false);
            }
            if (client.getAssetsMap().isEmpty()) {
                writer.writeNext(new String[]{id, "0", "0"}, false);
            }
        }
    }


}
