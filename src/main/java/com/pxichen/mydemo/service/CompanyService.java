package com.pxichen.mydemo.service;

import com.pxichen.mydemo.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * 公司业务层接口
 */
public interface CompanyService {

    //保存
    void save(Company company);

    //根据uuid删除记录
    @Transactional
    void delete(String uuid);

    //修改
    @Transactional
    void update(Company company);

    //根据comname修改
    @Transactional
    void updateByName(String comaddress,String comname);

    //查询全部数据
    List<Company> findAll();

    //执行原生sql语句的查询
    List<Company> findByNativeSQL(String companyname);

    //简单分页查询
    Page<Company> findAllSimplePage(Pageable pageable);

    //验证公司名称唯一性
    boolean validateComname(String comname);

    //验证邮箱唯一性
    boolean validateEmail(String email);

    //验证手机号唯一性
    boolean validateMobile(String mobile);
    //多条件查询
    Page<Company> queryDynamic(Map<String,Object> req,Pageable pageable);
}
