package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.handler.observer.PagedNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public abstract class PagedPresenterUser extends PagedPresenter<User> {
    public PagedPresenterUser(View view) {
        super(view);
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public class GetFollowersObserver implements PagedNotificationObserver<User> {

        @Override
        public void handleSuccess(List<User> followers, boolean hasMorePages) {
            setLoading(false);
            view.setLoadingStatus(isLoading());
            lastItem = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.addFollowers(followers);
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
}
