package com.example.ehunter01;

import com.example.ehunter01.utils.RateLimiter;

public class TestOne {
 final static Object obj = new Object();
    public static void main(String[] args) throws InterruptedException {
        // 配置每分钟有100个请求，生成速度为1秒10个。
        int duration = 1000;
        RateLimiter rateLimiter = new RateLimiter(100,duration,10);
        rateLimiter.startProduce(obj);
        // 模拟在第一秒时有30个请求过来，只生产了10个,只有10个拿到，其他的没拿到。
        synchronized (obj){
            obj.wait();
        }
        for (int i = 0; i < 30; i++){
            new Thread(()->{
                while(true){
                    String name = Thread.currentThread().getName();
                    if (rateLimiter.tryGet()){
                        System.out.println("线程:"+ name +"没有拿到令牌！");// 这里可以返回异常信息。
                    }else{
                        System.out.println("线程:"+ name +"拿到令牌！");
                    }
                    try{
                        Thread.sleep(1000);// 无论拿到不拿到，休眠1秒，启动一秒就可以关闭查看结果。
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }
}
