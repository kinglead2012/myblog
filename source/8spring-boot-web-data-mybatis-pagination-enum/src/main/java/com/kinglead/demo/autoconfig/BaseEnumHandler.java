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

/**
 * @author kinglead
 * @date 2020-09-27 下午 14:03
 * @describe {请写具体描述}
 */
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
