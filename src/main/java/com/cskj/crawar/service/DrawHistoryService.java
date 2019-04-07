package com.cskj.crawar.service;

import com.cskj.crawar.entity.DrawHistory;
import com.cskj.crawar.entity.Prizegrades;
import com.cskj.crawar.mapper.DrawHistoryMapper;
import com.cskj.crawar.mapper.PrizegradesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrawHistoryService {

    @Autowired
    private DrawHistoryMapper drawHistoryMapper;

    @Autowired
    private PrizegradesMapper prizegradesMapper;

    public void save(DrawHistory drawHistory){
        drawHistoryMapper.add(drawHistory);
        List<Prizegrades> prizegrades=drawHistory.getPrizegrades();
        if(prizegrades!=null && prizegrades.size()>0){
            prizegrades.forEach(o->{
                o.setCode(drawHistory.getCode());
                prizegradesMapper.add(o);
            });
        }

    }

}
