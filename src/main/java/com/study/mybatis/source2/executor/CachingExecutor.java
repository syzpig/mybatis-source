package com.study.mybatis.source2.executor;


import com.study.mybatis.source2.config.GpConfiguration;
import com.study.mybatis.source2.config.MapperRegistory;
import com.study.mybatis.source2.statement.StatementHandler;

import java.util.HashMap;
import java.util.Map;

public class CachingExecutor implements Executor {
    private GpConfiguration configuration;

    private SimpleExecutor delegate;

    private Map<String,Object> localCache = new HashMap();

    public CachingExecutor(SimpleExecutor delegate) {
        this.delegate = delegate;
    }

    public CachingExecutor(GpConfiguration configuration) {
        this.configuration = configuration;
    }

    public <E> E query(MapperRegistory.MapperData mapperData, Object parameter)
            throws Exception {
        //初始化StatementHandler --> ParameterHandler --> ResultSetHandler
        StatementHandler handler = new StatementHandler(configuration);
        Object result = localCache.get(mapperData.getSql());
        if( null != result){
            System.out.println("缓存命中");
            return (E)result;
        }
        result =  (E) delegate.query(mapperData,parameter);
        localCache.put(mapperData.getSql(),result);
        return (E)result;
    }
}