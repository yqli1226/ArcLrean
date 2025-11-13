package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "日程地点")
public class EventLocation {
    @Schema(description = "地点名称", example = "北京办公室")
    private String name;

    @Schema(description = "地点地址", example = "北京市朝阳区xxx大厦")
    private String address;

    @Schema(description = "纬度（国内GCJ-02，海外WGS84）")
    private Double latitude;

    @Schema(description = "经度（国内GCJ-02，海外WGS84）")
    private Double longitude;

    public EventLocation() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventLocation that = (EventLocation) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, latitude, longitude);
    }

    @Override
    public String toString() {
        return "EventLocation{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
