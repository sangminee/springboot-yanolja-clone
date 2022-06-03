package com.example.demo.src.booking.model.Patch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchTimeBookingReq {
    private int id;  // room_booking_time id
    private String status;
}
