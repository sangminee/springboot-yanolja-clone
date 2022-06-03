package com.example.demo.src.cart.model.Get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTimeUserCartRes {
    private int id;
    private String status;
    private int room_cart_id;
    private String room_type;

    private String hotel_name;
    private String location_memo;
    private String room_name;
    private int min_personnel;
    private int max_personnel;
    private String room_memo;
    private String img_url;
    private String booking_start_date;
    private String booking_end_date;
    private String booking_start_time;
    private String booking_end_time;
    private String time; // 예약 일수 = (예약 끝 시간 - 예약 시작 시간)/100 =  (booking_end_time - booking_start_time)/100

    // 가격
    private String room_final_price;
}
