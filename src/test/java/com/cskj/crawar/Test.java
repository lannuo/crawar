package com.cskj.crawar;

import com.cskj.crawar.entity.DrawHistory;
import com.cskj.crawar.entity.HistoryResult;
import com.cskj.crawar.util.json.JsonUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class Test {

    @org.junit.Test
    public void test()throws Exception{
        String url="http://kaijiang.500.com/shtml/ssq/03024.shtml";
        int index=url.indexOf("q");
        String str=url.substring(index+2,index+7);
        System.out.println(str);

        HttpResponse<String> result = Unirest.get("http://www.cwl.gov.cn/cwl_admin/kjxx/findDrawNotice?name=ssq&issueCount=5")
                .header("Referer","http://www.cwl.gov.cn/kjxx/ssq/")
                .asString();
        System.out.println(result);
        if (result.getStatus() == 200) {
            HistoryResult history = JsonUtil.jsonStr2Entity(result.getBody(), HistoryResult.class);
            if (history != null) {
                System.out.println(history.getResult().get(0).getRed());
            }
        }
    }
}
