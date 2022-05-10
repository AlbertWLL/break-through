package com.example.danque.api.shardingsphereservice.impl;

import com.example.danque.api.entity.ShardingVehicle;
import com.example.danque.api.mapper.ShardingVehicleMapper;
import com.example.danque.api.shardingsphereservice.ShardingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 动态数据源
 * @ClassName:ShardingServiceImpl
 * @author: danque
 * @date: 2022/5/10 16:14
 */
@Service
@Slf4j
public class ShardingServiceImpl implements ShardingService {

    @Autowired
    private ShardingVehicleMapper shardingVehicleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class,value = "masterTM")
    public ShardingVehicle getShardingSphereVehicleFromSlave(long id) {
        log.info("shardingsphere jdbc 动态数据源-查询数据");
        ShardingVehicle shardingVehicle = shardingVehicleMapper.selectById(id);
        return shardingVehicle;
    }
}
