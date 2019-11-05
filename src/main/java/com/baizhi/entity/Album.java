package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Album {
    @Id
    private String id;
    private String title;//标题
    private String count;//章节数
    private String cover;//封面
    private Double score;//得分
    private String brief;//简介
    @JsonFormat(pattern = "yyyy/MM/dd",timezone="GMT+8")
    private Date createDate;
    private String starId;
    private Star star;

}
