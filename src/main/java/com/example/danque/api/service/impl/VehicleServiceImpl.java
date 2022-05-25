package com.example.danque.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.danque.annotation.DBSwitch;
import com.example.danque.api.entity.Vehicle;
import com.example.danque.api.mapper.VehicleMapper;
import com.example.danque.api.service.VehicleService;
import com.example.danque.common.Result;
import com.example.danque.common.cache.CachedData;
import com.example.danque.common.constants.RedisConstants;
import com.example.danque.mq.DanqueMQPublisher;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author danque
 * @date 2022/4/13
 * @desc
 */
@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    public static final String VEHICLE_LOCK_KEY = "danque-vehicle-lk-";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private DanqueMQPublisher danqueMQPublisher;

    @Override
    @DBSwitch(name = "MASTER")
    public String getVehicleFromMaster(long id) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        return JSON.toJSONString(vehicle);
    }

    /**
     * redisson 接口限流
     * @param id
     * @return
     */
    @Override
    @DBSwitch(name = "MASTER")
    public String getVehicleFromMasterByRateLimiter(long id) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        return JSON.toJSONString(vehicle);
    }

    @Override
    @DBSwitch(name = "SLAVE")
    @Cacheable(cacheNames = RedisConstants.CACHE_NAME,key = " 'danque:break-through:vehicle_id_' + #id")
//    @Cacheable(cacheNames = RedisConstants.CACHE_NAME,key = VEHICLE_LOCK_KEY)
    public CachedData<Vehicle> getVehicleFromSlave(long id) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        return new CachedData<>(vehicle);
    }


    @Override
    @DBSwitch(name = "MASTER")
    @Transactional(rollbackFor = Exception.class,propagation= Propagation.SUPPORTS)
    public void saveVehicleInfo(Vehicle vehicle) {
        vehicleMapper.insert(vehicle);
    }


    @Override
    @DBSwitch(name = "SLAVE")
    @CacheEvict(cacheNames = RedisConstants.CACHE_NAME,key = " 'danque:break-through:vehicle_id_' + #vehicle.getId()")
    @Transactional(rollbackFor = Exception.class,propagation= Propagation.SUPPORTS,value = "dynamicDataSourceTM")
    public Result updateVehicleInfo(Vehicle vehicle) {
        String lockKey = "redisson-ratelimiter";
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean flag = lock.tryLock(5, 50, TimeUnit.SECONDS);
            if(!flag){
                return Result.fail("正在更新车辆信息，请勿重复操作！");
            }
            //处理接下来的业务逻辑
            UpdateWrapper<Vehicle> queryWrapper = new UpdateWrapper<>();
            queryWrapper.lambda().eq(Vehicle::getId,vehicle.getId());
            int update = vehicleMapper.update(vehicle, queryWrapper);
            if(update > 0){
                System.out.println("update vehicle success!");
            }
            try {
                //发送MQ消息
                for(long i = 3; i <= 10; i ++){
                    Thread.sleep(2000);
                    vehicle.setId(i);
                    if (false) danqueMQPublisher.sendMessage(JSON.toJSONString(vehicle));
                }
            } catch (Exception e) {
                log.error("VehicleServiceImpl#updateVehicleInfo()-发送MQ消息失败：{}", e);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //判断是否加锁成功  && 并且都当前线程持有
            if(lock.isLocked() && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
//            RFuture<Void> voidRFuture = lock.unlockAsync();
        }
        return Result.success("车辆信息更新成功！");
    }
}
