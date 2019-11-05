package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Banner {
    @Id
    private String id;
    private String name;
    private String cover;
    private String description;
    private String status;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date createDate;

}
