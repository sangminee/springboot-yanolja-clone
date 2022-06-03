package com.example.demo.src.cancle.model.Get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetCancelDetailSleepRes {

    private int id;  // room_booking_cancel id
    // 1. 거래 일시
    private Timestamp create_at;

    // 2. 상품 및 이용정보
    private int hotel_id;
    private int room_id;
    private String hotel_name;
    private String room_name;
    private String img_url;
    private String booking_type;   // 필요없는 정보
    private String checkIn;
    private String checkOut;
    private String day;

    // 3. 비용
    private String booking_price;
    private String payment;
    private String payment_name;

    // 4. 환불정보
    private String cancel_list_name; // 취소 사유
    private String refund;
}
