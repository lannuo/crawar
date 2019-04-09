package com.cskj.crawar.controller;

import com.cskj.crawar.entity.common.OperInfo;
import com.cskj.crawar.entity.ssq.History;
import com.cskj.crawar.exception.AppException;
import com.cskj.crawar.service.HistoryService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 历史纪录
 */
@Controller
@RequestMapping("history")
public class HistoryController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HistoryService historyService;

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public OperInfo findList(int pageNo, int pageSize) {
        log.info("pageNo {}, pageSize {} ", pageNo, pageSize);
        PageHelper.startPage(pageNo, pageSize);
        List<History> all = historyService.findAll();
        return new OperInfo("list", all);
    }

    /**
     * 查询最近100期以内的数据
     *
     * @param stage
     * @return
     */
    @RequestMapping("nearStage")
    @ResponseBody
    public OperInfo nearStage(int stage) {
        if (stage <= 0 || stage > 100) {
            throw new AppException("Stage cannot be greater than 100 !");
        }
        PageHelper.startPage(1, stage);
        List<History> all = historyService.findAll();
        return new OperInfo("list", all);
    }

    /**
     * 根据code查找
     *
     * @param code
     * @return
     */
    @RequestMapping("findByCode")
    @ResponseBody
    public OperInfo findByCode(String code) {
        if (StringUtils.isBlank(code)) {
            throw new AppException("code cannot be null or empty! ");
        }
        History history = historyService.findByCode(code);
        return new OperInfo("vo", history);
    }

    /**
     * 查找最新
     *
     * @return
     */
    @RequestMapping("findLast")
    @ResponseBody
    public OperInfo findLast() {
        History history = historyService.findLast();
        return new OperInfo("vo", history);
    }

}
