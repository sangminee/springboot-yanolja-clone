package com.example.demo.src.like;

import com.example.demo.src.like.model.GetLikeRes;
import com.example.demo.src.like.model.PatchLikeReq;
import com.example.demo.src.like.model.PostLikeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class LikeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetLikeRes> getLike(int user_id){
        String getLikeQuery =
                "SELECT `like`.user_id, `like`.id , `like`.status , `like`.hotel_id,\n" +
                "       `hotel`.hotel_name,`hotel`.notice,`hotel`.event,\n" +
                "       `hotel_img`.img_url\n" +
                "FROM `like`" +
                "Join hotel\n" +
                "on `like`.hotel_id = hotel.id\n" +
                "JOIN hotel_img\n" +
                "on  hotel.hotel_img_id = hotel_img.id\n" +
                "where user_id = ?  AND `like`.status in ('1')";
        int getLikeUser_idParams = user_id;
        return this.jdbcTemplate.query(getLikeQuery,
                (rs,rowNum)->new GetLikeRes(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("status"),
                        rs.getInt("hotel_id"),
                        rs.getString("hotel_name"),
                        rs.getString("notice"),
                        rs.getString("event"),
                        rs.getString("img_url")),
                getLikeUser_idParams);
    }

    // [POST] 찜하기 API
    public int createLike(PostLikeReq postLikeReq){
        String createLikeQuery ="insert into `like` ( user_id, hotel_id )  VALUES (?,?)";
        Object[] createLikeParams = new Object[]{
                postLikeReq.getUser_id(),
                postLikeReq.getHotel_id()};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    // 29. [PATCH] 찜 삭제하기  API
    public int modifyStatus(PatchLikeReq patchLikeReq) {
        String modifyStatusQuery = "update `like` set status = ? where id = ? ";
        Object[] modifyStatusParams = new Object[]{patchLikeReq.getStatus(), patchLikeReq.getId()};

        return this.jdbcTemplate.update(modifyStatusQuery, modifyStatusParams);
    }

}
