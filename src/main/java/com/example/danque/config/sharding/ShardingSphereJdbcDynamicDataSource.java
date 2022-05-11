package com.example.danque.config.sharding;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @ClassName ShardingSphereJdbcDynamicDataSource
 * @Description shardingsphere动态数据源配置
 * @Author danque
 * @Email wlljava@163.com
 * @Date 2022/5/11 16:01
 */
@Configuration
public class ShardingSphereJdbcDynamicDataSource {

    @Bean("shardingsphereMasterDataSource")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.master")
//    @Primary
    public DataSource masterDataSource(){
        return new HikariDataSource();
    }

    @Bean("masterTM")
    public DataSourceTransactionManager masgerTransactionManager(@Qualifier("shardingsphereMasterDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("shardingsphereSlaveDataSource")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.slave")
    public DataSource slaveDataSource(){
        return new HikariDataSource();
    }

    @Bean("slaveTM")
    public DataSourceTransactionManager slaveTransactionManager(@Qualifier("shardingsphereSlaveDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
