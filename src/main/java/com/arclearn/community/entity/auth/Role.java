package com.arclearn.community.entity.auth;


import com.arclearn.community.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role")
public class Role extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String name;

    @TableField(exist = false)
    private List<Permission> permissions = new ArrayList<>();

    public void loadPermissions(List<Permission> permissions){
        this.permissions = permissions;
    }

}
