package com.arclearn.community.service.feishu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiService {

    private final com.lark.oapi.Client client;

    public ApiService(@Value("${appId}") String appId,
                      @Value("${appSecret}") String appSecret) {
        if (appId == null || appSecret == null) {
            throw new IllegalStateException("Missing appId or appSecret in config!");
        }
        this.client = com.lark.oapi.Client.newBuilder(appId, appSecret).build();
    }

    public com.lark.oapi.Client getClient() {
        return client;
    }
}
