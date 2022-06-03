package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.search.model.Get.*;
import com.example.demo.src.search.model.Patch.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/search")
public class SearchController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SearchProvider searchProvider;
    @Autowired
    private final SearchService searchService;
    @Autowired
    private final JwtService jwtService;


    public SearchController(SearchProvider searchProvider, SearchService searchService, JwtService jwtService) {
        this.searchProvider = searchProvider;
        this.searchService = searchService;
        this.jwtService = jwtService;
    }

    // 36. [GET] /search?area=서울&userId=3&date=0   지역 검색 API
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetSearchRes>> getArea(@RequestParam(required = false) String area,
                                                    @RequestParam(required = false) int userId,
                                                    @RequestParam(required = false) String date){
        try{
            // jwt
            int user_noxByJwt = jwtService.getUserId();
            if(userId != user_noxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            if(date.equals("weekend")){   // 평일: weekday, 주말 : weekend
                List<GetSearchWeekendRes> getSearchWeekendRes = searchProvider.getAreaWeekend(area, userId);
                return new BaseResponse(getSearchWeekendRes);
            }else{
                List<GetSearchRes> getSearchRes = searchProvider.getArea(area,userId);
                return new BaseResponse(getSearchRes);
            }

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    // 37. [GET] /search/list?userId=3   최근 검색 기록 조회 API
    @ResponseBody
    @GetMapping("/list")
    public BaseResponse<List<GetSearchListRes>> getSearchList(@RequestParam(required = false) int userId){
        try{
              // jwt
            int user_noxByJwt = jwtService.getUserId();
            if(userId != user_noxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetSearchListRes> getSearchListRes = searchProvider.getSearchList(userId);
            return new BaseResponse(getSearchListRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 38. [PATCH] /search/delete?userId=3&searchId=4  최근 검색 기록 한 개 삭제 API
    @ResponseBody
    @PatchMapping("/delete")
    public BaseResponse<String> modifyStatus(@RequestParam(required = false) int searchId,
                                             @RequestBody Search search,
                                             @RequestParam(required = false) int userId) throws BaseException {

        // jwt
        int user_noxByJwt = jwtService.getUserId();
        if(userId != user_noxByJwt){
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        PatchSearchReq patchSearchReq = new PatchSearchReq(searchId,userId,search.getStatus());
        searchService.modifyStatus(patchSearchReq);
        String result = "삭제되었습니다";
        return new BaseResponse(result);
    }

    // 39. [PATCH] /search/delete/all?userId=   최근 검색 기록 전체 삭제 API
    @ResponseBody
    @PatchMapping("/delete/all")
    public BaseResponse<String> modifyAllStatus(@RequestParam(required = false) int userId,
                                             @RequestBody Search search) throws BaseException {

        // jwt
        int user_noxByJwt = jwtService.getUserId();
        if(userId != user_noxByJwt){
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        PatchAllSearchReq patchAllSearchReq = new PatchAllSearchReq(userId,search.getStatus());
        searchService.modifyAllStatus(patchAllSearchReq);
        String result = "삭제되었습니다";
        return new BaseResponse(result);
    }
}
