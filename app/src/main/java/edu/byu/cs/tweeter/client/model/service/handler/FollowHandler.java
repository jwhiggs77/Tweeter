package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;

// FollowHandler
public class FollowHandler extends Handler {
    FollowService.FollowObserver observer;

    public FollowHandler(FollowService.FollowObserver observer) {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(FollowTask.SUCCESS_KEY);
        if (success) {
            observer.handleSuccess();
        } else if (msg.getData().containsKey(FollowTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(FollowTask.MESSAGE_KEY);
            observer.handleMessage(message);
        } else if (msg.getData().containsKey(FollowTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);
            observer.handleException(ex);
        }
    }
}
