package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

@Schema(description = "创建日程的请求体")
public class CreateCalendarEventRequest {

    @Schema(description = "日程标题，最大长度1000字符", example = "项目周会")
    private String summary;

    @Schema(description = "日程描述，支持HTML标签，最大长度40960字符", example = "讨论Q4产品路线图")
    private String description;

    @Schema(description = "更新日程时是否给参与人发送Bot通知", defaultValue = "true", example = "true")
    private Boolean needNotification;

    @Schema(description = "日程开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private TimeInfo startTime;

    @Schema(description = "日程结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private TimeInfo endTime;

    @Schema(description = "视频会议信息")
    private VChat vchat;

    @Schema(description = "日程公开范围",
            allowableValues = {"default", "public", "private"},
            defaultValue = "default",
            example = "default")
    private String visibility;

    @Schema(description = "参与人权限",
            allowableValues = {"none", "can_see_others", "can_invite_others", "can_modify_event"},
            defaultValue = "none",
            example = "can_see_others")
    private String attendeeAbility;

    @Schema(description = "日程占用的忙闲状态",
            allowableValues = {"busy", "free"},
            defaultValue = "busy",
            example = "busy")
    private String freeBusyStatus;

    @Schema(description = "日程地点")
    private EventLocation location;

    @Schema(description = "日程颜色，RGB的int32表示，0或-1表示跟随日历颜色", defaultValue = "-1", example = "-1")
    private Integer color;

    @Schema(description = "日程提醒列表，仅对当前身份生效")
    private List<Reminder> reminders;

    @Schema(description = "重复日程规则（RFC5545格式）", example = "FREQ=DAILY;INTERVAL=1")
    private String recurrence;

    @Schema(description = "日程自定义UI信息")
    private List<SchemaItem> schemas;

    @Schema(description = "日程附件列表")
    private List<Attachment> attachments;

    @Schema(description = "日程签到设置")
    private EventCheckIn eventCheckIn;

    public CreateCalendarEventRequest() {
        // 默认值处理
    }

    public CreateCalendarEventRequest(String summary, String description, Boolean needNotification,
                                      TimeInfo startTime, TimeInfo endTime, VChat vchat,
                                      String visibility, String attendeeAbility, String freeBusyStatus,
                                      EventLocation location, Integer color, List<Reminder> reminders,
                                      String recurrence, List<SchemaItem> schemas, List<Attachment> attachments,
                                      EventCheckIn eventCheckIn) {
        this.summary = summary;
        this.description = description;
        this.needNotification = needNotification != null ? needNotification : true;
        this.startTime = startTime;
        this.endTime = endTime;
        this.vchat = vchat;
        this.visibility = visibility != null ? visibility : "default";
        this.attendeeAbility = attendeeAbility != null ? attendeeAbility : "none";
        this.freeBusyStatus = freeBusyStatus != null ? freeBusyStatus : "busy";
        this.location = location;
        this.color = color != null ? color : -1;
        this.reminders = reminders;
        this.recurrence = recurrence;
        this.schemas = schemas;
        this.attachments = attachments;
        this.eventCheckIn = eventCheckIn;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getNeedNotification() {
        return needNotification != null ? needNotification : true;
    }

    public void setNeedNotification(Boolean needNotification) {
        this.needNotification = needNotification;
    }

    public TimeInfo getStartTime() {
        return startTime;
    }

    public void setStartTime(TimeInfo startTime) {
        this.startTime = startTime;
    }

    public TimeInfo getEndTime() {
        return endTime;
    }

    public void setEndTime(TimeInfo endTime) {
        this.endTime = endTime;
    }

    public VChat getVchat() {
        return vchat;
    }

    public void setVchat(VChat vchat) {
        this.vchat = vchat;
    }

    public String getVisibility() {
        return visibility != null ? visibility : "default";
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getAttendeeAbility() {
        return attendeeAbility != null ? attendeeAbility : "none";
    }

    public void setAttendeeAbility(String attendeeAbility) {
        this.attendeeAbility = attendeeAbility;
    }

    public String getFreeBusyStatus() {
        return freeBusyStatus != null ? freeBusyStatus : "busy";
    }

    public void setFreeBusyStatus(String freeBusyStatus) {
        this.freeBusyStatus = freeBusyStatus;
    }

    public EventLocation getLocation() {
        return location;
    }

    public void setLocation(EventLocation location) {
        this.location = location;
    }

    public Integer getColor() {
        return color != null ? color : -1;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public List<SchemaItem> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<SchemaItem> schemas) {
        this.schemas = schemas;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public EventCheckIn getEventCheckIn() {
        return eventCheckIn;
    }

    public void setEventCheckIn(EventCheckIn eventCheckIn) {
        this.eventCheckIn = eventCheckIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateCalendarEventRequest that = (CreateCalendarEventRequest) o;
        return Objects.equals(summary, that.summary) &&
                Objects.equals(description, that.description) &&
                Objects.equals(needNotification, that.needNotification) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(vchat, that.vchat) &&
                Objects.equals(visibility, that.visibility) &&
                Objects.equals(attendeeAbility, that.attendeeAbility) &&
                Objects.equals(freeBusyStatus, that.freeBusyStatus) &&
                Objects.equals(location, that.location) &&
                Objects.equals(color, that.color) &&
                Objects.equals(reminders, that.reminders) &&
                Objects.equals(recurrence, that.recurrence) &&
                Objects.equals(schemas, that.schemas) &&
                Objects.equals(attachments, that.attachments) &&
                Objects.equals(eventCheckIn, that.eventCheckIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summary, description, needNotification, startTime, endTime,
                vchat, visibility, attendeeAbility, freeBusyStatus, location,
                color, reminders, recurrence, schemas, attachments, eventCheckIn);
    }

    @Override
    public String toString() {
        return "CreateCalendarEventRequest{" +
                "summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", needNotification=" + getNeedNotification() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", vchat=" + vchat +
                ", visibility='" + getVisibility() + '\'' +
                ", attendeeAbility='" + getAttendeeAbility() + '\'' +
                ", freeBusyStatus='" + getFreeBusyStatus() + '\'' +
                ", location=" + location +
                ", color=" + getColor() +
                ", reminders=" + reminders +
                ", recurrence='" + recurrence + '\'' +
                ", schemas=" + schemas +
                ", attachments=" + attachments +
                ", eventCheckIn=" + eventCheckIn +
                '}';
    }
}













