package com.example.danque.test;

import java.util.concurrent.*;
import java.util.function.Function;

/**
 * CompletableFuture 用法
 */
public class CompletableFuturePractice {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService1 = Executors.newFixedThreadPool(2);
		ExecutorService executorService2 = Executors.newCachedThreadPool();
		Executors.newSingleThreadExecutor();
		Executors.newScheduledThreadPool(1);
		ExecutorService executorService = Executors.newWorkStealingPool(3);

		executorService.awaitTermination(3,TimeUnit.SECONDS);
		executorService.shutdown();
		executorService.shutdownNow();
		//设置队列的大小
		ThreadPoolExecutor threadPoolExecutor =
				new ThreadPoolExecutor(10,100,5,
				TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(100),new ThreadPoolExecutor.DiscardPolicy());
		/**
		 * 线程类
		 */
		//创建线程的方法？
		//线程的状态？
		/**
		 * 线程池
		 * Executors.newCachedThreadPool();
		 * Executors.newFixedThreadPool();
		 * Executors.newSingleThreadExecutor();
		 * Executors.newScheduledThreadPool(1);     ScheduledThreadPoolExecutor
		 */
		//  拒绝策略：RejectedExecutionHandler，有哪几种拒绝 策略？
		//  ThreadPoolExecutor 创建线程池有哪些参数？
		//  为什么不建议用Executors，有界队列、无界队列的区别？
		//  线程池的至执行流程？
		//  多线程的原理？

		/**
		 * CompletableFuture异步编排
		 * runAsync 不支持返回值，SupplyAsync支持返回值。
		 */
		CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
			}
			// ThreadPoolExecutor 核心类
		}, Executors.newFixedThreadPool(3));


		// anonymous 匿名的
		CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
			if (Math.random() < 0.5) {
				throw new RuntimeException("抛出异常");
			}
			System.out.println("正常结束");
			return 1.1;
		}).thenApply(result -> {
			System.out.println("thenApply接收到的参数 = " + result);
			return result;
		}).exceptionally(new Function<Throwable, Double>() {
			@Override
			public Double apply(Throwable throwable) {
				System.out.println("异常：" + throwable.getMessage());
				return 0.0;
			}
		});

		System.out.println("最终返回的结果 = " + future.get());

	}
}
