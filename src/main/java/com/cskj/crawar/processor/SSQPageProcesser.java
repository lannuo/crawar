package com.cskj.crawar.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class SSQPageProcesser implements PageProcessor {

    private Site site=new Site().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
