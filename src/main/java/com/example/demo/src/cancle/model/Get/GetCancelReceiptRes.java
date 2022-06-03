package com.example.demo.src.cancle.model.Get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetCancelReceiptRes {
    private int id;
    private Timestamp created_at;
    private String hotel_name;
    private String final_payment;
    private String company;
}
