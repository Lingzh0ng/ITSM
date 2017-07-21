package com.wearapay.lightning;

/**
 * Created by lyz on 2017/7/13.
 */

public class LConsts {
  public static final String USER_TOKEN = "USER_TOKEN";
  public static final String TOKEN_EXPIRED = "error.user.token.expired";
  public static final String UNKONWN_USER = "error.unkonwn.user";
  public static final String ERROR_NETWORK = "error_network";

  public static String ITSM = "itsm/";

  public static String ITSM_HSOT = "http://zabbix.wearapay.com/" + ITSM;

  private static final int INCIDENT_OCCUR = 1;
  private static final int INCIDENT_HANDLE = 2;
  private static final int INCIDENT_CLOSE = 3;
}
