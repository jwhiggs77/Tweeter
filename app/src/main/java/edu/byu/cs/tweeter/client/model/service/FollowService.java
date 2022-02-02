package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.*;
import edu.byu.cs.tweeter.client.model.service.handler.FollowHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowersHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowingHandler;
import edu.byu.cs.tweeter.client.model.service.handler.UnfollowHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FollowService {

    public interface GetFollowingObserver {
        void handleSuccess(List<User> followees, boolean hasMorePages);

        void handleFailure(String message);

        void handleException(Exception exception);
    }

    public void getFollowing(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, GetFollowingObserver getFollowingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowingHandler(getFollowingObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);
    }

    public interface GetFollowersObserver {
        void handleSuccess(List<User> followees, boolean hasMorePages);

        void handleFailure(String message);

        void handleException(Exception exception);
    }

    public void getFollowers(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, GetFollowersObserver getFollowersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowersHandler(getFollowersObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowersTask);
    }

    public interface FollowObserver {
        void handleSuccess();

        void handleMessage(String message);

        void handleException(Exception ex);
    }

    public void follow(User selectedUser, FollowService.FollowObserver followObserver) {
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowHandler(followObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followTask);
    }

    public interface UnfollowObserver {
        void handleSuccess();

        void handleMessage(String message);

        void handleException(Exception ex);
    }

    public void unfollow(User selectedUser, FollowService.UnfollowObserver unfollowObserver) {
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new UnfollowHandler(unfollowObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(unfollowTask);
    }
}
