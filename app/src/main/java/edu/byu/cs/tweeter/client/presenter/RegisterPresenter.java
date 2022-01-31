package edu.byu.cs.tweeter.client.presenter;

import android.widget.ImageView;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter {

    public interface View {
        void displayErrorMessage(String message);

        void startActivity(User loggedInUser, AuthToken authToken);
    }

    private View view;
    private UserService userService;

    public RegisterPresenter(View view) {
        this.view = view;
        userService = new UserService();
    }

    public class RegisterObserver implements UserService.RegisterObserver {

        @Override
        public void handleSuccess(User registeredUser, AuthToken authToken) {
            view.startActivity(registeredUser, authToken);
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to register: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to register because of exception: " + ex.getMessage());
        }
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64) {
        userService.register(firstName, lastName, alias, password, imageBytesBase64, new RegisterObserver());
    }

    public void validateRegistration(String firstName, String lastName, String alias, String password, ImageView imageToUpload) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        if (imageToUpload.getDrawable() == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }
}
