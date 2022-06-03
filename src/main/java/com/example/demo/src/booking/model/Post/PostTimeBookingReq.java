package com.example.demo.src.booking.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostTimeBookingReq {
    private int user_id;
    private int hotel_id;
    private int room_id;
    private String booking_type;
    private int payment_id;
    private String booking_start_date;
    private String booking_end_date;
    private String booking_start_time;
    private String booking_end_time;

    private String room_final_price;
}
