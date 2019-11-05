package com.baizhi.controller;

import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("star")
public class StarController {

    @Autowired
    private StarService starService;

    //查所有
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){
        Map<String, Object> map = starService.queryAll(page,rows);
        return map;
    }

    //添加
    @RequestMapping("edit")
    public Map<String,Object> edit(Star star,String oper){
        Map<String,Object> map = new HashMap<>();
        try {
            if(oper.equals("add")){
                String id = starService.insert(star);
                map.put("status",true);
                map.put("message",id);
            }

            if(oper.equals("edit")){
                starService.update(star);
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
    public Map<String,Object> upload(HttpServletRequest request, String id, MultipartFile photo){
        Map<String,Object> map = new HashMap<>();
        try {
            //获取文件夹
            String realPath = request.getServletContext().getRealPath("star/image");
            //文件夹名
            String filename = photo.getOriginalFilename();
            //上传文件
            photo.transferTo(new File(realPath,filename));
            //修改Star中的photo属性
            Star star = new Star();
            star.setId(id);
            star.setPhoto(filename);
            starService.update(star);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("queryStarName")
    public void queryStarName(HttpServletResponse response) throws IOException {
        List<Star> stars = starService.queryStarName();
        StringBuilder sb = new StringBuilder();
        sb.append("<select>");
        stars.forEach(star -> {
            //每次遍历出一个明星对象就创建一个option
            sb.append("<option value=").append(star.getId()).append(">").append(star.getRealname()).append("</option>");
        });
        sb.append("</select>");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(sb.toString());
    }
}
