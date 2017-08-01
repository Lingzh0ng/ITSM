package com.wearapay.lightning.bean;

import java.io.Serializable;

/**
 * Created by lyz on 2017/8/1.
 */
public class BAutoDeployInfo implements Serializable {
  private String id;
  private String scriptType;
  private String scriptName;
  private String deploySystem;
  private String deployVersion;
  private String ip;

  @Override public String toString() {
    return "BAutoDeployInfo{" +
        "id='" + id + '\'' +
        ", scriptType='" + scriptType + '\'' +
        ", scriptName='" + scriptName + '\'' +
        ", deploySystem='" + deploySystem + '\'' +
        ", deployVersion='" + deployVersion + '\'' +
        ", ip='" + ip + '\'' +
        '}';
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getDeploySystem() {
    return deploySystem;
  }

  public void setDeploySystem(String deploySystem) {
    this.deploySystem = deploySystem;
  }

  public String getDeployVersion() {
    return deployVersion;
  }

  public void setDeployVersion(String deployVersion) {
    this.deployVersion = deployVersion;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }
}
