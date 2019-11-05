package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Article> articles = articleDao.selectByRowBounds(article, rowBounds);
        int count = articleDao.selectCount(article);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",articles);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public String save(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        int result = articleDao.insertSelective(article);
        if(result == 0){
            throw new RuntimeException("添加失败");
        }
        return article.getId();
    }

    @Override
    public void update(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public void delete(String id) {
        articleDao.deleteByPrimaryKey(id);
    }


}
