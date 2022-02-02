package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetFeedHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetStoryHandler;
import edu.byu.cs.tweeter.client.model.service.handler.PostStatusHandler;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatusService {

    public interface PostStatusObserver {
        void handleSuccess();

        void handleMessage(String message);

        void handleException(Exception ex);
    }

    public void postStatus(Status newStatus, PostStatusObserver postStatusObserver) {
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new PostStatusHandler(postStatusObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(statusTask);
    }

    public interface GetFeedObserver {
        void handleSuccess(List<Status> statuses, boolean hasMorePages);

        void handleFailure(String message);

        void handleException(Exception exception);
    }

    public void getFeed(User user, int pageSize, Status lastStatus, GetFeedObserver getFeedObserver) {
        GetFeedTask getFeedTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new GetFeedHandler(getFeedObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFeedTask);
    }

    public interface GetStoryObserver {
        void handleSuccess(List<Status> statuses, boolean hasMorePages);

        void handleFailure(String message);

        void handleException(Exception exception);
    }

    public void getStory(User user, int pageSize, Status lastStatus, GetStoryObserver getStoryObserver) {
        GetStoryTask getStoryTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new GetStoryHandler(getStoryObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getStoryTask);
    }

}
