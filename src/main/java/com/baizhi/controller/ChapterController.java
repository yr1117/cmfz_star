package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(String albumId,Integer page, Integer rows){
        Map<String, Object> map = chapterService.queryAll(albumId,page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String,Object> edit(Chapter chapter,String oper){
        Map<String,Object> map = new HashMap<>();
        try {
            if(oper.equals("add")){
                String id = chapterService.save(chapter);
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



    @RequestMapping("upload")
    public Map<String,Object> upload(String albumId,MultipartFile name, HttpServletRequest request,String id) throws  Exception{
        Map<String,Object> map = new HashMap<>();
        try {
            //处理文件上传
            File file = new File(request.getServletContext().getRealPath("/album/music"), name.getOriginalFilename());
            name.transferTo(file);
            //修改文件名称
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setName(name.getOriginalFilename());
            chapter.setAlbumId(albumId);
            //文件大小

            BigDecimal size = new BigDecimal(name.getSize());
            BigDecimal mod = new BigDecimal(1024);
            BigDecimal realsize = size.divide(mod).divide(mod).setScale(2, BigDecimal.ROUND_UP);
            chapter.setSize(realsize+"MB");
            Encoder encoder = new Encoder();
            long duration = encoder.getInfo(file).getDuration();
            chapter.setDuration(duration/1000/60+":"+duration/1000%60);
            chapterService.update(chapter);
            //修改专辑中的数量
            Album album = albumService.selectOne(albumId);
            album.setCount(album.getCount()+1);
            albumService.update(album);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }

}