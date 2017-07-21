package com.wearapay.lightning.net.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by leo on 3/2/16.
 */
public class PPDeviceToken implements Serializable {
  private String token;
  private long expireTime;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(long expireTime) {
    this.expireTime = expireTime;
  }

  public boolean isExpired() {
    return Calendar.getInstance().getTimeInMillis() > expireTime;
  }
}
