package usecases;

public interface LoginInteractor {
  public static final String LOGIN_FAILURE_MESSAGE = "Invalid Login";
  public static final String LOGIN_SUCCESS_MESSAGE = "Welcome";


  void login(LoginRequest request);
}
