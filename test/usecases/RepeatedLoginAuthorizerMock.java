package usecases;

import authorizer.Authorizer;
import authorizer.InvalidUserID;
import authorizer.UserID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepeatedLoginAuthorizerMock implements Authorizer {
  private List<String> actions = new ArrayList<String>();

  public UserID authorize(String username, String password) {
    actions.add("Authorize:" + username);
    return new InvalidUserID();
  }

  public void hold(String username) {
    actions.add("Hold:"+username);
  }

  public boolean verifyHeldOnThirdAttempt(String username) {
    final List<String> expectedActions = Arrays.asList(
      "Authorize:"+username,
      "Authorize:"+username,
      "Authorize:"+username,
      "Hold:"+username);

    return actions.equals(expectedActions);
  }
}
