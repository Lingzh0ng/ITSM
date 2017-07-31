package com.wearapay.lightning;

import com.wearapay.lightning.ui.fragment.MemberFragment;

/**
 * Created by lyz on 2017/7/13.
 */

public class LConsts {

  public static String FRAGMENT_TYPE = "fragment_type";
  public static String CAN_BACK = "can_back";

  public enum FragmentType {
    Member(MemberFragment.class);

    private Class clazz;

    FragmentType(Class clazz) {
      this.clazz = clazz;
    }

    public Class getClazz() {
      return clazz;
    }
  }

  public static final String USER_TOKEN = "USER_TOKEN";
  public static final String TOKEN_EXPIRED = "error.user.token.expired";
  public static final String UNKONWN_USER = "error.unkonwn.user";
  public static final String ERROR_NETWORK = "error_network";

  public static String ITSM = "itsm/";

  public static String ITSM_HSOT = "http://zabbix.wearapay.com/" + ITSM;

  public static final int INCIDENT_OCCUR = 1;
  public static final int INCIDENT_HANDLE = 2;
  public static final int INCIDENT_CLOSE = 3;

  public static final long MONTH_TIME = 30 * 24 * 60 * 60 * 1000L;
  public static final long YEAR_TIME = 12 * MONTH_TIME;
}
