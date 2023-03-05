package com.example.pradhuman.entities;

import lombok.Getter;


@Getter
public enum OrderStatus {
    NEW("New"),SUCCESS("Success"),FAIL("Fail");

    private String status;

    OrderStatus(String status){
        this.status = status;
    }

    public static OrderStatus getOrderStatus(String statusString){
        switch (statusString){
            case "New":
                return OrderStatus.NEW;
            case "Success":
                return OrderStatus.SUCCESS;
            case "Fail":
                return OrderStatus.FAIL;
            default:
                throw new RuntimeException(String.format("No such OrderStatus exist : %s", statusString));
        }
    }

}
