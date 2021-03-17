package com.pxichen.mydemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.pxichen.mydemo.domain.Company;
import com.pxichen.mydemo.domain.EchartsData;
import com.pxichen.mydemo.service.impl.CompanyServiceImpl;
import com.pxichen.mydemo.utils.ReceiveUploadFile;
import org.apache.tomcat.util.http.fileupload.util.mime.MimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公司控制层
 */
@Controller
@RequestMapping("CompanyModule")
public class CompanyController {
    @Resource(name = "companyServiceImpl")
    private CompanyServiceImpl companyService;
    @Autowired
    private  ReceiveUploadFile receiveUploadFile;

    @PostMapping("upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile file){
        /*
         文件上传 ,并返回给客户端全路径
         */
        return receiveUploadFile.receiveFile(file,"testfile");
    }


    @PostMapping("/save")
    @ResponseBody
    public void save(Company company) {
        /**
         * 保存公司信息
         */
        companyService.save(company);
    }

    @GetMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam String uuid) {
        /**
         * 删除公司信息
         */
        companyService.delete(uuid);
    }

    @PostMapping("/multiQuery")
    @ResponseBody
    public Map<String, Object> mutiQuery(@RequestBody(required = false) Map<String, Object> reqMap) {
        /**
         * 复杂查询
         */
        String a1 = reqMap.get("a1").toString();
        String a2 = reqMap.get("a2").toString();
        String a3 = reqMap.get("a3").toString();
        String a4 = reqMap.get("a4").toString();
        String a5 = reqMap.get("a5").toString();

        return reqMap;
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Company> findAll() {
        /**
         * 查询所有
         */
        return companyService.findAll();
    }


    //*****第三种
    @RequestMapping("/queryDynamic")
    @ResponseBody
    public String queryDynamic(@RequestBody(required = false) Map<String, Object> reqMap) {
        /**
         * 动态多条件查询
         */
        int page = 0;
        int size = 3;
        if (null != reqMap) {
            if (null != reqMap.get("page").toString()) {
                page = Integer.parseInt(reqMap.get("page").toString());
            }
            if (null != reqMap.get("size").toString()) {
                size = Integer.parseInt(reqMap.get("size").toString());
            }
        }
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "comname"));
        orders.add(new Sort.Order(Sort.Direction.ASC, "comaddress"));

        Page<Company> pageinfo = companyService.queryDynamic(reqMap,PageRequest.of(page, size, Sort.by(orders)));
        List<Company> content = pageinfo.getContent();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows", content);
        jsonObject.put("total", pageinfo.getTotalElements());

        return jsonObject.toJSONString();

    }




    //*****第一种
    @RequestMapping("/findAllSimplePage")
    @ResponseBody
    public Page<Company> findAllSimplePage(@RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "size", required = false, defaultValue = "2") int size) {
        /**
         * 简单排序分页
         */
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "comname"));
        orders.add(new Sort.Order(Sort.Direction.ASC, "comaddress"));
        return companyService.findAllSimplePage(PageRequest.of(page, size, Sort.by(orders)));
    }

    //*****第二种
    @RequestMapping("/findAllSimplePageMap")
    @ResponseBody
    public String findAllSimplePageMap(@RequestBody(required = false) Map<String, Object> reqMap) {
        /**
         * 简单排序分页
         */
        int page = 0;
        int size = 3;
        if (null != reqMap) {
            if (null != reqMap.get("page").toString()) {
                page = Integer.parseInt(reqMap.get("page").toString());
            }
            if (null != reqMap.get("size").toString()) {
                size = Integer.parseInt(reqMap.get("size").toString());
            }
        }
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "comname"));
        orders.add(new Sort.Order(Sort.Direction.ASC, "comaddress"));
        Page<Company> allSimplePage = companyService.findAllSimplePage(PageRequest.of(page, size, Sort.by(orders)));
        List<Company> content = allSimplePage.getContent();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows", content);
        jsonObject.put("total", allSimplePage.getTotalElements());

        return jsonObject.toJSONString();

    }


    //***********************访问页面******
    @RequestMapping("/listCompanyHtml")
    public String listCompany() {
        //跳转 listCompany.html
        return "/company/listCompany.html";
    }

    @RequestMapping("/addCompanyHtml")
    public String addCompany() {
        //跳转 addCompany.html
        return "/company/addCompany.html";
    }


    @RequestMapping("/")
    public String showTemplateHtml() {
        //跳转 首页
        return "index.html";
    }

    //
    @RequestMapping("/chartCompanyHtml")
    public String chartHtml() {
        //公司图表展示
        return "/company/chartCompany.html";
    }

    //************restful风格*****************
    @GetMapping("/company/{comname}")
    @ResponseBody
    public List<Company> query1(@PathVariable String comname) {
        /**
         * 根据公司名查询
         */
        System.out.println(comname);
        return companyService.findByNativeSQL(comname);
    }

    @PutMapping("/company/{comaddress}/{comname}")
    @ResponseBody
    public String query2(@PathVariable String comaddress, @PathVariable String comname) {
        /**
         * 修改
         */
        System.out.println(comname);
        System.out.println(comaddress);
        companyService.updateByName(comaddress, comname);
        return "comaddress:" + comaddress + ",comname:" + comname;
    }

    @RequestMapping("/chart")
    @ResponseBody
    public Map<String,List<EchartsData>> charts()
    {
        /**
         * 为echarts图表展示，准备数据
         * 返回员工数量，营业收入两个集合
         */
        HashMap<String, List<EchartsData>> map = new HashMap<>();

        //员工数量
        List<EchartsData> listEmployeeNumber = new ArrayList<>();
        //营业收入
        List<EchartsData> listTotalOutput = new ArrayList<>();
        List<Company> companies = companyService.findAll();

        //组装数据
        for (Company company:companies)
        {
            listEmployeeNumber.add(new EchartsData(company.getComname(),company.getEmployeenumber()));
            listTotalOutput.add(new EchartsData(company.getComname(),company.getTotaloutput()));

        }

        //赋值给map集合
        map.put("listperson",listEmployeeNumber);
        map.put("listoutput",listTotalOutput);

        return map;
    }




    //**********验证***********

    @RequestMapping("/validateEmail")
    @ResponseBody
    public String  validateEmail(@RequestParam String contactemail)
    {
      boolean blStatus = companyService.validateEmail(contactemail);
      JSONObject result = new JSONObject();
      result.put("valid",blStatus);
      return result.toJSONString();
    }


}
