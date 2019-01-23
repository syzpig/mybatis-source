package com.study.mybatis.source1;

public interface Executor {
    <E> E query(String statement, Object parameter);
}