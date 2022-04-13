package com.example.danque.common;

/**
 * 数据源枚举类
 */
public enum DataSourceEnum {

    MASTER("MASTER","主数据源"),
    SLAVE("SLAVE", "从数据源");

    private String code;
    private String desc;

    DataSourceEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }

}
