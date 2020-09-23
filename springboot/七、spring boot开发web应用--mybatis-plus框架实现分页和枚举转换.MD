列表查询分页是很常用的一个功能，既然项目使用了mybatis-plus，那得聊聊mybatis-plus是如何实现分页和枚举转换的。mybatis-plus的框架简单使用可以查看第六节《[spring boot开发web应用--mybatis-plus为简化而生](https://www.cnblogs.com/kinglead/p/13704678.html)》，这里不在赘述。

### 1.实现分页

（1）.添加Bean配置

​	在项目启动类中添加@Bean配置

```java
package com.kinglead.demo;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.kinglead.demo.mapper")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    /**
     * mybatis-plus分页配置
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
    
}
```

（2）.在ServiceImpl实现方法中加入分页实现Page

```java
package com.kinglead.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kinglead.demo.domain.User;
import com.kinglead.demo.mapper.UserMapper;
import com.kinglead.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> queryUserList() {
        //new分页模型:这里硬编码，第一页，每页5条
        //current:当前页
        //size:每页显示条数
        Page<User> page = new Page<User>(1,5);
        //使用mybatis-plus公共查询接口完成列表查询
        Page<User> userPage = userMapper.selectPage(page, null);
        return userPage.getRecords();
    }

}
```

（3）.测试:第一页5条数据

​	**数据库数据**

![image-20200923180655568](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200923180655568.png)

​	**查询列表**

​		![image-20200923180802182](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200923180802182.png)

### 2.枚举转换

​	(1).创建枚举	

主要用到2个注解@EnumValue和@JsonValue。@EnumValue注明写入数据库字段值；@JsonValue是Jackson方式带的注解，注明response返回值。

```java
package com.kinglead.demo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderEnum {
    MALE("男","MALE"),
    FEMALE("女","FEMALE");


    private final String code;

    //@EnumValue注明写入数据库字段值
    @EnumValue
    private final String value;

    GenderEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    //Jackson方式：@JsonValue注明response返回值
    @JsonValue
    public String getDescription() {
        return this.code;
    }
}
```

（2）.实体类User

```java
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
```

（3）.测试：性别字段在数据库里存的是“MALE”和“FEMALE”，查询出来的是“男”和“女”

**数据库数据**

![image-20200923180655568](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200923180655568.png)

​	**查询列表**

![image-20200923180802182](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200923180802182.png)


源码github地址：https://github.com/kinglead2012/myblog

