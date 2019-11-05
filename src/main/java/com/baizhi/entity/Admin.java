package com.baizhi.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Admin implements Serializable {

    @Id//表明当前id在表中为主键
    private String id;
    private String username;
    private String password;
}
