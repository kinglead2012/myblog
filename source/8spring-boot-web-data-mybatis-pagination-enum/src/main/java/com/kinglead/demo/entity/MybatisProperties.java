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
