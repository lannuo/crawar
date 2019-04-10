package com.cskj.crawar.job;

import com.cskj.crawar.processor.SequencePageProcesser;
import com.cskj.crawar.service.HistoryService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.stream.Collectors;

@JobHandler(value = "sequenceJob")
@Component
public class SequenceJob extends IJobHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${crawl.five.url}")
    private String fiveMainUrl;

    @Autowired
    private SequencePageProcesser sequencePageProcesser;

    @Autowired
    private HistoryService historyService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        getSeqFromFivePage();
        return ReturnT.SUCCESS;
    }

    private void getSeqFromFivePage() {
        log.info("start execute getSeqFromFivePage");
        List<String> emptySq = historyService.findEmptySq();
        if (emptySq != null && emptySq.size() > 0) {
//            String urls = emptySq.stream().map(o -> fiveMainUrl + o.substring(2) + ".shtml").collect(Collectors.joining(","));
//            log.info("urls>>>>>>{}",urls);
            Spider spider = Spider.create(sequencePageProcesser);
            emptySq.stream().map(o -> fiveMainUrl + o.substring(2) + ".shtml").forEach(url->{
                spider.addUrl(url);
            });
            spider.start();
            log.info("end execute getSeqFromFivePage");
        }
    }
}
