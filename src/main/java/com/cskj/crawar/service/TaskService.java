package com.cskj.crawar.service;

import com.cskj.crawar.entity.ssq.Task;
import com.cskj.crawar.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;

    public Task findById(String id) {
        return taskMapper.findById(id);
    }

    public int add(Task task) {
        return taskMapper.add(task);
    }

    public void deleteById(String id) {
        taskMapper.deleteById(id);
    }

    public List<Task> findAll() {
        return taskMapper.findAll();
    }
}
