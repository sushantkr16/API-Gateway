package com.sk.learn.gateway.filters.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sk.learn.gateway.config.GatewayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Enumeration;

@Slf4j
@Component
public class PreRequestFilterTwo extends ZuulFilter {

    @Autowired
    GatewayProperties gatewayProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return gatewayProperties.isEnablePreRequestFilterTwo();
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("Inside PreRequestFilterTwo Filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        try {
            String authorizationHeaderValue = request.getHeader("Authorization");

            byte[] decodedBytes = Base64.getDecoder().decode(authorizationHeaderValue);
            String decodedClientId = new String(decodedBytes);

            if (StringUtils.isEmpty(decodedClientId) || !gatewayProperties.getClientId().equals(decodedClientId)) {
                System.out.println("PreRequestFilterTwo: decodedClientId is wrong : not authorized to access service");
                handleError(ctx);
            }
        } catch (Exception ex) {
            System.out.println("PreRequestFilterTwo: exception : not authorized to access service");
            handleError(ctx);
        }


        return null;
    }

    private void handleError(RequestContext ctx) {
        try {
            gatewayProperties.setEnableRouteRequestFilter(false);
            gatewayProperties.setEnablePostRequestFilter(false);
            gatewayProperties.setEnableErrorFilter(true);
            HttpServletResponse servletResponse = ctx.getResponse();
            servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "not authorized to access service");
            //throw new AuthorizationServiceException("not authorized to access service");
        } catch (IOException ex) {
            System.out.println("PreRequestFilterTwo IO exception: not authorized to access service");
        }
    }

}
