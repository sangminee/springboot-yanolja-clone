package com.example.demo.src.like;

import com.example.demo.config.BaseException;
import com.example.demo.src.like.model.GetLikeRes;
import com.example.demo.utils.JwtService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class LikeProvider {

    private final LikeDao likeDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public LikeProvider(LikeDao likeDao, JwtService jwtService) {
        this.likeDao = likeDao;
        this.jwtService = jwtService;
    }

    public List<GetLikeRes> getLike(int user_id)throws BaseException{
        try{
            List<GetLikeRes> getLikeRes = likeDao.getLike(user_id);
            return getLikeRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
