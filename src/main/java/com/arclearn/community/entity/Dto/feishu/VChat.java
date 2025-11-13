package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "视频会议信息")
public class VChat {
    @Schema(description = "视频会议类型",
            allowableValues = {"vc", "third_party", "no_meeting"},
            example = "third_party")
    private String vcType;

    @Schema(description = "第三方会议icon类型",
            allowableValues = {"vc", "live", "default"},
            defaultValue = "default",
            example = "vc")
    private String iconType;

    @Schema(description = "第三方会议文案", example = "点击加入会议")
    private String description;

    @Schema(description = "会议URL", example = "https://example.com")
    private String meetingUrl;

    @Schema(description = "飞书会议设置（仅当vcType=vc时生效）")
    private MeetingSettings meetingSettings;

    public VChat() {}

    public String getVcType() {
        return vcType;
    }

    public void setVcType(String vcType) {
        this.vcType = vcType;
    }

    public String getIconType() {
        return iconType != null ? iconType : "default";
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeetingUrl() {
        return meetingUrl;
    }

    public void setMeetingUrl(String meetingUrl) {
        this.meetingUrl = meetingUrl;
    }

    public MeetingSettings getMeetingSettings() {
        return meetingSettings;
    }

    public void setMeetingSettings(MeetingSettings meetingSettings) {
        this.meetingSettings = meetingSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VChat vChat = (VChat) o;
        return Objects.equals(vcType, vChat.vcType) &&
                Objects.equals(iconType, vChat.iconType) &&
                Objects.equals(description, vChat.description) &&
                Objects.equals(meetingUrl, vChat.meetingUrl) &&
                Objects.equals(meetingSettings, vChat.meetingSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vcType, iconType, description, meetingUrl, meetingSettings);
    }

    @Override
    public String toString() {
        return "VChat{" +
                "vcType='" + vcType + '\'' +
                ", iconType='" + getIconType() + '\'' +
                ", description='" + description + '\'' +
                ", meetingUrl='" + meetingUrl + '\'' +
                ", meetingSettings=" + meetingSettings +
                '}';
    }
}