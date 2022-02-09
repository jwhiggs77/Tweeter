package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.handler.observer.GetUserObserver;
import edu.byu.cs.tweeter.client.model.service.handler.observer.PagedNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class FollowingPresenter {
    private static final int PAGE_SIZE = 10;

    public interface View {
        void displayErrorMessage(String message);

        void setLoadingStatus(boolean value);

        void addFollowees(List<User> followees);

        void startActivity(User user);
    }

    private View view;
    private FollowService followService;
    private UserService userService;

    private User lastFollowee;

    private boolean hasMorePages;
    private boolean isLoading = false;

    public FollowingPresenter(View view) {
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            setLoading(true);
            view.setLoadingStatus(isLoading());
            followService.getFollowing(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastFollowee, new GetFollowingObserver());
        }
    }

    public class GetFollowingObserver implements PagedNotificationObserver<User> {

        @Override
        public void handleSuccess(List<User> followees, boolean hasMorePages) {
            setLoading(false);
            view.setLoadingStatus(isLoading());
            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.addFollowees(followees);
        }

        @Override
        public void handleMessage(String message) {
            setLoading(false);
            view.setLoadingStatus(isLoading());
            view.displayErrorMessage("Failed to get following: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            setLoading(false);
            view.setLoadingStatus(isLoading());
            view.displayErrorMessage("Failed to get following because of exception: " + exception.getMessage());
        }
    }

    private class GetUserObserver implements edu.byu.cs.tweeter.client.model.service.handler.observer.GetUserObserver {

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
