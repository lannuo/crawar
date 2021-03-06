package com.cskj.crawar.mapper;

import com.cskj.crawar.entity.ssq.Prize;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PrizeMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into prize(code,type,num,money) values(#{prize.code},#{prize.type}," +
            "#{prize.num},#{prize.money})")
    int add(@Param("prize") Prize prize);

    @Insert("<script><foreach collection='txList' item='tx' index='index' separator=';'>" +
            "insert into prize(code,type,num,money) values " +
            "(#{tx.code},#{tx.type},#{tx.num},#{tx.money})" +
            "</foreach></script>")
    void batchAdd(@Param("txList") List<Prize> txList);

    @Select("select * from prize where code =#{code}")
    List<Prize> findByCode(@Param("code") String code);
}
