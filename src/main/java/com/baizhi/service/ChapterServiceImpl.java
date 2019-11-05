package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("chapterService")
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    @Override
    public Map<String, Object> queryAll(String albumId, Integer page, Integer rows) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, rowBounds);
        int count = chapterDao.selectCount(chapter);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",chapters);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public String save(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setCreateDate(new Date());
        int result = chapterDao.insertSelective(chapter);
        if(result == 0 ){
            throw new RuntimeException("添加失败");
        }
        return chapter.getId();
    }

    @Override
    public void update(Chapter chapter) {
        int result = chapterDao.updateByPrimaryKeySelective(chapter);
        if(result == 0){
            throw new RuntimeException("修改章节失败");
        }

    }

}
