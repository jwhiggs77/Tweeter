package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.MainActivityService;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter {

    public interface View {
        void logout();

        void setFollow();

        void displayErrorMessage(String message);

        void checkFollower(boolean isFollower);

        void getFollowerCount(int count);

        void getFollowingCount(int count);
    }

    private View view;
    private MainActivityService mainActivityService;

    MainActivityPresenter(View view) {
        this.view = view;
        mainActivityService = new MainActivityService();
    }

//    public void updateSelectedUserFollowingAndFollowers(User selectedUser) {
//        mainActivityService.updateSelectedUserFollowingAndFollowers(selectedUser);
//    }

    public class LogoutObserver implements MainActivityService.LogoutObserver {

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
        mainActivityService.logout(new LogoutObserver());
    }

    public class GetFollowersCountObserver implements MainActivityService.GetFollowersCountObserver {

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
        mainActivityService.GetFollowersCount(selectedUser, new GetFollowersCountObserver());
    }

    public class GetFollowingCountObserver implements MainActivityService.GetFollowingCountObserver {

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
        mainActivityService.GetFollowingCount(selectedUser, new GetFollowingCountObserver());
    }

    public class IsFollowerObserver implements MainActivityService.IsFollowerObserver {

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
        mainActivityService.isFollower(selectedUser, new IsFollowerObserver());
    }

    public class FollowObserver implements MainActivityService.FollowObserver {

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
        mainActivityService.follow(selectedUser, new FollowObserver());
    }

    public class UnfollowerObserver implements MainActivityService.UnfollowObserver {

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
        mainActivityService.unfollow(selectedUser, new UnfollowerObserver());
    }
}
