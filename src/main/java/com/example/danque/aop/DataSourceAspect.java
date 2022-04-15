package com.example.danque.aop;

import com.example.danque.annotation.DBSwitch;
import com.example.danque.common.enums.DataSourceEnum;
import com.example.danque.config.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;


@Slf4j
@Aspect
@Component
public class DataSourceAspect implements Ordered {

    @Pointcut("execution (* com.example.danque.api.service.impl..*.*(..))")
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DBSwitch ds = method.getAnnotation(DBSwitch.class);
        if (ds == null) {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getCode());
            log.info("set datasource is " + DataSourceEnum.MASTER.getCode());
        } else {
            DynamicDataSource.setDataSource(ds.name());
            log.info("set datasource is " + ds.name());
        }
        try {
            return point.proceed();
        }catch (Exception e){
            log.info("DataSourceAspect-around接口调用异常：{}",e);
            DynamicDataSource.clearDataSource();
            throw e;
        }finally {
            DynamicDataSource.clearDataSource();
            log.info("clean datasource success!");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

