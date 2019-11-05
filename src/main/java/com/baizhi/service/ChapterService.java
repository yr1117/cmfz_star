package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {

    public Map<String,Object> queryAll(String albumIb,Integer page,Integer rows);

    String save(Chapter chapter);

    void update(Chapter chapter);
}
