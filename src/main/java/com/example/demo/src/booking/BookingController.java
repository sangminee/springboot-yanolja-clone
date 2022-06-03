package com.example.demo.src.booking;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.booking.model.Get.*;
import com.example.demo.src.booking.model.Patch.*;
import com.example.demo.src.booking.model.Post.*;
import com.example.demo.src.cart.model.Patch.PatchCartReq;
import com.example.demo.src.cart.model.Patch.PatchSleepCartReq;
import com.example.demo.src.cart.model.Patch.PatchTimeCartReq;
import com.example.demo.utils.JwtService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BookingProvider bookingProvider;
    @Autowired
    private final BookingService bookingService;
    @Autowired
    private final JwtService jwtService;

    public BookingController(BookingProvider bookingProvider, BookingService bookingService, JwtService jwtService) {
        this.bookingProvider = bookingProvider;
        this.bookingService = bookingService;
        this.jwtService = jwtService;
    }

    // [GET] 숙소 예약내역 조회 - 전체 조회
    @ResponseBody
    @GetMapping("/{user_id}")
    public BaseResponse<String> getUserBooking(@PathVariable("user_id") int user_id){
        try{

            // jwt
            int user_noxByJwt = jwtService.getUserId();
            if(user_id != user_noxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GeTimeUserBookingRes> getUserTime = bookingProvider.getUserTime(user_id);
            List<GetSleepUserBookingRes> getUserSleep= bookingProvider.getUserSleep(user_id);
            List list = new ArrayList();
            list.add(getUserTime);
            list.add(getUserSleep);
            return new BaseResponse(list);
        }catch (BaseException exception){
          return new BaseResponse<>(exception.getStatus());
        }
    }

    // 47. [GET]  숙소 예약 내역 상세 API  & [GET] 예약 확인서 API
    @ResponseBody
    @GetMapping("/{user_id}/bookingCheck")     // /booking/:userId/bookingCheck?bookingType=S&roomBookingId=1
    public BaseResponse<String> getBookingCheck(@PathVariable("user_id") int user_id,
                                                     @RequestParam(required = false) String bookingType,
                                                     @RequestParam(required = false) int roomBookingId){
        try{
            // jwt
            int user_noxByJwt = jwtService.getUserId();
            if(user_id != user_noxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(bookingType.equals("T")){
                System.out.println("T");
                List<GetCheckTimeRes> getCheckTime = bookingProvider.getCheckTime(user_id,roomBookingId);
                return new BaseResponse(getCheckTime);
            }else {
                System.out.println("S");
                List<GetCheckSleepRes> getCheckSleep= bookingProvider.getCheckSleep(user_id,roomBookingId);
                return new BaseResponse(getCheckSleep);
            }
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    ///////////////////////////////////////////////////////////////
    // [PATCH] 숙소 예약 취소 - status 값 변경
    @ResponseBody      // /booking/:userid?roomBookingId=30&roomBookingType=S&bookingId=
    @PatchMapping("/{user_id}")
    public BaseResponse<String> modifyStatus(@PathVariable("user_id") int user_id,
                                             @RequestBody UserBooking userBooking,

                                              @RequestParam(required = false) int roomBookingId,
                                             @RequestParam(required = false) String roomBookingType) throws BaseException{
        // jwt
        int user_noxByJwt = jwtService.getUserId();
        if(user_id != user_noxByJwt){
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        if(roomBookingType.equals("T")){
            PatchUserBookingReq patchUserBookingReq = new PatchUserBookingReq(roomBookingId, userBooking.getStatus());
            PatchTimeBookingReq patchTimeBookingReq = new PatchTimeBookingReq(roomBookingId, userBooking.getStatus());

            bookingService.modifyStatus(patchUserBookingReq);
            bookingService.modifyTimeStatus(patchTimeBookingReq);

            String result = "대실 - 예약취소되었습니다";
            return new BaseResponse(result);
        }else{
            PatchUserBookingReq patchUserBookingReq = new PatchUserBookingReq(roomBookingId, userBooking.getStatus());
            PatchSleepBookingReq patchSleepBookingReq = new PatchSleepBookingReq(roomBookingId, userBooking.getStatus());

            bookingService.modifyStatus(patchUserBookingReq);
            bookingService.modifySleepStatus(patchSleepBookingReq);

            String result = "숙박 - 예약취소되었습니다";
            return new BaseResponse(result);
        }
    }

    // [GET] 대실 이용 시간 보여주기 API
    @ResponseBody
    @GetMapping("/day/time")
    public BaseResponse<List<GetDayTimeRes>> getTime() {
        try{
            List<GetDayTimeRes> getDayTimeRes = bookingProvider.getTime();
            return new BaseResponse(getDayTimeRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    ///////////////////////////////////////////////////////////////
    // [POST] 대실 예약하기 API
    @ResponseBody
    @PostMapping("/time")
    public BaseResponse<PostTimeBookingRes> createTime(@RequestBody PostTimeBookingReq postTimeBookingReq){
        try{

            // 호텔, 방 정확히 선택했는지 확인
           if(postTimeBookingReq.getHotel_id() == 0) {
              return new BaseResponse<>(POST_EMPTY_HOTEL);
           }
           if(postTimeBookingReq.getRoom_id() == 0) {
              return new BaseResponse<>(POST_EMPTY_ROOM);
          }

          // 시간 선택 확인
           if(postTimeBookingReq.getBooking_start_time() == null){
               return new BaseResponse<>(POST_EMPTY_TIME);
           }
           if(postTimeBookingReq.getBooking_end_time() == null){
               return new BaseResponse<>(POST_EMPTY_TIME);
           }

           // 날짜 선택 확인
            if(postTimeBookingReq.getBooking_start_date() == null){
                return new BaseResponse<>(POST_EMPTY_DATE);
            }
            if(postTimeBookingReq.getBooking_end_date() == null){
                return new BaseResponse<>(POST_EMPTY_DATE);
            }

            PostTimeBookingRes postTimeBookingRes =bookingService.createTime(postTimeBookingReq);

            return new BaseResponse(postTimeBookingRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // [POST] 숙박 예약하기 API
    @ResponseBody
    @PostMapping("/sleep")
    public BaseResponse<PostSleepBookingRes> createTime(@RequestBody PostSleepBookingReq postSleepBookingReq){
        try{

            // 호텔, 방 정확히 선택했는지 확인
            if(postSleepBookingReq.getHotel_id() == 0) {
                return new BaseResponse<>(POST_EMPTY_HOTEL);
            }
            if(postSleepBookingReq.getRoom_id() == 0) {
                return new BaseResponse<>(POST_EMPTY_ROOM);
            }

            // 날짜 선택 확인
            if(postSleepBookingReq.getBooking_start_date() == null){
                return new BaseResponse<>(POST_EMPTY_DATE);
            }
            if(postSleepBookingReq.getBooking_end_date() == null){
                return new BaseResponse<>(POST_EMPTY_DATE);
            }

            PostSleepBookingRes postSleepBookingRes =bookingService.createSleep(postSleepBookingReq);

            return new BaseResponse(postSleepBookingRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
