package com.alpha.entities.clients;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClientTest {

    @Test
    public void equalsTest() {
        Client client1 = new Client(1, "asset", 2);
        Client client2 = new Client(1, "asset", 2);
        Assertions.assertEquals(client1, client2);
    }

    @Test
    public void addTest() {
        ClientsSet clients = new ClientsSet();
        for (int i = 0; i < 10; i++) {
            int id = i < 5 ? i : 6;
            clients.add(new Client(id, "a", 1));
        }
        String expected = "[Client{id=1, assetsMap={a=1}}, Client{id=0, assetsMap={a=1}}, Client{id=6, assetsMap={a=5}}, Client{id=4, assetsMap={a=1}}, Client{id=3, assetsMap={a=1}}, Client{id=2, assetsMap={a=1}}]";
        Assertions.assertEquals(expected, clients.toString());
    }
}
