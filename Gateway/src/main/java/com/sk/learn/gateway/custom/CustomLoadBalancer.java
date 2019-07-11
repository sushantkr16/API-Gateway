package com.sk.learn.gateway.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class CustomLoadBalancer {

    @Autowired
    private DiscoveryClient discoveryClient;

    public void getOrders() throws RestClientException, IOException {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;

        try {
            List<ServiceInstance> instances = discoveryClient.getInstances("checkout");
            log.info("CustomLoadBalancer instances: "+instances);

            for (ServiceInstance serviceInstance : instances) {
                log.info("CustomLoadBalancer serviceInstance host: "+ serviceInstance.getHost());
                String baseUrl = serviceInstance.getUri().toString();

                baseUrl = baseUrl + "/checkout/orders";

                log.info("CustomLoadBalancer baseUrl: "+ baseUrl);
                response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), String.class);
                log.info("CustomLoadBalancer response.getBody(): "+response.getBody());
            }

        } catch (Exception ex) {
            log.error("CustomLoadBalancer : Exception while load balancing" + ex);
        }
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
