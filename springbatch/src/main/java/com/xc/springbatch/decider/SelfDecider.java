package com.xc.springbatch.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

// 自定义任务决策器
// 可以设计Job或者Step的执行策略
@Component
public class SelfDecider  implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        LocalDate now=LocalDate.now();
        DayOfWeek dayOfWeek=now.getDayOfWeek();

        if(dayOfWeek==DayOfWeek.SATURDAY||dayOfWeek==DayOfWeek.SUNDAY){
            return new FlowExecutionStatus("weekend");
        }else{
            return new FlowExecutionStatus("workingDay");
        }
    }
}
