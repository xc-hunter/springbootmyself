package com.xc.springbatch.job;

import com.xc.springbatch.decider.SelfDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

// 使用自定义的决策器进行job的步骤step执行
@Component
public class JobBySelfDecider {

    @Autowired
    private SelfDecider selfDecider;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobByDecider(){
        return jobBuilderFactory.get("deciderJob")
                .start(step1())
                .next(selfDecider)
                // 依据决策器的decide()方法结果决定执行哪个步骤
                .from(selfDecider).on("weekend").to(step2())
                .from(selfDecider).on("workingDay").to(step3())
                // 如果执行了step3(),无论什么结果都执行step4
                .from(step3()).on("*").to(step4())
                .end()
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

    private Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("步骤四执行");
                    return RepeatStatus.FINISHED;
                })).build();
    }
}
