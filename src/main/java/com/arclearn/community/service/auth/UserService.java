package com.arclearn.community.service.auth;

import com.arclearn.community.entity.auth.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    void register(String username, String password);
}
