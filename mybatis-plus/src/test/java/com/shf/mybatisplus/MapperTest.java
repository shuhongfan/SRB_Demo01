package com.shf.mybatisplus;

import com.shf.mybatisplus.entity.User;
import com.shf.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class MapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("建国3");
        user.setAge(74);
        user.setEmail("222@er.com");
//        user.setCreateTime(LocalDateTime.now());
//        user.setUpdateTime(LocalDateTime.now());

        int result = userMapper.insert(user);
        System.out.println("结果：" + result);
    }

    @Test
    public void testSelect() {
        User user = userMapper.selectById(1);
        System.out.println(user);

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1,2,3));
        users.stream().forEach(System.out::println);

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "建国");
        map.put("age", 74);
        userMapper.selectByMap(map);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(1550412309907030017L);
        user.setAge(75);

        int result = userMapper.updateById(user);
        System.out.println("result=" + result);
    }

    @Test
    public void testDelete() {
        int result = userMapper.deleteById(1L);
        System.out.println("结果：" + result);
    }

    @Test
    public void testSelectAllByName() {
        List<User> users = userMapper.selectAllByName("Jack");
        users.forEach(System.out::println);
    }
}
