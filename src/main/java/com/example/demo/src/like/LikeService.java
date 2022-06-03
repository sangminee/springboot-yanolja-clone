package com.example.demo.src.like;

import com.example.demo.config.BaseException;
import com.example.demo.src.like.model.*;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class LikeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LikeDao likeDao;

    @Autowired
    public LikeService(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    // [POST] 찜하기 API
    public PostLikeRes createLike(PostLikeReq postLikeReq) throws BaseException{
        try{
            int id = likeDao.createLike(postLikeReq);
            return new PostLikeRes(id);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // [PATCH] 찜 삭제하기  API
    public void modifyStatus(PatchLikeReq patchLikeReq) throws BaseException {
        try{
            int result = likeDao.modifyStatus(patchLikeReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STATUS);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
