package com.example.demo.src.booking.model.Get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDayTimeRes {
    private int id;
    private String time_start;
    private String time_end;
}
