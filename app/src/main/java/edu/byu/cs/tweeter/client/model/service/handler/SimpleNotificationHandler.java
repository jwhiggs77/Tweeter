package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;
import edu.byu.cs.tweeter.client.model.service.handler.observer.SimpleNotificationObserver;

public class SimpleNotificationHandler extends BackgroundTaskHandler<SimpleNotificationObserver> {

    public SimpleNotificationHandler(SimpleNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, SimpleNotificationObserver observer) {
        observer.handleSuccess();
    }
}
