package com.alpha.entities.requests;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestsQueue extends ConcurrentLinkedQueue<Request> {
    public RequestsQueue(Collection<Request> collection) {
        addAll(collection);
    }

    public RequestsQueue() {
    }

    public Request getRequest(String requestId) {
        for (Request request : this) {
            if (request.getRequestId().equals(requestId)) return request;
        }
        return null;
    }
}
