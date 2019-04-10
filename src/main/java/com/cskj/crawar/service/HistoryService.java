package com.cskj.crawar.service;

import com.cskj.crawar.entity.ssq.History;
import com.cskj.crawar.entity.ssq.Prize;
import com.cskj.crawar.exception.AppException;
import com.cskj.crawar.mapper.HistoryMapper;
import com.cskj.crawar.mapper.PrizeMapper;
import com.cskj.crawar.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class HistoryService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private PrizeMapper prizeMapper;

    @Cacheable(value = "crawl", key = "'history_'+#id")
    public History findById(String id) {
        return historyMapper.findById(id);
    }

    /**
     * 单个保存
     *
     * @param history
     */
    @Transactional
    @CacheEvict(value = "crawl", key = "'history_last'")
    public void add(History history) {
        String date = history.getDate();
        if (StringUtils.isNotBlank(date)) {
            Date formatDate = formatDateString(date);
            history.setLotteryDate(formatDate);
            history.setWithdrawDate(DateUtil.addDay(formatDate, 60));
        }
        if (StringUtils.isBlank(history.getWeek()) && history.getLotteryDate() != null) {
            history.setWeek(DateUtil.getWeekday(history.getLotteryDate()));
        }
        try {
            historyMapper.add(history);
        } catch (DuplicateKeyException e) {
            String msg = history.getCode() + " data has aleardy exit !";
            log.error(msg);
            throw new AppException(msg);
        }

        List<Prize> prizegrades = history.getPrizes();
        if (prizegrades != null && prizegrades.size() > 0) {
            prizegrades.forEach(o -> {
                if (StringUtils.isBlank(o.getNum())) {
                    o.setNum("0");
                }
                o.setCode(history.getCode());
                prizeMapper.add(o);
            });
        }
    }

    /**
     * 批量保存
     *
     * @param histories
     */
    @Transactional
    @CacheEvict(value = "crawl", key = "'history_last'")
    public void batchAdd(List<History> histories) {
        if (histories != null && histories.size() > 0) {
            List<Prize> prizeList = new ArrayList<>();
            histories.forEach(history -> {
                String date = history.getDate();
                if (StringUtils.isNotBlank(date)) {
                    Date formatDate = formatDateString(date);
                    history.setLotteryDate(formatDate);
                    history.setWithdrawDate(DateUtil.addDay(formatDate, 60));
                }

                List<Prize> prizegrades = history.getPrizes();
                if (prizegrades != null && prizegrades.size() > 0) {
                    prizegrades.forEach(prize -> {
                        prize.setCode(history.getCode());
                        if (StringUtils.isBlank(prize.getNum())) {
                            prize.setNum("0");
                        }
                        prizeList.add(prize);
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
     * 修改redSq
     *
     * @param code
     * @param redsq
     */
    @CacheEvict(value = "crawl", key = "'history_all'")
    public void update(String code, String redsq) {
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(redsq)) {
            historyMapper.update(code, redsq);
        }
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Cacheable(value = "crawl", key = "'history_all'")
    public List<History> findAll() {
        List<History> all = historyMapper.findAll();
        if (all != null && all.size() > 0) {
            all.forEach(o -> {
                refactor(o);
            });
        }
        return all;
    }

    /**
     * 查找最新
     *
     * @return
     */
    @Cacheable(value = "crawl", key = "'history_last'")
    public History findLast() {
        History history = historyMapper.findLast();
        refactor(history);
        return history;
    }

    /**
     * 根据code查找
     *
     * @param code
     * @return
     */
    @Cacheable(value = "crawl", key = "'history_'+#code")
    public History findByCode(String code) {
        History history = historyMapper.findByCode(code);
        refactor(history);
        return history;
    }

    /**
     * 查找resSq为空的数据
     *
     * @return
     */
    public List<String> findEmptySq() {
        return historyMapper.findEmptySq();
    }

    /**
     * 重构结果
     *
     * @param history
     */
    private void refactor(History history) {
        history.setReds(Arrays.asList(StringUtils.split(history.getRed(), ",")));
        history.setPrizes(prizeMapper.findByCode(history.getCode()));
    }

    /**
     * 处理日期
     *
     * @param date
     * @return
     */
    private Date formatDateString(String date) {
        int index = date.indexOf("(");
        if (index != -1) {
            String str = date.substring(0, index);
            return DateUtil.parseDate(str, null);
        }
        return DateUtil.parseDate(date, null);
    }

}
