package usecases;

import authorizer.Authorizer;
import authorizer.InvalidUserID;
import authorizer.UserID;

public class FakeAuthorizer implements Authorizer {
  public UserID authorize(String username, String password) {
    if (username.toLowerCase().startsWith("good"))
      return new UserID(1);
    else
      return new InvalidUserID();
  }

  public void hold(String username) {
  }
}
