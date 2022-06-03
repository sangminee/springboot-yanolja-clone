package com.example.demo.src.cart.model.Patch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private int id;
    private int user_id;
    private int hotel_id;
    private int room_id;
    private String room_type;
    private String status;
}
