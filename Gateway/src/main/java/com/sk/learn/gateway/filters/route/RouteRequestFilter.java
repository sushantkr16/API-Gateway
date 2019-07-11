package com.sk.learn.gateway.filters.route;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sk.learn.gateway.config.GatewayProperties;
import com.sk.learn.gateway.custom.CustomLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class RouteRequestFilter extends ZuulFilter {

    @Autowired
    private ProxyRequestHelper helper;

    @Autowired
    GatewayProperties gatewayProperties;

    @Autowired
    CustomLoadBalancer customLoadBalancer;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return gatewayProperties.isEnableRouteRequestFilter();
    }

    @Override
    public Object run() {
        System.out.println("Inside Route Request Filter : "+ "What's the matter with your mind");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String method = request.getMethod();

        String uri = this.helper.buildZuulRequestURI(request);

        System.out.println("Finished Route Request Filter: uri :  "+ uri);
        System.out.println("Finished Route Request Filter: method : "+ method);

        System.out.println("Finished Route Request Filter: request.getRequestURL() : "+ request.getRequestURL());

        if (gatewayProperties.isEnableCustomBalancer()) {
            try {
                customLoadBalancer.getOrders();
            } catch (IOException e) {
                log.error("RouteRequestFilter Error : Not able to get orders with custom load balancing");
                e.printStackTrace();
            }
        }

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
    }
}
