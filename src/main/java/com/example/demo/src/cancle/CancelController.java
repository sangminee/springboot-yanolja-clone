package com.example.demo.src.cancle;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.cancle.model.Get.*;
import com.example.demo.src.cancle.model.Post.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/order-cancel")
public class CancelController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CancelProvider cancelProvider;
    @Autowired
    private final CancelService cancelService;
    @Autowired
    private final JwtService jwtService;

    public CancelController(CancelProvider cancelProvider, CancelService cancelService, JwtService jwtService) {
        this.cancelProvider = cancelProvider;
        this.cancelService = cancelService;
        this.jwtService = jwtService;
    }

    // 48. [GET] 유저 별 취소 내역 보기
    @ResponseBody
    @GetMapping("/{user_id}/all")
    public BaseResponse<String> getCancelUser(@PathVariable("user_id") int user_id) throws BaseException {
          List<GetUserCancelRes> getUserCancelRes = cancelProvider.getCancelUser(user_id);
          return new BaseResponse(getUserCancelRes);
    }

    // 20. [GET] 예약 - 취소 및 환불 상세 정보
    @ResponseBody
    @GetMapping("/{user_id}/detail")  // /order-cancel/:userId/detail?bookingType=&roomBookingId=&userBookingId=
    public BaseResponse<String> getCancelDetail(@PathVariable("user_id") int user_id,
                                          @RequestParam(required = false) String bookingType,
                                          @RequestParam(required = false) int roomBookingId,
                                          @RequestParam(required = false) int userBookingId){
        try{
            // jwt
//            int user_noxByJwt = jwtService.getUserId();
//            if(user_id != user_noxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

            if(bookingType.equals("T")){
                System.out.println("T");
                List<GetCancelDetailTimeRes> getTimeCancelDetail = cancelProvider.getTimeCancelDetail(user_id, roomBookingId,userBookingId);
                return new BaseResponse(getTimeCancelDetail);
            }else {
                System.out.println("S");
                List<GetCancelDetailSleepRes> getCancelDetailSleepRes= cancelProvider.getCancelDetailSleep(user_id,roomBookingId,userBookingId);
                return new BaseResponse(getCancelDetailSleepRes);
            }

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // 21. [GET] 예약 - 예약취소 요청 페이지 API   /order-cancel/:userId?bookingType=&roomBookingId=
    @ResponseBody
    @GetMapping("/{user_id}")
    public BaseResponse<String> getCancel(@PathVariable("user_id") int user_id,
                                          @RequestParam(required = false) String bookingType,
                                          @RequestParam(required = false) int roomBookingId){
        try{

            // jwt
            int user_noxByJwt = jwtService.getUserId();
            if(user_id != user_noxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetCancelListRes> getCancelList = cancelProvider.getCancelList();

            List list = new ArrayList();
            list.add(getCancelList);   // 캔슬리스트


            if(bookingType.equals("T")){
                System.out.println("T");
                List<GetTimeCancelRes> getTimeCancel = cancelProvider.getTimeCancel(user_id, roomBookingId);
                list.add(getTimeCancel);
                return new BaseResponse(list);
            }else {
                System.out.println("S");
                List<GetSleepCancelRes> getSleepCancel= cancelProvider.getSleepCancel(user_id,roomBookingId);
                list.add(getSleepCancel);
                return new BaseResponse(list);
            }

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 45. [POST] 예약 - 예약 취소 API
    @ResponseBody  // /order-cancel/:userId?bookingType=&roomBookingId=&userBookingId=&cancel_list_id=&refund=
    @PostMapping("/{user_id}")
    public BaseResponse<PostBookingCancelRes> createCancel(@RequestBody PostBookingCancelReq postBookingCancelReq,
                                                           @RequestParam(required = false) String bookingType,
                                                           @RequestParam(required = false) int roomBookingId,
                                                           @RequestParam(required = false) int userBookingId,
                                                           @RequestParam(required = false) int cancel_list_id,
                                                           @RequestParam(required = false) String refund,
                                                           @PathVariable("user_id") int user_id){
        try{
            // user_id 확인
            if(postBookingCancelReq.getUser_id() == 0){
                return new BaseResponse<>(POST_CHECK_USER_ID);
            }
            //  user_booking_id  확인
            if(postBookingCancelReq.getUser_booking_id() == 0){
                return new BaseResponse<>(POST_CHECK_USER_BOOKING_ID);
            }
            // room_booking_id  확인
            if(postBookingCancelReq.getRoom_booking_id() == 0){
                return new BaseResponse<>(POST_CHECK_ROOM_BOOKING_ID);
            }
            // booking_type 확인
            if(postBookingCancelReq.getBooking_type() == null){
                return new BaseResponse<>(POST_CHECK_BOOKING_TYPE);
            }
            // cancel_list_id 확인
            if(postBookingCancelReq.getCancel_list_id() == 0){
                return new BaseResponse<>(POST_CHECK_CANCEL_LIST_ID);
            }
            // cancel_list_name 확인
            if(postBookingCancelReq.getCancel_list_name() == null){
                return new BaseResponse<>(POST_CHECK_CANCEL_LIST_NAME);
            }
            // refund 확인
            if(postBookingCancelReq.getRefund() == null){
                return new BaseResponse<>(POST_CHECK_REFUND);
            }

            PostBookingCancelRes postBookingCancelRes =cancelService.createCancel(postBookingCancelReq);
            return new BaseResponse(postBookingCancelRes);

        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 24. [GET]  취소 완료 영수증 조회
    @ResponseBody
    @GetMapping("/{user_id}/receipts/{id}")  // /order-cancel/:userId/receipts/:id
    public BaseResponse<String> getCancelReceipt(@PathVariable("user_id") int user_id, @PathVariable("id") int id){
        try{
            // jwt
//            int user_noxByJwt = jwtService.getUserId();
//            if(user_id != user_noxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

            List<GetCancelReceiptRes> getCancelReceipt= cancelProvider.getCancelReceipt(user_id,id);
                return new BaseResponse(getCancelReceipt);

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
