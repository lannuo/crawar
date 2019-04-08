package com.cskj.crawar.mapper;

import com.cskj.crawar.entity.ssq.Task;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TaskMapper {
    @Insert("insert into task(code,url,status) values(#{task.code},#{task.url},#{task.status})")
    int add(@Param("task") Task task);

    @Select("select * from task where id =#{id}")
    Task findById(@Param("id") String id);

    @Delete("delete from task where id=#{id}")
    void deleteById(@Param("id") String id);

    @Select("select * from task")
    List<Task> findAll();
}
