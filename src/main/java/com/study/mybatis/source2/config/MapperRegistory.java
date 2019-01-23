package com.study.mybatis.source2.config;

import com.study.mybatis.beans.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *   变相的实现，更源码有出入，但原理一样
 *
 */
public class MapperRegistory {
    public static final Map<String, MapperData> methodSqlMapping = new HashMap<>();
    //这是我们自己实现源码的一个操作
    //使用 用户要使用这个框架需要满足
    // 1. 在这里配置
    //2. Java Bean的属性名字要和数据库表的名字一致  为了映射结果集操作，做的一个折中
    public MapperRegistory() {
        methodSqlMapping.put("TestMapper.selectByPrimaryKey",
                new MapperData("select * from test where id = %d",Test.class));
        //Test.class是返回值
    }

    public class MapperData<T>{
        private String sql;
        private Class<T> type;

        public MapperData(String sql, Class<T> type) {
            this.sql = sql;
            this.type = type;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Class<T> getType() {
            return type;
        }

        public void setType(Class<T> type) {
            this.type = type;
        }
    }
    /**
     *也可以通过命名空间获取
     */
    public MapperData get(String nameSpace) {
        return methodSqlMapping.get(nameSpace);
    }
}
