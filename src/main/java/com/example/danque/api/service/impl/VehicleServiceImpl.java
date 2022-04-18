package com.example.danque.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.danque.annotation.DBSwitch;
import com.example.danque.api.mapper.VehicleMapper;
import com.example.danque.api.service.VehicleService;
import com.example.danque.common.cache.CachedData;
import com.example.danque.common.constants.RedisConstants;
import com.example.danque.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author danque
 * @date 2022/4/13
 * @desc
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired(required = false)
    private VehicleMapper vehicleMapper;

    @Override
    @DBSwitch(name = "MASTER")
    public String getVehicleFromMaster(long id) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        return JSON.toJSONString(vehicle);
    }

    @Override
    @DBSwitch(name = "SLAVE")
    @Cacheable(cacheNames = RedisConstants.CACHE_NAME,keyGenerator = "keyGenerator")
    public CachedData<Vehicle> getVehicleFromSlave(long id) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        return new CachedData<>(vehicle);
    }

    @Override
    @DBSwitch(name = "MASTER")
    public void saveVehicleInfo(Vehicle vehicle) {
        vehicleMapper.insert(vehicle);
    }
}
