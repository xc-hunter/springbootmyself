package com.xc.kafka.producer;

import com.xc.kafka.message.DemoFirst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor(onConstructor_=@Autowired)
@Slf4j
public class MessageProducerFirst {

    private final KafkaTemplate<Object,Object> kafkaTemplate;

    /**
     * 同步发送，通过get阻塞等待获取发送结果实现同步发送
     * @param id
     * @return
     */
    public SendResult syncSend(Integer id){
        DemoFirst demoFirst=new DemoFirst();
        demoFirst.setId(id);
        demoFirst.setInfo(id.toString());
        try {
            return kafkaTemplate.send(DemoFirst.DEFAULT_TOPIC,demoFirst).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //kafkaTemplate.send(DemoFirst.DEFAULT_TOPIC,demoFirst).addCallback((result)->{
        //
        //},(error)->{
        //    log.error("生产消息时失败{}",error.getMessage());
        //});
        //kafkaTemplate.send(DemoFirst.DEFAULT_TOPIC,demoFirst).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
        // 此处为Throwable
        //    @Override
        //    public void onFailure(Throwable ex) {
        //
        //    }
        //
        //    @Override
        //    public void onSuccess(SendResult<Object, Object> result) {
        //
        //    }
        //});
        //kafkaTemplate.send(DemoFirst.DEFAULT_TOPIC,demoFirst).addCallback(new KafkaSendCallback<>(){
        //    // 此处为producerException,避免异常转换
        //    @Override
        //    public void onFailure(KafkaProducerException ex) {
        //
        //    }
        //
        //    @Override
        //    public void onSuccess(Object result) {
        //
        //    }
        //});
        return null;
    }

    /**
     * 异步发送
     * @param id
     * @return
     */
    public ListenableFuture<SendResult<Object,Object>> asyncSend(Integer id){
        DemoFirst demoFirst=new DemoFirst();
        demoFirst.setId(id);
        demoFirst.setInfo(id.toString());
        return kafkaTemplate.send(DemoFirst.DEFAULT_TOPIC,demoFirst);
    }
}
