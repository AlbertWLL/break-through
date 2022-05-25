package com.example.danque.config;


import com.example.danque.common.enums.DataSourceEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源加载
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("clusterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource clusterSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(@Qualifier("masterDataSource")DataSource masterDataSource,@Qualifier("clusterDataSource")DataSource clusterDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceEnum.MASTER.getCode(), masterDataSource);
        targetDataSources.put(DataSourceEnum.SLAVE.getCode(), clusterDataSource);
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }

    @Bean("dynamicDataSourceTM")
    public DataSourceTransactionManager slaveTransactionManager(@Qualifier("dynamicDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
