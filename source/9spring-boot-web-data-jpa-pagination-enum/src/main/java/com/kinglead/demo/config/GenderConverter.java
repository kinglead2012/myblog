package com.kinglead.demo.config;

import com.kinglead.demo.enums.GenderEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author kinglead
 * @date 2020-09-28 下午 17:53
 * @describe {请写具体描述}
 */
@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<GenderEnum,String> {

    @Override
    public String convertToDatabaseColumn(GenderEnum attribute) {
        return attribute.getCode();
    }

    @Override
    public GenderEnum convertToEntityAttribute(String dbData) {
        return GenderEnum.valueOf(dbData);
    }
}
