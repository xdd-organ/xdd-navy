package com.java.xdd.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义typeHandler
 * 由于自定义的TypeHandler在定义时已经通过注解指定了jdbcType, 所以此处不用再配置jdbcType
 */
public class DateToStringTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        System.out.println("设置时间参数：" + parameter);
        ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        Timestamp date = rs.getTimestamp(columnName);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date.getTime()));

        System.out.println("获取时间返回值1：" + string);
        return format;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String string = rs.getString(columnIndex);
        Timestamp date = rs.getTimestamp(columnIndex);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date.getTime()));

        System.out.println("获取时间返回值2：" + string);
        return format;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String string = cs.getString(columnIndex);
        Timestamp date = cs.getTimestamp(columnIndex);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date.getTime()));

        System.out.println("获取时间返回值3：" + string);
        return format;
    }
}
