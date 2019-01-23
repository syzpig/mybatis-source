package com.study.mybatis.source2.mapper;


import com.study.mybatis.source2.config.MapperRegistory;
import com.study.mybatis.source2.session.GpSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *  .
 *
 *
 */
public class MapperProxy<T> implements InvocationHandler {
    private final GpSqlSession sqlSession;
    private final Class<T> mappperInterface;

    public MapperProxy(GpSqlSession gpSqlSession, Class<T> clazz) {
        this.sqlSession = gpSqlSession;
        this.mappperInterface = clazz;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //下面就是体现对开发者友好的通过接口去映射我们的sql的,q去映射到我sql的结果类的集合的，
        // 集合类型的，我的方法的实现与思路思想
        MapperRegistory.MapperData mapperData =
                sqlSession.getConfiguration()
                        .getMapperRegistory()
                        .get(method.getDeclaringClass().getName() + "." + method.getName());
        if (null != mapperData) {
            System.out.println(String.format("SQL [ %s ], parameter [%s] ", mapperData.getSql(), args[0]));
            return sqlSession.selectOne(mapperData, String.valueOf(args[0]));
        }
        return method.invoke(proxy, args);
    }
}
