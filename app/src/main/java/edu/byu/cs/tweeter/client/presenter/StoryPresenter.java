package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class StoryPresenter {
    public interface View {
        void displayErrorMessage(String message);
        void startActivity(User user);
    }

    private View view;
    private UserService userService;

    public StoryPresenter(View view) {
        this.view = view;
        userService = new UserService();
    }

    private class GetUserObserver implements UserService.GetUserObserver {

        @Override
        public void handleSuccess(User user) {
            view.startActivity(user);
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to get user's profile: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to get user's profile because of exception: " + ex.getMessage());
        }

    }

    public void getUser(String userAlias) {
        userService.getUser(Cache.getInstance().getCurrUserAuthToken(), userAlias, new GetUserObserver());
    }
}
