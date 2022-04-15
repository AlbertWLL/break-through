package com.example.danque.api.service;

import com.example.danque.entity.Vehicle;

/**
 * @author danque
 * @date 2022/4/13
 * @desc
 */
public interface VehicleService {

    public String getVehicleFromMaster(long id);

    public String getVehicleFromSlave(long id);

    void saveVehicleInfo(Vehicle vehicle);
}
