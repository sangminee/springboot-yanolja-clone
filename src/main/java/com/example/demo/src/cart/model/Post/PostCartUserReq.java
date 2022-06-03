package com.example.demo.src.cart.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCartUserReq {
    private int user_id;
    private int hotel_id;
    private int room_cart_id;
    private String room_type;

    public PostCartUserReq() {

    }
}
