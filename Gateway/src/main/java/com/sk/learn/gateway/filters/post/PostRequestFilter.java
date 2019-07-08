package com.sk.learn.gateway.filters.post;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sk.learn.gateway.config.GatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Component
public class PostRequestFilter extends ZuulFilter {

    @Autowired
    GatewayProperties gatewayProperties;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return gatewayProperties.isEnablePostRequestFilter();
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("Inside PostRequestFilter Filter");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        HttpServletResponse servletResponse = context.getResponse();
        servletResponse.addHeader("X-Enjoy", "I'm not much into health food, I am into champagne");
        System.out.println("Finished PostRequestFilter Filter");
        return null;
    }
}
