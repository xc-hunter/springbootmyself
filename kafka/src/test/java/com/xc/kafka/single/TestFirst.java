package com.xc.kafka.single;

import com.xc.kafka.producer.MessageProducerFirst;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
//127.0.0.1 0 62f8c2a978f问题,
@SpringBootTest
public class TestFirst {

    @Autowired
    MessageProducerFirst messageProducerFirst;

    @Test
    public void testSyncSend()throws InterruptedException{
        int id=(int)(System.currentTimeMillis()/1000);
        SendResult result=messageProducerFirst.syncSend(id);
        System.out.println(result);
        // 一直阻塞,保证消费出来结果
        new CountDownLatch(1).await();
    }

    @Test
    public void testAsyncSend()throws InterruptedException{
        int id=(int)(System.currentTimeMillis()/1000);
        messageProducerFirst.asyncSend(id).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(SendResult<Object, Object> result) {

            }
        });
        // 一直阻塞,保证消费出来结果
        new CountDownLatch(1).await();
    }
}
