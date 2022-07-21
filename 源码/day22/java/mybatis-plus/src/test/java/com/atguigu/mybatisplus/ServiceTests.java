package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ServiceTests {

    @Resource
    private UserService userService;

    @Test
    public void testCount(){

        int count = userService.count();
        System.out.println("总记录数：" + count);
    }

    @Test
    public void testSaveBatch(){

        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("瓜瓜" + i);
            user.setAge(20 + i);
            users.add(user);
        }

        boolean b = userService.saveBatch(users);
        System.out.println("插入是否成功：" + b);
    }

    @Test
    public void testListAllByName(){
        List<User> users = userService.listAllByName("Tom");
        users.forEach(System.out::println);
    }
}
