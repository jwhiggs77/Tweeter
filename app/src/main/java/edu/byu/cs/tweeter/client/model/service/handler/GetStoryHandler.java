package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.model.domain.Status;

import java.util.List;

/**
 * Message handler (i.e., observer) for GetStoryTask.
 */
public class GetStoryHandler extends Handler {
    private StatusService.GetStoryObserver observer;

    public GetStoryHandler(StatusService.GetStoryObserver observer) {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {

        boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
        if (success) {
            List<Status> statuses = (List<Status>) msg.getData().getSerializable(PagedTask.ITEMS_KEY);
            boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);
            observer.handleSuccess(statuses, hasMorePages);
        } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
            observer.handleException(ex);
        }
    }
}
