package com.example.pradhuman.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {

    private String category;
    private int quantity;
    private double price;
}
