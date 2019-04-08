package com.cskj.crawar.controller;

import com.cskj.crawar.entity.HistoryResult;
import com.cskj.crawar.entity.common.OperInfo;
import com.cskj.crawar.processor.FivePageProcesser;
import com.cskj.crawar.service.HistoryService;
import com.cskj.crawar.util.json.JsonUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

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

    @RequestMapping("history")
    @ResponseBody
    public OperInfo history(int pageNo) {
        try {
            addHistory(pageNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new OperInfo();
    }

    @RequestMapping("batchHistory")
    @ResponseBody
    public OperInfo batchHistory(int pageNo) {
        try {
            batchAddHistory(pageNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new OperInfo();
    }

    private void addHistory(int pageNo) throws Exception {
        HttpResponse<String> result = Unirest.get("http://www.cwl.gov.cn/cwl_admin/kjxx/findDrawNotice?name=ssq&issueCount=&issueStart=&issueEnd=&dayStart=2012-04-01&dayEnd=2019-04-07&pageNo=" + pageNo)
                .header("Referer", "http://www.cwl.gov.cn/kjxx/ssq/")
                .asString();
        if (result.getStatus() == 200) {
            HistoryResult history = JsonUtil.jsonStr2Entity(result.getBody(), HistoryResult.class);
            if (history != null) {
                history.getResult().forEach(o -> historyService.add(o));
            }
        }
    }

    private void batchAddHistory(int pageNo) throws Exception {
        HttpResponse<String> result = Unirest.get("http://www.cwl.gov.cn/cwl_admin/kjxx/findDrawNotice?name=ssq&issueCount=&issueStart=&issueEnd=&dayStart=2012-04-01&dayEnd=2019-04-07&pageNo=" + pageNo)
                .header("Referer", "http://www.cwl.gov.cn/kjxx/ssq/")
                .asString();
        if (result.getStatus() == 200) {
            HistoryResult history = JsonUtil.jsonStr2Entity(result.getBody(), HistoryResult.class);
            if (history != null) {
                historyService.batchAdd(history.getResult());
            }
        }
    }
}