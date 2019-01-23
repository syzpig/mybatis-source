package com.study.mybatis.source1;


import java.lang.reflect.Proxy;

/**
 * Created by James
 *
 *
 */
public class MySqlSession {
    private Executor executor = new SimpleExecutor();
    //TODO configuration

    public <T> T selectOne(String statement, Object parameter) {
        return executor.query(statement,parameter);
    }

    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new MapperProxy(this, clazz));
    }
}
