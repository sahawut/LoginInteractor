package usecases;

import authorizer.Authorizer;
import authorizer.UserID;
import entities.User;

import java.util.HashMap;

public class LoginInteractorImpl implements LoginInteractor {
  private Authorizer authorizer;
  private UserGateway userGateway;
  private LoginPresenter presenter;
  private HashMap<String, Integer> loginAttemptCounter = new HashMap<String, Integer>();

  public void setAuthorizer(Authorizer authorizer) {
    this.authorizer = authorizer;
  }

  public void setUserGateway(UserGateway userGateway) {
    this.userGateway = userGateway;
  }

  public void setPresenter(LoginPresenter presenter) {
    this.presenter = presenter;
  }

  public void login(LoginRequest request) {
    LoginResponse response = new LoginResponse();

    UserID userID = authorizer.authorize(request.username, request.password);
    if (!userID.isValid()) {
      response.message = LOGIN_FAILURE_MESSAGE;

      if (countInvalidLoginAttempts(request.username) >= 3) {
        authorizer.hold(request.username);
      }
    } else {
      response.message = LOGIN_SUCCESS_MESSAGE;

      User user = userGateway.getUser(userID);
      response.name = user.getName();
      response.lastLoginTime = user.getLastLoginTime();
      response.loginCount = user.getLoginCount();
    }
    presenter.presentResponse(response);
  }

  private int countInvalidLoginAttempts(String username) {
    Integer loginAttempts = loginAttemptCounter.get(username);
    if (loginAttempts == null)
      loginAttempts = 0;
    loginAttempts++;
    loginAttemptCounter.put(username, loginAttempts);
    return loginAttempts;
  }
}
