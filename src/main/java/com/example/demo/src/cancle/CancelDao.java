package com.example.demo.src.cancle;

import com.example.demo.src.cancle.model.Get.*;
import com.example.demo.src.cancle.model.Post.PostBookingCancelReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CancelDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //
    public List<GetUserCancelRes> getCancelUser(int user_id){
        String getUserBookingQuery ="select c.user_booking_id, c.room_booking_id, c.booking_type\n" +
                "from `user_booking_cancel` c\n" +
                "where c.user_id = ?;";
        int getUser_idParms = user_id;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetUserCancelRes(
                        rs.getInt("user_booking_id"),
                        rs.getInt("room_booking_id"),
                        rs.getString("booking_type")
                ),getUser_idParms);
    }

    //
    public List<GetCancelReceiptRes> getCancelReceipt(int user_id, int id){
        String getUserBookingQuery ="select c.id , c.created_at,h.hotel_name,\n" +
                "       c.final_payment, c.company\n" +
                "from `user_booking_cancel` c\n" +
                "Join `user_booking` b\n" +
                "    on c.user_booking_id = b.id\n" +
                "Join `hotel` h\n" +
                "on b.hotel_id = h.id\n" +
                "where c.user_id = ? AND c.id = ?;";
        int getUser_idParms = user_id;
        int getCancelIdParms = id;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetCancelReceiptRes(
                        rs.getInt("id"),
                        rs.getTimestamp("created_at"),
                        rs.getString("hotel_name"),
                        rs.getString("final_payment"),
                        rs.getString("company")
                ),getUser_idParms,getCancelIdParms);
    }

    //  user Detail Time
    public List<GetCancelDetailTimeRes> getTimeCancelDetail(int user_id, int roomBookingId, int userBookingId) {
        String getUserBookingQuery =" select `user_booking_cancel`.id, `room_booking_time`.created_at,`room_booking_time`.hotel_id, `room_booking_time`.room_id,\n" +
                "                `hotel`.hotel_name,\n" +
                "                       `room`.room_name,\n" +
                "                       `room_img`.img_url,\n" +
                "                       `room_booking_time`.booking_type,\n" +
                "                       `room_booking_time`.booking_start_date,\n" +
                "                       `room_booking_time`.booking_end_date,\n" +
                "                       `room_booking_time`.booking_start_time  as checkIn,\n" +
                "                       `room_booking_time`.booking_end_time as checkOut,\n" +
                "                       (`room_booking_time`.booking_end_time - `room_booking_time`.booking_start_time)/100 as time,\n" +
                "                       `room_booking_time`.room_final_price as booking_price,\n" +
                "                       `room_booking_time`.room_final_price as payment,\n" +
                "                       `payment`.payment_name, `user_booking_cancel`.cancel_list_name,`user_booking_cancel`.refund\n" +
                "                From `user_booking`\n" +
                "                Join `hotel`\n" +
                "                on `user_booking`.hotel_id = `hotel`.id\n" +
                "                Join `room_booking_time`\n" +
                "                on `user_booking`.room_booking_id = `room_booking_time`.id\n" +
                "                JOIN `room`\n" +
                "                on `room_booking_time`.room_id = `room`.id\n" +
                "                join `room_img`\n" +
                "                on `room`.id = `room_img`.room_id\n" +
                "                Join `user`\n" +
                "                on `user_booking`.user_id = `user`.id\n" +
                "                Join `payment`\n" +
                "                on `room_booking_time`.payment_id = `payment`.id\n" +
                "                Join `user_booking_cancel`\n" +
                "                on `user_booking`.id = `user_booking_cancel`.user_booking_id\n" +
                "                where `user_booking_cancel`.room_booking_id = ? AND `user_booking_cancel`.user_booking_id = ? AND `user_booking_cancel`.booking_type in('T')\n" +
                "                GROUP BY `user_booking`.user_id;";
        int getRoomBookingParms = roomBookingId;
        int getuserBookingIdParms = userBookingId;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetCancelDetailTimeRes(
                        rs.getInt("id"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("hotel_id"),
                        rs.getInt("room_id"),
                        rs.getString("hotel_name"),
                        rs.getString("room_name"),
                        rs.getString("img_url"),
                        rs.getString("booking_type"),
                        rs.getString("booking_start_date"),
                        rs.getString("booking_end_date"),
                        rs.getString("checkIn"),
                        rs.getString("checkOut"),
                        rs.getString("time"),
                        rs.getString("booking_price"),
                        rs.getString("payment"),
                        rs.getString("payment_name"),
                        rs.getString("cancel_list_name"),
                        rs.getString("refund")
                ),getRoomBookingParms,getuserBookingIdParms);
    }

    // user Detail Sleep
    public List<GetCancelDetailSleepRes> getCancelDetailSleep(int user_id, int roomBookingId, int userBookingId) {
        String getUserBookingQuery = "select `user_booking_cancel`.id, `room_booking_sleep`.created_at,`room_booking_sleep`.hotel_id, `room_booking_sleep`.room_id,\n" +
                "                    `hotel`.hotel_name,\n" +
                "                      `room`.room_name,\n" +
                "                       `room_img`.img_url,\n" +
                "                       `room_booking_sleep`.booking_type,\n" +
                "                       `room_booking_sleep`.booking_start_date  as checkIn,\n" +
                "                       `room_booking_sleep`.booking_end_date as checkOut,\n" +
                "                       (`room_booking_sleep`.booking_end_date - `room_booking_sleep`.booking_start_date) as day,\n" +
                "                       `room_booking_sleep`.room_final_price as booking_price,\n" +
                "                       `room_booking_sleep`.room_final_price as payment,\n" +
                "                       `payment`.payment_name, `user_booking_cancel`.cancel_list_name,`user_booking_cancel`.refund\n" +
                "                From `user_booking`\n" +
                "                Join `hotel`\n" +
                "                on `user_booking`.hotel_id = `hotel`.id\n" +
                "                Join `room_booking_sleep`\n" +
                "                on `user_booking`.room_booking_id = `room_booking_sleep`.id\n" +
                "                JOIN `room`\n" +
                "                on `room_booking_sleep`.room_id = `room`.id\n" +
                "                join `room_img`\n" +
                "                on `room`.id = `room_img`.room_id\n" +
                "                Join `user`\n" +
                "                on `user_booking`.user_id = `user`.id\n" +
                "                Join `payment`\n" +
                "                on `room_booking_sleep`.payment_id = `payment`.id\n" +
                "                Join `user_booking_cancel`\n" +
                "                on `room_booking_sleep`.id = `user_booking_cancel`.user_booking_id\n" +
                "                where `user_booking_cancel`.room_booking_id = ? AND `user_booking_cancel`.user_booking_id = ? AND `user_booking_cancel`.booking_type in('S')\n" +
                "                GROUP BY `user_booking`.user_id;";
        int getRoomBookingParms = roomBookingId;
        int getuserBookingIdParms = userBookingId;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetCancelDetailSleepRes(
                        rs.getInt("id"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("hotel_id"),
                        rs.getInt("room_id"),
                        rs.getString("hotel_name"),
                        rs.getString("room_name"),
                        rs.getString("img_url"),
                        rs.getString("booking_type"),
                        rs.getString("checkIn"),
                        rs.getString("checkOut"),
                        rs.getString("day"),
                        rs.getString("booking_price"),
                        rs.getString("payment"),
                        rs.getString("payment_name"),
                        rs.getString("cancel_list_name"),
                        rs.getString("refund")
                ),getRoomBookingParms,getuserBookingIdParms);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    // 캔슬리스트
    public List<GetCancelListRes> getCancelList(){
        String getUserBookingQuery ="select * from booking_cancel_list;";
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetCancelListRes(
                        rs.getInt("id"),
                        rs.getString("cancel_name")
                ));
    }

    // user Time - 호텔 정보 + 취소 및 환불 예상 정보
    public List<GetTimeCancelRes> getTimeCancel(int user_id, int roomBookingId) {
        String getUserBookingQuery ="select `user_booking`.id, `user_booking`.user_id ,`user_booking`.room_booking_id, \n" +
                "       `room_booking_time`.created_at,`room_booking_time`.hotel_id, `room_booking_time`.room_id, \n" +
                "       `hotel`.hotel_name,\n" +
                "       `room`.room_name,\n" +
                "       `room_img`.img_url,\n" +
                "       `room_booking_time`.booking_type,\n" +
                "       `room_booking_time`.booking_start_date,\n" +
                "       `room_booking_time`.booking_end_date,\n" +
                "       `room_booking_time`.booking_start_time  as checkIn,\n" +
                "       `room_booking_time`.booking_end_time as checkOut,\n" +
                "       (`room_booking_time`.booking_end_time - `room_booking_time`.booking_start_time)/100 as time,\n" +
                "       `room_booking_time`.room_final_price as booking_price,\n" +
                "       `room_booking_time`.room_final_price as payment\n" +
                "From `user_booking`\n" +
                "Join `hotel`\n" +
                "on `user_booking`.hotel_id = `hotel`.id\n" +
                "Join `room_booking_time`\n" +
                "on `user_booking`.room_booking_id = `room_booking_time`.id\n" +
                "JOIN `room`\n" +
                "on `room_booking_time`.room_id = `room`.id\n" +
                "join `room_img`\n" +
                "on `room`.id = `room_img`.room_id\n" +
                "Join `user`\n" +
                "on `user_booking`.user_id = `user`.id\n" +
                "where `user_booking`.user_id = ? AND `user_booking`.room_booking_id = ? AND `user_booking`.booking_type in('T')\n" +
                "GROUP BY `user_booking`.user_id;";
        int getBookingUser_idParms = user_id;
        int getRoomBookingParms = roomBookingId;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetTimeCancelRes(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("room_booking_id"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("hotel_id"),
                        rs.getInt("room_id"),
                        rs.getString("hotel_name"),
                        rs.getString("room_name"),
                        rs.getString("img_url"),
                        rs.getString("booking_type"),
                        rs.getString("booking_start_date"),
                        rs.getString("booking_end_date"),
                        rs.getString("checkIn"),
                        rs.getString("checkOut"),
                        rs.getString("time"),
                        rs.getString("booking_price"),
                        rs.getString("payment")
                ),getBookingUser_idParms,getRoomBookingParms);
    }

    // user Sleep - 호텔 정보 + 취소 및 환불 예상 정보
    public List<GetSleepCancelRes> getSleepCancel(int user_id, int roomBookingId) {
        String getUserBookingQuery = "select `user_booking`.id, `user_booking`.user_id ,`user_booking`.room_booking_id, \n" +
                "`room_booking_sleep`.created_at,`room_booking_sleep`.hotel_id, `room_booking_sleep`.room_id,\n" +
                "       `hotel`.hotel_name,\n" +
                "       `room`.room_name,\n" +
                "       `room_img`.img_url,\n" +
                "       `room_booking_sleep`.booking_type,\n" +
                "       `room_booking_sleep`.booking_start_date  as checkIn,\n" +
                "       `room_booking_sleep`.booking_end_date as checkOut,\n" +
                "       (`room_booking_sleep`.booking_end_date - `room_booking_sleep`.booking_start_date) as day,\n" +
                "       `room_booking_sleep`.room_final_price as booking_price,\n" +
                "       `room_booking_sleep`.room_final_price as payment\n" +
                "From `user_booking`\n" +
                "Join `hotel`\n" +
                "on `user_booking`.hotel_id = `hotel`.id\n" +
                "Join `room_booking_sleep`\n" +
                "on `user_booking`.room_booking_id = `room_booking_sleep`.id\n" +
                "JOIN `room`\n" +
                "on `room_booking_sleep`.room_id = `room`.id\n" +
                "join `room_img`\n" +
                "on `room`.id = `room_img`.room_id\n" +
                "Join `user`\n" +
                "on `user_booking`.user_id = `user`.id\n" +
                "where `user_booking`.user_id = ? AND `user_booking`.room_booking_id = ? AND `user_booking`.booking_type in('S')\n" +
                "GROUP BY `user_booking`.user_id;";
        int getBookingUser_idParms = user_id;
        int getRoomBookingParms = roomBookingId;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetSleepCancelRes(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("room_booking_id"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("hotel_id"),
                        rs.getInt("room_id"),
                        rs.getString("hotel_name"),
                        rs.getString("room_name"),
                        rs.getString("img_url"),
                        rs.getString("booking_type"),
                        rs.getString("checkIn"),
                        rs.getString("checkOut"),
                        rs.getString("day"),
                        rs.getString("booking_price"),
                        rs.getString("payment")
                ),getBookingUser_idParms,getRoomBookingParms);
    }

    ///////////////////////////////////////////////////////
    // [POST]
    public int createCancel(PostBookingCancelReq postBookingCancelReq){
        String createCancelQuery="insert into user_booking_cancel (" +
                "user_id, user_booking_id,room_booking_id,booking_type, cancel_list_id, cancel_list_name," +
                "refund) VALUES(?,?,?,?,?,?,?)";
        Object[] createCancelParams = new Object[]{
                postBookingCancelReq.getUser_id(),
                postBookingCancelReq.getUser_booking_id(),
                postBookingCancelReq.getRoom_booking_id(),
                postBookingCancelReq.getBooking_type(),
                postBookingCancelReq.getCancel_list_id(),
                postBookingCancelReq.getCancel_list_name(),
                postBookingCancelReq.getRefund()

        };
        this.jdbcTemplate.update(createCancelQuery, createCancelParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);

    }
}
