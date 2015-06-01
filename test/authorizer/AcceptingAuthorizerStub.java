package authorizer;

public class AcceptingAuthorizerStub implements Authorizer {
  public UserID authorize(String username, String password) {
    return new UserID(1);
  }

  public void hold(String username) {
  }
}
