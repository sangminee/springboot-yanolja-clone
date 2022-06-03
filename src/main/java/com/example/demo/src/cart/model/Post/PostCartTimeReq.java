package com.example.demo.src.cart.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCartTimeReq {
    private int user_id;
    private int hotel_id;
    private int room_id;
    private String cart_type;
    private String booking_start_date;
    private String booking_end_date;
    private String booking_start_time;
    private String booking_end_time;

    // 가격
    private String room_final_price;
}
