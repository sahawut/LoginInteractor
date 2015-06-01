package authorizer;

public class RejectingAuthorizerStub implements Authorizer {
  public UserID authorize(String username, String password) {
    return new InvalidUserID();
  }

  public void hold(String username) {
  }
}
