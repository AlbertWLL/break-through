package com.example.danque.api.controller;

import com.example.danque.api.service.VehicleService;
import com.example.danque.common.Result;
import com.example.danque.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

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
        String vehicleFromSlave = vehicleService.getVehicleFromSlave(id);
        return Result.success(vehicleFromSlave);
    }

    @PostMapping("/saveVehicleInfo")
    public Result SaveVehicleInfo(@RequestBody Vehicle vehicle) {
        vehicleService.saveVehicleInfo(vehicle);
        return Result.success(null);
    }
}
