package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.handler.observer.PagedNotificationObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class FollowersPresenter extends PagedPresenterUser {
    private FollowService followService;

    public FollowersPresenter(View view) {
        super(view);
        followService = new FollowService();
    }

    @Override
    public void getItems(AuthToken authToken, User user, int pageSize, User lastFollower) {
        followService.getFollowers(authToken, user, pageSize, lastFollower, new GetFollowersObserver());
    }
}