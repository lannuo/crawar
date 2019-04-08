package com.cskj.crawar.service;

import com.cskj.crawar.entity.ssq.History;
import com.cskj.crawar.entity.ssq.Prize;
import com.cskj.crawar.mapper.HistoryMapper;
import com.cskj.crawar.mapper.PrizeMapper;
import com.cskj.crawar.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private PrizeMapper prizeMapper;

    public History findById(String id) {
        return historyMapper.findById(id);
    }

    /**
     * 单个保存
     *
     * @param history
     */
    @Transactional
    public void add(History history) {
        String date = history.getDate();
        Date formatDate = formatDateString(date);
        history.setLotteryDate(formatDate);
        history.setWithdrawDate(DateUtil.addDay(formatDate, 60));
        historyMapper.add(history);

        List<Prize> prizegrades = history.getPrizes();
        if (prizegrades != null && prizegrades.size() > 0) {
            prizegrades.forEach(o -> {
                o.setCode(history.getCode());
                if (StringUtils.isNotBlank(o.getNum())) {
                    prizeMapper.add(o);
                }
            });
        }
    }

    /**
     * 批量保存
     *
     * @param histories
     */
    @Transactional
    public void batchAdd(List<History> histories) {
        if (histories != null && histories.size() > 0) {
            List<Prize> prizeList = new ArrayList<>();
            histories.forEach(history -> {
                String date = history.getDate();
                Date formatDate = formatDateString(date);
                history.setLotteryDate(formatDate);
                history.setWithdrawDate(DateUtil.addDay(formatDate, 60));

                List<Prize> prizegrades = history.getPrizes();
                if (prizegrades != null && prizegrades.size() > 0) {
                    prizegrades.forEach(prize -> {
                        prize.setCode(history.getCode());
                        if (StringUtils.isNotBlank(prize.getNum())) {
                            prizeList.add(prize);
                        }
                    });
                }
            });
            historyMapper.batchAdd(histories);
            prizeMapper.batchAdd(prizeList);
        }
    }

    public void deleteById(String id) {
        historyMapper.deleteById(id);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<History> findAll() {
        List<History> all = historyMapper.findAll();
        if(all!=null && all.size()>0){
            all.forEach(o->{
                o.setPrizes(prizeMapper.findByCode(o.getCode()));
            });
        }
        return all;
    }

    /**
     * 查找最新
     *
     * @return
     */
    public History findLast() {
        History history = historyMapper.findLast();
        history.setPrizes(prizeMapper.findByCode(history.getCode()));
        return history;
    }

    /**
     * 根据code查找
     * @param code
     * @return
     */
    public History findByCode(String code){
        History history = historyMapper.findByCode(code);
        history.setPrizes(prizeMapper.findByCode(history.getCode()));
        return history;
    }

    private Date formatDateString(String date) {
        int index = date.indexOf("(");
        String str = date.substring(0, index);
        return DateUtil.parseDate(str, null);
    }

}
