package com.sk.learn.checkout.resource;

import com.sk.learn.checkout.dto.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class CheckoutResource {


    @GetMapping
    public ResponseEntity<List<Order>> getOrders(HttpServletRequest httpServletRequest) {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setOrderId("o1");
        order.setItemId("i1");
        order.setAddress("Plano, Texas, US");
        order.setBilling("4999999999999999");
        orderList.add(order);

        System.out.println("request url : "+ httpServletRequest.getRequestURL());

        System.out.println("request port : "+ httpServletRequest.getServerPort());

        return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(HttpServletRequest httpServletRequest, @RequestBody Order order) {

        System.out.println("request url : "+ httpServletRequest.getRequestURL());

        System.out.println("request port : "+ httpServletRequest.getServerPort());

        return new ResponseEntity<Order>(order, HttpStatus.OK);

    }

}
