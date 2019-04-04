package com.cskj.crawar.processor;

import com.cskj.crawar.entity.ssq.Task;
import com.cskj.crawar.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

@Component
public class FivePageProcesser implements PageProcessor {

    private Site site = new Site().setRetryTimes(3).setSleepTime(100);

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public void process(Page page) {
        //http://kaijiang.500.com/shtml/ssq/19037.shtml
        page.addTargetRequest(page.getHtml().links().regex("http://kaijiang\\.500\\.com/ssq.shtml").toString());
        List<String> urls = page.getHtml().xpath("//div[@class='iSelectList']/a/@href").all();
        System.out.println("size" + urls.size());
        if (urls != null && urls.size() > 0) {
            urls.forEach(o -> {
                System.out.println("currentURL" + o);
                String str = o.substring(o.length() - 11, o.length() - 6);
                System.out.println("str" + str);
                Long code = Long.valueOf(str);
                taskMapper.add(new Task(code, o, 0));
            });
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws Exception {
        Thread.sleep(10000);
        Spider spider = Spider.create(new FivePageProcesser()).addUrl("http://kaijiang.500.com/ssq.shtml");

        SpiderMonitor.instance().register(spider);
        spider.start();
    }
}
