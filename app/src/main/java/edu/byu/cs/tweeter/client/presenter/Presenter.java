package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.User;

public abstract class Presenter {
    public interface View {
        void displayErrorMessage(String message);

//        void startActivity(User user);
    }

    protected View view;
    public abstract String getMessageTag();
}
