package com.study.mybatis.source2.executor;


import com.study.mybatis.source2.config.MapperRegistory;

/**
 *  .
 *
 *
 */
public interface Executor {

    <T> T query(MapperRegistory.MapperData mapperData, Object parameter) throws Exception;
}
