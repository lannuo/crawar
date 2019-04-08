package com.cskj.crawar.service;

import com.cskj.crawar.entity.ssq.History;
import com.cskj.crawar.entity.ssq.Prize;
import com.cskj.crawar.mapper.HistoryMapper;
import com.cskj.crawar.mapper.PrizeMapper;
import com.cskj.crawar.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class HistoryService {
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private PrizeMapper prizeMapper;

    public History findById(String id) {
        return historyMapper.findById(id);
    }

    public void add(History history) {
        String date=history.getDate();
        Date formatDate=formatDateString(date);
        history.setLotteryDate(formatDate);
        history.setWithdrawDate(DateUtil.addDay(formatDate,60));
        historyMapper.add(history);

        List<Prize> prizegrades=history.getPrizes();
        if(prizegrades!=null && prizegrades.size()>0){
            prizegrades.forEach(o->{
                o.setCode(history.getCode());
                if(StringUtils.isNotBlank(o.getNum())) {
                    prizeMapper.add(o);
                }
            });
        }
    }

    public void deleteById(String id) {
        historyMapper.deleteById(id);
    }

    public List<History> findAll() {
        return historyMapper.findAll();
    }

    public History findLast(){
        return historyMapper.findLast();
    }

    private static Date formatDateString(String date){
        int index=date.indexOf("(");
        String str=date.substring(0,index);
        return DateUtil.parseDate(str,null);
    }

}
