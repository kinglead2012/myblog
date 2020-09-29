package com.kinglead.demo.entity;

import com.kinglead.demo.enums.GenderEnum;
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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "姓名")  //指明字段
    private String name;

    @Column(name = "age", columnDefinition = "年龄")  //指明字段
    private Long age;

    @Column(name = "email", columnDefinition = "邮箱") //指明字段
    private String email;

    @Column(name = "gender", columnDefinition = "性别") //指明字段
    private GenderEnum gender;

}