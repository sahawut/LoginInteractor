package usecases;

import authorizer.UserID;
import entities.User;
import entities.UserStub;

public class UserGatewayStub implements UserGateway {
  public User getUser(UserID id) {
    return new UserStub();
  }
}
