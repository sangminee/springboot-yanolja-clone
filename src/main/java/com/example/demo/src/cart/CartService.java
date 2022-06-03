package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.src.booking.model.Patch.PatchUserBookingReq;
import com.example.demo.src.cart.model.Patch.PatchCartReq;
import com.example.demo.src.cart.model.Patch.PatchSleepCartReq;
import com.example.demo.src.cart.model.Patch.PatchTimeCartReq;
import com.example.demo.src.cart.model.Post.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_STATUS;

@Service
public class CartService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CartDao cartDao;

    @Autowired
    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    // [PATCH] - User
    public void modifyStatus(PatchCartReq patchCartReq) throws BaseException{
        try{
            int result = cartDao.modifyStatus(patchCartReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STATUS);
            }
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // [PATCH] - Time
    public void modifyTimeStatus(PatchTimeCartReq patchTimeCartReq) throws BaseException{
        try{
            int result = cartDao.modifyTimeStatus(patchTimeCartReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STATUS);
            }
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // [PATCH] - Sleep
    public void modifySleepStatus(PatchSleepCartReq patchSleepCartReq) throws BaseException{
        try{
            int result = cartDao.modifySleepStatus(patchSleepCartReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STATUS);
            }
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // [POST]  대실 장바구니 담기 API - Time
    public PostCartTimeRes createTime(PostCartTimeReq postCartTimeReq) throws BaseException {
        try{
            int id = cartDao.createTime(postCartTimeReq);

            PostCartUserReq postCartUserReq = new PostCartUserReq();
            postCartUserReq.setUser_id(postCartTimeReq.getUser_id());
            postCartUserReq.setHotel_id(postCartTimeReq.getHotel_id());
            postCartUserReq.setRoom_cart_id(id);
            postCartUserReq.setRoom_type(postCartTimeReq.getCart_type());

            cartDao.createUserCart(postCartUserReq);

            return new PostCartTimeRes(id);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // [POST]  숙박 장바구니 담기 API -Sleep
    public PostCartSleepRes createSleep(PostCartSleepReq postCartSleepReq) throws BaseException {
        try{
            int id = cartDao.createSleep(postCartSleepReq);

            PostCartUserReq postCartUserReq = new PostCartUserReq();
            postCartUserReq.setUser_id(postCartSleepReq.getUser_id());
            postCartUserReq.setHotel_id(postCartSleepReq.getHotel_id());
            postCartUserReq.setRoom_cart_id(id);
            postCartUserReq.setRoom_type(postCartSleepReq.getCart_type());

            cartDao.createUserCart(postCartUserReq);

            return new PostCartSleepRes(id);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
