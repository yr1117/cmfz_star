package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("queryAllByStarId")
    @ResponseBody
    public Map<String,Object> queryAllByStarId(String id,Integer page,Integer rows){
        Map<String, Object> map = userService.queryAll(id,page, rows);
        return map;
    }

    @RequestMapping("queryAll")
    @ResponseBody
    public Map<String,Object> queryAll(Integer page,Integer rows){
        Map<String, Object> map = userService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(User user,String oper){
        Map<String,Object> map = new HashMap<>();
        try {
            if(oper.equals("add")){
                String id = userService.save(user);
                map.put("status",true);
                map.put("message",id);
            }
            if(oper.equals("edit")){
                userService.update(user);
            }
            if(oper.equals("del")){
                userService.delete(user.getId());
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
    public void upload(String id, MultipartFile photo, HttpServletRequest request){
        //获取文件夹名
        try {
            String filename = photo.getOriginalFilename();
            photo.transferTo(new File(request.getServletContext().getRealPath("user/image"),filename));
            //修改user中的photo属性
            User user = new User();
            user.setId(id);
            user.setPhoto(filename);
            userService.update(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("export")
    public void export(HttpServletResponse response,HttpServletRequest request) throws IOException {
        Map<String,Object> map = new HashMap<>();
        //准备数据
        List<User> users = userService.export(request);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有用户信息", "用户"), User.class, users);
        String filename="用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        //处理中文下载名乱码
        try {
            filename = new String(filename.getBytes("gbk"),"iso-8859-1");
            //设置response
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition","attachment;filename="+filename);
            //将文件导出
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
    }

    @RequestMapping("queryByDate")
    @ResponseBody
    public Map<String,Object> queryByDate(){
        Map<String,Object> map = new HashMap<>();
        Integer[] man = userService.queryByDate("男");
        map.put("man",man);
        Integer[] woman = userService.queryByDate("女");
        map.put("woman",woman);
        return map;
    }
}
