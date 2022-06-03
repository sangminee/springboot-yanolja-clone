package com.example.demo.src.like.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostLikeReq {
    private int user_id;
    private int hotel_id;
//    private Timestamp updated_at;
}
