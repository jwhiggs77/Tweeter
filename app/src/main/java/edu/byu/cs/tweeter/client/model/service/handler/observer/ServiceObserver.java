package edu.byu.cs.tweeter.client.model.service.handler.observer;

public interface ServiceObserver {

//    void handleSuccess();

    void handleMessage(String message);

    void handleException(Exception ex);
}
