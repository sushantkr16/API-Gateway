package com.sk.learn.checkout.dto;

import lombok.Data;

@Data
public class Order {

    private String orderId;
    private String itemId;
    private String address;
    private String billing;

}
