package com.example.demo.src.like.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetLikeRes {
    private int id;   // like id
    private int user_id;
    private String status;
    private int hotel_id;
    private String hotel_name;
    private String notice;
    private String event;
    private String img_url;
}
