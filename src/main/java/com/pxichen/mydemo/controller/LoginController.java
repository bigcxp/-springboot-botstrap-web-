package com.pxichen.mydemo.controller;

import com.pxichen.mydemo.logaop.AOPLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录相关的控制层
 */
@Controller
public class LoginController
{
    @RequestMapping(value="/login")
    public String login()
    {
        /**
         * 返回登录页面
         */
        return "login.html";
    }

    @RequestMapping(value = "/index")
    @AOPLog(operatetype="登录",operatedesc="用户登录后台系统")
    public String index()
    {
        /**
         * 返回首页
         */
        return "index.html";
    }
}
