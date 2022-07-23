package com.shf.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.mybatisplus.entity.Product;
import com.shf.mybatisplus.entity.User;
import com.shf.mybatisplus.mapper.ProductMapper;
import com.shf.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class InterceptorTest {
    @Resource
    private UserMapper userMapper;

    @Resource
    private ProductMapper productMapper;

    @Test
    public void testSelectPage() {
        Page<User> pageParam = new Page<>(1, 5);
        userMapper.selectPage(pageParam, null);

        List<User> users = pageParam.getRecords();
        users.forEach(System.out::println);

        long total = pageParam.getTotal();
        System.out.println("总页数："+total);

        boolean bn = pageParam.hasNext();
        System.out.println("下一页？" + bn);

        boolean bp = pageParam.hasPrevious();
        System.out.println("上一页？"+bn);
    }

    @Test
    public void testSelectPageByAge() {
        Page<User> pageParam = new Page<>(1, 5);
        userMapper.selectPageByPage(pageParam, 18);

        List<User> users = pageParam.getRecords();
        users.forEach(System.out::println);
    }

    @Test
    public void testConcurrentUpdate() {
//        小李取数据
        Product p1 = productMapper.selectById(1L);

//        小王取数据
        Product p2 = productMapper.selectById(1L);

//        小李修改+50
        p1.setPrice(p1.getPrice() + 50);
        int result = productMapper.updateById(p1);
        System.out.println("小李修改结果："+result);

//        小王修改-30
        p2.setPrice(p2.getPrice() - 30);
        int res = productMapper.updateById(p2);
        System.out.println("小王修改结果：" + res);
        if (res == 0) { // 更新失败重试
            System.out.println("小王重试");
//            重新获取数据
            p2 = productMapper.selectById(1L);
//            更新
            p2.setPrice(p2.getPrice() - 30);
            productMapper.updateById(p2);
        }

//        老板看到的价格
        Product product = productMapper.selectById(1L);
        System.out.println("老板看到的价格："+product);
    }
}
