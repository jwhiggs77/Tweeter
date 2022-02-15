package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.handler.observer.GetCountObserver;
import edu.byu.cs.tweeter.client.model.service.handler.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter extends Presenter {

    public interface View extends Presenter.View {
        void logout();

        void setFollow();

        void checkFollower(boolean isFollower);

        void getFollowerCount(int count);

        void getFollowingCount(int count);

        void post();
    }

    private View view;
    private UserService userService;
    private FollowService followService;
    private StatusService statusService;

    public MainActivityPresenter(View view) {
        this.view = view;
        userService = new UserService();
        followService = new FollowService();
        statusService = new StatusService();
    }

    public class LogoutObserver extends Observer implements SimpleNotificationObserver {

        @Override
        public void handleSuccess() {
            view.logout();
        }

        @Override
        public String getMessageTag() {
            return "Failed to logout: ";
        }
    }

    public void logout() {
        userService.logout(new LogoutObserver());
    }

    public class GetFollowersCountObserver extends Observer implements GetCountObserver {

        @Override
        public void handleSuccess(int count) {
            view.getFollowerCount(count);
        }

        @Override
        public String getMessageTag() {
            return "Failed to get followers count: ";
        }
    }

    public void getFollowersCount(User selectedUser) {
        userService.GetFollowersCount(selectedUser, new GetFollowersCountObserver());
    }

    public class GetFollowingCountObserver extends Observer implements GetCountObserver {

        @Override
        public void handleSuccess(int count) {
            view.getFollowingCount(count);
        }

        @Override
        public String getMessageTag() {
            return "Failed to get following count: ";
        }
    }

    public void getFollowingCount(User selectedUser) {
        userService.GetFollowingCount(selectedUser, new GetFollowingCountObserver());
    }

    public class IsFollowerObserver extends Observer implements edu.byu.cs.tweeter.client.model.service.handler.observer.IsFollowerObserver {

        @Override
        public void handleSuccess(boolean isFollower) {
            view.checkFollower(isFollower);
        }

        @Override
        public String getMessageTag() {
            return "Failed to determine following relationship: ";
        }
    }

    public void isFollower(User selectedUser) {
        userService.isFollower(selectedUser, new IsFollowerObserver());
    }

    public class FollowObserver extends Observer implements SimpleNotificationObserver {

        @Override
        public void handleSuccess() {
            view.setFollow();
        }

        @Override
        public String getMessageTag() {
            return "Failed to follow: ";
        }
    }

    public void follow(User selectedUser) {
        followService.follow(selectedUser, new FollowObserver());
    }

    public class UnfollowerObserver extends Observer implements SimpleNotificationObserver {

        @Override
        public void handleSuccess() {
            view.setFollow();
        }

        @Override
        public String getMessageTag() {
            return "Failed to unfollow: ";
        }
    }

    public void unfollow(User selectedUser) {
        followService.unfollow(selectedUser, new UnfollowerObserver());
    }

    public class PostStatusObserver extends Observer implements SimpleNotificationObserver {

        @Override
        public void handleSuccess() {
            view.post();
        }

        @Override
        public String getMessageTag() {
            return "Failed to post status: ";
        }
    }

    public void postStatus(Status newStatus) {
        statusService.postStatus(newStatus, new PostStatusObserver());
    }
}
