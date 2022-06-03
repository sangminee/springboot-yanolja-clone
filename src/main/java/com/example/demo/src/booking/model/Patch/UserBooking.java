package com.example.demo.src.booking.model.Patch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class UserBooking {
    private int id;
    private int user_id;
    private int hotel_id;
    private int room_booking_id;
    private String booking_type;
    private String status;
}
