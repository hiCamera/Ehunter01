package com.example.ehunter01.utils;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimiter {

    // 令牌
    public static final String TOKEN = "token";

    // 阻塞队列，用于存放令牌
    private ArrayBlockingQueue<String> blockingQueue;

    // 令牌桶容量
    private int limitCapacity;

    // 令牌产生的时间间隔，单位 毫秒
    private int duration;

    // 令牌每次产生的数量
    private int number;

    public RateLimiter(int limitCapacity, int duration, int number) {
        this.limitCapacity = limitCapacity;
        this.duration = duration;
        this.number = number;
        blockingQueue = new ArrayBlockingQueue<>(limitCapacity);
        init();
    }
    // 生成令牌
    public void startProduce(Object obj){
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() ->{
            synchronized (obj){
                addToken();
                obj.notify();
            }
        },500,this.duration, TimeUnit.MILLISECONDS);// 延迟启动500毫秒
    }

    // 创建时初始化令牌
    private void init(){
        for (int i = 0; i < this.number; i++){
            blockingQueue.add(TOKEN);
        }
    }
    // 添加令牌
    private void addToken(){
        for(int i = 0; i < this.number; i++){
            blockingQueue.offer(TOKEN);// 溢出会抛异常
        }
    }
    // 获取令牌
    public boolean tryGet(){
        // 对手元素先出
        return blockingQueue.poll() != null;
    }
}
