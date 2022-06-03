package com.example.demo.src.booking.model.Get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeTimeUserBookingRes {

    private int room_booking_id;   // room_booking_time
    private String booking_type;

    private int hotel_id;
    private int room_id;

    private String hotel_name;
    private String room_name;
    private String img_url;
    private String booking_start_date;
    private String booking_end_date;
    private String booking_start_time;
    private String booking_end_time;
    private String time;

}
