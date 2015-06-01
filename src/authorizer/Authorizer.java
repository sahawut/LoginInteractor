package authorizer;

public interface Authorizer {
  public UserID authorize(String username, String password);
  public void hold(String username);
}

