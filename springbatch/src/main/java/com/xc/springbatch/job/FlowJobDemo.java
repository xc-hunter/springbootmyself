package com.xc.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

// flow组合step
@Component
public class FlowJobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJob(){
        return jobBuilderFactory.get("flowjob")
                .start(flow())
                .next(step3())
                .end()
                .build();

    }

    // 组合步骤，构成任务流，赋给job
    // 使用Flow和step的区别：使用flow构建job需要在build()方法前调用end()方法
    private Flow flow() {
        return new FlowBuilder<Flow>("flow")
                .start(step1())
                .next(step2())
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("步骤一执行");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("步骤二执行");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("步骤三执行");
                    return RepeatStatus.FINISHED;
                })).build();
    }
}
