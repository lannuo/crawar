package com.cskj.crawar.processor;

import com.cskj.crawar.entity.ssq.History;
import com.cskj.crawar.entity.ssq.Prize;
import com.cskj.crawar.exception.AppException;
import com.cskj.crawar.service.HistoryService;
import com.cskj.crawar.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FiveDetailPageProcesser implements PageProcessor {
    private Site site = new Site().setRetryTimes(3).setSleepTime(100);

    private History history;

    @Autowired
    private HistoryService historyService;

    @Override
    public void process(Page page) {
        //http://kaijiang.500.com/shtml/ssq/19037.shtml
        page.addTargetRequest(page.getHtml().links().regex("http://kaijiang\\.500\\.com/shtml/ssq\\d+").toString());

        //获取奖金池金额和销售金额
        List<String> poolMoneyArr = analysisList(page, "//span[@class='cfont1 ']/text()");
        poolMoneyArr.forEach(o -> System.out.println("poolMoneyArr" + o));

        //获取红色球顺序
        String redSequenceStr = analysisString(page, "//table[@class='kj_tablelist02']//table//tr[2]/td[2]/text()");
        String redSequence = analysisRedSequence(redSequenceStr);
        System.out.println("redSequence" + redSequence);

        //获取红色球
        List<String> redArr = analysisList(page, "//li[@class='ball_red']/text()");
        String red = analysisRed(redArr);
        System.out.println("red" + red);

        //获取蓝色球
        String blue = analysisString(page, "//li[@class='ball_blue']/text()");
        System.out.println("blue" + blue);

        //获取中奖注数
        List<String> prizeArr = analysisList(page, "//table[@class='kj_tablelist02'][2]//tr/td[2]/text()");
        anlysisPrize(prizeArr);
        prizeArr.forEach(o -> System.out.println("num" + o));

        //获取奖金数
        List<String> prizeMoneyArr = analysisList(page, "//table[@class='kj_tablelist02'][2]//tr/td[3]/text()");
        anlysisPrizeMoney(prizeMoneyArr);
        prizeMoneyArr.forEach(o -> System.out.println("money" + o));

        //获取开奖日期和最后兑奖日期
        String dateStr = analysisString(page, "//td[@class='td_title01']/span[@class='span_right']/text()");
        String[] dateArry = anlysisDate(dateStr);
        Date lotteryDate = formatDateString(dateArry[0]);
        Date withdrawDate = formatDateString(dateArry[1]);
        System.out.println(lotteryDate);
        System.out.println(withdrawDate);

        //获取期号
        String codeStr = "20" + analysisString(page, "//a[@id='change_date']/text()");
        System.out.println("codeStr" + "20" + codeStr);
        history=null;
        history = new History(codeStr, red, redSequence, blue, poolMoneyArr.get(0), poolMoneyArr.get(1), lotteryDate, withdrawDate);
        List<Prize> prizeList = new ArrayList<>();
        for (int i = 0; i < prizeArr.size(); i++) {
            prizeList.add(new Prize(i, prizeArr.get(i), prizeMoneyArr.get(i)));
        }
        history.setPrizes(prizeList);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void saveData(){
        historyService.add(history);
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

    /**
     * 解析红色球规则
     *
     * @param list
     * @return
     */
    private String analysisRed(List<String> list) {
        return StringUtils.join(list.toArray(), ",");
    }

    /**
     * 解析排名规则
     *
     * @param list
     */
    private void anlysisPrize(List<String> list) {
        list.remove(list.size() - 1);
        list.remove(0);
    }

    /**
     * 解析排名金额
     *
     * @param list
     */
    private void anlysisPrizeMoney(List<String> list) {
        list.remove(0);
    }

    /**
     * 解析日期
     *
     * @param str
     * @return
     */
    private String[] anlysisDate(String str) {
        if (StringUtils.isNotBlank(str)) {
            String f = StringUtils.replace(str, "开奖日期：", "");
            String g = StringUtils.replace(f, "兑奖截止日期：", "");
            return StringUtils.split(g, " ");
        }
        return null;
    }

    private Date formatDateString(String str) {
        return DateUtil.parseDate(str, "yyyy年MM月dd日");
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

    private List<String> analysisList(Page page, String xpath) {
        if (StringUtils.isBlank(xpath)) {
            throw new AppException("xpath not be empty! ");
        }
        List<String> list = page.getHtml().xpath(xpath).all();
        if (list == null || list.size() == 0) {
            throw new AppException(xpath + " anlysis list is empty! ");
        }
        return list;
    }


    private String getNum(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    public static void main(String[] args) throws Exception {
//        Spider spider = Spider.create(new FiveDetailPageProcesser()).addUrl("http://kaijiang.500.com/shtml/ssq/19037.shtml");
//
//        SpiderMonitor.instance().register(spider);
//        spider.start();

    }
}
