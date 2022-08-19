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
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

//step并行执行demo
// 实现逻辑,分两步
// 1. 将step转换为flow
// 2. 任务job中执行flow并行 ,通过split()指定异步执行器
@Component
public class ParallelStepJobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parallelStepJob(){
        return jobBuilderFactory.get("parallelStepJob")
                .start(flow1())
                // split指定一个异步执行器，将flow添加到异步执行器
                .split(new SimpleAsyncTaskExecutor()).add(flow2())
                .end()
                .build();
    }

    private Flow flow1() {
        return new FlowBuilder<Flow>("flow1")
                .start(step1())
                .next(step2())
                .build();
    }

    private Flow flow2() {
        return new FlowBuilder<Flow>("flow2")
                .start(step3())
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
