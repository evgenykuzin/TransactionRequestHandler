package com.alpha.handler;

import com.alpha.entities.clients.Client;
import com.alpha.entities.clients.ClientsSet;
import com.alpha.entities.reports.Report;
import com.alpha.entities.requests.Request;
import com.alpha.entities.requests.RequestsQueue;
import com.alpha.exceptions.IllegalDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class RequestHandler {
    private final ClientsSet clients;
    private final Queue<Request> buyRequestQueue;
    private final Queue<Request> sellRequestQueue;
    private final List<Report> reports;

    public RequestHandler(RequestsQueue requestQueue, ClientsSet clients) {
        buyRequestQueue = new RequestsQueue();
        sellRequestQueue = new RequestsQueue();
        for (Request request : requestQueue) {
            if (request.isBuyOperation()) {
                buyRequestQueue.add(request);
            } else if (request.isSellOperation()) {
                sellRequestQueue.add(request);
            } else {
                throw new IllegalArgumentException("Wrong request method. Choose 'buy' or 'sell' ");
            }
        }
        this.clients = clients;
        this.reports = new ArrayList<>();
    }

    public void execute() {
        int i = 0;
        for (Request buyRequest : buyRequestQueue) {
            for (Request sellRequest : sellRequestQueue) {
                String buyAsset = buyRequest.getAsset();
                String sellAsset = sellRequest.getAsset();
                int buyAssetCount = buyRequest.getAssetCount();
                int sellAssetCount = sellRequest.getAssetCount();
                if (buyAsset.equals(sellAsset)) {
                    Client clientBuy = getClient(buyRequest);
                    Client clientSell = getClient(sellRequest);
                    if (buyAssetCount <= sellAssetCount && clientSell.hasAssetsCountOf(sellAsset, buyAssetCount)) {
                        i++;
                        change(clientBuy, clientSell, buyAsset, buyAssetCount);
                        reports.add(createReport(i, buyRequest, sellRequest));

                    }
                }
            }
        }
    }

    private Client getClient(Request request) {
        Client client = clients.getClientById(request.getClientId());
        if (client == null)
            throw new IllegalDataException("client with id " + request.getClientId() + " doesn't exist");
        return client;
    }

    private Report createReport(int i, Request buyRequest, Request sellRequest) {
        int buyAssetCount = buyRequest.getAssetCount();
        return new Report(
                i,
                buyRequest.getRequestId(),
                sellRequest.getRequestId(),
                buyAssetCount);
    }

    private void change(Client clientBuy, Client clientSell, String asset, int assetCount) {
        clientBuy.addAsset(asset, assetCount);
        clientSell.decAssetCountOf(asset, assetCount);
        //if clients must to change their existing assets, then the next lines must to work.
        /*
        String someAsset = getSomeAsset(clientBuy, assetCount);
        clientSell.addAsset(someAsset, assetCount);
        clientBuy.decAssetCountOf(someAsset, assetCount);
         */
    }

    private String getSomeAsset(Client client, int assetsCount) {
        for (String key : client.getAssetsMap().keySet()) {
            int value = client.getAssetsMap().get(key);
            if (value >= assetsCount) return key;
        }
        return null;
    }

    public List<Report> getReports() {
        return reports;
    }
}
