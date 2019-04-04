package com.cskj.crawar;

import org.apache.commons.lang3.StringUtils;

public class Test {

    @org.junit.Test
    public void test(){
        String url="http://kaijiang.500.com/shtml/ssq/03024.shtml";
        int index=url.indexOf("q");
        String str=url.substring(index+2,index+7);
        System.out.println(str);
    }
}
