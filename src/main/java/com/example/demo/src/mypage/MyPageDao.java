package com.example.demo.src.mypage;

import com.example.demo.src.booking.model.Get.GeTimeUserBookingRes;
import com.example.demo.src.mypage.model.GetMyPageRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MyPageDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetMyPageRes> getMyPage(int user_id) {
        String getUserBookingQuery ="select * from user where id = ?";
        int getBookingUser_idParms = user_id;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetMyPageRes(
                        rs.getString("nickname"),
                        rs.getString("img_url")
                ),getBookingUser_idParms);
    }
}
