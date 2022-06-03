package com.example.demo.src.search.model.Get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSearchWeekendRes {  // 주말
    private String area_name;  // main_area
    private String sub_area_name;   // sub_area
    private String hotel_name;
    private String notice;
    private String event;
    private String img_url;
    private String weekend_time_price;   // 주말 대실
    private String weekend_sleep_price;   // 주말 숙박
}
