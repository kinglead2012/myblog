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
