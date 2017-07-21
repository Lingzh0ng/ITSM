package com.wearapay.lightning.exception;

public class NotLoginException extends RuntimeException {
  public NotLoginException(String detailMessage) {
    super(detailMessage);
  }
}
