package com.example.demo.src.cancle.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostBookingCancelReq {
    private int user_id;
    private int user_booking_id;
    private int room_booking_id;
    private String booking_type;
    private int cancel_list_id;
    private String cancel_list_name;
    private String refund;
}
