package com.example.danque.aop;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.danque.annotation.RequestRateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateLimiterConfig;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class RequestRateLimitAspect {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 根据自定义注解获取切点
     * @param requestRateLimiter
     */
    @Pointcut("@annotation(requestRateLimiter)")
    public void accessLimit(RequestRateLimiter requestRateLimiter) {
    }

    @Around(value = "accessLimit(requestRateLimiter)", argNames = "pjp,requestRateLimiter")
    public Object around(ProceedingJoinPoint pjp, RequestRateLimiter requestRateLimiter) throws Throwable {
        // 限流拦截器
        RRateLimiter rRateLimiter = getRateLimiter(requestRateLimiter);
        if (rRateLimiter.tryAcquire()) {
            return pjp.proceed();
        } else {
            return buildErrorResult();
        }
    }

    /**
     * 获取限流拦截器
     * @param requestRateLimiter
     * @return
     */
    private RRateLimiter getRateLimiter(RequestRateLimiter requestRateLimiter){
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(StringUtils.isBlank(requestRateLimiter.key()) ? "default:limiter" : requestRateLimiter.key());
        // 设置限流
        if(rRateLimiter.isExists()) {
            RateLimiterConfig rateLimiterConfig = rRateLimiter.getConfig();
            // 判断配置是否更新，如果更新，重新加载限流器配置
            if (!Objects.equals(requestRateLimiter.rate(), rateLimiterConfig.getRate())
                    || !Objects.equals(requestRateLimiter.timeUnit().toMillis(requestRateLimiter.rateInterval()), rateLimiterConfig.getRateInterval())
                    || !Objects.equals(requestRateLimiter.type(), rateLimiterConfig.getRateType())) {
                rRateLimiter.delete();
                rRateLimiter.trySetRate(requestRateLimiter.type(), requestRateLimiter.rate(), requestRateLimiter.rateInterval(), requestRateLimiter.timeUnit());
            }
        } else {
            rRateLimiter.trySetRate(requestRateLimiter.type(), requestRateLimiter.rate(), requestRateLimiter.rateInterval(), requestRateLimiter.timeUnit());
        }
        return rRateLimiter;
    }

    /**
     * 没有限流令牌返回结果
     * @return
     */
    public String buildErrorResult() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "-1");
        map.put("message", "Too many people visit");
        return JSON.toJSONString(map);
    }

}

