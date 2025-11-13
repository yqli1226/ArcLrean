package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "时间信息")
public class TimeInfo {
    @Schema(description = "仅全天日程使用，RFC3339格式，如2018-09-01，该参数不能与 timestamp 同时指定")
    private String date;

    @Schema(description = "秒级时间戳，如1602504000（UTC+8），该参数不能与 date 同时指定。")
    private String timestamp;

    @Schema(description = "时区，如Asia/Shanghai", defaultValue = "Asia/Shanghai", example = "Asia/Shanghai")
    private String timezone;

    public TimeInfo() {
        // 默认构造器
    }

    public TimeInfo(String date, String timestamp, String timezone) {
        this.date = date;
        this.timestamp = timestamp;
        this.timezone = timezone != null ? timezone : "Asia/Shanghai";

        // 校验互斥和必填
        if (date != null && timestamp != null) {
            throw new IllegalArgumentException("date 和 timestamp 不能同时指定");
        }
        if (date == null && timestamp == null) {
            throw new IllegalArgumentException("必须指定 date 或 timestamp 中的一个");
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimezone() {
        return timezone != null ? timezone : "Asia/Shanghai";
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInfo timeInfo = (TimeInfo) o;
        return Objects.equals(date, timeInfo.date) &&
                Objects.equals(timestamp, timeInfo.timestamp) &&
                Objects.equals(timezone, timeInfo.timezone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, timestamp, timezone);
    }

    @Override
    public String toString() {
        return "TimeInfo{" +
                "date='" + date + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", timezone='" + getTimezone() + '\'' +
                '}';
    }
}