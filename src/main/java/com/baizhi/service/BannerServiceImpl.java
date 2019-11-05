package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("bannerService")
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public Map<String,Object> queryAll(Integer page, Integer rows) {
        Banner banner = new Banner();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Banner> banners = bannerDao.selectByRowBounds(banner,rowBounds);
        int count = bannerDao.selectCount(banner);
        Map<String,Object> maps = new HashMap<>();
        maps.put("page",page);//当前页
        maps.put("rows",banners);//每页显示多少行
        maps.put("total",count%rows==0?count/rows:count/rows+1);//总共多少页
        maps.put("records",count);//总共多少条数据
        return maps;
    }

    @Override
    public String save(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreateDate(new Date());
        int result = bannerDao.insert(banner);
        if(result == 1){
            return banner.getId();
        }
        throw new RuntimeException("添加失败");
    }

    @Override
    public void update(Banner banner) {
        if("".equals(banner.getCover())){
            banner.setCover(null);
        }
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void delete(String id) {
        bannerDao.deleteByPrimaryKey(id);
    }
}

