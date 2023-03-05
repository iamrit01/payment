package com.example.pradhuman.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private String city;
    private String state;
    private String pincode;
}
