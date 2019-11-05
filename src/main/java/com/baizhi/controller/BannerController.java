package com.baizhi.controller;

import com.alibaba.druid.util.StringUtils;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    //查所有
    @RequestMapping("queryAll")
    @ResponseBody
    public Map<String,Object> queryAll(Integer page,Integer rows){
        Map<String, Object> map = bannerService.queryAll(page, rows);
        return map;
    }

    //添加
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(Banner banner,String oper){
        Map<String,Object> map = new HashMap<>();
        try {
            if(StringUtils.equals(oper,"add")) {
                String id = bannerService.save(banner);
                map.put("status",true);
                map.put("message",id);
            }
            if(StringUtils.equals(oper,"edit")){
                bannerService.update(banner);
            }
            if(StringUtils.equals(oper,"del")){
                bannerService.delete(banner.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    //文件上传
    @RequestMapping("upload")
    public Map<String,Object> upload(String id, MultipartFile cover, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            //获取要上传的文件夹
            String realPath = request.getServletContext().getRealPath("banner/img");
            String filename = cover.getOriginalFilename();
            //上传文件
            cover.transferTo(new File(realPath,filename));
            //修改banner对象中的cover属性
            Banner banner = new Banner();
            banner.setId(id);
            banner.setCover(filename);
            bannerService.update(banner);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
