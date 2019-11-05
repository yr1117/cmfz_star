package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("Admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //登录
    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(Admin admin, String inputCode, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();
        try {
            adminService.Login(admin, inputCode, request);
            result.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status",false);
            result.put("message",e.getMessage());
        }
        return result;
    }

    //验证码
    @RequestMapping("getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取验证码
        String code = SecurityCode.getSecurityCode();
        request.getSession().setAttribute("code",code);
        //获取图片
        BufferedImage image = SecurityImage.createImage(code);
        //设置图片类型
        response.setContentType("img/png");
        //输出
        ImageIO.write(image,"png",response.getOutputStream());
    }

    //安全退出
    @RequestMapping("exit")
    public String exit(HttpServletRequest request){
        //移除登录标记
        request.getSession().removeAttribute("login");
        //清除session
        request.getSession().invalidate();
        return "redirect:/login/login.jsp";
    }

    //手机验证码
    @RequestMapping("PhoneMessage")
    @ResponseBody
    public void PhoneMessage(String phoneCode,String phone,HttpServletRequest request){
        try {
            Random random = new Random();
            for (int i = 1 ; i < 6 ; i++){
                phoneCode += random.nextInt(10);
            }
            adminService.sendMessage(phoneCode,phone);
            System.out.println("========"+phone);
            request.getSession().setAttribute("phonecode",phoneCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
