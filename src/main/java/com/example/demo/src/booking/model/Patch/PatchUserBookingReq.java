package com.example.demo.src.booking.model.Patch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserBookingReq {
    private int room_booking_id;
    private String status;
}
