package com.xc.datajpa.tool;

import java.util.Map;
import java.util.concurrent.*;

public class TaskProcessUtil {
    // 每个任务对应一个单独的线程池
    private final static Map<String, ExecutorService> executors=new ConcurrentHashMap<>();

    /**
     * 获取线程池
     * @param poolName 池Name
     * @param poolSize 池大小
     * @return
     */
    public static ExecutorService getOrInitExecutors(String poolName,int poolSize){
        ExecutorService executorService=executors.get(poolName);
        if(null==executorService){
            // 并发加锁保安全
            synchronized (TaskProcessUtil.class){
                executorService=executors.get(poolName);
                // 二次判空
                if(null==executorService){
                    executorService=init(poolName,poolSize);
                    executors.put(poolName,executorService);
                }
            }
        }
        return executorService;
    }

    /**
     * 回收线程池
     * @param poolName
     */
    public static void releaseExecutor(String poolName){
        ExecutorService executor=executors.remove(poolName);
        if(executor!=null){
            executor.shutdown();
        }
    }

    private static ExecutorService init(String poolName, int poolSize) {
        // 线程池工厂构造推荐使用 Guava的ThreadFactoryBuilder
        // 初始化一个线程池
        //    return new ThreadPoolExecutor(poolSize, poolSize, 0L,
        //            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
        //            new ThreadFactoryBuilder()
        //                    .setNameFormat("Pool-" + poolName)
        //                    .setDaemon(false)
        //                    .build(),
        //            new ThreadPoolExecutor.CallerRunsPolicy());
        // 队列无限、核心池与最大池大小依据参数决定，策略使用调用者自行执行策略
        return new ThreadPoolExecutor(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread=new Thread(r);
                thread.setName("Pool-" + poolName);
                thread.setDaemon(false);
                return thread;
            }
        },new ThreadPoolExecutor.CallerRunsPolicy());
    }


}
