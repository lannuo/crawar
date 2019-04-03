package com.cskj.crawar.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class OschinaBlogPageProcesser implements PageProcessor {
    private Site site=new Site().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("http://my\\.oschina\\.net/flashsword/blog/\\d+").all());
        page.putField("title", page.getHtml().xpath("//div[@class='blog-item']/div[@class='content']/a/text()").toString());
        page.putField("content", page.getHtml().$(".description .line-clamp").toString());
        page.putField("tags",page.getHtml().xpath("//div[@class='item']/a[@class='catalog-name-link']/text()").toString());

    }

    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) throws Exception{
        Thread.sleep(10000);
        Spider spider = Spider.create(new OschinaBlogPageProcesser()).addUrl("http://my.oschina.net/flashsword/blog")
                .addPipeline(new ConsolePipeline());

        SpiderMonitor.instance().register(spider);
        spider.start();
    }
}
