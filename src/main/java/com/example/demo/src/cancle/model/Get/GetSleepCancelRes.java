package com.example.demo.src.cancle.model.Get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetSleepCancelRes {

    private int id;  // user_booking table 의 pk값 (id)
    private int user_id;
    private int room_booking_id;  // room_booking_sleep 의 pk 값 (id)

    // 1. 거래 일시
    private Timestamp create_at;

    // 2. 상품 및 이용정보
    private int hotel_id;
    private int room_id;
    private String hotel_name;
    private String room_name;
    private String img_url;
    private String booking_type;
    private String checkIn;
    private String checkOut;
    private String day;

    // 3. 금액 및 할인 정보
    private String booking_price;
    private String payment;
}
