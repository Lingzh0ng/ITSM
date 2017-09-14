package com.wearapay.lightning;

import com.wearapay.lightning.ui.fragment.event.MemberFragment;
import com.wearapay.lightning.ui.fragment.login.UserInfoFragment;
import com.wearapay.lightning.ui.fragment.point.PhotoViewFragment;

/**
 * Created by lyz on 2017/7/13.
 */

public class LConsts {

  public static String FRAGMENT_TYPE = "fragment_type";
  public static String CAN_BACK = "can_back";

  public enum FragmentType {
    Member(MemberFragment.class), UserInfo(UserInfoFragment.class), PhotoView(
        PhotoViewFragment.class);

    private Class clazz;

    FragmentType(Class clazz) {
      this.clazz = clazz;
    }

    public Class getClazz() {
      return clazz;
    }
  }

  public enum ReleaseEnvironment {
    ZSC("prepro"), SC("pro");

    private String env;

    ReleaseEnvironment(String env) {
      this.env = env;
    }

    public String getEnv() {
      return env;
    }
  }

  public static String STATUS_WAITING = "STATUS_WAITING";
  public static String STATUS_PROCESS = "STATUS_PROCESS";
  public static String STATUS_SUCCESS = "STATUS_SUCCESS";
  public static String STATUS_FAILED = "STATUS_FAILED";

  public static final String USER_TOKEN = "USER_TOKEN";
  public static final String USER_TOKEN_ZSC = "USER_TOKEN_ZSC";
  public static final String TOKEN_EXPIRED = "error.user.token.expired";
  public static final String UNKONWN_USER = "error.unkonwn.user";
  public static final String ERROR_NETWORK = "error_network";

  public static String ITSM = "itsm/";

  public static String ITSM_HSOT = "http://zabbix.wearapay.com/" + ITSM;
  public static String ITSM_HSOT_ZSC = "http://115.29.114.73:8100/" + ITSM;

  public static final int INCIDENT_OCCUR = 1;
  public static final int INCIDENT_HANDLE = 2;
  public static final int INCIDENT_CLOSE = 3;

  public static final long MONTH_TIME = 30 * 24 * 60 * 60 * 1000L;
  public static final long YEAR_TIME = 12 * MONTH_TIME;

  public static final int DAY_TIME_MIN = 24 * 60 * 60;
  public static final int WEEK_TIME_MIN = DAY_TIME_MIN * 7;
  public static final int MONTH_TIME_MIM = DAY_TIME_MIN * 30;
}
