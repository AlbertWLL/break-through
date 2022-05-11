package com.example.danque.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.danque.api.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @ClassName:CompanyMapper
 * @author: danque
 * @date: 2022/5/11 21:14
 */
@Mapper
@Repository
public interface CompanyMapper extends BaseMapper<Company> {

    @Select("select * from company where co_id = #{coId}")
    Company selectByCoId(Long coId);
}
