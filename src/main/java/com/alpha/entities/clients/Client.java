package com.alpha.entities.clients;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Client {
    private int id;
    private Map<String, Integer> assetsMap;

    public Client(int id, Map<String, Integer> assetsMap) {
        this.id = id;
        this.assetsMap = assetsMap;
    }

    public Client(int id, String asset, int assetCount) {
        this(id);
        addAsset(asset, assetCount);
    }

    public Client(int id) {
        this(id, new HashMap<>());
    }

    public Client(Client client) {
        this(client.id, new HashMap<>(client.assetsMap));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Integer> getAssetsMap() {
        return assetsMap;
    }

    public void setAssetsMap(Map<String, Integer> assetsMap) {
        this.assetsMap = assetsMap;
    }

    public void addAsset(String asset, int assetCount) {
        if (!assetsMap.containsKey(asset)) {
            assetsMap.put(asset, assetCount);
        } else {
            incAssetCountOf(asset, assetCount);
        }
    }

    public void addAllAssetsMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            addAsset(entry.getKey(), entry.getValue());
        }
    }

    private void changeAssetCountOf(String asset, int count) {
        int resultValue = assetsMap.get(asset) + count;
        if (resultValue <= 0) assetsMap.remove(asset);
        else assetsMap.replace(asset, resultValue);
    }

    public void incAssetCountOf(String asset, int incCount) {
        changeAssetCountOf(asset, incCount);
    }

    public void decAssetCountOf(String asset, int decCount) {
        changeAssetCountOf(asset, -decCount);
    }

    public boolean hasAssetsCountOf(String asset, int count) {
        if (!assetsMap.containsKey(asset)) return false;
        return assetsMap.get(asset) >= count;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", assetsMap=" + assetsMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(assetsMap, client.assetsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assetsMap);
    }
}
