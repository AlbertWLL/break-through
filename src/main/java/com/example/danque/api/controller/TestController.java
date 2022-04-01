package com.example.danque.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/HelloWorld")
public class TestController {

    @GetMapping("/hi")
    public String getServiceInfo() {
        String mysql = "userName:root, PassWord:不告诉你";
        String redis = "address:******:6379";
        String nginx = "ip:****************";
        String rabbitMQ = "访问地址：http://101.132.177.32:15672/";
        return mysql + redis + nginx + rabbitMQ;
    }
}
