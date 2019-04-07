package com.cskj.crawar.mapper;

import com.cskj.crawar.entity.DrawHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DrawHistoryMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into draw_history(blue,code,content,date,name,poolmoney,red,sales,week) values(#{drawHistory.blue},#{drawHistory.code}," +
            "#{drawHistory.content},#{drawHistory.date},#{drawHistory.name},#{drawHistory.poolmoney},#{drawHistory.red},#{drawHistory.sales},#{drawHistory.week})")
    int add(@Param("drawHistory") DrawHistory drawHistory);
}
