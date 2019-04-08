package com.cskj.crawar.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class SSQPageProcesser implements PageProcessor {

    private Site site=new Site().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.addTargetRequest(page.getHtml().links().regex("http://www\\.cwl\\.gov\\.cn\\d+").toString());
        for (int i = 1; i <= 6; i++) {
            String red = page.getHtml().xpath("//span[@class='qiuH"+i+"']/text()").toString();
            System.out.println("red" + red);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws Exception {
        Spider spider = Spider.create(new SSQPageProcesser()).addUrl("http://www.cwl.gov.cn/c/2019-04-07/451167.shtml");
        SpiderMonitor.instance().register(spider);
        spider.start();
    }
}
