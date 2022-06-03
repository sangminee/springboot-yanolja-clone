package com.example.demo.src.mypage;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.mypage.model.GetMyPageRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/mypage")
public class MyPageController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MyPageProvider myPageProvider;
    @Autowired
    private final JwtService jwtService;

    public MyPageController(MyPageProvider myPageProvider, JwtService jwtService) {
        this.myPageProvider = myPageProvider;
        this.jwtService = jwtService;
    }

    // [GET] 마이페이지 조회 API
    @ResponseBody
    @GetMapping("/{user_id}")
    public BaseResponse<String> getMyPage(@PathVariable("user_id") int user_id){
        try{

            // jwt
            int user_noxByJwt = jwtService.getUserId();
            if(user_id != user_noxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetMyPageRes> getMyPageRes = myPageProvider.getMyPage(user_id);
            return new BaseResponse(getMyPageRes);

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
