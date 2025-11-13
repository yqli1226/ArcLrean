package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "签到时间偏移")
public class CheckInTime {
    @Schema(description = "时间节点类型",
            allowableValues = {"before_event_start", "after_event_start", "after_event_end"},
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "before_event_start")
    private String timeType;

    @Schema(description = "偏移量（分钟），可选值：0,5,15,30,60",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "15")
    private Integer duration;

    public CheckInTime() {}

    public CheckInTime(String timeType, Integer duration) {
        this.timeType = timeType;
        this.duration = duration;

        if (timeType == null) {
            throw new IllegalArgumentException("timeType 不能为空");
        }
        if (duration == null) {
            throw new IllegalArgumentException("duration 不能为空");
        }
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckInTime that = (CheckInTime) o;
        return Objects.equals(timeType, that.timeType) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeType, duration);
    }

    @Override
    public String toString() {
        return "CheckInTime{" +
                "timeType='" + timeType + '\'' +
                ", duration=" + duration +
                '}';
    }
}
