package com.wearapay.lightning.bean;

import java.io.Serializable;

/**
 * Created by lyz on 2017/7/13.
 */
public class UserConfDto implements Serializable {
  private String id;
  private String ldapUserName;
  private String name;
  private String department;
  private String departPosition;
  private String evenPosition;
  private String changePosition;
  private String configuePosition;
  private String team;
  private String phone;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
  }

  private String email;

  @Override public String toString() {
    return "UserConfDto{" +
        "id='" + id + '\'' +
        ", ldapUserName='" + ldapUserName + '\'' +
        ", name='" + name + '\'' +
        ", department='" + department + '\'' +
        ", departPosition='" + departPosition + '\'' +
        ", evenPosition='" + evenPosition + '\'' +
        ", changePosition='" + changePosition + '\'' +
        ", configuePosition='" + configuePosition + '\'' +
        ", phone='" + phone + '\'' +
        '}';
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLdapUserName() {
    return ldapUserName;
  }

  public void setLdapUserName(String ldapUserName) {
    this.ldapUserName = ldapUserName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getDepartPosition() {
    return departPosition;
  }

  public void setDepartPosition(String departPosition) {
    this.departPosition = departPosition;
  }

  public String getEvenPosition() {
    return evenPosition;
  }

  public void setEvenPosition(String evenPosition) {
    this.evenPosition = evenPosition;
  }

  public String getChangePosition() {
    return changePosition;
  }

  public void setChangePosition(String changePosition) {
    this.changePosition = changePosition;
  }

  public String getConfiguePosition() {
    return configuePosition;
  }

  public void setConfiguePosition(String configuePosition) {
    this.configuePosition = configuePosition;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
