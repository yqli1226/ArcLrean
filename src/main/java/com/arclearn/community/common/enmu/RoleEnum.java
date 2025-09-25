package com.arclearn.community.common.enmu;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ROLE_ADMIN("ROLE_ADMIN", "管理员"),
    ROLE_USER("ROLE_USER", "普通用户"),
    ROLE_TEST("ROLE_TEST", "测试用户");

    private final String code;
    private final String name;


    RoleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
