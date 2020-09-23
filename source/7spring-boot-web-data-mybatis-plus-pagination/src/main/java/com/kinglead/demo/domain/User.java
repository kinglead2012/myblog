package com.kinglead.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kinglead.demo.enums.GenderEnum;
import lombok.Data;

@Data
@TableName(value = "t_user")  //指明数据库表名
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private GenderEnum gender;
}
