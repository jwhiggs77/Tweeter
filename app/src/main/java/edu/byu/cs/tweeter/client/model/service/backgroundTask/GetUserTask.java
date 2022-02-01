package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends BackgroundTask {
//    private static final String LOG_TAG = "GetUserTask";

    /**
     * Auth token for logged-in user.
     */
    private AuthToken authToken;
    /**
     * Alias (or handle) for user whose profile is being retrieved.
     */
    private String alias;
//    /**
//     * Message handler that will receive task results.
//     */
//    private Handler messageHandler;

    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler) {
        super(messageHandler);
        this.authToken = authToken;
        this.alias = alias;
    }

    private User getUser() {
        User user = getFakeData().findUserByAlias(alias);
        return user;
    }

    @Override
    protected void runTask() {
        // TODO: This is empty only cuz we're using dummy data
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, getUser());
    }
}
