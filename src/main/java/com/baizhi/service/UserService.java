package com.baizhi.service;

import com.baizhi.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {

    public Map<String ,Object> queryAll(String starId,Integer page, Integer rows);

    //查询所有用户
    public Map<String,Object> queryAll(Integer page,Integer rows);

    void update(User user);

    String save(User user);

    void delete(String id);

    List<User> export(HttpServletRequest request);

    public Integer[] queryByDate(String sex);
}
