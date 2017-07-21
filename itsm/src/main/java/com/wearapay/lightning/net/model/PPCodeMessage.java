package com.wearapay.lightning.net.model;

/**
 * Created by Kindy on 2016/11/23.
 */
public class PPCodeMessage {
  private String code;
  private String message;

  public PPCodeMessage() {
  }

  public PPCodeMessage(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }
}
