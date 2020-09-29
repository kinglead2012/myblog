### 1.实现分页

**方法一：使用Pageable**

​	使用Pageable作为入参传入Repository，返回值用Page<T>接收

**UserRepository**

```java
package com.kinglead.demo.dao;

import com.kinglead.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
```

**UserServiceImpl**

```java
package com.kinglead.demo.service.impl;

import com.kinglead.demo.dao.UserRepository;
import com.kinglead.demo.entity.User;
import com.kinglead.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 查询用户列表
     */
    @Override
    public Page<User> queryAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
```

**UserService**

```java
package com.kinglead.demo.service;

import com.kinglead.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 查询用户列表
     */
    Page<User> queryAll(Pageable pageable);

}
```

**Controller**

```java
/**
 * 查询用户列表
 */
@GetMapping("/userList")
public Page<User> queryAll(){
    //注意，前端页面的页面是从1开始，而JPA是从0开始
    Pageable pageable = PageRequest.of(0,5);
    //查询用户列表
    return userService.queryAll(pageable);
}
```

**方法二：以元模型概念为基础的Criteria 查询方法**

​	UserRepository额外继承JpaSpecificationExecutor<>

```java
package com.kinglead.demo.dao;

import com.kinglead.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
```

​	UserServiceImpl

```java
    /**
     * 查询用户列表
     */
    @Override
    public Page<User> queryAll(String name,String age,Pageable pageable) {
            Specification<User> specification =  (Specification<User>)(root, query, criteriaBuilder) ->{
                List<Predicate> list = new ArrayList<>();
                // 第一个name为User实体对象中的字段，第二个name为参数
                Predicate p1 = criteriaBuilder.equal(root.get("name"),name);
                list.add(p1);
//                if (!age.equals(null)) {
//                    // 此处为查询serverName中含有age的数据
//                    Predicate p2 = criteriaBuilder.like(root.get("age"),"%"+age+"%" );
//                    list.add(p2);
//                }
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            };
            return userRepository.findAll(specification,pageable);
    }
```

​	其它代码如方法一，不用动。



### 2.枚举转换

 **方法一：实体类加@Enumerated注解	

```java
package com.kinglead.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface BaseEnum<K> {

    /**
     * 真正与数据库进行映射的值
     *
     * @return
     */
    K getCode();

    /**
     * 显示的信息
     *
     * @return
     */
    @JsonValue //jackson返回报文response的设置
    String getDisplayName();
}
```

```java
package com.kinglead.demo.enums;

public enum GenderEnum implements BaseEnum<String> {

    MALE("MALE","男"),
    FEMALE("FEMALE","女");


    private final String code;
    private final String displayName;

    GenderEnum(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }}
```

```java
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
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "姓名")  //指明字段
    private String name;

    @Column(name = "age", columnDefinition = "年龄")  //指明字段
    private Long age;

    @Column(name = "email", columnDefinition = "邮箱") //指明字段
    private String email;

    @Column(name = "gender", columnDefinition = "性别") //指明字段
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

}
```

**方法二：使用jpa2.1规范里面的属性转换器**

  将方法一中实体对象User的gender成员变量上的注解@Enumerated(EnumType.STRING)去掉

```java
package com.kinglead.demo.config;

import com.kinglead.demo.enums.GenderEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ColorConverter implements AttributeConverter<GenderEnum,String> {

    @Override
    public String convertToDatabaseColumn(GenderEnum attribute) {
        return attribute.getCode();
    }

    @Override
    public GenderEnum convertToEntityAttribute(String dbData) {
        return GenderEnum.valueOf(dbData);
    }
}
```

**上面的转换器只是针对某一种枚举进行转换，如果写成通用的转换器呢，待后续研究
