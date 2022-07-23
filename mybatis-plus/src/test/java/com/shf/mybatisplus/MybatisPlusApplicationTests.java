package com.shf.mybatisplus;

import com.shf.mybatisplus.entity.User;
import com.shf.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest // 自动创建spring上下文环境
class MybatisPlusApplicationTests {

//	@Autowired  // 默认按类型装配。是spring的注解
	@Resource   //默认按名称装配，找不到与名称匹配的bean，则按照类型装配。是J2EE的注解
	private UserMapper userMapper;

	@Test
	void testSelectList() {
		List<User> users = userMapper.selectList(null);
		System.out.println(users);
	}

}
