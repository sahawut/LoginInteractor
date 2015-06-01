package usecases;

import authorizer.UserID;

public class AcceptingAuthorizerSpy extends AuthorizerSpy {
  public static final UserID STUB_ID = new UserID(1);

  protected UserID makeUser() {
    return STUB_ID;
  }
}
