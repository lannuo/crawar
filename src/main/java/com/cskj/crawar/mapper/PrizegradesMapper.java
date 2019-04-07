package com.cskj.crawar.mapper;

import com.cskj.crawar.entity.Prizegrades;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PrizegradesMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into prizegrades(type,typemoney,typenum,code) values(#{prizegrades.type},#{prizegrades.typemoney}," +
            "#{prizegrades.typenum},#{prizegrades.code})")
    int add(@Param("prizegrades") Prizegrades prizegrades);
}
