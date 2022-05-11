package com.example.danque.api.service.impl;

import com.example.danque.annotation.DBSwitch;
import com.example.danque.api.entity.Company;
import com.example.danque.api.mapper.CompanyMapper;
import com.example.danque.api.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @ClassName:ShardingServiceImpl
 * @author: danque
 * @date: 2022/5/11 21:06
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    @DBSwitch(name = "MASTER")
    public Long getShardingInfo(Long coId) {
        Company company = companyMapper.selectByCoId(coId);
        if(null != company){
            return company.getTableTeamId();
        }
        return 1l;
    }
}
