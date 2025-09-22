package com.arclearn.community.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(unique = true, nullable = false)
    private String username;

    // 存储加密后的密码
    @Column(nullable = false)
    private String password;

    // 是否启用
    private boolean enabled = true;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();


    @PrePersist // 生命周期回调注解：在执行EntityManager.persist()或repository.save()之前触发
    public void generateUuid() {
        if (this.uuid == null) { // 如果uuid为空才生成
            this.uuid = java.util.UUID.randomUUID().toString();
        }
    }

}
