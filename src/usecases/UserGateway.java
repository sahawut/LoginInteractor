package usecases;

import authorizer.UserID;
import entities.User;

public interface UserGateway {
  public User getUser(UserID id);
}
