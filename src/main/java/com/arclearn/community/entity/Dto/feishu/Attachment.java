package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "日程附件")
public class Attachment {
    @Schema(description = "附件Token，通过上传素材接口获取", example = "xAAAAA")
    private String fileToken;

    public Attachment() {}

    public String getFileToken() {
        return fileToken;
    }

    public void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(fileToken, that.fileToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileToken);
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "fileToken='" + fileToken + '\'' +
                '}';
    }
}