package com.wearapay.lightning.net;

/**
 * Created by lyz on 2017/7/12.
 */

public interface ILocalHelper {

  String getUserId();

  void storeUserId(String userId);

  boolean loginStatus();

  String getEmail();

  void storeEmail(String email);

  void storeZSCUserId(String userId);

  String getZSCUserId();

  void storeUserId(String userId, String ZSCUserId);

  void localLogout();
}
