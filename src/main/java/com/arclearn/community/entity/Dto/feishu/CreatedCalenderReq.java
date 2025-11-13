package com.arclearn.community.entity.Dto.feishu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "创建共享日历入参")
public class CreatedCalenderReq {
    @Schema(description = "日历标题", example = "测试日历")
    private String summary;
    @Schema(description = "日历描述", example = "描述")
    private String description;
    @Schema(description = "日历公开范围：private：私密、show_only_free_busy：仅展示忙闲信息、public：公开，他人可查看日程详情", example = "private")
    private String permissions;
    @Schema(description = "日历颜色，取值通过颜色 RGB 值的 int32 表示，其中，24 ~ 31 位为透明度，16 ~ 23 位为红，8 ~ 15 位为绿，0 ~ 7 位为蓝。例如，-11034625 表示 RGB 值 (87, 159, 255)", example = "-1")
    private int  color;
    @Schema(description = "日历备注名，设置该字段后（包括后续修改该字段）仅对当前身份生效", example = "日历备注名")
    private String summaryAlias;

}
