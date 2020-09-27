第七节](https://www.cnblogs.com/kinglead/p/13720115.html)我们讲解了mybatis-plus工具的分页和枚举转换，把原生mybatis框架的分页和枚举转换漏讲了，这一节我们把这一块内容不上。

### 1.实现分页

​	mybatis框架通常会使用[Mybatis-PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)分页插件实现分页。

​	首先来看一下Mybatis-PageHelper的用法，https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md中列出非常多用法：

```java
//第一种，RowBounds方式的调用
List<Country> list = sqlSession.selectList("x.y.selectIf", null, new RowBounds(0, 10));

//第二种，Mapper接口方式的调用，推荐这种使用方式。
PageHelper.startPage(1, 10);
List<Country> list = countryMapper.selectIf(1);

//第三种，Mapper接口方式的调用，推荐这种使用方式。
PageHelper.offsetPage(1, 10);
List<Country> list = countryMapper.selectIf(1);

//第四种，参数方法调用
//存在以下 Mapper 接口方法，你不需要在 xml 处理后两个参数
public interface CountryMapper {
    List<Country> selectByPageNumSize(
            @Param("user") User user,
            @Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);
}
//配置supportMethodsArguments=true
//在代码中直接调用：
List<Country> list = countryMapper.selectByPageNumSize(user, 1, 10);
```

(1).引入依赖

```xml
<!--pagehelper分页插件-->
<dependency>
   <groupId>com.github.pagehelper</groupId>
   <artifactId>pagehelper-spring-boot-starter</artifactId>
   <version>1.2.5</version>
</dependency>
```

(2).用法

**方式一：Mapper中使用PageRowBounds**

​	自定义分页信息类PageRowInfo

```java
package com.kinglead.demo.entity;

import com.github.pagehelper.PageRowBounds;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class PageRowInfo {

    /**
     * 页数
     */
    @Range(min = 1, message = "最小是第一页")
    private int pageNum = 1;

    /**
     * 每页条数
     */
    @Range(min = 5, message = "每页最小5条")
    private int pageSize = 1;

    public int getPageNum() {
        return (pageNum - 1) * pageSize;
    }

    public int getCurPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * 初始化 PageRowBounds
     */
    public PageRowBounds toPageRowBounds() {
        return new PageRowBounds(getPageNum(), getPageSize());
    }
}
```

VO类继承PageRowInfo

```java
package com.kinglead.demo.vo;

import com.kinglead.demo.entity.PageRowInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserVo extends PageRowInfo implements Serializable {
    private static final long serialVersionUID = -21070736985722463L;
    /**
    * 用户名
    */
    @NotNull(message = "用户名不能为空")
    @Size(max = 20, message = "用户名长度不能大于20")
    private String name;

}
```

Service调用Mapper时

```java
/**
 * 查询用户列表
 */
@Override
public List<User> queryAll(UserVo userVo) {
    return userMapper.queryAll(userVo, userVo.toPageRowBounds());
}
```

Mapper方法

```java
/**
 * 查询用户列表
 */
List<User> queryAll(UserVo userVo, PageRowBounds pageRowBounds);
```

Controller

```java
/**
 * 查询用户列表
 */
@GetMapping("/userList")
public List<User> queryAll(){
    UserVo userVo = new UserVo();
    userVo.setPageNum(1);
    userVo.setPageSize(10);
    //查询用户列表
    List<User> userList = userService.queryAll(userVo);
    //返回
    return userList;
}
```

**方法二：调用接口方法前使用startPage**

  相对方法一，只需要修改Service对应的方法

```java
/**
 * 查询用户列表
 */
@Override
public List<User> queryAll(UserVo userVo) {
    PageHelper.startPage(userVo.getPageNum(),userVo.getPageSize());
    return userMapper.queryAll(userVo);
}
```

**方法三：配置文件中打开supportMethodsArguments参数**

application.yml

```yaml
#配置分页插件pagehelper
pagehelper:
  helperDialect: mysql  #设置数据库
  reasonable: true  #分页参数合理化，默认是false.当启用合理化时，如果pageNum>pageSize,默认会查询最后一页的数据。禁用合理化后，当pageNum>pageSize会返回空数据
  supportMethodsArguments: true #如果入参中有分页参数，是否自动分页，默认是false
```

Service方法中就不用使用PageHelper.startPage(userVo.getPageNum(),userVo.getPageSize())了。

 

```java
    /**
     * 查询用户列表
     */
    @Override
    public List<User> queryAll(UserVo userVo) {
        //PageHelper.startPage(userVo.getPageNum(),userVo.getPageSize());
        return userMapper.queryAll(userVo);
    }
```

(3)小结：

​	使用pagehelper实现分页还有很多种方法，以上3中方法是笔者整理的项目中常用的方式。

​	使用方法太多不一定是好事，因为后期维护的人也要熟悉所有用法，因为可会遇到每个人用法都不一样的情况。其实只有方法一用法才是正真需要的，官网推荐的是方法二，但感觉不是很好，原因如下几点(引用公司架构部话术)：

- 首先Mapper中的方法省略了分页所需要的参数，代码简化了，但使代码更不好理解，可读性变差，语文更不清晰；

- PageHelper.startPage(1, 10);这个方法可以在任务地方调用，有的人喜欢在Controller层调用，有的人会在Service调用；后期维护人员要定位问题时，需要找到所有相关的地方；

- 因为它是通过ThreadLocal传参，在异步处理时就无法获得分页参数；

- 在使用AOP时，无法获取分页相关的参数；

- 在使用缓存时，需要把count和list 两个分成两次执行，对于有经验的人都知道，当表中的数据量达到一定级别后，count查询的性能也是会越来越差，在使用缓存时，还是要减少执行count的次数。

- 分页查询需要考虑兼容以下两种方式：

  ​	a.先执行count获取符合查询条件的总记录数，同时获取当前页的数据；这种一般用于数据量小或管理后台中；

  ​	b.“上拉式分页”，这种分页方式在APP中非常常见，列表往上拉时，加载下页的数据，当拉取到的数据量小于pageSize时，达到最后一页，不再往下拉。此事方式省去了count查询，性能更优，所以常用于APP这种互联网应用中。

  

**另外：如果需要返回更多的分页信息，可以返回封装好的包装类PageInfo<T>**

```java
/**
 * 查询用户列表
 */
@Override
public PageInfo<User> queryAll(UserVo userVo) {
    PageHelper.startPage(userVo.getPageNum(),userVo.getPageSize());
    List<User> userList = userMapper.queryAll(userVo);
    return new PageInfo<>(userList);
}
```

### 2.枚举转换

​	我们做项目时，经常会遇到字段值是枚举类型，如果用户的性别，希望数据库的保存值，和java代码使用的值区别开来，这样我们就需要考虑枚举类型的转换。

​	(1).基类枚举接口定义

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
    String getDisplayName();
}
```

(2)枚举的定义，本例以用户的性别为例

**自定义枚举实现**BaseEnum

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

(3).自定义BaseEnumHandler，继承BaseTypeHandler

```java
package com.kinglead.demo.autoconfig;

import com.kinglead.demo.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

@Slf4j
public class BaseEnumHandler<E extends Enum<E> & BaseEnum<K>, K> extends BaseTypeHandler<E> {

    private final EnumSet<E> enumSet;

    public BaseEnumHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        // this.type = type;
        this.enumSet = EnumSet.allOf(type);
        if (enumSet == null || enumSet.isEmpty()) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        K code = parameter.getCode();
        ps.setObject(i, code);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        /**
         * wasNull，必须放到get方法后面使用
         */
        if (rs.wasNull()) {
            return null;
        }
        return toEnum(code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        /**
         *  wasNull，必须放到get方法后面使用
         */
        if (rs.wasNull()) {
            return null;
        }
        return toEnum(code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        /**
         * wasNull，必须放到get方法后面使用
         */
        if (cs.wasNull()) {
            return null;
        }
        return toEnum(code);
    }

    private E toEnum(String code) {
        for (E e : enumSet) {
            K k = e.getCode();
            if (k != null) {
                if (k.toString().equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }
}
```

(4).自定义配置类

```java
package com.kinglead.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = MybatisProperties.PREFIX)
public class MybatisProperties {

    /**
     * properties前缀
     */
    public static final String PREFIX = "kinglead.mybatis";

    /**
     * 是否注册 BaseEnumHandler
     */
    private boolean registerBaseEnumHandler = true;

    /**
     * 实现BaseEnum 的所在包
     */
    private String[] baseEnumPackages;

    /**
     * 是否注册 IntegerArrayTypeHandler
     */
    private boolean registerIntegerArrayTypeHandler = true;

    /**
     * 是否注册 LongArrayTypeHandler
     */
    private boolean registerLongArrayTypeHandler = true;

    /**
     * 是否注册 StringArrayTypeHandler
     */
    private boolean registerStringArrayTypeHandler = true;

    /**
     * 多数据源的情况下配置 key 为 sqlSessionFactoryBeanName
     */
    private Map<String, MultiMybatisProperties> multi;

    private MonitorProperties monitor = new MonitorProperties();

    @Getter
    @Setter
    public static class MultiMybatisProperties extends org.mybatis.spring.boot.autoconfigure.MybatisProperties {

        private String dataSourceBeanName;

        private String[] mapperInterfacePackage;
    }

    @Getter
    @Setter
    public static class MonitorProperties {

        /**
         * 是否打印慢日志
         */
        private boolean printSlowLog = true;

        /**
         * 当执行时间超过此值时打印错误日志
         */
        private int slowSql = 1500;

        /**
         * 是否打印查询结果超过一定条数日志，因为查询数据量过多会造成内存溢出
         */
        private boolean printTooLargeResultLog = true;

        /**
         * 当查询结果超过此设置值时打印错误日志
         */
        private int tooLargeResult = 5000;

        /**
         * 添加、修改、删除操作所影响的行数
         */
        private int rowCount = 5000;

    }

}
```

(5).添加配置

```yaml
kinglead:
  mybatis:
    # 设置 BaseEnum 实现的枚举类所在的package，设置完这个后，mapper.xml中不需要再手动指定TypeHandler
    baseEnumPackages: com.kinglead.demo.enums
```

(6).对Mybatis 自动配置进行扩展,增加对BaseEnum的TypeHandler注册

```java
package com.kinglead.demo.autoconfig;


import com.kinglead.demo.entity.MybatisProperties;
import com.kinglead.demo.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * 对Mybatis 自动配置进行扩展<br/>
 * 增加对BaseEnum的TypeHandler注册<br/>
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureBefore(MybatisAutoConfiguration.class)
public class CustomTypeHandlerAutoConfiguration {

    @Autowired
    private MybatisProperties mybatisProperties;

    @Bean
    public ConfigurationCustomizer baseEnumTypeHandlerConfigurationCustomizer() {

        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                String[] baseEnumPackages = mybatisProperties.getBaseEnumPackages();
                TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
                if (mybatisProperties.isRegisterBaseEnumHandler() && null != baseEnumPackages && baseEnumPackages.length > 0) {
                    configuration.setVfsImpl(SpringBootVFS.class);
                    for (String packageName : baseEnumPackages) {
                        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
                        resolverUtil.find(new ResolverUtil.IsA(BaseEnum.class), packageName);
                        Set<Class<? extends Class<?>>> handlerSet = resolverUtil.getClasses();
                        for (Class<?> type : handlerSet) {
                            if (type == BaseEnum.class) {
                                continue;
                            }
                            if (type.isEnum()) {
                                log.debug("register " + type.getName() + " use " + BaseEnumHandler.class.getName());
                                typeHandlerRegistry.register(type, BaseEnumHandler.class);
                            }
                        }
                    }
                }
            }
        };
    }
}
```

完成以上6步操作，即可对枚举类型进行转换。

**但是，完成上面的6步，只能将数据库的字段值转化成枚举名称返回，如果接口希望返回枚举的描述值，可以利用jackson的@JsonValue注解指明返回报文的取值，如下对BaseEnum的修改**

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

