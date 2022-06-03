package com.example.demo.config;

import lombok.Getter;

@Getter
public enum PushStatus {

    SUCCESS_PAYMENT("결제 성공했습니다.");

    private final String message;

    private PushStatus(String message) {
        this.message = message;
    }
}
