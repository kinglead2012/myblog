第三节和第四节分别介绍了传统的JDBC方式和简单的JPA方式，但这两种方式在行业内使用的不多，更常见使用的是mybatis，下面我们就再次改造web应用，让其使用更为常见的mybatis方式。

	### 1.依赖引入

​	要使用mybatis框架，首先要引入依赖，但spring boot data没有包含mybatis的依赖，那我们该如何查找mybatis的依赖呢？我们可以打开maven中央仓库官网[https://mvnrepository.com](https://mvnrepository.com/)，搜索mybatis进行查询，如下：

![image-20200917104109571](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200917104109571.png)

找到你需要的内容MyBatis Spring Boot Starter，点击进去

![image-20200917104254758](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200917104254758.png)

进去后，会列出所有版本信息，选择你想要的版本（一般不要选择最新的，而是选择使用人数最多的）点击进去

![image-20200917104503160](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200917104503160.png)

进去后，可以根据使用的是maven、Gradle还是其它来查看依赖，我们的项目使用的是maven，所有拿到的依赖是：

```xml
<!--mybatis-->
<dependency>
   <groupId>org.mybatis.spring.boot</groupId>
   <artifactId>mybatis-spring-boot-starter</artifactId>
   <version>2.1.1</version>
</dependency>
```

### 2.创建mybatis的xml文件

#### (1)在resources在创建mapper文件夹，用于存放xml文件

#### (2)配置mapper路径

在配置文件application.yml中添加下面配置（如果Mapper.java和Mapper.xml在同一个包下，可以不用设置）

```yaml
mybatis:
  # 如果Mapper.java和Mapper.xml在同一个包下，可以不用设置
  mapper-locations: classpath:mapper/*.xml
```

#### (3)启动类添加Mapper全局扫描(如果不添加，也可以单独在指定的Dao类加@Mapper注解)

```java
package com.kinglead.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication注解代表这是一个spring boot应用
//它是一个组合注解
//@SpringBootConfiguration注解将该类声明为配置类，相当于@Configuration的特殊形式
//@EnableAutoConfiguration启动spring boot的自动配置
//@ComponentScan启动组件扫描：将通过@Component、@Controller、@Service这样注解的类，注册为spring应用上下文的组件
@MapperScan("com.kinglead.demo.dao") //Mapper全局扫描
@SpringBootApplication
public class SpringInitDemoApplication {

   /**
    * @param args 命令行参数
    */
   public static void main(String[] args) {
      SpringApplication.run(SpringInitDemoApplication.class, args);
   }

}
```

#### (4)创建xml文件mapper文件夹下

**UserDao.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.kinglead.demo.dao.UserDao">

    <resultMap type="com.kinglead.demo.entity.User" id="TUserMap">
        <result property="id" column="id" jdbcType="INTEGER"/>
        <result property="name" column="name" jdbcType="VARCHAR"/>
        <result property="password" column="password" jdbcType="VARCHAR"/>
    </resultMap>


    <!--通过实体作为筛选条件查询-->
    <select id="queryAll" resultMap="TUserMap">
        select
          id, name, password
        from t_user
        <where>
            <if test="id != null">
                and id = #{id}
            </if>
            <if test="name != null and name != ''">
                and name = #{name}
            </if>
            <if test="password != null and password != ''">
                and password = #{password}
            </if>
        </where>
    </select>

    <!--新增所有列-->
    <insert id="insert" keyProperty="id" useGeneratedKeys="true">
        insert into t_user(name, password)
        values (#{name}, #{password})
    </insert>

    <!--根据用户名和密码查询-->
    <select id="findByNameAndPassword" resultMap="TUserMap">
        select id, name, password
        from t_user
        <where>
            <if test="name != null and name != ''">
                and name = #{name}
            </if>
            <if test="password != null and password != ''">
                and password = #{password}
            </if>
        </where>
    </select>

</mapper>
```

### 3.改造Dao层，删除UserRepository,新增UserDao

```java
package com.kinglead.demo.dao;

import com.kinglead.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {

    /**
     * 新增用户
     */
    int insert(User user);

    /**
     * 通过用户名和密码查询用户
     */
    User findByNameAndPassword(@Param("name")String name, @Param("password") String password);

    /**
     * 查询用户列表
     */
    List<User> queryAll(User user);
}
```

### 4.改造UserServiceImpl实现类，注入UserDao

```java
package com.kinglead.demo.service.impl;

import com.kinglead.demo.dao.UserDao;
import com.kinglead.demo.entity.User;
import com.kinglead.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 新增用户
     */
    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    /**
     * 通过用户名和密码查询用户
     */
    @Override
    public User queryByNameAndPassword(User user) {
        return userDao.findByNameAndPassword(user.getName(), user.getPassword());
    }

    /**
     * 查询用户列表
     */
    @Override
    public List<User> queryAll() {
        return userDao.queryAll(null);
    }

}
```

### 5.html页面和Controller保持不变

### 6.测试

访问请求http://localhost:8080/user/userList，同样能返回数据

![img](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_16003137027240.png)

### 总结

​	mybatis是现在行业内最流行框架，也是项目使用最多的框架，大家在实际项目技术选型时，可以大胆的选择mybatis。另外，由于mybatis在编写mapper.xml和dao文件上很费时，为了让开发人员专注于业务处理，现有很多IDEA插件能自动生成这些文件，如：EasyCode，mybatis generate等。笔者使用的是EasyCode，大家根据自己的喜好可以任选一款，至于工具的使用在这就不介绍了，后面有机会单独发文介绍。

​	源码地址：https://github.com/kinglead2012/myblog
