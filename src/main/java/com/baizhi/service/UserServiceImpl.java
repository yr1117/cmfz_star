package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, Object> queryAll(String starId,Integer page, Integer rows) {
        User user = new User();
        user.setStarId(starId);
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",users);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        User user = new User();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",users);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public String save(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreateDate(new Date());
        int result = userDao.insert(user);
        if(result == 1){
            return user.getId();
        }
        throw new RuntimeException("添加失败");
    }

    @Override
    public void delete(String id) {
        userDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<User> export(HttpServletRequest request) {
        List<User> users = userDao.selectAll();
        users.forEach( user->{
            //根据相对路径获取绝对路径
            String realPath = request.getServletContext().getRealPath("/user/image");
            user.setPhoto(realPath+"/"+user.getPhoto());
        });
        return users;
    }

    @Override
    public Integer[] queryByDate(String sex) {
        Map<String,Integer> map = new HashMap<>();
        //将12个月放进数组中
        Integer[] month ={1,2,3,4,5,6,7,8,9,10,11,12};
        //遍历
        for (int i = 0 ; i<month.length;i++){
            userDao.queryByDate(sex).forEach(u->{
                //如果获取到的月份不为空
                if(u.getMonth()!=null){
                    //就将月份和数量存入map
                    map.put(u.getMonth(),u.getCount());
                }
            });
            //如果没有，就将月份对应的数量设置为空
            map.put(month[i]+"月",0);
        }
        Integer[] c = new Integer[12];
        for (int i = 0 ; i <c.length;i++){
            //根据月份获取数量
            Integer count = map.get(month[i] + "月");
            //将遍历的数量赋值给获取的数量
            c[i] = count;
        }
        //将数量返回
        return c;
    }
}

