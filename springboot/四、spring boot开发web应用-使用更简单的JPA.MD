第三节《spring boot开发web应用-使用传统的JDBC》中，我们使用了传统的JDBC方式连接关系型数据库，完成数据持久化操作，文章最后提到“虽然JdbcTemplate将创建连接、创建语句、关闭连接、关闭结果集和sql异常处理等做了很好的封装，但是还有待完善。所以本节内容将web项目进行改造，支持更简单的JPA方式。

​		JPA全称Java Persistence API。JPA通过JDK 5.0注解或XML描述对象－关系表du的映射关系，并将运行期的实体对象持久化到数据库中。

### 1.引入依赖

​	要使用JPA，需先引入相关依赖，JPA是Spring Data的子项目，具体依赖如下

```xml
<!--JPA-->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### 2.实体类改造

```java
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
```

@Entity注解将User类声明为JPA实体

@Table注解指明数据库表名

@Id注解指明表的主键

@GeneratedValue注解指明主键生成策略，strategy= GenerationType.AUTO依赖数据库字段生成ID

@Column注解指明表的字段

注：关于JPA实体还有很多注解，这里就不展开讲解了，后续后时间专门写一篇文章来讲解。感兴趣的同学也可以去官网继续学习：https://spring.io/projects/spring-data-jpa

### 3.声明JPA repository

```java
package com.kinglead.demo.dao;

import com.kinglead.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}
```

UserRepository接口需继承JpaRepository接口，JpaRepository定义了很多CRUD的操作方法。继承JpaRepository需要传入2个参数，第一是持久化的实体类，第二是主键id的类型。如果实现了JpaRepository接口类，那么UserRepository根本不用再编写实现类，因为Spring Data JPA会在项目启动时自动帮我们生成。（另外也可以实现CrudRepository接口来声明JPA repository，它与JpaRepository的最大不同是JpaRepository支持分页查询）

### 4.在repository添加自定义方法

​	除了JpaRepository提供的CRUD才做外，我们也可以自定义方法，如findAllByNameAndPassword方法

```java
package com.kinglead.demo.dao;

import com.kinglead.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNameAndPassword(String name, String password);

}
```

​	在这里自定义的findAllByNameAndPassword方法也不需要自己单独编写实现，只要自定义的方法名遵循规范（领域特定语言Domain-Specific Language DSL），JPA就能自动生成。

​     另外，如果规范约定不支持实际需求，可以在自定义方法上使用@Query注解，编写原生SQL。如findAllByNameAndPassword方法也可以这样声明，当然针对这种简单的查询，不建议这么做，让JPA自动处理就好。

```java
@Query(value = "select id,name,password from t_suer where name=? and password=?", 
       nativeQuery = true)
User findByNameAndPassword(String name, String password);
```

### 5.UserServiceImpl用户服务实现改造

```java
package com.kinglead.demo.service.impl;

import com.kinglead.demo.dao.UserRepository;
import com.kinglead.demo.entity.User;
import com.kinglead.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 新增用户
     */
    @Override
    public User insert(User user) {
        return userRepository.save(user);
    }

    /**
     * 通过用户名和密码查询用户
     */
    @Override
    public User queryByNameAndPassword(User user) {
        return userRepository.findByNameAndPassword(user.getName(), user.getPassword());
    }

    /**
     * 查询用户列表
     */
    @Override
    public List<User> queryAll() {
        return userRepository.findAll();
    }

}
```

### 6.其它

项目涉及到的其它代码不变，保持与第三节一直即可。

测试下http://localhost:8080/user/userList，同样能返回用户列表

![image-20200916143407110](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200916143407110.png)
