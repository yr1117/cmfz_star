package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(classes = CmfzStarApplication.class)
class CmfzStarApplicationTests {
    @Autowired
    private AdminDao adminDao;

    @Test
    public void contextLoads() {
        //查所有
        //adminDao.selectAll().forEach(a -> System.out.println(a));

        //查所有带参数
        /*Admin admin = new Admin();
        adminDao.select(admin).forEach(l-> System.out.println(l));*/

        //根据id查一个人
        //System.out.println(adminDao.selectByPrimaryKey("1"));

        /*Example example = new Example(Admin.class);
        //根据username查一个人
        example.createCriteria().andEqualTo("username");
        //根据username对应的值查一个人
        example.createCriteria().andEqualTo("username","admin");
        System.out.println(adminDao.selectByExample(example));
*/
        //查一个
        /*Admin admin = new Admin();
        System.out.println(adminDao.selectOne(admin));*/

        //添加
        Admin admin = new Admin();
        admin.setId(UUID.randomUUID().toString()).setUsername("haha").setPassword("123123");
        adminDao.insert(admin);
    }
}
