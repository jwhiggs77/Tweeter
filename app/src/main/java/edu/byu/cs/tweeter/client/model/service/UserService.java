package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.*;
import edu.byu.cs.tweeter.client.model.service.handler.*;
import edu.byu.cs.tweeter.client.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserService {
    public interface GetUserObserver {
        void handleSuccess(User user);

        void handleException(Exception ex);

        void handleMessage(String message);
    }

    public void getUser(AuthToken currUserAuthToken, String userAlias, GetUserObserver getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(currUserAuthToken,
                userAlias, new GetUserHandler(getUserObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }

    public interface LoginObserver {
        void handleSuccess(User loggedInUser, AuthToken authToken);

        void handleException(Exception ex);

        void handleMessage(String message);
    }

    public void login(String alias, String password, LoginObserver loginObserver) {
        // Send the login request.
        LoginTask loginTask = new LoginTask(alias,
                password,
                new LoginHandler(loginObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(loginTask);
    }

    public interface RegisterObserver {
        void handleSuccess(User registeredUser, AuthToken authToken);

        void handleMessage(String message);

        void handleException(Exception ex);
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64, RegisterObserver registerObserver) {
        // Send register request.
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new RegisterHandler(registerObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(registerTask);
    }

    public interface LogoutObserver {
        void handleSuccess();

        void handleMessage(String message);

        void handleException(Exception ex);
    }

    public void logout(LogoutObserver logoutObserver) {
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandler(logoutObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(logoutTask);
    }

    public interface GetFollowersCountObserver {
        void handleSuccess(int count);

        void handleMessage(String message);

        void handleException(Exception ex);
    }

    public void GetFollowersCount(User selectedUser, UserService.GetFollowersCountObserver getFollowersCountObserver) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowersCountHandler(getFollowersCountObserver));
        executor.execute(followersCountTask);
    }

    public interface GetFollowingCountObserver {
        void handleSuccess(int count);

        void handleMessage(String message);

        void handleException(Exception ex);
    }

    public void GetFollowingCount(User selectedUser, UserService.GetFollowingCountObserver getFollowingCountObserver) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowingCountHandler(getFollowingCountObserver));
        executor.execute(followingCountTask);
    }

    public interface IsFollowerObserver {

        void handleSuccess(boolean isFollower);

        void handleMessage(String message);

        void handleException(Exception ex);
    }

    public void isFollower(User selectedUser, MainActivityPresenter.IsFollowerObserver isFollowerObserver) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(isFollowerObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }

}
