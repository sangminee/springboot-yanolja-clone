package com.example.demo.src.search.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostSearchReq {
    private int user_id;
    private String search;

    public PostSearchReq() {

    }
}
