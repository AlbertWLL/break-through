package com.example.danque.api.controller;

import com.example.danque.annotation.RequestRateLimiter;
import com.example.danque.api.entity.ShardingVehicle;
import com.example.danque.api.entity.Vehicle;
import com.example.danque.api.service.VehicleService;
import com.example.danque.api.shardingsphereservice.ShardingService;
import com.example.danque.common.Result;
import com.example.danque.common.cache.CachedData;
import org.redisson.api.RateIntervalUnit;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getServiceInfo")
    public String getServiceInfo() {
        String mysql = "userName:root, PassWord:不告诉你";
        String redis = "address:******:6379";
        String nginx = "ip:****************";
        String rabbitMQ = "访问地址：http://101.132.177.32:15672/";
        return mysql + redis + nginx + rabbitMQ;
    }

    /**
     * 主库数据查询
     * @param id
     * @return
     */
    @GetMapping("/getVehicleFromMaster")
    public String getVehicleFromMaster(@RequestParam("id") long id) {
        return vehicleService.getVehicleFromMaster(id);
    }

    /**
     * 利用redisson 做接口限流
     * @param id
     * @return
     */
    @GetMapping("/getVehicleFromMasterByRateLimiter")
    @RequestRateLimiter(key = "getVehicleFromMasterByRateLimiter",rate = 10l ,rateInterval = 1l,timeUnit = RateIntervalUnit.SECONDS)
    public String getVehicleFromMasterByRateLimiter(@RequestParam("id") long id) {
        return vehicleService.getVehicleFromMasterByRateLimiter(id);
    }

    /**
     * 从库数据查询
     * @param id
     * @return
     */
    @GetMapping("/getVehicleFromSlave")
    public Result getVehicleFromSlave(@RequestParam("id") long id) {
        CachedData<Vehicle> vehicleFromSlave = vehicleService.getVehicleFromSlave(id);
        return Result.success(vehicleFromSlave.getPayload());
    }

    /**
     * 更新从库数据
     * @param vehicle
     * @return
     */
    @PostMapping("/updateVehicleInfo")
    public Result updateVehicleInfo(@RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicleInfo(vehicle);
    }

    /**
     * 保存主库数据
     * @param vehicle
     * @return
     */
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


