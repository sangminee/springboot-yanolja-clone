package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.src.search.model.Get.GetSearchListRes;
import com.example.demo.src.search.model.Get.GetSearchRes;
import com.example.demo.src.search.model.Get.GetSearchWeekendRes;
import com.example.demo.src.search.model.Post.PostSearchReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class SearchProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SearchDao searchDao;

    @Autowired
    public SearchProvider(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    // [GET] /search?area=서울   지역 검색 API   - 평일
    public List<GetSearchRes> getArea(String area,int userId) throws BaseException {
        try{
            List<GetSearchRes> getSearchRes = searchDao.getArea(area);

            PostSearchReq postSearchReq = new PostSearchReq();
            postSearchReq.setUser_id(userId);
            postSearchReq.setSearch(area);
            searchDao.createSearch(postSearchReq);

            return getSearchRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // [GET] /search?area=서울   지역 검색 API   - 주말
    public List<GetSearchWeekendRes> getAreaWeekend(String area, int userId) throws BaseException {
        try{
            List<GetSearchWeekendRes> getSearchWeekendRes = searchDao.getAreaWeekend(area);

            PostSearchReq postSearchReq = new PostSearchReq();
            postSearchReq.setUser_id(userId);
            postSearchReq.setSearch(area);
            searchDao.createSearch(postSearchReq);

            return getSearchWeekendRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // [GET] /search/list&userId=3   최근 검색 기록 조회 API
    public List<GetSearchListRes> getSearchList(int user_id) throws BaseException {
        try{
            List<GetSearchListRes> getSearchListRes = searchDao.getSearchList(user_id);
            return getSearchListRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
