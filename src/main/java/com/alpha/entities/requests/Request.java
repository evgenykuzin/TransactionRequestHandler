package com.alpha.entities.requests;

import com.alpha.main.Main;

import java.util.Objects;

public class Request {

    private final String requestId;
    private final int clientId;
    private final String operation;
    private final String asset;
    private final int assetCount;

    public Request(String requestId, int clientId, String operation, String asset, int assetCount) {
        this.requestId = requestId;
        this.clientId = clientId;
        this.operation = operation;
        this.asset = asset;
        this.assetCount = assetCount;
    }

    public Request(Request request) {
        this.requestId = request.requestId;
        this.clientId = request.clientId;
        this.operation = request.operation;
        this.asset = request.asset;
        this.assetCount = request.assetCount;
    }

    public String getRequestId() {
        return requestId;
    }

    public int getClientId() {
        return clientId;
    }

    public String getOperation() {
        return operation;
    }

    public String getAsset() {
        return asset;
    }

    public int getAssetCount() {
        return assetCount;
    }

    public boolean isBuyOperation() {
        return operation.equals(Main.buyOperationName);
    }

    public boolean isSellOperation() {
        return operation.equals(Main.sellOperationName);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId='" + requestId + '\'' +
                ", clientId=" + clientId +
                ", operation='" + operation + '\'' +
                ", asset='" + asset + '\'' +
                ", assetCount=" + assetCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return clientId == request.clientId &&
                assetCount == request.assetCount &&
                Objects.equals(requestId, request.requestId) &&
                Objects.equals(operation, request.operation) &&
                Objects.equals(asset, request.asset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, clientId, operation, asset, assetCount);
    }
}
