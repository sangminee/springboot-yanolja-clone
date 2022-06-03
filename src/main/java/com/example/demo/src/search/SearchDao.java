package com.example.demo.src.search;

import com.example.demo.src.booking.model.Patch.PatchUserBookingReq;
import com.example.demo.src.search.model.Get.GetSearchListRes;
import com.example.demo.src.search.model.Get.GetSearchRes;
import com.example.demo.src.search.model.Get.GetSearchWeekendRes;
import com.example.demo.src.search.model.Patch.PatchAllSearchReq;
import com.example.demo.src.search.model.Patch.PatchSearchReq;
import com.example.demo.src.search.model.Post.PostSearchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SearchDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 평일
    public List<GetSearchRes> getArea(String area){
        String getSearchQuery = "select `main_area`.area_name, `sub_area`.sub_area_name,\n" +
                "        `hotel`.hotel_name,`hotel`.notice,`hotel`.event,\n" +
                "        `hotel_img`.img_url,\n" +
                "        `room_price`.weekday_time_price, `room_price`.weekday_sleep_price\n" +
                "FROM `main_area`\n" +
                "join `sub_area`\n" +
                "    on `main_area`.id = `sub_area`.main_area_id\n" +
                "Join `hotel`\n" +
                "on `sub_area`.id = `hotel`.sub_area_id\n" +
                "join `hotel_img`\n" +
                "on `hotel`.hotel_img_id = `hotel_img`.id\n" +
                "join `room_price`\n" +
                "on `hotel`.id = `room_price`.hotel_id\n" +
                "where `main_area`.area_name =? ";
        String getSearchParms = area;
        return this.jdbcTemplate.query(getSearchQuery,
                (rs, rowNum) -> new GetSearchRes(
                        rs.getString("area_name"),
                        rs.getString("sub_area_name"),
                        rs.getString("hotel_name"),
                        rs.getString("notice"),
                        rs.getString("event"),
                        rs.getString("img_url"),
                        rs.getString("weekday_time_price"),
                        rs.getString("weekday_sleep_price")
                ),getSearchParms);
    }

    // 주말
    public List<GetSearchWeekendRes> getAreaWeekend(String area){
        String getSearchQuery = "select `main_area`.area_name, `sub_area`.sub_area_name,\n" +
                "        `hotel`.hotel_name,`hotel`.notice,`hotel`.event,\n" +
                "        `hotel_img`.img_url,\n" +
                "        `room_price`.weekend_time_price, `room_price`.weekend_sleep_price\n" +
                "FROM `main_area`\n" +
                "join `sub_area`\n" +
                "    on `main_area`.id = `sub_area`.main_area_id\n" +
                "Join `hotel`\n" +
                "on `sub_area`.id = `hotel`.sub_area_id\n" +
                "join `hotel_img`\n" +
                "on `hotel`.hotel_img_id = `hotel_img`.id\n" +
                "join `room_price`\n" +
                "on `hotel`.id = `room_price`.hotel_id\n" +
                "where `main_area`.area_name =? ";
        String getSearchParms = area;
        return this.jdbcTemplate.query(getSearchQuery,
                (rs, rowNum) -> new GetSearchWeekendRes(
                        rs.getString("area_name"),
                        rs.getString("sub_area_name"),
                        rs.getString("hotel_name"),
                        rs.getString("notice"),
                        rs.getString("event"),
                        rs.getString("img_url"),
                        rs.getString("weekend_time_price"),
                        rs.getString("weekend_sleep_price")
                ),getSearchParms);
    }

    public void createSearch(PostSearchReq postSearchReq){
        String createSearchQuery= "insert into search (user_id, search) VALUES(?,?)";
        Object[] createSearchParams = new Object[]{
                postSearchReq.getUser_id(),
                postSearchReq.getSearch()
        };
        this.jdbcTemplate.update(createSearchQuery, createSearchParams);
        String lastInserIdQuery = "select last_insert_id()";
        this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    // [GET] /search/list&userId=3    최근 검색 기록 조회 API
    public List<GetSearchListRes> getSearchList(int user_id){
        String getSearchQuery ="select * from search where user_id = ? AND status in('1'); ";
        int getSearchUser_idParms = user_id;
        return this.jdbcTemplate.query(getSearchQuery,
                (rs, rowNum) -> new GetSearchListRes(
                        rs.getInt("id"),
                        rs.getString("search"),
                        rs.getString("status")),getSearchUser_idParms);
    }

    // 38. [PATCH] /search?searchId=1  최근 검색 기록 한 개 삭제 API
    public int modifyStatus(PatchSearchReq patchSearchReq){
        String modifyStatusQuery = "update search set status = ? where id = ? ";
        Object[] modifyStatusParams = new Object[]{patchSearchReq.getStatus(), patchSearchReq.getId()};
        System.out.println(this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams));
        return this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams);   // 쿼리 실행 결과로 변경된 행의 개수를 리턴
    }

    // 39. [PATCH] /search/all?userId=   최근 검색 기록 전체 삭제 API
    public int modifyAllStatus(PatchAllSearchReq patchAllSearchReq){
        String modifyStatusQuery = "update search set status = ? where user_id = ? ";
        Object[] modifyStatusParams = new Object[]{patchAllSearchReq.getStatus(), patchAllSearchReq.getUser_id()};
        System.out.println(this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams));
        return this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams);   // 쿼리 실행 결과로 변경된 행의 개수를 리턴
    }
}
