package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "日程签到设置")
public class EventCheckIn {
    @Schema(description = "是否启用签到", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean enableCheckIn;

    @Schema(description = "签到开始时间")
    private CheckInTime checkInStartTime;

    @Schema(description = "签到结束时间")
    private CheckInTime checkInEndTime;

    @Schema(description = "签到开始时是否通知参与者", defaultValue = "false", example = "false")
    private Boolean needNotifyAttendees;

    public EventCheckIn() {
        // 默认值处理
    }

    public EventCheckIn(Boolean enableCheckIn, CheckInTime checkInStartTime, CheckInTime checkInEndTime,
                        Boolean needNotifyAttendees) {
        this.enableCheckIn = enableCheckIn;
        this.checkInStartTime = checkInStartTime;
        this.checkInEndTime = checkInEndTime;
        this.needNotifyAttendees = needNotifyAttendees != null ? needNotifyAttendees : false;
    }

    public Boolean getEnableCheckIn() {
        return enableCheckIn;
    }

    public void setEnableCheckIn(Boolean enableCheckIn) {
        this.enableCheckIn = enableCheckIn;
    }

    public CheckInTime getCheckInStartTime() {
        return checkInStartTime;
    }

    public void setCheckInStartTime(CheckInTime checkInStartTime) {
        this.checkInStartTime = checkInStartTime;
    }

    public CheckInTime getCheckInEndTime() {
        return checkInEndTime;
    }

    public void setCheckInEndTime(CheckInTime checkInEndTime) {
        this.checkInEndTime = checkInEndTime;
    }

    public Boolean getNeedNotifyAttendees() {
        return needNotifyAttendees != null ? needNotifyAttendees : false;
    }

    public void setNeedNotifyAttendees(Boolean needNotifyAttendees) {
        this.needNotifyAttendees = needNotifyAttendees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventCheckIn that = (EventCheckIn) o;
        return Objects.equals(enableCheckIn, that.enableCheckIn) &&
                Objects.equals(checkInStartTime, that.checkInStartTime) &&
                Objects.equals(checkInEndTime, that.checkInEndTime) &&
                Objects.equals(needNotifyAttendees, that.needNotifyAttendees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enableCheckIn, checkInStartTime, checkInEndTime, needNotifyAttendees);
    }

    @Override
    public String toString() {
        return "EventCheckIn{" +
                "enableCheckIn=" + enableCheckIn +
                ", checkInStartTime=" + checkInStartTime +
                ", checkInEndTime=" + checkInEndTime +
                ", needNotifyAttendees=" + getNeedNotifyAttendees() +
                '}';
    }
}