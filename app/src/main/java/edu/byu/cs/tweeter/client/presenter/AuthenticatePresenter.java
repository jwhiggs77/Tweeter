package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticatePresenter extends Presenter {
    public interface View extends Presenter.View {
        void startActivity(User user, AuthToken authToken);
    }

    protected View view;
    protected UserService userService;

    public abstract class AuthenticateObserver extends Observer implements edu.byu.cs.tweeter.client.model.service.handler.observer.AuthenticateObserver {

        @Override
        public void handleSuccess(User currentUser, AuthToken authToken) {
            view.startActivity(currentUser, authToken);
        }

//        @Override
//        public void handleMessage(String message) {
//            view.displayErrorMessage(getMessageTag() + message);
//        }
//
//        @Override
//        public void handleException(Exception ex) {
//            view.displayErrorMessage(getMessageTag() + ex.getMessage());
//        }
    }

    public void validate(String alias, String password) {
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }
}
