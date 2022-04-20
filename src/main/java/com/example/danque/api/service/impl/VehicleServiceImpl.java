package com.example.danque.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.danque.annotation.DBSwitch;
import com.example.danque.api.mapper.VehicleMapper;
import com.example.danque.api.service.VehicleService;
import com.example.danque.common.cache.CachedData;
import com.example.danque.common.constants.RedisConstants;
import com.example.danque.entity.Vehicle;
import com.example.danque.mq.DanqueMQPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author danque
 * @date 2022/4/13
 * @desc
 */
@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

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

    @Override
    @DBSwitch(name = "SLAVE")
    @Cacheable(cacheNames = RedisConstants.CACHE_NAME,key = " 'danque:break-through:vehicle_id_' + #id")
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
    @Transactional(rollbackFor = Exception.class,propagation= Propagation.SUPPORTS)
    public void updateVehicleInfo(Vehicle vehicle) {
        UpdateWrapper<Vehicle> queryWrapper = new UpdateWrapper<>();
        queryWrapper.lambda().eq(Vehicle::getId,vehicle.getId());
        int update = vehicleMapper.update(vehicle, queryWrapper);
        if(update > 0){
            System.out.println("update vehicle success!");
        }
        try {
            for(long i = 3; i <= 10; i ++){
                vehicle.setId(i);
                if (false) danqueMQPublisher.sendMessage(JSON.toJSONString(vehicle));
            }
        } catch (Exception e) {
            log.error("VehicleServiceImpl#updateVehicleInfo()-发送MQ消息失败：{}", e);
        }
    }
}
