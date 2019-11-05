package com.baizhi.service;

import com.baizhi.entity.Star;

import java.util.List;
import java.util.Map;

public interface StarService {

    //查所有
    public Map<String,Object> queryAll(Integer page, Integer rows);

    //修改
    public void update(Star star);

    //修改
    public String insert(Star star);

    Star queryOne(String id);

    List<Star> queryStarName();
}
