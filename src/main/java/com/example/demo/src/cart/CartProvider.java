package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.src.cart.model.Get.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CartProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CartDao cartDao;

    @Autowired
    public CartProvider(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    // User Time
    public List<GetTimeUserCartRes> getUserTime(int user_id) throws BaseException {
        try {
            List<GetTimeUserCartRes> getUserTime= cartDao.getUserTime(user_id);
            return getUserTime;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // User Sleep
    public List<GetSleepUserCartRes> getUserSleep(int user_id) throws BaseException {
        try{
            List<GetSleepUserCartRes> getUserSleep = cartDao.getUserSleep(user_id);
            return getUserSleep;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
