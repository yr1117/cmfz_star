package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.Map;

public interface BannerService {

    //查所有
    public Map<String,Object> queryAll(Integer page,Integer rows);

    //添加
    public String save(Banner banner);

    //修改
    public void update(Banner banner);

    //删除
    public void delete(String id);
}
