package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.src.booking.model.Patch.PatchSleepBookingReq;
import com.example.demo.src.search.model.Patch.PatchAllSearchReq;
import com.example.demo.src.search.model.Patch.PatchSearchReq;
import com.example.demo.src.search.model.Post.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_STATUS;

@Service
public class SearchService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SearchDao searchDao;

    @Autowired
    public SearchService(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    // [PATCH] /search?searchId=1  최근 검색 기록 한 개 삭제 API
    public void modifyStatus(PatchSearchReq patchSearchReq) throws BaseException{
        try{
            int result = searchDao.modifyStatus(patchSearchReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STATUS);
            }
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 39. [PATCH] /search/all?userId=   최근 검색 기록 전체 삭제 API
    public void modifyAllStatus(PatchAllSearchReq patchAllSearchReq) throws BaseException{
        try{
            int result = searchDao.modifyAllStatus(patchAllSearchReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STATUS);
            }
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
