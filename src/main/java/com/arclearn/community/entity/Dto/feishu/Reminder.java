package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "日程提醒")
public class Reminder {
    @Schema(description = "提醒偏移量（分钟），正数为开始前，负数为开始后", example = "10")
    private Integer minutes;

    public Reminder() {}

    public Reminder(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reminder reminder = (Reminder) o;
        return Objects.equals(minutes, reminder.minutes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minutes);
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "minutes=" + minutes +
                '}';
    }
}