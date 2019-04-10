package com.cskj.crawar.processor;

import com.cskj.crawar.exception.AppException;
import com.cskj.crawar.service.HistoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class SequencePageProcesser implements PageProcessor {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private Site site = new Site().setRetryTimes(3).setSleepTime(100);

    @Value("${crawl.five.regex}")
    private String fiveRegex;

    @Autowired
    private HistoryService historyService;

    @Override
    public void process(Page page) {

        log.info("start crawl 500.com process");
        page.addTargetRequest(page.getHtml().links().regex(fiveRegex).toString());

        //获取红色球顺序
        String redSequenceStr = analysisString(page, "//table[@class='kj_tablelist02']//table//tr[2]/td[2]/text()");
        String redSequence = analysisRedSequence(redSequenceStr);
        log.info("redSequence {}", redSequence);

        //获取期号
        String codeStr = "20" + analysisString(page, "//a[@id='change_date']/text()");
        log.info("codeStr" + "20" + codeStr);
        historyService.update(codeStr, redSequence);

    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 解析红色球的出球顺序规则
     *
     * @param str
     * @return
     */
    private String analysisRedSequence(String str) {
        return StringUtils.replace(StringUtils.trim(str), " ", ",");
    }

    private String analysisString(Page page, String xpath) {
        if (StringUtils.isBlank(xpath)) {
            throw new AppException("xpath not be empty! ");
        }
        String result = page.getHtml().xpath(xpath).toString();
        if (StringUtils.isBlank(result)) {
            throw new AppException(xpath + " anlysis result is empty! ");
        }
        return result;
    }
}
