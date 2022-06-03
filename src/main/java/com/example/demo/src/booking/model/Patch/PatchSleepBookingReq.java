package com.example.demo.src.booking.model.Patch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchSleepBookingReq {
    private int id;  // room_booking_sleep id
    private String status;
}
