package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.handler.observer.GetUserObserver;
import edu.byu.cs.tweeter.client.model.service.handler.observer.PagedNotificationObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class FeedPresenter {
    private static final int PAGE_SIZE = 10;

    public interface View {
        void displayErrorMessage(String message);

        void setLoadingStatus(boolean value);

        void startActivity(User user);

        void addItems(List<Status> statuses);
    }

    private View view;
    private UserService userService;
    private StatusService statusService;

    private Status lastStatus;

    private boolean hasMorePages;
    private boolean isLoading = false;

    public FeedPresenter(View view) {
        this.view = view;
        userService = new UserService();
        statusService = new StatusService();
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
            statusService.getFeed(user, PAGE_SIZE, lastStatus, new GetFeedObserver());
        }
    }

    public class GetFeedObserver implements PagedNotificationObserver<Status> {

        @Override
        public void handleSuccess(List<Status> statuses, boolean hasMorePages) {
            setLoading(false);
            view.setLoadingStatus(isLoading());
            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.addItems(statuses);
        }

        @Override
        public void handleMessage(String message) {
            setLoading(false);
            view.setLoadingStatus(isLoading());
            view.displayErrorMessage("Failed to get feed: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            setLoading(false);
            view.setLoadingStatus(isLoading());
            view.displayErrorMessage("Failed to get feed because of exception: " + ex.getMessage());
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
