package com.cskj.crawar.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

@JobHandler(value = "blockAnalysisJob")
@Component
public class TaskJob extends IJobHandler {
    @Override
    public ReturnT<String> execute(String s) throws Exception {
        return null;
    }
}
