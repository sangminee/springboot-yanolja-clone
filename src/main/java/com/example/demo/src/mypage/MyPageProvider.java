package com.example.demo.src.mypage;

import com.example.demo.config.BaseException;
import com.example.demo.src.like.model.GetLikeRes;
import com.example.demo.src.mypage.model.GetMyPageRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MyPageProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MyPageDao myPageDao;

    public MyPageProvider(MyPageDao myPageDao) {
        this.myPageDao = myPageDao;
    }

    public List<GetMyPageRes> getMyPage(int user_id) throws BaseException {
        try{
            List<GetMyPageRes> getMyPageRes = myPageDao.getMyPage(user_id);
            return getMyPageRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
