package com.study.mybatis.source2.result;


import com.study.mybatis.source2.config.GpConfiguration;
import com.study.mybatis.source2.config.MapperRegistory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 *
 *
 */
public class ResultSetHandler {
    private final GpConfiguration configuration;

    public ResultSetHandler(GpConfiguration configuration) {
        this.configuration = configuration;
    }

    //这里我们自己实现了一个更简单的方式，也获取结果集并解析的方式  mybtis实现结果和实体属性映射复制是通过typeHandle来判断呢当前获取的值是
    //什么类型，然后用rs的getInt()等等方式复制
    public <E> E handle(PreparedStatement pstmt, MapperRegistory.MapperData mapperData) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object resultObj = new DefaultObjectFactory().create(mapperData.getType());
        ResultSet rs = pstmt.getResultSet();
        if (rs.next()) {

            for (Field field : resultObj.getClass().getDeclaredFields()) {
                setValue(resultObj, field, rs );//这里利用field是通过反射对每个属性都做了一次setValue操作
            }
        }
        return (E) resultObj;
    }

    private void setValue(Object resultObj, Field field, ResultSet rs) throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        Method setMethod = resultObj.getClass().getMethod("set" + upperCapital(field.getName()), field.getType());
        setMethod.invoke(resultObj, getResult(field,rs));
    }

    private Object getResult(Field field, ResultSet rs) throws SQLException {
        //TODO type handles
        Class<?> type = field.getType();
        if(Integer.class == type){
            return rs.getInt(field.getName());
        }
        if(String.class == type){
            return rs.getString(field.getName());
        }
        return rs.getString(field.getName());
    }

    private String upperCapital(String name) {
        String first = name.substring(0, 1);
        String tail = name.substring(1);
        return first.toUpperCase() + tail;
    }
}
