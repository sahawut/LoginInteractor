package usecases;

import authorizer.UserID;
import entities.User;
import entities.UserStub;

public class UserGatewaySpy implements UserGateway {
  private UserID requestedId;

  public User getUser(UserID id) {
    requestedId = id;
    return new UserStub();
  }

  public UserID getRequestedId() {
    return requestedId;
  }
}
