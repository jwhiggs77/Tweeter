package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.handler.observer.GetUserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public abstract class PagedPresenter<T> {

    public interface View {
        void displayErrorMessage(String message);

        void setLoadingStatus(boolean value);

        void addFollowers(List<User> followers);

        void startActivity(User user);
    }

    private static final int PAGE_SIZE = 10;
    protected T lastItem;
    protected boolean hasMorePages;
    protected boolean isLoading = false;

    protected View view;
    private UserService userService;

    public PagedPresenter(View view) {
        this.view = view;
        userService = new UserService();
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            setLoading(true);
            view.setLoadingStatus(isLoading());
            getItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        }
    }

    public abstract void getItems(AuthToken authToken, User user, int pageSize, T lastItem);

    public void getUser(String userAlias) {
        userService.getUser(Cache.getInstance().getCurrUserAuthToken(), userAlias, new GetUserObserver());
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    protected class GetUserObserver implements edu.byu.cs.tweeter.client.model.service.handler.observer.GetUserObserver {

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
}
