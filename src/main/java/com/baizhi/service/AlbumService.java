package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {

    //查所有
    public Map<String,Object> queryAll(Integer page, Integer rows);

    void update(Album album);

    String save(Album album);

    public List<Album> queryByName();

    Album selectOne(String id);
}
