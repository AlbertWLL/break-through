package com.example.danque.test;

import org.springframework.util.StopWatch;

/**
 * @description:
 * @ClassName:StopWatchPractice
 * @author: danque
 * @date: 2022/5/24 15:05
 */
public class StopWatchPractice {

    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("操作开始1");
        /*
           此处写业务逻辑代码
        */
        for(int i =0 ; i < 1000 ;i++ ){
//            System.out.println("操作开始"+i);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("currentTaskName:" + stopWatch.currentTaskName());
        System.out.println("getTotalTimeNanos:" + stopWatch.getTotalTimeNanos());
        System.out.println("getTotalTimeSeconds:" + stopWatch.getTotalTimeSeconds());
        System.out.println("getTotalTimeMillis:" + stopWatch.getTotalTimeMillis());
        stopWatch.stop();

        stopWatch.start("操作开始2");
        /*
        此处写业务逻辑代码
        */
        System.out.println("操作开始2");
        stopWatch.stop();

        //全部打印输出
        stopWatch.prettyPrint();
    }


}
