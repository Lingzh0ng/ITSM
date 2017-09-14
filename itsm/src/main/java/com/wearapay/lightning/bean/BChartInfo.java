package com.wearapay.lightning.bean;

import java.io.Serializable;

/**
 * Created by lyz on 2017/9/11.
 */

public class BChartInfo implements Serializable {
  private int period;
  private String serviceNameC;
  private String url;
  private String appDisplay;

  public BChartInfo(int period, String applicationNameC, String url) {
    this.period = period;
    this.serviceNameC = applicationNameC;
    this.url = url;
  }

  public BChartInfo() {
  }

  @Override public String toString() {
    return "BChartInfo{"
        + "period="
        + period
        + ", applicationNameC='"
        + serviceNameC
        + '\''
        + ", url='"
        + url
        + '\''
        + '}';
  }

  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public String getServiceNameC() {
    return serviceNameC;
  }

  public void setServiceNameC(String serviceNameC) {
    this.serviceNameC = serviceNameC;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getAppDisplay() {
    return appDisplay;
  }

  public void setAppDisplay(String appDisplay) {
    this.appDisplay = appDisplay;
  }
}
