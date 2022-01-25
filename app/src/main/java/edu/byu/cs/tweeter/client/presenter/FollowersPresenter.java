package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class FollowersPresenter {
    private static final int PAGE_SIZE = 10;

    public interface View {
        void displayErrorMessage(String message);
        void setLoadingStatus(boolean value);
        void addFollowers(List<User> followers);
    }

    private View view;
    private FollowService followService;

    private User lastFollower;

    private boolean hasMorePages;
    private boolean isLoading = false;

    public FollowersPresenter(View view) {
        this.view = view;
        followService = new FollowService();
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
            isLoading = true;
            view.setLoadingStatus(true);
            followService.getFollowers(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(), PAGE_SIZE, lastFollower, new GetFollowersObserver());
        }
    }

    public class GetFollowersObserver implements FollowService.GetFollowersObserver {

        @Override
        public void handleSuccess(List<User> followers, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingStatus(false);
            lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.addFollowers(followers);
        }

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingStatus(false);
            view.displayErrorMessage("Failed to get following: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            isLoading = false;
            view.setLoadingStatus(false);
            view.displayErrorMessage("Failed to get following because of exception: " + exception.getMessage());
        }
    }
}