package com.wearapay.lightning.net.callback;

import com.wearapay.lightning.net.model.PPCodeMessage;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Leo Ren(leo.ren@paypos.ca) on 10/8/15.
 */
public class PPCodedException extends RuntimeException {
  private Collection<PPCodeMessage> messages;

  public PPCodedException(String code, String message) {
    Collection<PPCodeMessage> collections = new ArrayList<>();
    collections.add(new PPCodeMessage(code, message));
    messages = collections;
  }

  public PPCodedException(Collection<PPCodeMessage> messages) {
    this.messages = messages;
  }

  public Collection<PPCodeMessage> getMessages() {
    return this.messages;
  }

  @Override public String getMessage() {
    StringBuilder sb = new StringBuilder();
    for (PPCodeMessage m : messages) {
      sb.append("{code=" + m.getCode() + ",message=" + m.getMessage() + "}\n");
    }
    return sb.toString();
  }
}
