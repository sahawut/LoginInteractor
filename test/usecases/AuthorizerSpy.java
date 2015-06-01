package usecases;

import authorizer.Authorizer;
import authorizer.UserID;

public abstract class AuthorizerSpy implements Authorizer {
  private String username;
  private String password;
  private String heldUsername = null;

  public UserID authorize(String username, String password) {
    this.username = username;
    this.password = password;
    return makeUser();
  }

  public void hold(String username) {
    heldUsername = username;
  }

  protected abstract UserID makeUser();

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String heldUsername() {
    return heldUsername;
  }
}
