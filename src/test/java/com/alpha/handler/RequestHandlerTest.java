package com.alpha.handler;

import com.alpha.entities.clients.Client;
import com.alpha.entities.clients.ClientsSet;
import com.alpha.entities.reports.Report;
import com.alpha.entities.requests.Request;
import com.alpha.entities.requests.RequestsQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHandlerTest {

    @Test
    public void testExecute() {
        List<Report> expectedReports = new ArrayList<>();
        ClientsSet expectedClientsSet = new ClientsSet();
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("assetA", 100);
        map1.put("assetB", 100);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("assetB", 100);
        expectedClientsSet.add(new Client(1, map1));
        expectedClientsSet.add(new Client(2, map2));
        expectedReports.add(new Report(1, "1", "2", 100));
        ClientsSet clientsSet = new ClientsSet();
        RequestsQueue requestsQueue = new RequestsQueue();
        clientsSet.add(new Client(1, "assetA", 100));
        clientsSet.add(new Client(2, "assetB", 200));
        requestsQueue.add(new Request("1", 1, "buy", "assetB", 100));
        requestsQueue.add(new Request("2", 2, "sell", "assetB", 200));
        RequestHandler requestHandler = new RequestHandler(requestsQueue, clientsSet);
        requestHandler.execute();
        List<Report> actualReports = requestHandler.getReports();
        Assertions.assertEquals(expectedReports, actualReports);
        Assertions.assertEquals(expectedClientsSet, clientsSet);
    }
}
