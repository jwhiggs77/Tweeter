package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;

// UnfollowHandler
public class UnfollowHandler extends Handler {
    FollowService.UnfollowObserver observer;

    public UnfollowHandler(FollowService.UnfollowObserver observer) {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(UnfollowTask.SUCCESS_KEY);
        if (success) {
            observer.handleSuccess();
        } else if (msg.getData().containsKey(UnfollowTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(UnfollowTask.MESSAGE_KEY);
            observer.handleMessage(message);
        } else if (msg.getData().containsKey(UnfollowTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(UnfollowTask.EXCEPTION_KEY);
            observer.handleException(ex);
        }

    }
}
