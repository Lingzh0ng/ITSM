package com.wearapay.lightning.net.callback;

/**
 * Created by swang on 6/21/16.
 */
public class PPLoginException extends RuntimeException {
  private String code;

  public PPLoginException(String code, String detailMessage) {
    super(detailMessage);
    this.code = code;
  }

  public PPLoginException(String detailMessage) {
    super(detailMessage);
  }

  public PPLoginException(Throwable throwable) {
    super(throwable);
  }

  public String getCode() {
    return code;
  }
}
