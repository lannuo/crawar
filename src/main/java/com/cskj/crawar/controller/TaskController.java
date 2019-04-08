package com.cskj.crawar.controller;

import com.cskj.crawar.entity.HistoryResult;
import com.cskj.crawar.entity.common.OperInfo;
import com.cskj.crawar.exception.AppException;
import com.cskj.crawar.processor.FivePageProcesser;
import com.cskj.crawar.service.HistoryService;
import com.cskj.crawar.util.json.JsonUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

import java.io.IOException;

@Controller
@RequestMapping("task")
public class TaskController {

    @Autowired
    private FivePageProcesser fivePageProcesser;
    @Autowired
    private HistoryService historyService;

    @RequestMapping("start")
    @ResponseBody
    public OperInfo start() {
        Spider spider = Spider.create(fivePageProcesser).addUrl("http://kaijiang.500.com/ssq.shtml");
        spider.start();
        return new OperInfo();
    }

    /**
     * 添加历史
     *
     * @param pageNo
     * @return
     */
    @RequestMapping("history")
    @ResponseBody
    public OperInfo history(int pageNo) {
        try {
            addHistory(pageNo);
        } catch (DuplicateKeyException e) {
            throw new AppException(pageNo + " pageNo data has aleardy exit !");
        }
        return new OperInfo();
    }

    /**
     * 批量添加历史记录
     *
     * @param pageNo
     * @return
     */
    @RequestMapping("batchHistory")
    @ResponseBody
    public OperInfo batchHistory(int pageNo) {
        try {
            batchAddHistory(pageNo);
        } catch (DuplicateKeyException e) {
            throw new AppException(pageNo + " pageNo data has aleardy exit !");
        }
        return new OperInfo();
    }

    private void addHistory(int pageNo) {
        HttpResponse<String> result = null;
        try {
            result = Unirest.get("http://www.cwl.gov.cn/cwl_admin/kjxx/findDrawNotice?name=ssq&issueCount=&issueStart=&issueEnd=&dayStart=2012-04-01&dayEnd=2019-04-07&pageNo=" + pageNo)
                    .header("Referer", "http://www.cwl.gov.cn/kjxx/ssq/")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (result.getStatus() == 200) {
            HistoryResult history = null;
            try {
                history = JsonUtil.jsonStr2Entity(result.getBody(), HistoryResult.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (history != null) {
                history.getResult().forEach(o -> historyService.add(o));
            }
        }
    }

    private void batchAddHistory(int pageNo) {
        HttpResponse<String> result = null;
        try {
            result = Unirest.get("http://www.cwl.gov.cn/cwl_admin/kjxx/findDrawNotice?name=ssq&issueCount=&issueStart=&issueEnd=&dayStart=2012-04-01&dayEnd=2019-04-07&pageNo=" + pageNo)
                    .header("Referer", "http://www.cwl.gov.cn/kjxx/ssq/")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (result.getStatus() == 200) {
            HistoryResult history = null;
            try {
                history = JsonUtil.jsonStr2Entity(result.getBody(), HistoryResult.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (history != null) {
                historyService.batchAdd(history.getResult());
            }
        }
    }
}
