package com.alpha.io;

import com.alpha.entities.clients.Client;
import com.alpha.entities.clients.ClientsSet;
import com.alpha.entities.requests.Request;
import com.alpha.entities.requests.RequestsQueue;
import com.alpha.exceptions.IllegalDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvManagerTest {
    private static final String resources = new File("").getAbsolutePath()+"/src/test/resources";
    private static final CsvManager csvManager = new CsvManager("buy", "sell", ',');
    @Test
    public void testIllegalData() {
        assertThrows("balanceEmptyClientId");
        assertThrows("balanceNegativeClientId");
        assertThrows("balanceNegativeAssetCount");
        assertThrows("requestsWrongOperationName");
        assertThrows("requestsEmptyField");
    }

    private void assertThrows(String fileName) {
        Assertions.assertThrows(IllegalDataException.class, ()-> {
            csvManager.getClients(getPathToIllegalDataFiles(fileName));
        });
    }

    private String getPathToIllegalDataFiles(String fileName) {
        return resources + "/illegalData/" + fileName + ".csv";
    }

    @Test
    public void testGetClients() throws IOException {
        ClientsSet expected = new ClientsSet();
        expected.add(new Client(1, "AAA", 10));
        expected.add(new Client(2, "BBB", 200));
        expected.add(new Client(3, "AAA", 150));
        Map<String, Integer> map = new HashMap<>();
        map.put("CCC", 200);
        map.put("DDD", 120);
        expected.add(new Client(4, map));

        ClientsSet clientsSet = csvManager.getClients(resources+"/default_balance.csv");
        Assertions.assertEquals(expected, clientsSet);
    }

    @Test
    public void testGetRequests() throws IOException {
        RequestsQueue expected = new RequestsQueue();
        expected.add(new Request("3",1,"buy","AAA", 100));
        expected.add(new Request("2",3,"sell","AAA", 150));
        expected.add(new Request("1",5,"buy","BBB", 4));
        expected.add(new Request("4",2,"sell","BBB", 100));
        expected.add(new Request("5",3,"buy","BBB", 96));
        expected.add(new Request("6",4,"sell","CCC", 110));
        expected.add(new Request("7",4,"buy","DDD", 20));
        RequestsQueue requestsQueue = csvManager.getRequests(resources+"/default_requests.csv");
        Assertions.assertTrue(expected.containsAll(requestsQueue));
    }

}
