package com.wearapay.lightning.net.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Kindy on 2016/11/23.
 */
public class PPResultBean<T> {

  public static final String SUCCESS = "success";
  public static final String ERROR = "error";
  private String status = "success";
  private Collection<PPCodeMessage> errors = Collections.synchronizedList(new ArrayList());
  private T data;

  public PPResultBean() {
  }

  public PPResultBean<T> addError(PPCodeMessage error) {
    this.errors.add(error);
    this.status = "error";
    return this;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Collection<PPCodeMessage> getErrors() {
    return this.errors;
  }

  public boolean hasErrors() {
    return !this.errors.isEmpty();
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
