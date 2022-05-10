package com.example.danque.api.controller;

import com.example.danque.api.entity.ShardingVehicle;
import com.example.danque.api.service.VehicleService;
import com.example.danque.api.shardingsphereservice.ShardingService;
import com.example.danque.common.Result;
import com.example.danque.common.cache.CachedData;
import com.example.danque.api.entity.Vehicle;
import com.example.danque.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * 动态数据源 + redis缓存 + rabbitMQ练习
 */
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ShardingService shardingService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/getServiceInfo")
    public String getServiceInfo() {
        String mysql = "userName:root, PassWord:不告诉你";
        String redis = "address:******:6379";
        String nginx = "ip:****************";
        String rabbitMQ = "访问地址：http://101.132.177.32:15672/";
        return mysql + redis + nginx + rabbitMQ;
    }

    @GetMapping("/getVehicleFromMaster")
    public String getVehicleFromMaster(@RequestParam("id") long id) {
        return vehicleService.getVehicleFromMaster(id);
    }

    @GetMapping("/getVehicleFromSlave")
    public Result getVehicleFromSlave(@RequestParam("id") long id) {
        CachedData<Vehicle> vehicleFromSlave = vehicleService.getVehicleFromSlave(id);
        return Result.success(vehicleFromSlave.getPayload());
    }

    @PostMapping("/updateVehicleInfo")
    public Result updateVehicleInfo(@RequestBody Vehicle vehicle) {
        vehicleService.updateVehicleInfo(vehicle);
        return Result.success(null);
    }

    @PostMapping("/saveVehicleInfo")
    public Result saveVehicleInfo(@RequestBody Vehicle vehicle) {
        vehicleService.saveVehicleInfo(vehicle);
        return Result.success(null);
    }

    /**
     * shardingSphere-jdbc 动态数据源
     */
    @GetMapping("/getShardingSphereVehicleFromSlave")
    public Result getShardingSphereVehicleFromSlave(@RequestParam("id") long id) {
        ShardingVehicle vehicleFromSlave = shardingService.getShardingSphereVehicleFromSlave(id);
        return Result.success(vehicleFromSlave);
    }
}


