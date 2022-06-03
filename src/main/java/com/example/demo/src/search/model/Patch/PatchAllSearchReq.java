package com.example.demo.src.search.model.Patch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchAllSearchReq {
    private int user_id;
    private String status;
}
