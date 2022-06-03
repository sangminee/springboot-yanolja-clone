package com.example.demo.src.booking;

import com.example.demo.config.BaseException;
import com.example.demo.src.booking.model.Patch.PatchSleepBookingReq;
import com.example.demo.src.booking.model.Patch.PatchTimeBookingReq;
import com.example.demo.src.booking.model.Patch.PatchUserBookingReq;
import com.example.demo.src.booking.model.Patch.UserBooking;
import com.example.demo.src.booking.model.Post.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BookingService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BookingDao bookingDao;

    @Autowired
    public BookingService(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    // [POST] 대실 예약하기 API
    public PostTimeBookingRes createTime(PostTimeBookingReq postTimeBookingReq) throws BaseException {
        try{
            int id = bookingDao.createTime(postTimeBookingReq);

            PostUserBookingReq postUserBookingReq = new PostUserBookingReq();
            postUserBookingReq.setUser_id(postTimeBookingReq.getUser_id());
            postUserBookingReq.setHotel_id(postTimeBookingReq.getHotel_id());
            postUserBookingReq.setRoom_booking_id(id);
            postUserBookingReq.setBooking_type(postTimeBookingReq.getBooking_type());

            bookingDao.createUserBooking(postUserBookingReq);

            return new PostTimeBookingRes(id);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // [POST] 숙박 예약하기 API
    public PostSleepBookingRes createSleep(PostSleepBookingReq postSleepBookingReq) throws BaseException {
        try{
            int id = bookingDao.createSleep(postSleepBookingReq);
            PostUserBookingReq postUserBookingReq = new PostUserBookingReq();
            postUserBookingReq.setUser_id(postSleepBookingReq.getUser_id());
            postUserBookingReq.setHotel_id(postSleepBookingReq.getHotel_id());
            postUserBookingReq.setRoom_booking_id(id);
            postUserBookingReq.setBooking_type(postSleepBookingReq.getBooking_type());
            bookingDao.createUserBooking(postUserBookingReq);

            return new PostSleepBookingRes(id);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // [PATCH] 숙소 예약 취소 - status 값 변경
    public void modifyStatus(PatchUserBookingReq patchUserBookingReq) throws BaseException{
        try{
            int result = bookingDao.modifyStatus(patchUserBookingReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STATUS);
            }

        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifySleepStatus(PatchSleepBookingReq patchSleepBookingReq) throws BaseException{
        try{
            int result = bookingDao.modifySleepStatus(patchSleepBookingReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STATUS);
            }
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyTimeStatus(PatchTimeBookingReq patchTimeBookingReq) throws BaseException{
        try{
            int result = bookingDao.modifyTimeStatus(patchTimeBookingReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STATUS);
            }
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
