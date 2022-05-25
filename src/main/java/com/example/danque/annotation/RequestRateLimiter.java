package com.example.danque.annotation;

import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * redisson限流器
 * @author danque
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestRateLimiter {

    /**
     * 限流的 key值
     * @return
     */
    String key();

    /**
     * 限流模式 rate mode ,默认单机
     * @return
     */
    RateType type() default RateType.PER_CLIENT;

    /**
     * 限流速率，每单位时间内生成多少个令牌
     * @return
     */
    long rate() default 10;

    /**
     * rate time interval  间隔
     * @return
     */
    long rateInterval() default 1;

    /**
     * rate time interval  间隔单位
     * @return
     */
    RateIntervalUnit timeUnit() default RateIntervalUnit.MILLISECONDS;

}

