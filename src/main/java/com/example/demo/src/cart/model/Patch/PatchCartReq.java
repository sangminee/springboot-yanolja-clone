package com.example.demo.src.cart.model.Patch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PatchCartReq {
    private int room_cart_id;
    private String status;
}
