package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

@Schema(description = "飞书视频会议设置")
public class MeetingSettings {
    @Schema(description = "会议owner用户ID（需与user_id_type一致）")
    private String ownerId;

    @Schema(description = "入会范围",
            allowableValues = {"anyone_can_join", "only_organization_employees", "only_event_attendees"},
            defaultValue = "anyone_can_join",
            example = "only_organization_employees")
    private String joinMeetingPermission;

    @Schema(description = "主持人列表（用户ID）")
    private List<String> assignHosts;

    @Schema(description = "是否自动录制", defaultValue = "false", example = "false")
    private Boolean autoRecord;

    @Schema(description = "是否开启等候室", defaultValue = "true", example = "true")
    private Boolean openLobby;

    @Schema(description = "是否允许参与者发起会议", defaultValue = "true", example = "true")
    private Boolean allowAttendeesStart;

    public MeetingSettings() {
        // 默认值处理
    }

    public MeetingSettings(String ownerId, String joinMeetingPermission, List<String> assignHosts,
                           Boolean autoRecord, Boolean openLobby, Boolean allowAttendeesStart) {
        this.ownerId = ownerId;
        this.joinMeetingPermission = joinMeetingPermission != null ? joinMeetingPermission : "anyone_can_join";
        this.assignHosts = assignHosts;
        this.autoRecord = autoRecord != null ? autoRecord : false;
        this.openLobby = openLobby != null ? openLobby : true;
        this.allowAttendeesStart = allowAttendeesStart != null ? allowAttendeesStart : true;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getJoinMeetingPermission() {
        return joinMeetingPermission != null ? joinMeetingPermission : "anyone_can_join";
    }

    public void setJoinMeetingPermission(String joinMeetingPermission) {
        this.joinMeetingPermission = joinMeetingPermission;
    }

    public List<String> getAssignHosts() {
        return assignHosts;
    }

    public void setAssignHosts(List<String> assignHosts) {
        this.assignHosts = assignHosts;
    }

    public Boolean getAutoRecord() {
        return autoRecord != null ? autoRecord : false;
    }

    public void setAutoRecord(Boolean autoRecord) {
        this.autoRecord = autoRecord;
    }

    public Boolean getOpenLobby() {
        return openLobby != null ? openLobby : true;
    }

    public void setOpenLobby(Boolean openLobby) {
        this.openLobby = openLobby;
    }

    public Boolean getAllowAttendeesStart() {
        return allowAttendeesStart != null ? allowAttendeesStart : true;
    }

    public void setAllowAttendeesStart(Boolean allowAttendeesStart) {
        this.allowAttendeesStart = allowAttendeesStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingSettings that = (MeetingSettings) o;
        return Objects.equals(ownerId, that.ownerId) &&
                Objects.equals(joinMeetingPermission, that.joinMeetingPermission) &&
                Objects.equals(assignHosts, that.assignHosts) &&
                Objects.equals(autoRecord, that.autoRecord) &&
                Objects.equals(openLobby, that.openLobby) &&
                Objects.equals(allowAttendeesStart, that.allowAttendeesStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, joinMeetingPermission, assignHosts, autoRecord, openLobby, allowAttendeesStart);
    }

    @Override
    public String toString() {
        return "MeetingSettings{" +
                "ownerId='" + ownerId + '\'' +
                ", joinMeetingPermission='" + getJoinMeetingPermission() + '\'' +
                ", assignHosts=" + assignHosts +
                ", autoRecord=" + getAutoRecord() +
                ", openLobby=" + getOpenLobby() +
                ", allowAttendeesStart=" + getAllowAttendeesStart() +
                '}';
    }
}
