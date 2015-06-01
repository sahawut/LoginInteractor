package usecases;

import authorizer.InvalidUserID;
import authorizer.UserID;

public class RejectingAuthorizerSpy extends AuthorizerSpy {
  protected UserID makeUser() {
    return new InvalidUserID();
  }
}
