package com.cskj.crawar.job;

import com.cskj.crawar.entity.HistoryResult;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 获取最新开奖信息定时任务
 */
@JobHandler(value = "lotteryJob")
@Component
public class LotteryJob extends IJobHandler {

    @Autowired
    private HistoryService historyService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        History history=historyService.findLast();
        if(history!=null){
            int code=Integer.valueOf(history.getCode())+1;
            getFromApi(code);
        }
        return null;
    }

    /**
     * 从彩票官网根据api获取最新开奖信息
     * @param code
     */
    private void getFromApi(int code){
        try {
            HttpResponse<String> result = Unirest.get("http://www.cwl.gov.cn/cwl_admin/kjxx/findKjxx/forIssue?name=ssq&code="+code)
                    .header("Referer","http://www.cwl.gov.cn/kjxx/ssq/")
                    .asString();
            if (result.getStatus() == 200) {
                HistoryResult history = JsonUtil.jsonStr2Entity(result.getBody(), HistoryResult.class);
                if (history != null) {
                    history.getResult().forEach(o->historyService.add(o));
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
