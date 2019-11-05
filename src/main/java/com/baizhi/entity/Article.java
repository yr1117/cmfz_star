package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Article {

    @Id
    private String id;
    private String title;
    private String author;
    private String brief;
    private String content;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    private Date createDate;
}
