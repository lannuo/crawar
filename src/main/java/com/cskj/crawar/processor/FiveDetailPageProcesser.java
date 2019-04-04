package com.cskj.crawar.processor;

import com.cskj.crawar.entity.ssq.History;
import com.cskj.crawar.mapper.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FiveDetailPageProcesser implements PageProcessor {
    private Site site = new Site().setRetryTimes(3).setSleepTime(100);

    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public void process(Page page) {
        //http://kaijiang.500.com/shtml/ssq/19037.shtml
        page.addTargetRequest(page.getHtml().links().regex("http://kaijiang\\.500\\.com/shtml/ssq\\d+").toString());
        List<String> moneys = page.getHtml().xpath("//span[@class='cfont1 ']/text()").all();
        System.out.println("size" + moneys.size());
        moneys.forEach(o -> System.out.println("money" + o));
        String red = page.getHtml().xpath("//table[@class='kj_tablelist02']//table//tr[2]/td[2]/text()").toString();
        System.out.println("red" + red);

        String blue = page.getHtml().xpath("//li[@class='ball_blue']/text()").toString();
        System.out.println("blue" + blue);

        List<String> num = page.getHtml().xpath("//table[@class='kj_tablelist02'][2]//tr/td[2]/text()").all();
        num.remove(num.size() - 1);
        num.remove(0);
        num.forEach(o -> System.out.println("num" + o));

        List<String> money = page.getHtml().xpath("//table[@class='kj_tablelist02'][2]//tr/td[3]/text()").all();
        money.remove(0);
        money.forEach(o -> System.out.println("money" + o));
        History history=new History();

        //td_title01

        String date = page.getHtml().xpath("//td[@class='td_title01']/span[@class='span_right']/text()").toString();
        System.out.println(date);
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);
        System.out.println( m.replaceAll("").trim());

    }

    @Override
    public Site getSite() {
        return site;
    }

    private String getNum(String str){
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    public static void main(String[] args) throws Exception {
        Thread.sleep(10000);
        Spider spider = Spider.create(new FiveDetailPageProcesser()).addUrl("http://kaijiang.500.com/shtml/ssq/19037.shtml");

        SpiderMonitor.instance().register(spider);
        spider.start();
    }
}
