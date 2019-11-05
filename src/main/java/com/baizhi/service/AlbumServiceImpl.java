package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("albumService")
@Transactional
public class AlbumServiceImpl implements AlbumService{

    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private StarDao starDao;

    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Album album = new Album();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Album> albums = albumDao.selectByRowBounds(album, rowBounds);
        for (Album a : albums) {
            String starId = a.getStarId();
            Star star = starDao.selectByPrimaryKey(starId);
            a.setStar(star);
        }
        int count = albumDao.selectCount(album);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",albums);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    public String save(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCreateDate(new Date());
        int result = albumDao.insert(album);
        if(result == 1){
            return album.getId();
        }
        throw  new RuntimeException("添加失败");
    }

    @Override
    public List<Album> queryByName(){
        Album album = new Album();
        List<Album> albums = albumDao.select(album);
        return albums;
    }

    @Override
    public Album selectOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }
}
