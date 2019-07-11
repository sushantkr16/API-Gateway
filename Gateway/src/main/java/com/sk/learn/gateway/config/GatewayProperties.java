package com.sk.learn.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "gateway")
public class GatewayProperties {

    private boolean enablePreRequestFilter = true;
    private boolean enablePreRequestFilterTwo = true;
    private boolean enableRouteRequestFilter = true;
    private boolean enablePostRequestFilter = true;
    private boolean enableErrorFilter = true;
    private String clientId = "learning_thursday";
    private boolean enableCustomBalancer = true;
}
