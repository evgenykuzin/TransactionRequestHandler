package com.alpha.main;

import com.alpha.entities.clients.ClientsSet;
import com.alpha.entities.requests.RequestsQueue;
import com.alpha.handler.RequestHandler;
import com.alpha.io.CsvManager;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class Main {
    @Argument(required = true)
    public static String balancePath;
    @Argument(index = 1, required = true)
    public static String requestsPath;
    @Option(name = "-b") //name of buy operation in csv file
    public static String buyOperationName = "buy";
    @Option(name = "-s")//name of sell operation in csv file
    public static String sellOperationName = "sell";
    @Option(name = "-d")//delimiter in csv file
    public static char delimiter = ',';

    public static void main(String[] args) throws IOException {
        runConsoleVersion(args);
    }

    public static void runConsoleVersion(String[] args) throws IOException {
        initArguments(args);
        CsvManager csvManager = new CsvManager(buyOperationName, sellOperationName, delimiter);
        ClientsSet clients = csvManager.getClients(balancePath);
        RequestsQueue requests = csvManager.getRequests(requestsPath);
        RequestHandler requestHandler = new RequestHandler(requests, clients);
        requestHandler.execute();
        csvManager.createCsvFiles(requestHandler.getReports(), clients);
    }

    private static void initArguments(String[] arguments) {
        final CmdLineParser parser = new CmdLineParser(new Main());
        try {
            parser.parseArgument(arguments);
        } catch (CmdLineException clEx) {
            System.out.println("ERROR: Unable to parse command-line options: " + clEx);
        }
    }
}
