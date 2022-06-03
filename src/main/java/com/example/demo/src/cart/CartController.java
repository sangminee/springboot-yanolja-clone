package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.cart.model.Get.*;
import com.example.demo.src.cart.model.Patch.*;
import com.example.demo.src.cart.model.Post.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CartProvider cartProvider;
    @Autowired
    private final CartService cartService;
    @Autowired
    private final JwtService jwtService;

    public CartController(CartProvider cartProvider, CartService cartService, JwtService jwtService) {
        this.cartProvider = cartProvider;
        this.cartService = cartService;
        this.jwtService = jwtService;
    }

    // 35. [GET] 장바구니 조회 API - 전체 조회
    @ResponseBody
    @GetMapping("/{user_id}")
    public BaseResponse<String> getUserBooking(@PathVariable("user_id") int user_id){
        try{

            // jwt
            int user_noxByJwt = jwtService.getUserId();
            if(user_id != user_noxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }


            List<GetTimeUserCartRes> getUserTime = cartProvider.getUserTime(user_id);
            List<GetSleepUserCartRes> getUserSleep= cartProvider.getUserSleep(user_id);
            List list = new ArrayList();
            list.add(getUserTime);
            list.add(getUserSleep);

            return new BaseResponse(list);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 30. [POST]  대실 장바구니 담기 API - Time
    @ResponseBody
    @PostMapping("/time")   // /cart/time
    public BaseResponse<PostCartTimeRes> createTime(@RequestBody PostCartTimeReq postCartTimeReq){
        try{
            // 호텔, 방 정확히 선택했는지 확인
            if(postCartTimeReq.getHotel_id() == 0) {
                return new BaseResponse<>(POST_EMPTY_HOTEL);
            }
            if(postCartTimeReq.getRoom_id() == 0) {
                return new BaseResponse<>(POST_EMPTY_ROOM);
            }

            // 시간 선택 확인
            if(postCartTimeReq.getBooking_start_time() == null){
                return new BaseResponse<>(POST_EMPTY_TIME);
            }
            if(postCartTimeReq.getBooking_end_time() == null){
                return new BaseResponse<>(POST_EMPTY_TIME);
            }

            // 날짜 선택 확인
            if(postCartTimeReq.getBooking_start_date() == null){
                return new BaseResponse<>(POST_EMPTY_DATE);
            }
            if(postCartTimeReq.getBooking_end_date() == null){
                return new BaseResponse<>(POST_EMPTY_DATE);
            }

            PostCartTimeRes postCartTimeRes =cartService.createTime(postCartTimeReq);

            return new BaseResponse(postCartTimeRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 46. [POST]  숙박 장바구니 담기 API -Sleep
    @ResponseBody
    @PostMapping("/sleep")   //  /cart/sleep
    public BaseResponse<PostCartSleepRes> createSleep(@RequestBody PostCartSleepReq postCartSleepReq){
        try{

            // 호텔, 방 정확히 선택했는지 확인
            if(postCartSleepReq.getHotel_id() == 0) {
                return new BaseResponse<>(POST_EMPTY_HOTEL);
            }
            if(postCartSleepReq.getRoom_id() == 0) {
                return new BaseResponse<>(POST_EMPTY_ROOM);
            }

            // 날짜 선택 확인
            if(postCartSleepReq.getBooking_start_date() == null){
                return new BaseResponse<>(POST_EMPTY_DATE);
            }
            if(postCartSleepReq.getBooking_end_date() == null){
                return new BaseResponse<>(POST_EMPTY_DATE);
            }

            PostCartSleepRes postCartSleepRes =cartService.createSleep(postCartSleepReq);

            return new BaseResponse(postCartSleepRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 31. [PATCH]  장바구니 삭제 API - status 변경
    @ResponseBody
    @PatchMapping("/{user_id}")   // /cart/:userId?roomCartType=T&roomCartId=
    public BaseResponse<String> modifyStatus(@PathVariable("user_id") int user_id,
                                             @RequestParam(required = false) int roomCartId,
                                             @RequestParam(required = false) String roomCartType,
                                             @RequestBody Cart cart)throws BaseException{

        // jwt
        int user_noxByJwt = jwtService.getUserId();
        if(user_id != user_noxByJwt){
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        if(roomCartType.equals("T")){
            PatchCartReq patchCartReq = new PatchCartReq(roomCartId,cart.getStatus());
            PatchTimeCartReq patchTimeCartReq = new PatchTimeCartReq(roomCartId,cart.getStatus());
            cartService.modifyStatus(patchCartReq);
            cartService.modifyTimeStatus(patchTimeCartReq);
            String result = "대실 - 삭제되었습니다";
            return new BaseResponse(result);
        }else{
            PatchCartReq patchCartReq = new PatchCartReq(roomCartId,cart.getStatus());
            PatchSleepCartReq patchSleepCartReq = new PatchSleepCartReq(roomCartId,cart.getStatus());
            cartService.modifyStatus(patchCartReq);
            cartService.modifySleepStatus(patchSleepCartReq);
            String result = "숙박 - 삭제되었습니다";
            return new BaseResponse(result);
        }

    }

    //  [PATCH]  장바구니 전체 삭제하기


}
