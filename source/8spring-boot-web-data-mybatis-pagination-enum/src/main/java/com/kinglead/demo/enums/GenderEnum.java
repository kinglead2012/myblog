package com.kinglead.demo.enums;

/**
 * @author kinglead
 * @date 2020-09-27 下午 13:43
 * @describe {请写具体描述}
 */
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
