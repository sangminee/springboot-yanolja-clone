package com.example.demo.src.booking.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostUserBookingReq {
    private int user_id;
    private int hotel_id;
    private int room_booking_id;
    private String booking_type;

    public PostUserBookingReq() {

    }
}
