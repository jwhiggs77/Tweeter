package edu.byu.cs.tweeter.client.presenter;

import android.widget.EditText;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter {

    public interface View {
        void displayErrorMessage(String message);
        void startActivity(User loggedInUser, AuthToken authToken);
    }

    private View view;
    private UserService userService;

    public LoginPresenter(View view) {
        this.view = view;
        userService = new UserService();
    }

    public class LoginObserver implements UserService.LoginObserver {

        @Override
        public void handleSuccess(User loggedInUser, AuthToken authToken) {
            view.startActivity(loggedInUser, authToken);
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to login: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to login because of exception: " + ex.getMessage());
        }
    }

    public void login(String alias, String password) {
        userService.login(alias, password, new LoginObserver());
    }

}
