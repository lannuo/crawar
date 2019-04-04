package com.cskj.crawar.controller;

import com.cskj.crawar.entity.common.OperInfo;
import com.cskj.crawar.processor.FivePageProcesser;
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

    @RequestMapping("start")
    @ResponseBody
    public OperInfo start() {
        Spider spider = Spider.create(fivePageProcesser).addUrl("http://kaijiang.500.com/ssq.shtml");
        spider.start();
        return new OperInfo();
    }
}
