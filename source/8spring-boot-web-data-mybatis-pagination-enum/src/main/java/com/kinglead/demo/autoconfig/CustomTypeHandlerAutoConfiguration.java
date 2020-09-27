package com.kinglead.demo.autoconfig;


import com.kinglead.demo.entity.MybatisProperties;
import com.kinglead.demo.enums.BaseEnum;
import com.kinglead.demo.autoconfig.BaseEnumHandler;
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
//                if (mybatisProperties.isRegisterIntegerArrayTypeHandler()) {
//                    typeHandlerRegistry.register(new IntegerArrayTypeHandler());
//                }
//                if (mybatisProperties.isRegisterLongArrayTypeHandler()) {
//                    typeHandlerRegistry.register(new LongArrayTypeHandler());
//                }
//                if (mybatisProperties.isRegisterStringArrayTypeHandler()) {
//                    typeHandlerRegistry.register(new StringArrayTypeHandler());
//                }
                // 注意：以下集合类型因泛型会类型信息擦除，所以不能自动匹配类型，比如：IntegerListTypeHandler中List<Integer>,注册后的类型是：java.util.List
//                typeHandlerRegistry.register(new IntegerListTypeHandler());
//                typeHandlerRegistry.register(new IntegerSetTypeHandler());
//                typeHandlerRegistry.register(new LongListTypeHandler());
//                typeHandlerRegistry.register(new LongSetTypeHandler());
//                typeHandlerRegistry.register(new StringListTypeHandler());
//                typeHandlerRegistry.register(new StringSetTypeHandler());
//                typeHandlerRegistry.register(new MapJsonTypeHandler());
            }
        };
    }
}
