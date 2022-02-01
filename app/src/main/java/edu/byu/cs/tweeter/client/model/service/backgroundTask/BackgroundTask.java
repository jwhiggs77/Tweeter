package edu.byu.cs.tweeter.client.model.service.backgroundTask;

public abstract class BackgroundTask implements Runnable {
    public static final String SUCCESS_KEY = "success";
    public static final String USER_KEY = "user";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";
}
