package com.pxichen.mydemo.service.impl;

import com.pxichen.mydemo.domain.Company;
import com.pxichen.mydemo.repository.CompanyRepo;
import com.pxichen.mydemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 公司业务层实现类
 */
@Service("companyServiceImpl")
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public void save(Company company)
    {
        companyRepo.save(company);
    }

    @Override
    public void delete(String uuid)
    {
        companyRepo.deleteById(uuid);
    }

    @Override
    @Transactional
    public void update(Company company)
    {
        companyRepo.save(company);
    }

    @Transactional
    @Override
    public void updateByName(String comaddress, String comname) {
        companyRepo.updateByNmae(comaddress,comname);
    }


    @Override
    public List<Company> findAll()
    {
        return companyRepo.findAll();
    }

    @Override
    public List<Company> findByNativeSQL(String companyname)
    {
        return companyRepo.findByNativeSQL(companyname);
    }

    @Override
    public Page<Company> findAllSimplePage(Pageable pageable) {
        return  companyRepo.findAll(pageable);
    }

    @Override
    public boolean validateComname(String comname)
    {
        /**
         * 查询对应的公司名称是否存在（服务层用于唯一性验证）,如果公司名称已经存在返回false，否则返回true
         */
        int intNumber=companyRepo.validateComname(comname);
        if(intNumber==0){return true;}
        else{return false;}
    }

    @Override
    public boolean validateEmail(String email)
    {
        /**
         * 查询联系人邮箱是否存在（服务层用于唯一性验证）,如果联系人邮箱已经存在返回false，否则返回true
         */
        int intNumber=companyRepo.validateEmail(email);
        if(intNumber==0){return true;}
        else{return false;}
    }

    @Override
    public boolean validateMobile(String mobile)
    {
        /**
         * 查询联系人手机号是否存在（服务层用于唯一性验证）,如果联系人手机号已经存在返回false，否则返回true
         */
        int intNumber=companyRepo.validateMobile(mobile);
        if(intNumber==0){return true;}
        else{return false;}
    }

    @Override
    public Page<Company> queryDynamic(Map<String, Object> reqMap, Pageable pageable)
    {
        /**
         * 多条件动态查询，
         * 常用查询：equal(等于)，notEqual(不等于)， gt(大于)， ge(大于等于)，lt(小于)， le(小于等于)，between(闭区间中的值)，like(模糊)等
         */
        Specification querySpecifi=new Specification<Company>()
        {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)
            {
                List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
                //条件设置部分，查询‘CriteriaBuilder’源码，查询的分类
                if(!reqMap.get("comname").toString().equals(""))//公司名称，like 模糊查询
                {
                    predicates.add(cb.like(root.get("comname"),"%"+reqMap.get("comname").toString()+"%"));
                }
                if(!reqMap.get("comstatus").toString().equals("全部"))//运营状态，精确查询
                {
                    predicates.add(cb.equal(root.get("comstatus"),reqMap.get("comstatus").toString()));
                }
                if(!reqMap.get("employeenumber").toString().equals(""))//大于员工人数
                {
                    predicates.add(cb.gt(root.get("employeenumber"),Integer.parseInt(reqMap.get("employeenumber").toString())));
                }
                return cb.and(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]));
            }
        };
        return this.companyRepo.findAll(querySpecifi,pageable);//这里利用的是JpaSpecificationExecuto接口的分页查询方法
    }

}
