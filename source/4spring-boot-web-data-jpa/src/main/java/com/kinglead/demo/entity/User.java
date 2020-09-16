package com.kinglead.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data //添加getter、setter方法
@NoArgsConstructor   //无参构造函数
@AllArgsConstructor  //所以参数构造函数
@Entity  //声明为JPA实体
@Table(name = "t_user") //该标注与@Entity标注并列使用，用于指明数据库的表名
public class User implements Serializable {
    private static final long serialVersionUID = -21070736985722463L;

    @Id //指明主键
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "姓名")  //指明字段
    private String name;

    @Column(name = "password", columnDefinition = "密码") //指明字段
    private String password;

}