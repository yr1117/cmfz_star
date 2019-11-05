package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {

    public Map<String,Object> queryAll(Integer page, Integer rows);

    public String save(Article article);

    public void update(Article article);

    public void delete(String id);

}
