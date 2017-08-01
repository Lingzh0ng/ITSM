package com.wearapay.lightning.bean;

import java.io.Serializable;

/**
 * Created by lyz on 2017/8/1.
 */
public class BAutoDeploy implements Serializable {
  private int deploySeq;
  private String scriptType;
  private String scriptName;
  private String shell;
  private String status;
  private String log;

  @Override public String toString() {
    return "BAutoDeploy{" +
        "deploySeq=" + deploySeq +
        ", scriptType='" + scriptType + '\'' +
        ", scriptName='" + scriptName + '\'' +
        ", shell='" + shell + '\'' +
        ", status='" + status + '\'' +
        ", log='" + log + '\'' +
        '}';
  }

  public int getDeploySeq() {
    return deploySeq;
  }

  public void setDeploySeq(int deploySeq) {
    this.deploySeq = deploySeq;
  }

  public String getScriptType() {
    return scriptType;
  }

  public void setScriptType(String scriptType) {
    this.scriptType = scriptType;
  }

  public String getScriptName() {
    return scriptName;
  }

  public void setScriptName(String scriptName) {
    this.scriptName = scriptName;
  }

  public String getShell() {
    return shell;
  }

  public void setShell(String shell) {
    this.shell = shell;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLog() {
    return log;
  }

  public void setLog(String log) {
    this.log = log;
  }
}
