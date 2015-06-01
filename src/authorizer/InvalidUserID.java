package authorizer;

public class InvalidUserID extends UserID {
  public InvalidUserID() {
    super(-1);
  }

  public boolean isValid() {
    return false;
  }
}
