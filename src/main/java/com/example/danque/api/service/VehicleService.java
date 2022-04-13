package com.example.danque.api.service;

/**
 * @author danque
 * @date 2022/4/13
 * @desc
 */
public interface VehicleService {

    public String getVehicleFromMaster(long id);

    public String getVehicleFromSlave(long id);
}
