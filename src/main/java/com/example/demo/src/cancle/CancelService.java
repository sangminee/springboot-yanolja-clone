package com.example.demo.src.cancle;

import com.example.demo.config.BaseException;
import com.example.demo.src.booking.model.Post.PostTimeBookingReq;
import com.example.demo.src.booking.model.Post.PostTimeBookingRes;
import com.example.demo.src.booking.model.Post.PostUserBookingReq;
import com.example.demo.src.cancle.model.Post.PostBookingCancelReq;
import com.example.demo.src.cancle.model.Post.PostBookingCancelRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CancelService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CancelDao cancelDao;

    public CancelService(CancelDao cancelDao) {
        this.cancelDao = cancelDao;
    }

    // [POST] 대실 예약하기 API
    public PostBookingCancelRes createCancel(PostBookingCancelReq postBookingCancelReq) throws BaseException {
        try{
            int id = cancelDao.createCancel(postBookingCancelReq);
//
//            PostUserBookingReq postUserBookingReq = new PostUserBookingReq();
//            postUserBookingReq.setUser_id(postTimeBookingReq.getUser_id());
//            postUserBookingReq.setHotel_id(postTimeBookingReq.getHotel_id());
//            postUserBookingReq.setRoom_booking_id(id);
//            postUserBookingReq.setBooking_type(postTimeBookingReq.getBooking_type());
//
//            bookingDao.createUserBooking(postUserBookingReq);

            return new PostBookingCancelRes(id);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
