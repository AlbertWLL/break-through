package com.example.danque.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @ClassName:Company
 * @author: danque
 * @date: 2022/5/11 21:12
 */
@Data
@TableName(value = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1l;

    private Long coId;

    private String name;

    private Long tableTeamId;

}
