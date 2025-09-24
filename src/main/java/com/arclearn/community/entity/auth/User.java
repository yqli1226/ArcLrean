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
@TableName("user")
public class User extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String uuid;

    private String username;

    // 存储加密后的密码
    private String password;

    // 是否启用
    private boolean enabled = true;

    @TableField(exist = false)
    private List<Role> roles = new ArrayList<>();

    public void generateUuid() {
        if (this.uuid == null) { // 如果uuid为空才生成
            this.uuid = java.util.UUID.randomUUID().toString();
        }
    }

    // 获取角色信息
    public void loadRoles(List<Role> roles) {
        this.roles = roles;
    }

}
