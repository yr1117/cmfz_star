package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){
        Map<String, Object> map = articleService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("add")
    public Map<String,Object> add(Article article){
        Map<String,Object> map = new HashMap<>();
        try {
            String id = articleService.save(article);
            map.put("status",true);
            map.put("message",id);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("edit")
    public Map<String,Object> edit(Article article){
        Map<String,Object> map = new HashMap<>();
        try {
            articleService.update(article);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("del")
    public Map<String,Object> delete(Article article){
        Map<String,Object> map = new HashMap<>();
        try {
            articleService.delete(article.getId());
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }


    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile articleImg, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        File file = new File(request.getServletContext().getRealPath("article/img"),articleImg.getOriginalFilename());
        try {
            articleImg.transferTo(file);
            map.put("error",0);
            map.put("url","http://192.168.1.12:8020/star/article/img/"+articleImg.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
        }
        return map;
    }

    @RequestMapping("browse")
    public Map<String,Object> browse(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        File file = new File(request.getServletContext().getRealPath("article/img"));
        File[] files = file.listFiles();
        List<Object> list = new ArrayList<>();
        for (File file1 : files) {
            Map<String,Object> imgObject = new HashMap<>();
            imgObject.put("is_dir",false);
            imgObject.put("has_file",false);
            imgObject.put("filesize",file1.length());
            imgObject.put("is_photo",true);
            imgObject.put("filetype", FilenameUtils.getExtension(file1.getName()));
            imgObject.put("filename",file1.getName());
            imgObject.put("datetime","2018-06-06 00:36:39");
            list.add(imgObject);
        }
        map.put("file_list",list);
        map.put("total_count",list.size());
        map.put("current_url","http://192.168.1.12:8020/star/article/img/");
        return map;
    }
}
