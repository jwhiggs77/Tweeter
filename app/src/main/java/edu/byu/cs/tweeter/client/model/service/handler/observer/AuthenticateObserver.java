package edu.byu.cs.tweeter.client.model.service.handler.observer;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface AuthenticateObserver extends ServiceObserver {
    void handleSuccess(User loggedInUser, AuthToken authToken);
}
