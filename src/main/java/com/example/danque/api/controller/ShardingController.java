package com.example.danque.api.controller;

import com.example.danque.api.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @ClassName:ShardingController
 * @author: danque
 * @date: 2022/5/11 21:03
 */
@RestController
@RequestMapping("/sharding")
public class ShardingController {

    @Autowired
    private CompanyService shardingService;

    @GetMapping("/getShardingInfo")
    public Long getShardingInfo(@RequestParam("coId") Long coId) {
        return shardingService.getShardingInfo(coId);
    }

}
