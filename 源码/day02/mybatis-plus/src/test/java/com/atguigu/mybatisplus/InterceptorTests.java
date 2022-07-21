package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class InterceptorTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public  void testSelectPage(){

        Page<User> pageParam = new Page<>(1, 5);
        userMapper.selectPage(pageParam, null);

        List<User> users = pageParam.getRecords();
        users.forEach(System.out::println);

        long total = pageParam.getTotal();
        System.out.println(total);

        boolean bn = pageParam.hasNext();
        System.out.println("下一页？" + bn);

        boolean bp = pageParam.hasPrevious();
        System.out.println("上一页？" + bp);
    }

    @Test
    public void testSelectPageByAge(){

        Page<User> pageParam = new Page<>(1, 5);
        userMapper.selectPageByAge(pageParam, 400);

        List<User> users = pageParam.getRecords();
        users.forEach(System.out::println);
    }
}
