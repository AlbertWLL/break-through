package com.example.danque.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @ClassName:RedissonConfig
 * @author: danque
 * @date: 2022/5/25 15:52
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redisson() {
        // 单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://dev-redis-master.dev.svc.sh.office:6379").setDatabase(0).setPassword("CoUmJ2LL4DHNSSw1wCs6");
        return Redisson.create(config);
    }



    //单机
    /**
     *     RedissonClient redisson = Redisson.create();
     *     Config config = new Config();
     *     config.useSingleServer().setAddress("myredisserver:6379");
     *     RedissonClient redisson = Redisson.create(config);
     */


        //主从
    /**
     *          Config config = new Config();
     *         config.useMasterSlaveServers().setMasterAddress("127.0.0.1:6379")
     *         .addSlaveAddress("127.0.0.1:6389","127.0.0.1:6332","127.0.0.1:6419")
     *         .addSlaveAddress("127.0.0.1:6399");
     *         RedissonClient redisson = Redisson.create(config);
     */



    //哨兵
    /**
     *      Config config = new Config();
     *     config.useSentinelServers().setMasterName("mymaster")
     *     .addSentinelAddress("127.0.0.1:26389","127.0.0.1:26379")
     *     .addSentinelAddress("127.0.0.1:26319");
     *     RedissonClient redisson = Redisson.create(config);
     */


    //集群
    /**
     *     Config config = new Config();
     *     config.useClusterServers()
     *     setScanInterval(2000) // cluster state scan interval in milliseconds
     *     .addNodeAddress("127.0.0.1:7000","127.0.0.1:7001").addNodeAddress("127.0.0.1:7002");
     *     RedissonClient redisson = Redisson.create(config);
     */

}
