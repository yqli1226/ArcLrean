package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "日程自定义UI")
public class SchemaItem {
    @Schema(description = "UI名称",
            allowableValues = {"ForwardIcon", "MeetingChatIcon", "MeetingMinutesIcon", "MeetingVideo", "RSVP", "Attendee", "OrganizerOrCreator"},
            example = "MeetingVideo")
    private String uiName;

    @Schema(description = "UI状态",
            allowableValues = {"hide", "readonly", "editable", "unknown"},
            example = "hide")
    private String uiStatus;

    public SchemaItem() {}

    public String getUiName() {
        return uiName;
    }

    public void setUiName(String uiName) {
        this.uiName = uiName;
    }

    public String getUiStatus() {
        return uiStatus;
    }

    public void setUiStatus(String uiStatus) {
        this.uiStatus = uiStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchemaItem that = (SchemaItem) o;
        return Objects.equals(uiName, that.uiName) &&
                Objects.equals(uiStatus, that.uiStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uiName, uiStatus);
    }

    @Override
    public String toString() {
        return "SchemaItem{" +
                "uiName='" + uiName + '\'' +
                ", uiStatus='" + uiStatus + '\'' +
                '}';
    }
}
