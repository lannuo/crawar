package com.cskj.crawar.mapper;

import com.cskj.crawar.entity.ssq.History;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface HistoryMapper {
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into history(code,red,blue,poolmoney,salemoney,lottery_date,withdraw_date,week,content) values(#{history.code},#{history.red}," +
            "#{history.blue},#{history.poolmoney},#{history.salemoney},#{history.lotteryDate},#{history.withdrawDate},#{history.week},#{history.content})")
    int add(@Param("history") History history);

    @Select("select * from history where id =#{id}")
    History findById(@Param("id") String id);

    @Select("select * from history order by lottery_date desc limit 0,1")
    History findLast();

    @Delete("delete from history where id=#{id}")
    void deleteById(@Param("id") String id);

    @Select("select * from history")
    List<History> findAll();
}
