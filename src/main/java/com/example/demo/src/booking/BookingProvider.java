package com.example.demo.src.booking;

import com.example.demo.config.BaseException;
import com.example.demo.src.booking.model.Get.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class BookingProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BookingDao bookingDao;

    @Autowired
    public BookingProvider(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    // [GET] 숙소 예약내역 조회 - 전체 조회
    // User Time
    public List<GeTimeUserBookingRes> getUserTime(int user_id) throws BaseException {
        try {
            List<GeTimeUserBookingRes> getUserTime= bookingDao.getUserTime(user_id);
            return getUserTime;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // User Sleep
    public List<GetSleepUserBookingRes> getUserSleep(int user_id) throws BaseException {
        try{
            List<GetSleepUserBookingRes> getUserSleep = bookingDao.getUserSleep(user_id);
            return getUserSleep;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    // [GET]  숙소 예약 내역 상세 API
    // [GET] 예약 확인서 API
    // User Time
    public List<GetCheckTimeRes> getCheckTime(int user_id, int roomBookingId) throws BaseException {
        try{
            List<GetCheckTimeRes> getCheckTime = bookingDao.getCheckTime(user_id,roomBookingId);
            return getCheckTime;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // User Sleep
    public List<GetCheckSleepRes> getCheckSleep(int user_id, int roomBookingId) throws BaseException {
        try {
            List<GetCheckSleepRes> getCheckSleep= bookingDao.getCheckSleep(user_id,roomBookingId);
            return getCheckSleep;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    // [GET] 대실시간 조회
    public List<GetDayTimeRes> getTime() throws BaseException {
        try{
            List<GetDayTimeRes> getDayTimeRes = bookingDao.getTime();
            return getDayTimeRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }




}
