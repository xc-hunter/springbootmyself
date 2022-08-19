package com.xc.springbatch.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

// 多步骤job
// 可定义执行步骤逻辑，可自定义前一个步骤的状态决定后一个步骤的执行
@Component
public class MultiStepJobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job multiStepJob(){
        return jobBuilderFactory.get("multistepjob")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    @Bean
    public Job multiStepJob2(){
        return jobBuilderFactory.get("multistepjob2")
                .start(step1())
                .on(ExitStatus.COMPLETED.getExitCode()).to(step2())
                .from(step2())
                .on(ExitStatus.COMPLETED.getExitCode()).to(step3())
                .from(step3()).end()
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
