package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */

    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"로그인이 필요합니다."),
    EMPTY_STATUS(false,2004,"정확한 상태 입력이 필요합니다."),
    EMPTY_TIME(false,2005,"정확한 생성 시간을 입력해 주세요"),


    // users
    USERS_EMPTY_EMAIL(false, 2010, "이메일을 입력해 주세요."),
    USERS_INVALID_EMAIL(false, 2011, "이메일 형식을 확인해 주세요."),
    USERS_EMPTY_PASSWORD(false, 2012, "비밀번호를 입력해 주세요."),
    USERS_INVALID_PASSWORD(false, 2013, "비밀번호는 영문, 숫자, 특수문자를 포함한 8자리 이상 20자리 이하여야 됩니다."),
    FAILED_TO_SEND_AUTH(false, 2014, "전송에 실패했습니다."),
    EXCEED_AUTH_NUMBER(false, 2015, "하루 인증 횟수를 초과하였습니다."),
    USERS_EMPTY_PHONE_NUMBER(false, 2016, "핸드폰 번호를 입력해 주세요."),
    USERS_INVALID_NICKNAME(false, 2017, "닉네임 형식을 확인해 주세요."),
    USERS_INVALID_PHONE_NUMBER(false, 2018, "핸드폰 번호를 확인해 주세요."),

    // area

    // [POST] /booking  & /cart
    POST_EMPTY_START_DATE(false,2030,"시간을 선택해 주세요."),
    POST_EMPTY_DATE(false,2031,"정확한 날짜를 선택해주세요"),
    POST_EMPTY_HOTEL(false,2032,"호텔을 선택해주세요"),
    POST_EMPTY_ROOM(false,2033,"방을 선택해주세요"),
    POST_EMPTY_TIME(false,2034,"정확한 시간을 입력해 주세요"),

    // [POST] /like
    POST_LIKE_INVALID_USER_ID(false,2041,"올바른 유저인지 확인해 주세요"),
    POST_LIKE_EMPTY_HOTEL(false,2042,"호텔을 선택해주세요."),

    // [PATCH] /like
    PATCH_LIKE_INVALID_STATUS(false,2043,"상태 값의 변화가 없습니다."),
    PATCH_CHECK_ID(false,2044,"올바른 찜 id인지 확인해 주세요."),

    // review
    EMPTY_REVIEW_SCORE(false, 2050, "점수를 입력해 주세요."),
    EMPTY_REVIEW_CONTENT(false, 2051, "후기를 입력해 주세요."),
    REVIEW_LESS_THAN_FIVE(false, 2052, "후기를 다섯 글자 이상 작성해 주세요."),

    // [POST] cancel
    POST_CHECK_USER_ID(false,2060,"해당 유저가 맞는지 확인해 주세요"),
    POST_CHECK_USER_BOOKING_ID(false,2061,"user_table id 값을 정확히 확인해 주세요"),
    POST_CHECK_ROOM_BOOKING_ID(false,2062,"room_booking_id 값을 정확히 확인해 주세요"),
    POST_CHECK_BOOKING_TYPE(false,2063,"방 타입을 정확히 확인해 주세요"),
    POST_CHECK_CANCEL_LIST_ID(false,2064,"선택한 취소사유인지 확인해주세요"),
    POST_CHECK_CANCEL_LIST_NAME(false,2065,"정확한 취소사유 내용인지 확인해주세요"),
    POST_CHECK_REFUND(false,2066,"정확한 환불금액인지 확인해주세요"),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    NO_DATA(false, 3001, "데이터가 존재하지 않습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3010, "중복된 이메일입니다."),
    DUPLICATED_PHONE_NUMBER(false, 3011, "중복된 핸드폰 번호입니다."),
    FAILED_TO_LOGIN(false,3012,"없는 아이디거나 비밀번호가 틀렸습니다."),
    FAILED_TO_AUTH(false, 3013, "인증번호가 일치하지 않습니다."),
    FAILED_TO_PASSWORD(false, 3014, "비밀번호가 일치하지 않습니다."),
    UNKNOWN_USER(false, 3015, "존재하지 않는 이메일입니다."),
    // area


    // hotel





    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),


    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    // [PATCH] /like /booking
    MODIFY_FAIL_STATUS(false,4020,"상태 수정 실패");



    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

