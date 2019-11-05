package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
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
@RequestMapping("album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    //查所有
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){
        Map<String, Object> map = albumService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String,Object> edit(Album album ,String oper){
        Map<String,Object> map = new HashMap<>();
        try {
            if(oper.equals("add")){
                String id = albumService.save(album);
                map.put("status",true);
                map.put("message",id);
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
    public Map<String,Object> upload(HttpServletRequest request, String id, MultipartFile cover){
        Map<String,Object> map = new HashMap<>();
        try {
            //获取文件夹
            //文件夹名
            String filename = cover.getOriginalFilename();
            //上传文件
            cover.transferTo(new File(request.getServletContext().getRealPath("album/image"),filename));
            //修改Star中的photo属性
           Album album = new Album();
           album.setId(id);
           album.setCover(filename);
           albumService.update(album);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("queryByName")
    public void queryByName(HttpServletResponse response){
        List<Album> albums = albumService.queryByName();
        StringBuilder builder = new StringBuilder();
        builder.append("<select>");
        albums.forEach(album -> {
            builder.append("<option value="+album.getId());
        });
    }
}
