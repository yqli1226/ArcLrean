package com.arclearn.community.entity.auth;


import com.arclearn.community.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("permission")
public class Permission extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String name;

}
