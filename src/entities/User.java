package entities;

import java.util.Calendar;

public class User {
  private String name;
  private Calendar lastLoginTime;
  private int loginCount;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Calendar getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Calendar lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public int getLoginCount() {
    return loginCount;
  }

  public void setLoginCount(int loginCount) {
    this.loginCount = loginCount;
  }
}
