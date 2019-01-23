package com.study.mybatis.source2;

import com.study.mybatis.beans.Test;
import com.study.mybatis.source2.config.GpConfiguration;
import com.study.mybatis.source2.config.mappers.TestMapper;
import com.study.mybatis.source2.executor.ExecutorFactory;
import com.study.mybatis.source2.session.GpSqlSession;

import java.io.IOException;

/**
 *  .
 *
 *
 */
public class BootStrap {
    public static void main(String[] args) throws IOException {
        start();
    }

    private static void start() throws IOException {
        GpConfiguration configuration = new GpConfiguration();
        configuration.setScanPath("com.gupaoedu.mybatis.gp.config.mappers");
        configuration.build();
//        GpSqlSession sqlSession = new GpSqlSession(configuration, ExecutorFactory.DEFAULT(configuration));
        GpSqlSession sqlSession = new GpSqlSession(configuration,
                ExecutorFactory.get(ExecutorFactory.ExecutorType.CACHING.name(),configuration));
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        long start = System.currentTimeMillis();
        Test test = testMapper.selectByPrimaryKey(1);
        System.out.println("cost:"+ (System.currentTimeMillis() -start));
//        start = System.currentTimeMillis();
//        test = testMapper.selectByPrimaryKey(1);
//        System.out.println("cost:"+ (System.currentTimeMillis() -start));
//        System.out.println(test);
    }
}
