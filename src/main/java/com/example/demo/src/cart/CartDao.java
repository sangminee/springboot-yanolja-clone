package com.example.demo.src.cart;

import com.example.demo.src.cart.model.Get.*;
import com.example.demo.src.cart.model.Patch.*;
import com.example.demo.src.cart.model.Post.PostCartSleepReq;
import com.example.demo.src.cart.model.Post.PostCartTimeReq;
import com.example.demo.src.cart.model.Post.PostCartUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CartDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // [GET] 장바구니 조회 API
    // User Time
    public List<GetTimeUserCartRes> getUserTime(int user_id){
        String getUserCartQuery ="select  `cart`.id, `cart`.status, `cart`.room_cart_id, `cart`.room_type,\n" +
                "       `hotel`.hotel_name,\n" +
                "       `hotel`.location_memo ,\n" +
                "       `room`.room_name,\n" +
                "       `room`.min_personnel,\n" +
                "       `room`.max_personnel,\n" +
                "       `room`.room_memo,\n" +
                "       `room_img`.img_url,\n" +
                "       `room_cart_time`.booking_start_date,\n" +
                "       `room_cart_time`.booking_end_date,\n" +
                "       `room_cart_time`.booking_start_time,\n" +
                "       `room_cart_time`.booking_end_time,\n" +
                "       (`room_cart_time`.booking_end_time - `room_cart_time`.booking_start_time)/100 as time, `room_cart_time`.room_final_price\n" +
                "From `cart`\n" +
                "Join `hotel`\n" +
                "on `cart`.hotel_id = `hotel`.id\n" +
                "Join `room_cart_time`\n" +
                "on `cart`.room_cart_id = `room_cart_time`.id\n" +
                "JOIN `room`\n" +
                "on `room_cart_time`.room_id = `room`.id\n" +
                "join `room_img`\n" +
                "on `room`.id = `room_img`.room_id\n" +
                "where `cart`.user_id = ? AND `cart`.room_type in('T') AND `cart`.status in ('1')\n" +
                "GROUP BY `room_cart_time`.id;";
        int getCartUser_idParms = user_id;
        return this.jdbcTemplate.query(getUserCartQuery,
                (rs, rowNum) -> new GetTimeUserCartRes(
                        rs.getInt("id"),
                        rs.getString("status"),
                        rs.getInt("room_cart_id"),
                        rs.getString("room_type"),
                        rs.getString("hotel_name"),
                        rs.getString("location_memo"),
                        rs.getString("room_name"),
                        rs.getInt("min_personnel"),
                        rs.getInt("max_personnel"),
                        rs.getString("room_memo"),
                        rs.getString("img_url"),
                        rs.getString("booking_start_date"),
                        rs.getString("booking_end_date"),
                        rs.getString("booking_start_time"),
                        rs.getString("booking_end_time"),
                        rs.getString("time"),
                        rs.getString("room_final_price")
                ),getCartUser_idParms);
    }

    // User Sleep
    public List<GetSleepUserCartRes> getUserSleep(int user_id){
        String getUserCartQuery = "select `cart`.id,`cart`.status, `cart`.room_cart_id, `cart`.room_type,\n" +
                "       `hotel`.hotel_name,\n" +
                "       `hotel`.location_memo ,\n" +
                "       `room`.room_name,\n" +
                "       `room`.min_personnel,\n" +
                "       `room`.max_personnel,\n" +
                "       `room`.room_memo,\n" +
                "       `room_img`.img_url,\n" +
                "       `room_cart_sleep`.booking_start_date,\n" +
                "       `room_cart_sleep`.booking_end_date,\n" +
                "       `room_cart_sleep`.booking_end_date - `room_cart_sleep`.booking_start_date as day, `room_cart_sleep`.room_final_price\n" +
                "From `cart`\n" +
                "Join `hotel`\n" +
                "on `cart`.hotel_id = `hotel`.id\n" +
                "Join `room_cart_sleep`\n" +
                "on `cart`.room_cart_id = `room_cart_sleep`.id\n" +
                "JOIN `room`\n" +
                "on `room_cart_sleep`.room_id = `room`.id\n" +
                "join `room_img`\n" +
                "on `room`.id = `room_img`.room_id\n" +
                "where `cart`.user_id = ?  AND `cart`.room_type in('S') AND `cart`.status in ('1') \n" +
                "GROUP BY `room_cart_sleep`.id;";
        int getCartUser_idParms = user_id;
        return this.jdbcTemplate.query(getUserCartQuery,
                (rs, rowNum) -> new GetSleepUserCartRes(
                        rs.getInt("id"),
                        rs.getString("status"),
                        rs.getInt("room_cart_id"),
                        rs.getString("room_type"),
                        rs.getString("hotel_name"),
                        rs.getString("location_memo"),
                        rs.getString("room_name"),
                        rs.getInt("min_personnel"),
                        rs.getInt("max_personnel"),
                        rs.getString("room_memo"),
                        rs.getString("img_url"),
                        rs.getString("booking_start_date"),
                        rs.getString("booking_end_date"),
                        rs.getString("day"),
                        rs.getString("room_final_price")
                ),getCartUser_idParms);
    }

    //////////////////////////////////////////////////////////

    // [PATCH]
    public int modifyStatus(PatchCartReq patchCartReq){
        String modifyStatusQuery = "update cart set status = ? where room_cart_id = ?";
        Object[] modifyStatusParams = new Object[]{patchCartReq.getStatus(), patchCartReq.getRoom_cart_id()};
        return this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams);
    }
    // [PATCH] - Time
    public int modifyTimeStatus(PatchTimeCartReq patchTimeCartReq){
        String modifyStatusQuery = "update room_cart_time set status = ? where id = ?";
        Object[] modifyStatusParams = new Object[]{patchTimeCartReq.getStatus(), patchTimeCartReq.getId()};
        return this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams);
    }

    // [PATCH] - Sleep
    public int modifySleepStatus(PatchSleepCartReq patchSleepCartReq){
        String modifyStatusQuery = "update room_cart_sleep set status = ? where id = ? ";
        Object[] modifyStatusParams = new Object[]{patchSleepCartReq.getStatus(), patchSleepCartReq.getId()};
        return this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams);
    }

    ///////////////////////////////////////////
    // [POST]  대실 장바구니 담기 API - Time
    public int createTime(PostCartTimeReq postCartTimegReq){
        String createTimeQuery= "insert into room_cart_time (" +
                "user_id, hotel_id, room_id,cart_type," +
                "booking_start_date,booking_end_date,booking_start_time, booking_end_time,room_final_price) VALUES(?,?,?,?,?,?,?,?,?)";
        Object[] createTimeParams = new Object[]{
                postCartTimegReq.getUser_id(),
                postCartTimegReq.getHotel_id(),
                postCartTimegReq.getRoom_id(),
                postCartTimegReq.getCart_type(),
                postCartTimegReq.getBooking_start_time(),
                postCartTimegReq.getBooking_end_date(),
                postCartTimegReq.getBooking_start_time(),
                postCartTimegReq.getBooking_end_time(),
                postCartTimegReq.getRoom_final_price()

        };

        this.jdbcTemplate.update(createTimeQuery, createTimeParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    // [POST]  숙박 장바구니 담기 API -Sleep
    public int createSleep(PostCartSleepReq postCartSleepReq){
        String createSleepQuery="insert into room_cart_sleep (" +
                "user_id, hotel_id, room_id, cart_type," +
                "booking_start_date,booking_end_date,room_final_price) VALUES(?,?,?,?,?,?,?)";
        Object[] createSleepParams = new Object[]{
                postCartSleepReq.getUser_id(),
                postCartSleepReq.getHotel_id(),
                postCartSleepReq.getRoom_id(),
                postCartSleepReq.getCart_type(),
                postCartSleepReq.getBooking_start_date(),
                postCartSleepReq.getBooking_end_date(),
                postCartSleepReq.getRoom_final_price()

        };
        this.jdbcTemplate.update(createSleepQuery, createSleepParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);

    }
    // [POST] 유저 cart 내역 추가하기 API
    public void createUserCart(PostCartUserReq postCartUserReq){
        String createUserBookingQuery=
                "insert into cart (user_id, hotel_id,room_cart_id,room_type) " +
                        "VALUES(?,?,?,?)";
        Object[] createUserBookingParams = new Object[]{
                postCartUserReq.getUser_id(),
                postCartUserReq.getHotel_id(),
                postCartUserReq.getRoom_cart_id(),
                postCartUserReq.getRoom_type()
        };

        this.jdbcTemplate.update(createUserBookingQuery, createUserBookingParams);
        String lastInserIdQuery = "select last_insert_id()";
        this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
}
