package com.example.danque.api.shardingsphereservice;

import com.example.danque.api.entity.ShardingVehicle;

/**
 * @description: 动态数据源
 * @ClassName:ShardingService
 * @author: danque
 * @date: 2022/5/10 16:14
 */
public interface ShardingService {

    ShardingVehicle getShardingSphereVehicleFromSlave(long id);
}
