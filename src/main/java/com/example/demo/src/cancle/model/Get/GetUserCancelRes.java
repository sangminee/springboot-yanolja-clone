package com.example.demo.src.cancle.model.Get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserCancelRes {
    private int user_booking_id;
    private int room_booking_id;
    private String booking_type;
}
