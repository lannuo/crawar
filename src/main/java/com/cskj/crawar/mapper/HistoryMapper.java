package com.cskj.crawar.mapper;

import com.cskj.crawar.entity.ssq.History;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HistoryMapper {
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into history(code,red,blue,poolmoney,salemoney,date,week) values(#{history.code},#{history.red}," +
            "#{history.blue},#{history.poolmoney},#{history.salemoney},#{history.date},#{history.week})")
    int add(@Param("history") History history);

    @Select("select * from history where id =#{id}")
    History findById(@Param("id") String id);

    @Update("update history set name=#{name} where id=#{id}")
    void updataById(@Param("id") String id, @Param("name") String name);

    @Delete("delete from history where id=#{id}")
    void deleteById(@Param("id") String id);

    @Select("select * from history")
    List<History> findAll();
}
