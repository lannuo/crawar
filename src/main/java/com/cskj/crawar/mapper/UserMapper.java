package com.cskj.crawar.mapper;

import com.cskj.crawar.entity.ssq.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {

    @Insert("insert into user(name,age) values(#{name},#{age})")
    int addUser(@Param("name") String name, @Param("age") String age);

    @Select("select * from user where id =#{id}")
    User findById(@Param("id") String id);

    @Update("update user set name=#{name} where id=#{id}")
    void updataById(@Param("id") String id, @Param("name") String name);

    @Delete("delete from user where id=#{id}")
    void deleteById(@Param("id") String id);

    @Select("select * from user")
    List<User> findAllUser();
}