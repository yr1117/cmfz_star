package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Chapter {
    @Id
    private String id;
    private String name;//章节名称
    private String size;//大小
    private String duration;//时长
    private String singer;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    private Date createDate;
    private String albumId;
 }
