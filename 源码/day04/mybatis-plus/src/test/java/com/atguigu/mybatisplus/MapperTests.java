package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MapperTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsert(){

        User user = new User();
        user.setName("观海400");
//        user.setAge(400);
        user.setEmail("guanhai@qq.com");
//        user.setCreateTime(LocalDateTime.now());
//        user.setUpdateTime(LocalDateTime.now());

        int result = userMapper.insert(user);
        System.out.println("结果：" + result);
    }

    @Test
    public void testSelect(){
        User user = userMapper.selectById(1);
        System.out.println(user);

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "建国");
        map.put("age", 74);
        List<User> users1 = userMapper.selectByMap(map);
        users1.forEach(System.out::println);
    }

    @Test
    public void testUpdate(){

        User user = new User();
        user.setId(1357203540854038541L);
        user.setAge(99);
//        user.setUpdateTime(LocalDateTime.now());

        int result = userMapper.updateById(user);
        System.out.println("结果：" + result);
    }

    @Test
    public void testDelete(){

        int result = userMapper.deleteById(2L);
        System.out.println("结果：" + result);
    }

    @Test
    public void testSelectAllByName(){
        List<User> users = userMapper.selectAllByName("Jack");
        users.forEach(System.out::println);
    }
}
