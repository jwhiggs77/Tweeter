package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter {

    public interface View {
        void logout();

        void setFollow();

        void displayErrorMessage(String message);

        void checkFollower(boolean isFollower);

        void getFollowerCount(int count);

        void getFollowingCount(int count);

        void post();
    }

    private View view;
    private UserService userService;
    private StatusService statusService;

    public MainActivityPresenter(View view) {
        this.view = view;
        userService = new UserService();
    }

//    public void updateSelectedUserFollowingAndFollowers(User selectedUser) {
//        UserService.updateSelectedUserFollowingAndFollowers(selectedUser);
//    }

    public class LogoutObserver implements UserService.LogoutObserver {

        @Override
        public void handleSuccess() {
            view.logout();
//            logOutToast.cancel();
//            logoutUser();
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to logout: " + message);
//            Toast.makeText(MainActivity.this, "Failed to logout: " + message, Toast.LENGTH_LONG).show();
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to logout because of exception: " + ex.getMessage());
//            Toast.makeText(MainActivity.this, "Failed to logout because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void logout() {
        userService.logout(new LogoutObserver());
    }

    public class GetFollowersCountObserver implements UserService.GetFollowersCountObserver {

        @Override
        public void handleSuccess(int count) {
            view.getFollowerCount(count);
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to get followers count: \" + message");
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to get followers count because of exception: " + ex.getMessage());
        }
    }

    public void getFollowersCount(User selectedUser) {
        userService.GetFollowersCount(selectedUser, new GetFollowersCountObserver());
    }

    public class GetFollowingCountObserver implements UserService.GetFollowingCountObserver {

        @Override
        public void handleSuccess(int count) {
            view.getFollowingCount(count);
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to get following count: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to get following count because of exception: " + ex.getMessage());
        }
    }

    public void getFollowingCount(User selectedUser) {
        userService.GetFollowingCount(selectedUser, new GetFollowingCountObserver());
    }

    public class IsFollowerObserver implements UserService.IsFollowerObserver {

        @Override
        public void handleSuccess(boolean isFollower) {
            view.checkFollower(isFollower);
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to determine following relationship: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to determine following relationship because of exception: " + ex.getMessage());
        }
    }

    public void isFollower(User selectedUser) {
        userService.isFollower(selectedUser, new IsFollowerObserver());
    }

    public class FollowObserver implements UserService.FollowObserver {

        @Override
        public void handleSuccess() {
            view.setFollow();
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to follow: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to follow because of exception: " + ex.getMessage());
        }
    }

    public void follow(User selectedUser) {
        userService.follow(selectedUser, new FollowObserver());
    }

    public class UnfollowerObserver implements UserService.UnfollowObserver {

        @Override
        public void handleSuccess() {
            view.setFollow();
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to unfollow: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to unfollow because of exception: " + ex.getMessage());
        }
    }

    public void unfollow(User selectedUser) {
        userService.unfollow(selectedUser, new UnfollowerObserver());
    }

    public class PostStatusObserver implements StatusService.PostStatusObserver {

        @Override
        public void handleSuccess() {
            view.post();
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to post status: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to post status because of exception: " + ex.getMessage());
        }
    }

    public void postStatus(Status newStatus) {
        statusService.postStatus(newStatus, new PostStatusObserver());
    }
}
