package com.cskj.crawar.controller;

import com.cskj.crawar.entity.common.OperInfo;
import com.cskj.crawar.processor.FiveDetailPageProcesser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

@Controller
@RequestMapping("crawl")
public class CrawlController {

    @Autowired
    private FiveDetailPageProcesser fiveDetailPageProcesser;

    /**
     * 根据页面url爬取500彩票网的双色球信息
     * @param url
     * @return
     */
    @RequestMapping("five")
    @ResponseBody
    public OperInfo five(String url){
        Spider spider = Spider.create(fiveDetailPageProcesser).addUrl(url);
        spider.start();
        fiveDetailPageProcesser.saveData();
        return new OperInfo();
    }
}
