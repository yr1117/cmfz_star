package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {

    public void Login(Admin admin, String inputCode, HttpServletRequest request);

    void sendMessage(String phoneCode,String phone) throws Exception;
}
