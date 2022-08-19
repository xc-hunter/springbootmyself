package com.xc.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FirstJobDemo {

    //注入任务创建工厂以及步骤创建工厂
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    // 创建job get()用来指定job名称，start()指定job的开始step
    // step通过stepBuilderFactory来创建
    // step job的名称组合保证唯一
    // 配置的job注入到IOC容器
    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("firstJob")
                .start(step())
                .build();
    }

    // get()定义步骤名称
    // 步骤由若干tasklet组成
    private Step step() {
        return stepBuilderFactory.get("step")
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("执行步骤");
                    return RepeatStatus.FINISHED;
                })).build();
    }
}
