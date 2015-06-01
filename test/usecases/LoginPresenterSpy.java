package usecases;

public class LoginPresenterSpy implements LoginPresenter {
  private LoginResponse invokedResponse;

  public void presentResponse(LoginResponse response) {
    invokedResponse = response;
  }

  public LoginResponse getInvokedResponse() {
    return invokedResponse;
  }
}
