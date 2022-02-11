package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.handler.observer.ServiceObserver;

public abstract class Presenter {
    public interface View {
        void displayErrorMessage(String message);
    }

    protected View view;

    public abstract class Observer implements ServiceObserver {

        public abstract String getMessageTag();

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage(getMessageTag() + message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage(getMessageTag() + ex.getMessage());
        }
    }
}