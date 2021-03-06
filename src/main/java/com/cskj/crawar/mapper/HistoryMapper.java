package com.cskj.crawar.mapper;

import com.cskj.crawar.entity.ssq.History;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface HistoryMapper {
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into history(code,red,redsq,blue,poolmoney,salemoney,lottery_date,withdraw_date,week,content) values(#{history.code},#{history.red},#{history.redsq}," +
            "#{history.blue},#{history.poolmoney},#{history.salemoney},#{history.lotteryDate},#{history.withdrawDate},#{history.week},#{history.content})")
    int add(@Param("history") History history);

    @Insert("<script><foreach collection='txList' item='tx' index='index' separator=';'>" +
            "insert into history(code,red,redsq,blue,poolmoney,salemoney,lottery_date,withdraw_date,week,content) values " +
            "(#{tx.code},#{tx.red},#{history.redsq}, #{tx.blue},#{tx.poolmoney},#{tx.salemoney},#{tx.lotteryDate},#{tx.withdrawDate},#{tx.week},#{tx.content})" +
            "</foreach></script>")
    void batchAdd(@Param("txList") List<History> txList);

    @Select("select * from history where id =#{id}")
    History findById(@Param("id") String id);

    @Select("select * from history order by lottery_date desc limit 0,1")
    History findLast();

    @Delete("delete from history where id=#{id}")
    void deleteById(@Param("id") String id);

    @Select("select * from history order by lottery_date desc")
    List<History> findAll();

    @Select("select * from history where code=#{code}")
    History findByCode(@Param("code") String code);

    @Select("select code from history where redsq is null order by lottery_date limit 0,10")
    List<String> findEmptySq();

    @Update("update history set redsq=#{redsq} where code=#{code}")
    void update(@Param("code") String code,@Param("redsq") String redsq);
}
