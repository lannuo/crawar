package com.cskj.crawar.scheduler;

import com.cskj.crawar.processor.FivePageProcesser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
public class TaskSheduler {
    @Autowired
    private FivePageProcesser fivePageProcesser;

//    @Scheduled(cron ="0/20 * * * * ? ")
//    public void startTask() {
//        System.out.println("定时器执行");
//        Spider spider = Spider.create(fivePageProcesser).addUrl("http://kaijiang.500.com/ssq.shtml");
//        spider.start();
//    }
}
