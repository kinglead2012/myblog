package com.kinglead.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author kinglead
 * @date 2020-09-27 下午 13:48
 * @describe {请写具体描述}
 */
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
