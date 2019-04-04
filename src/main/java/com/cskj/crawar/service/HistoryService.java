package com.cskj.crawar.service;

import com.cskj.crawar.entity.ssq.History;
import com.cskj.crawar.mapper.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryMapper historyMapper;

    public History findById(String id) {
        return historyMapper.findById(id);
    }

    public int add(History history) {
        return historyMapper.add(history);
    }

    public void updataById(String id, String name) {
        historyMapper.updataById(id, name);
    }

    public void deleteById(String id) {
        historyMapper.deleteById(id);
    }

    public List<History> findAll() {
        return historyMapper.findAll();
    }
}
