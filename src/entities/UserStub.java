package entities;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class UserStub extends User {
  public static final Calendar STUB_TIME =
    new GregorianCalendar(2013, 11, 13, 10, 31);
  public static final String STUB_NAME = "name stub";
  public static final int STUB_LOGIN_COUNT = 99;

  public int getLoginCount() {
    return STUB_LOGIN_COUNT;
  }

  public Calendar getLastLoginTime() {
    return STUB_TIME;
  }

  public String getName() {
    return STUB_NAME;
  }
}
