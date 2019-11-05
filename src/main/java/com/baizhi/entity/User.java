package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class User {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "名称")
    private String username;
    private String password;
    private String salt; //盐
    @Excel(name = "昵称")
    private String nickname;
    @Excel(name = "电话")
    private String phone;
    @Excel(name = "城市")
    private String city;
    @Excel(name = "签名")
    private String sign; //签名
    @Excel(name = "头像",type=2)
    private String photo;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "日期",format = "yyyy-MM-dd")
    private Date createDate;
    @Excel(name = "省份")
    private String province;
    private String starId;
}
