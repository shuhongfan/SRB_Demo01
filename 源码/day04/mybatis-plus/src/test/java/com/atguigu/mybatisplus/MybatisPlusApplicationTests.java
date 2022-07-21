package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest //自动创建spring上下文环境
class MybatisPlusApplicationTests {

//    @Autowired //Spring
    @Resource //J2EE
    private UserMapper userMapper;

    @Test
    void testSelectList() {

        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

    }

}
