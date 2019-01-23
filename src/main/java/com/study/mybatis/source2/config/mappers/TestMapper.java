package com.study.mybatis.source2.config.mappers;

import com.study.mybatis.beans.Test;

public interface TestMapper { //TestMapper
    Test selectByPrimaryKey(Integer userId);
}