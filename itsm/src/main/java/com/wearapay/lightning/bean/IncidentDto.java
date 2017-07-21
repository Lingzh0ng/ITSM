package com.wearapay.lightning.bean;

import java.io.Serializable;

/**
 * Created by lyz on 2017/7/13.
 */
public class IncidentDto implements Serializable {
  private String id;
  private String incidentNo;//APP用：事件号
  private String operatorStatus;//操作
  private String assignments;//APP用：被指派人
  private String status;//APP用：事件状态
  private String alarmId;//APP用：报警号
  private String alarmContent;//APP用：报警内容
  private String alarmCreationTime;//APP用：报警时间
  private String entityIp;//APP用：报警IP
  private String entityName;//APP用：报警系统
  private String alarmType;//APP用：报警类型
  private int level;//APP用：事件级别
  private String processingType;//APP用：事件类型
  private String handlingName;//APP用：事件处理人
  private String handlingStartTime;//APP用：事件处理开始时间
  private String handlingEndTime;//APP用：事件处理结束时间
  private int incidentCount;//APP用：事件连续发生次数
  private String affectedSystem;//影响其他系统
  private String isAvailabilityEffect;//是否有可用性影响
  private String effectStartTime;//可用性影响开始时间
  private String effectEndTime;//可用性影响结束时间
  private String effectRate;//可用性折算率
  private String report;//事件报告
  private String reason;//事件原因
  private String suggest;//后续改进建议
  private String isToProblem;//是否转问题
  private String mergeIncidentNo;//合并事件号
  private String isIncident;//是否为事件：非事件关闭时使用
  private String remark;//备注
  private String createdAt;//创建时间
  private String fileIdOne;//附件1
  private String fileIdTwo;//附件2
  private String fileIdThree;//附件3
  private String operatorLoginId;//操作人
  private String alarmDuration;//报警持续时间
  private String alarmValue;//报警值
  private String businessName;//报警业务

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getAlarmValue() {
    return alarmValue;
  }

  public void setAlarmValue(String alarmValue) {
    this.alarmValue = alarmValue;
  }

  public String getAlarmDuration() {
    return alarmDuration;
  }

  public void setAlarmDuration(String alarmDuration) {
    this.alarmDuration = alarmDuration;
  }

  public int getIncidentCount() {
    return incidentCount;
  }

  public void setIncidentCount(int incidentCount) {
    this.incidentCount = incidentCount;
  }

  @Override public String toString() {
    return "IncidentDto{" +
        "id='" + id + '\'' +
        ", incidentNo='" + incidentNo + '\'' +
        ", operatorStatus='" + operatorStatus + '\'' +
        ", assignments='" + assignments + '\'' +
        ", status='" + status + '\'' +
        ", alarmId='" + alarmId + '\'' +
        ", alarmContent='" + alarmContent + '\'' +
        ", alarmCreationTime='" + alarmCreationTime + '\'' +
        ", entityIp='" + entityIp + '\'' +
        ", entityName='" + entityName + '\'' +
        ", alarmType='" + alarmType + '\'' +
        ", level='" + level + '\'' +
        ", processingType='" + processingType + '\'' +
        ", handlingName='" + handlingName + '\'' +
        ", handlingStartTime='" + handlingStartTime + '\'' +
        ", handlingEndTime='" + handlingEndTime + '\'' +
        ", affectedSystem='" + affectedSystem + '\'' +
        ", isAvailabilityEffect='" + isAvailabilityEffect + '\'' +
        ", effectStartTime='" + effectStartTime + '\'' +
        ", effectEndTime='" + effectEndTime + '\'' +
        ", effectRate='" + effectRate + '\'' +
        ", report='" + report + '\'' +
        ", reason='" + reason + '\'' +
        ", suggest='" + suggest + '\'' +
        ", isToProblem='" + isToProblem + '\'' +
        ", mergeIncidentNo='" + mergeIncidentNo + '\'' +
        ", isIncident='" + isIncident + '\'' +
        ", remark='" + remark + '\'' +
        ", createdAt='" + createdAt + '\'' +
        ", fileIdOne='" + fileIdOne + '\'' +
        ", fileIdTwo='" + fileIdTwo + '\'' +
        ", fileIdThree='" + fileIdThree + '\'' +
        ", operatorLoginId='" + operatorLoginId + '\'' +
        '}';
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIncidentNo() {
    return incidentNo;
  }

  public void setIncidentNo(String incidentNo) {
    this.incidentNo = incidentNo;
  }

  public String getOperatorStatus() {
    return operatorStatus;
  }

  public void setOperatorStatus(String operatorStatus) {
    this.operatorStatus = operatorStatus;
  }

  public String getAssignments() {
    return assignments;
  }

  public void setAssignments(String assignments) {
    this.assignments = assignments;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAlarmId() {
    return alarmId;
  }

  public void setAlarmId(String alarmId) {
    this.alarmId = alarmId;
  }

  public String getAlarmContent() {
    return alarmContent;
  }

  public void setAlarmContent(String alarmContent) {
    this.alarmContent = alarmContent;
  }

  public String getAlarmCreationTime() {
    return alarmCreationTime;
  }

  public void setAlarmCreationTime(String alarmCreationTime) {
    this.alarmCreationTime = alarmCreationTime;
  }

  public String getEntityIp() {
    return entityIp;
  }

  public void setEntityIp(String entityIp) {
    this.entityIp = entityIp;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getAlarmType() {
    return alarmType;
  }

  public void setAlarmType(String alarmType) {
    this.alarmType = alarmType;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String getProcessingType() {
    return processingType;
  }

  public void setProcessingType(String processingType) {
    this.processingType = processingType;
  }

  public String getHandlingName() {
    return handlingName;
  }

  public void setHandlingName(String handlingName) {
    this.handlingName = handlingName;
  }

  public String getHandlingStartTime() {
    return handlingStartTime;
  }

  public void setHandlingStartTime(String handlingStartTime) {
    this.handlingStartTime = handlingStartTime;
  }

  public String getHandlingEndTime() {
    return handlingEndTime;
  }

  public void setHandlingEndTime(String handlingEndTime) {
    this.handlingEndTime = handlingEndTime;
  }

  public String getAffectedSystem() {
    return affectedSystem;
  }

  public void setAffectedSystem(String affectedSystem) {
    this.affectedSystem = affectedSystem;
  }

  public String getIsAvailabilityEffect() {
    return isAvailabilityEffect;
  }

  public void setIsAvailabilityEffect(String isAvailabilityEffect) {
    this.isAvailabilityEffect = isAvailabilityEffect;
  }

  public String getEffectStartTime() {
    return effectStartTime;
  }

  public void setEffectStartTime(String effectStartTime) {
    this.effectStartTime = effectStartTime;
  }

  public String getEffectEndTime() {
    return effectEndTime;
  }

  public void setEffectEndTime(String effectEndTime) {
    this.effectEndTime = effectEndTime;
  }

  public String getEffectRate() {
    return effectRate;
  }

  public void setEffectRate(String effectRate) {
    this.effectRate = effectRate;
  }

  public String getReport() {
    return report;
  }

  public void setReport(String report) {
    this.report = report;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getSuggest() {
    return suggest;
  }

  public void setSuggest(String suggest) {
    this.suggest = suggest;
  }

  public String getIsToProblem() {
    return isToProblem;
  }

  public void setIsToProblem(String isToProblem) {
    this.isToProblem = isToProblem;
  }

  public String getMergeIncidentNo() {
    return mergeIncidentNo;
  }

  public void setMergeIncidentNo(String mergeIncidentNo) {
    this.mergeIncidentNo = mergeIncidentNo;
  }

  public String getIsIncident() {
    return isIncident;
  }

  public void setIsIncident(String isIncident) {
    this.isIncident = isIncident;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getFileIdOne() {
    return fileIdOne;
  }

  public void setFileIdOne(String fileIdOne) {
    this.fileIdOne = fileIdOne;
  }

  public String getFileIdTwo() {
    return fileIdTwo;
  }

  public void setFileIdTwo(String fileIdTwo) {
    this.fileIdTwo = fileIdTwo;
  }

  public String getFileIdThree() {
    return fileIdThree;
  }

  public void setFileIdThree(String fileIdThree) {
    this.fileIdThree = fileIdThree;
  }

  public String getOperatorLoginId() {
    return operatorLoginId;
  }

  public void setOperatorLoginId(String operatorLoginId) {
    this.operatorLoginId = operatorLoginId;
  }
}
