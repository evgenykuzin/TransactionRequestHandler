package com.alpha.entities.clients;

import java.util.Collection;
import java.util.HashSet;

public class ClientsSet extends HashSet<Client> {
    public ClientsSet(Collection<Client> collection) {
        addAll(collection);
    }

    public ClientsSet() {
    }

    public Client getClientById(int id) {
        for (Client client : this) {
            if (client.getId() == id) return client;
        }
        return null;
    }

    @Override
    public boolean add(Client client) {
        Client existingClient = getClientById(client.getId());
        if (existingClient != null) {
            existingClient.addAllAssetsMap(client.getAssetsMap());
            return true;
        }
        return super.add(client);
    }


}
