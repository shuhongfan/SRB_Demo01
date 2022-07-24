package com.shf.srb.core;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class AssertTests {
    @Test
    public void test1() {
        Object o = null;
        if (o == null) {
            throw new IllegalArgumentException("参数错误");
        }
    }

    @Test
    public void test2() {
        Object o = null;
//        用断言代替if结果
        Assert.notNull(o, "参数错误");
    }
}
