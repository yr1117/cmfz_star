package com.baizhi.service;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("starService")
@Transactional
public class StarServiceImpl implements StarService {

    @Autowired
    private StarDao starDao;

    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Star star = new Star();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Star> list = starDao.selectByRowBounds(star, rowBounds);
        int count = starDao.selectCount(star);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public void update(Star star) {
        if("".equals(star.getPhoto())){
            star.setPhoto(null);
        }
        starDao.updateByPrimaryKeySelective(star);
    }

    @Override
    public String insert(Star star) {
        star.setId(UUID.randomUUID().toString());
        int result = starDao.insert(star);
        if(result == 1){
            return star.getId();
        }
        throw new RuntimeException("添加失败");
    }

    @Override
    public Star queryOne(String id) {
        return starDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Star> queryStarName() {
        Star star = new Star();
        List<Star> stars = starDao.select(star);
        return stars;
    }
}
