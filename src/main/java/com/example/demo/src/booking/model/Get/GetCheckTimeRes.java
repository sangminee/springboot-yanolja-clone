package com.example.demo.src.booking.model.Get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetCheckTimeRes {

    private int room_booking_id;  // table : user_booking

    // 1. 거래 일시
    private Timestamp create_at;

    // 2. 상품 및 이용정보
    private int hotel_id;
    private int room_id;
    private String hotel_name;
    private String room_name;
    private String img_url;
    private String booking_type;      // 필요없는 정보
    private String booking_start_date;
    private String booking_end_date;
    private String checkIn;   // 체크인
    private String checkOut;     // 체크 아웃
    private String time;

    // 3. 예약자 정보
    private String user;
    private String user_phone;

    // 4. 이용자 정보
    private String guest;
    private String guest_phone;

    // 5. 금액 및 할인 정보
    private String booking_price;
    private String payment;
    private String payment_name;
}
