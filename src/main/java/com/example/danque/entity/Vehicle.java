package com.example.danque.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author danque
 * @date 2022/4/13
 * @desc
 */
@Data
@TableName(value = "tb_vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 4544057081169003488L;

    @TableId(type = IdType.AUTO)
    private long id;
    private long user_id;
    private long contact_id;
    private String vin;
    private String pin_code;
    private String enger_no;
    private String vehicle_name;
    private long photo_id;
    private String license_no;
    private String license_color;
    private long brand_id;
    private long market_id;
    private String material_code;
    private Date bind_time;
    private long bind_status;
    private String description;
    private long status;
    private long create_by;
    private Date create_date;
    private long last_update_by;
    private Date last_update_date;
    private long row_version;
    private boolean is_valid;
    private long model_id;
    private String photo_url;
    private long package_id;
    private String order_no;
    private long series_no;
    private long oem_id;
    private Date finish_time;
    private String sap_code;
    private String checkout_no_14;
    private String checkout_no_15;
    private Date checkout_date;
    private Date license_date;
    private long platform_id;
    private String fc01;
    private String clxh;
    private long projectname_id;
    private String oem_factory;

}
