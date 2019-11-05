package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Star {
    @Id
    private String id;
    private String nickname;
    private String realname;
    private String sex;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date bir;
    private String photo;
}
