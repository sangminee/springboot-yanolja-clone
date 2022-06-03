package com.example.demo.src.like;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.like.model.*;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.demo.config.BaseResponseStatus.*;

@Slf4j   // 로깅할 때 사용하는 어노테이션
@RestController
@RequestMapping("/like")
public class LikeController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final LikeProvider likeProvider;
    @Autowired
    private final LikeService likeService;
    @Autowired
    private final JwtService jwtService;

    public LikeController(LikeProvider likeProvider, LikeService likeService, JwtService jwtService) {
        this.likeProvider = likeProvider;
        this.likeService = likeService;
        this.jwtService = jwtService;
    }


    // [GET] 찜하기 조회 API
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetLikeRes>> getLike(){
        try {
            // jwt
            int user_noxByJwt = jwtService.getUserId();

//            if(user_id != user_noxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

              List<GetLikeRes> getLikeRes = likeProvider.getLike(user_noxByJwt);
              handleRequest ();
              return new BaseResponse(getLikeRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    public SseEmitter handleRequest () {
        final SseEmitter emitter = new SseEmitter();
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    emitter.send(LocalTime.now().toString() , MediaType.TEXT_PLAIN);

                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.completeWithError(e);
                    return;
                }
            }
            emitter.complete();
        });

        return emitter;
    }

    // [POST] 찜하기 API
    @ResponseBody
    @PostMapping("/{user_id}")   //  /like/:userId
    public BaseResponse<PostLikeRes> createLike(@RequestBody @NotNull PostLikeReq postLikeReq, @PathVariable("user_id") int user_id){
        try {
            // jwt
            int user_noxByJwt = jwtService.getUserId();
            if(user_id != user_noxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            // validation
            // 1) user_id 확인
            if(postLikeReq.getUser_id() != user_noxByJwt ){
                return new BaseResponse<>(POST_LIKE_INVALID_USER_ID);
            }
            // 2) hotel_id 정확히 넣었는지
            if(postLikeReq.getHotel_id() == 0){
                return new BaseResponse<>(POST_LIKE_EMPTY_HOTEL);
            }

            PostLikeRes postLikeRes = likeService.createLike(postLikeReq);

            return new BaseResponse(postLikeRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    // 29. [PATCH] 찜 삭제하기  API
    @ResponseBody
    @PatchMapping("/delete")   // /like/delete?userId=1&likeId=1
    public BaseResponse<String> modifyStatus(@RequestParam(required = false) int userId,
                                             @RequestBody Like like,
                                             @RequestParam(required = false) int likeId) throws BaseException {
        try {

            // jwt
            int user_noxByJwt = jwtService.getUserId();
            if(userId != user_noxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            // validation
            if(like.getStatus() == "0"){
                return new BaseResponse<>(PATCH_LIKE_INVALID_STATUS);
            }

//            if(like.getId() != likeId){
//                return new BaseResponse<>(PATCH_CHECK_ID);
//            }

            PatchLikeReq patchLikeReq = new PatchLikeReq(likeId, like.getStatus());
            likeService.modifyStatus(patchLikeReq);
            String result = "찜에서 삭제되었습니다.";
            return new BaseResponse(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
