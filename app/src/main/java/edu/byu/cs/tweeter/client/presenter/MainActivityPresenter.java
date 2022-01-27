package edu.byu.cs.tweeter.client.presenter;

import android.widget.Toast;
import edu.byu.cs.tweeter.client.model.service.MainActivityService;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter {

    public interface View {
        void logout();
        void displayErrorMessage(String message);
    }

    private View view;
    private MainActivityService mainActivityService;

    MainActivityPresenter(View view) {
        this.view = view;
        mainActivityService = new MainActivityService();
    }

    public void updateSelectedUserFollowingAndFollowers(User selectedUser) {
        mainActivityService.updateSelectedUserFollowingAndFollowers(selectedUser);
    }

    public class LogoutObserver implements MainActivityService.LogoutObserver {

        @Override
        public void handleSuccess() {
            view.logout();
//            logOutToast.cancel();
//            logoutUser();
        }

        @Override
        public void handleMessage(String message) {
            view.displayErrorMessage("Failed to logout: " + message);
//            Toast.makeText(MainActivity.this, "Failed to logout: " + message, Toast.LENGTH_LONG).show();
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to logout because of exception: " + ex.getMessage());
//            Toast.makeText(MainActivity.this, "Failed to logout because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void logout() {
        mainActivityService.logout(new LogoutObserver());
    }


}
