package com.example.demo.src.like.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Like {
    private int id;
    private int user_id;
    private int hotel_id;
    private String status;
    private Timestamp created_at;
    private Timestamp updated_at;

}
