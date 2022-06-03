package com.example.demo.src.cancle;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.cancle.model.Get.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CancelProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CancelDao cancelDao;

    public CancelProvider(CancelDao cancelDao) {
        this.cancelDao = cancelDao;
    }

    // [GET] 유저 별 취소 내역 보기
    public List<GetUserCancelRes> getCancelUser(int user_id) throws BaseException {
        try{
            List<GetUserCancelRes> getUserCancelRes = cancelDao.getCancelUser(user_id);
            return getUserCancelRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //  user Detail Time
    public List<GetCancelDetailTimeRes> getTimeCancelDetail(int user_id, int roomBookingId, int userBookingId) throws BaseException {
        try{
            List<GetCancelDetailTimeRes> getTimeCancelDetail = cancelDao.getTimeCancelDetail(user_id,roomBookingId,userBookingId);
            return getTimeCancelDetail;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // user Detail Sleep
    public List<GetCancelDetailSleepRes> getCancelDetailSleep(int user_id, int roomBookingId, int userBookingId) throws BaseException {
        try{
            List<GetCancelDetailSleepRes> getCancelDetailSleep = cancelDao.getCancelDetailSleep(user_id,roomBookingId,userBookingId);
            return getCancelDetailSleep;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    ////////////////////////////////////////////////////////////////
    // 캔슬리스트
    public List<GetCancelListRes> getCancelList() throws BaseException {
        try {
            List<GetCancelListRes> getCancelList= cancelDao.getCancelList();
            return getCancelList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // user Time -> 호텔 정보 + 취소 및 환불 예상 정보
    public List<GetTimeCancelRes> getTimeCancel(int user_id, int roomBookingId) throws BaseException {
        try{
            List<GetTimeCancelRes> getTimeCancel = cancelDao.getTimeCancel(user_id,roomBookingId);
            return getTimeCancel;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // user Sleep -> 호텔 정보 + 취소 및 환불 예상 정보
    public List<GetSleepCancelRes> getSleepCancel(int user_id, int roomBookingId) throws BaseException {
        try{
            List<GetSleepCancelRes> getSleepCancel = cancelDao.getSleepCancel(user_id,roomBookingId);
            return getSleepCancel;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // [GET]  취소 완료 영수증 조회
    public List<GetCancelReceiptRes> getCancelReceipt(int user_id, int id) throws BaseException {
        try{
            List<GetCancelReceiptRes> getCancelReceipt = cancelDao.getCancelReceipt(user_id,id);
            return getCancelReceipt;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
