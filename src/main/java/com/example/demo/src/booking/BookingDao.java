package com.example.demo.src.booking;

import com.example.demo.src.booking.model.Get.*;
import com.example.demo.src.booking.model.Patch.PatchSleepBookingReq;
import com.example.demo.src.booking.model.Patch.PatchTimeBookingReq;
import com.example.demo.src.booking.model.Patch.PatchUserBookingReq;
import com.example.demo.src.booking.model.Post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BookingDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // [GET] 숙소 예약내역 조회 - 전체 조회
    // User Time
    public List<GeTimeUserBookingRes> getUserTime(int user_id){
        String getUserBookingQuery ="select `user_booking`.room_booking_id, \n" +
                "       `room_booking_time`.booking_type," +
                "       `room_booking_time`.hotel_id, `room_booking_time`.room_id," +
                "       `hotel`.hotel_name,\n" +
                "       `room`.room_name,\n" +
                "       `room_img`.img_url,\n" +
                "       `room_booking_time`.booking_start_date,\n" +
                "       `room_booking_time`.booking_end_date,\n" +
                "       `room_booking_time`.booking_start_time,\n" +
                "       `room_booking_time`.booking_end_time,\n" +
                "       (`room_booking_time`.booking_end_time - `room_booking_time`.booking_start_time)/100 as time\n" +
                "From `user_booking`\n" +
                "Join `hotel`\n" +
                "on `user_booking`.hotel_id = `hotel`.id\n" +
                "Join `room_booking_time`\n" +
                "on `user_booking`.room_booking_id = `room_booking_time`.id\n" +
                "JOIN `room`\n" +
                "on `room_booking_time`.room_id = `room`.id\n" +
                "join `room_img`\n" +
                "on `room`.id = `room_img`.room_id\n" +
                "where `user_booking`.user_id = ?\n" +
                "GROUP BY `user_booking`.room_booking_id;";
        int getBookingUser_idParms = user_id;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GeTimeUserBookingRes(
                        rs.getInt("room_booking_id"),
                        rs.getString("booking_type"),
                        rs.getInt("hotel_id"),
                        rs.getInt("room_id"),
                        rs.getString("hotel_name"),
                        rs.getString("room_name"),
                        rs.getString("img_url"),
                        rs.getString("booking_start_date"),
                        rs.getString("booking_end_date"),
                        rs.getString("booking_start_time"),
                        rs.getString("booking_end_time"),
                        rs.getString("time")
                ),getBookingUser_idParms);
    }

    // User Sleep
    public List<GetSleepUserBookingRes> getUserSleep(int user_id){
        String getUserBookingQuery = "select `user_booking`.room_booking_id,\n " +
                "       `room_booking_sleep`.booking_type, " +
                "       `room_booking_sleep`.hotel_id, `room_booking_sleep`.room_id, " +
                "       `hotel`.hotel_name,\n" +
                "       `room`.room_name,\n" +
                "       `room_img`.img_url,\n" +
                "       `room_booking_sleep`.booking_start_date,\n" +
                "       `room_booking_sleep`.booking_end_date,\n" +
                "       (`room_booking_sleep`.booking_end_date - `room_booking_sleep`.booking_start_date) as day\n" +
                "From `user_booking`\n" +
                "Join `hotel`\n" +
                "on `user_booking`.hotel_id = `hotel`.id\n" +
                "Join `room_booking_sleep`\n" +
                "on `user_booking`.room_booking_id = `room_booking_sleep`.id\n" +
                "JOIN `room`\n" +
                "on `room_booking_sleep`.room_id = `room`.id\n" +
                "join `room_img`\n" +
                "on `room`.id = `room_img`.room_id\n" +
                "where `user_booking`.user_id = ?\n" +
                "GROUP BY `user_booking`.room_booking_id;";
        int getBookingUser_idParms = user_id;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetSleepUserBookingRes(
                        rs.getInt("room_booking_id"),
                        rs.getString("booking_type"),
                        rs.getInt("hotel_id"),
                        rs.getInt("room_id"),
                        rs.getString("hotel_name"),
                        rs.getString("room_name"),
                        rs.getString("img_url"),
                        rs.getString("booking_start_date"),
                        rs.getString("booking_end_date"),
                        rs.getString("day")
                ),getBookingUser_idParms);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // 47. [GET]  숙소 예약 내역 상세 API
    // 47. [GET] 예약 확인서 API
    // User Time
    public List<GetCheckTimeRes> getCheckTime(int user_id, int roomBookingId) {
        String getUserBookingQuery ="select `user_booking`.room_booking_id,`room_booking_time`.created_at,`room_booking_time`.hotel_id, `room_booking_time`.room_id,\n" +
                "                       `hotel`.hotel_name,\n" +
                "                       `room`.room_name,\n" +
                "                       `room_img`.img_url,\n" +
                "                       `room_booking_time`.booking_type,\n" +
                "                       `room_booking_time`.booking_start_date,\n" +
                "                       `room_booking_time`.booking_end_date,\n" +
                "                       `room_booking_time`.booking_start_time  as checkIn,\n" +
                "                      `room_booking_time`.booking_end_time as checkOut,\n" +
                "                       (`room_booking_time`.booking_end_time - `room_booking_time`.booking_start_time)/100 as time,\n" +
                "                       `user`.name as user,\n" +
                "                       `user`.phone_number as user_phone,\n" +
                "                       `user`.name as guest,\n" +
                "                       `user`.phone_number as guest_phone,\n" +
                "                       `room_booking_time`.room_final_price as booking_price,\n" +
                "                       `room_booking_time`.room_final_price as payment,\n" +
                "                       `payment`.payment_name\n" +
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
                "                where `user_booking`.user_id = ? AND `user_booking`.room_booking_id = ? AND `user_booking`.booking_type in('T')\n" +
                "                GROUP BY `user_booking`.user_id;";
        int getBookingUser_idParms = user_id;
        int getRoomBookingParms = roomBookingId;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetCheckTimeRes(
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
                        rs.getString("user"),
                        rs.getString("user_phone"),
                        rs.getString("guest"),
                        rs.getString("guest_phone"),
                        rs.getString("booking_price"),
                        rs.getString("payment"),
                        rs.getString("payment_name")
                ),getBookingUser_idParms,getRoomBookingParms);
    }

    // User Sleep
    public List<GetCheckSleepRes> getCheckSleep(int user_id, int roomBookingId) {
        String getUserBookingQuery = "select `user_booking`.room_booking_id,`room_booking_sleep`.created_at,`room_booking_sleep`.hotel_id, `room_booking_sleep`.room_id,\n" +
                "       `hotel`.hotel_name,\n" +
                "       `room`.room_name,\n" +
                "       `room_img`.img_url,\n" +
                "       `room_booking_sleep`.booking_type,\n" +
                "       `room_booking_sleep`.booking_start_date  as checkIn,\n" +
                "       `room_booking_sleep`.booking_end_date as checkOut,\n" +
                "       (`room_booking_sleep`.booking_end_date - `room_booking_sleep`.booking_start_date) as day,\n" +
                "\n" +
                "       `user`.name as user,\n" +
                "       `user`.phone_number as user_phone,\n" +
                "       `user`.name as guest,\n" +
                "       `user`.phone_number as guest_phone,\n" +
                "\n" +
                "       `room_booking_sleep`.room_final_price as booking_price,\n" +
                "       `room_booking_sleep`.room_final_price as payment,\n" +
                "       `payment`.payment_name\n" +
                "\n" +
                "\n" +
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
                "Join `payment`\n" +
                "on `room_booking_sleep`.payment_id = `payment`.id\n" +
                "where `user_booking`.user_id = ? AND `user_booking`.room_booking_id = ? AND `user_booking`.booking_type in('S')\n" +
                "GROUP BY `user_booking`.user_id;";
        int getBookingUser_idParms = user_id;
        int getRoomBookingParms = roomBookingId;
        return this.jdbcTemplate.query(getUserBookingQuery,
                (rs, rowNum) -> new GetCheckSleepRes(
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
                        rs.getString("user"),
                        rs.getString("user_phone"),
                        rs.getString("guest"),
                        rs.getString("guest_phone"),
                        rs.getString("booking_price"),
                        rs.getString("payment"),
                        rs.getString("payment_name")
                ),getBookingUser_idParms,getRoomBookingParms);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // [GET] 대실 시간 보여주기
    public List<GetDayTimeRes> getTime(){
        String getTimesQuery = "select * from day_time";
        return this.jdbcTemplate.query(getTimesQuery,
                (rs, rowNum) -> new GetDayTimeRes(
                        rs.getInt("id"),
                        rs.getString("time_start"),
                        rs.getString("time_end")
                ));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // [POST] 대실 예약하기 API
    public int createTime(PostTimeBookingReq postTimeBookingReq){
        String createTimeQuery= "insert into room_booking_time (" +
                "user_id, hotel_id, room_id,booking_type,payment_id, " +
                "booking_start_date,booking_end_date,booking_start_time, booking_end_time,room_final_price) VALUES(?,?,?,?,?,?,?,?,?,?)";
        Object[] createTimeParams = new Object[]{
                postTimeBookingReq.getUser_id(),
                postTimeBookingReq.getHotel_id(),
                postTimeBookingReq.getRoom_id(),
                postTimeBookingReq.getBooking_type(),
                postTimeBookingReq.getPayment_id(),
                postTimeBookingReq.getBooking_start_time(),
                postTimeBookingReq.getBooking_end_date(),
                postTimeBookingReq.getBooking_start_time(),
                postTimeBookingReq.getBooking_end_time(),
                postTimeBookingReq.getRoom_final_price()

        };

        this.jdbcTemplate.update(createTimeQuery, createTimeParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    // [POST] 숙박 예약하기 API
    public int createSleep(PostSleepBookingReq postSleepBookingReq){
        String createSleepQuery="insert into room_booking_sleep (" +
                "user_id, hotel_id, room_id, booking_type,payment_id, " +
                "booking_start_date,booking_end_date,room_final_price) VALUES(?,?,?,?,?,?,?,?)";
        Object[] createSleepParams = new Object[]{
                postSleepBookingReq.getUser_id(),
                postSleepBookingReq.getHotel_id(),
                postSleepBookingReq.getRoom_id(),
                postSleepBookingReq.getBooking_type(),
                postSleepBookingReq.getPayment_id(),
                postSleepBookingReq.getBooking_start_date(),
                postSleepBookingReq.getBooking_end_date(),
                postSleepBookingReq.getRoom_final_price()

        };
        this.jdbcTemplate.update(createSleepQuery, createSleepParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);

    }

    // [POST] 유저 예약 내역 추가하기 API
    public void createUserBooking(PostUserBookingReq postUserBookingReq){
        String createUserBookingQuery=
                "insert into user_booking (user_id, hotel_id,room_booking_id,booking_type) " +
                "VALUES(?,?,?,?)";
        Object[] createUserBookingParams = new Object[]{
                postUserBookingReq.getUser_id(),
                postUserBookingReq.getHotel_id(),
                postUserBookingReq.getRoom_booking_id(),
                postUserBookingReq.getBooking_type()
        };

        this.jdbcTemplate.update(createUserBookingQuery, createUserBookingParams);
        String lastInserIdQuery = "select last_insert_id()";
        this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    // [PATCH] 숙소 예약 취소 - status 값 변경
    public int modifyStatus(PatchUserBookingReq patchUserBookingReq){
        String modifyStatusQuery = "update user_booking set status = ? where room_booking_id = ? ";
        Object[] modifyStatusParams = new Object[]{patchUserBookingReq.getStatus(), patchUserBookingReq.getRoom_booking_id()};
        return this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams);
    }

    public int modifySleepStatus(PatchSleepBookingReq patchSleepBookingReq){
        String modifyStatusQuery = "update room_booking_sleep set status = ? where id = ? ";
        Object[] modifyStatusParams = new Object[]{patchSleepBookingReq.getStatus(), patchSleepBookingReq.getId()};
        return this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams);
    }

    public int modifyTimeStatus(PatchTimeBookingReq patchTimeBookingReq){
        String modifyStatusQuery = "update room_booking_time set status = ? where id = ? ";
        Object[] modifyStatusParams = new Object[]{patchTimeBookingReq.getStatus(), patchTimeBookingReq.getId()};
        return this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams);
    }

}
