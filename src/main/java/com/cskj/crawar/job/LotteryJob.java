package com.cskj.crawar.job;

import com.cskj.crawar.entity.HistoryResult;
import com.cskj.crawar.entity.opencai.OpencaiHistory;
import com.cskj.crawar.entity.opencai.OpencaiResult;
import com.cskj.crawar.entity.ssq.History;
import com.cskj.crawar.service.HistoryService;
import com.cskj.crawar.util.json.JsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

/**
 * 获取最新开奖信息定时任务
 */
@JobHandler(value = "lotteryJob")
@Component
public class LotteryJob extends IJobHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private HistoryService historyService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        History history = historyService.findLast();
        if (history != null) {
            int code = Integer.valueOf(history.getCode()) + 1;
            getFromApi(code);
            getFromBuy(history);
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 从彩票官网根据api获取最新开奖信息
     *
     * @param code
     */
    @Async
    public void getFromApi(int code) {
        log.info("start execute getFromApi");
        try {
            HttpResponse<String> result = Unirest.get("http://www.cwl.gov.cn/cwl_admin/kjxx/findKjxx/forIssue?name=ssq&code=" + code)
                    .header("Referer", "http://www.cwl.gov.cn/kjxx/ssq/")
                    .asString();
            if (result.getStatus() == 200) {
                HistoryResult history = JsonUtil.jsonStr2Entity(result.getBody(), HistoryResult.class);
                if (history != null) {
                    XxlJobLogger.log("get new history from api" + history.getResult().size());
                    history.getResult().forEach(o -> historyService.add(o));
                    XxlJobLogger.log("get new lasthisory from api"+ Instant.now());
                }
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从购买的api中获取最新信息
     *
     * @param history
     */
    @Async
    public void getFromBuy(History history) {
        log.info("start execute getFromBuy");
        //http://101.37.189.39:7442/newly.do?token=40f0bc9ff8657b9f&code=ssq&format=json

        try {
            HttpResponse<String> result = Unirest.get("http://101.37.189.39:7442/newly.do?token=40f0bc9ff8657b9f&code=ssq&format=json")
                    .asString();
            if (result.getStatus() == 200) {
                OpencaiResult opencaiResult = JsonUtil.jsonStr2Entity(result.getBody(), OpencaiResult.class);
                if (opencaiResult != null) {
                    OpencaiHistory opencaiHistory = opencaiResult.getData().get(0);
                    XxlJobLogger.log("get new history from buy " + opencaiHistory.getOpencode());
                    if (Integer.valueOf(opencaiHistory.getExpect()) > Integer.valueOf(history.getCode())) {

                        String[] split = StringUtils.split(opencaiHistory.getOpencode(), "+");
                        History h = new History();
                        h.setRed(split[0]);
                        h.setBlue(split[1]);
                        historyService.add(h);
                        XxlJobLogger.log("get new lasthisory from buy");
                    }

                }
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
