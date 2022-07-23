package com.shf.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.shf.mybatisplus.entity.User;
import com.shf.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class WrapperTest {

    @Resource
    private UserMapper userMapper;

    /**
     * 1、例1：组装查询条件
     * 查询名字中包含n，年龄大于10且小于20，email不为空的用户
     */
    @Test
    public void test1() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .like("username", "n")
                .between("age", 51, 60)
                .isNotNull("email");

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 2、例2：组装排序条件
     * 按年龄降序查询用户，如果年龄相同则按id升序排列
     */
    @Test
    public void test2() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("age").orderByAsc("uid");

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 3、例3：组装删除条件
     * 删除email为空的用户
     */
    @Test
    public void test3() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNull("email");
        int result = userMapper.delete(wrapper);
        System.out.println("删除的记录数为：" + result);
    }

    /**
     * 4、例4：条件的优先级
     * 查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 user@atguigu.com
     */
    @Test
    public void test4() {
//        组装查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("username", "n")
                .and(
                        i -> i.lt("age", 18)
                                .or()
                                .isNull("email")
                );

//        组装更新条件
        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");

//        执行更新
        int result = userMapper.update(user, wrapper);
        System.out.println(result);
    }

    /**
     * 5、例5：组装select子句
     * 查询所有用户的用户名和年龄
     */
    @Test
    public void test5() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("username", "age");

        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    /**
     * 6、例6：实现子查询
     * 查询id不大于3的所有用户的id列表
     */
    @Test
    public void test6() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("uid", "select uid from user1 where uid<=3");

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * UpdateWrapper
     * 例7：需求同例4
     * 查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 user@atguigu.com
     */
    @Test
    public void test7() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper
                .set("age", 18)
                .set("email", "user@atguigu.com")
                .like("username", "n")
                .and(i -> i.lt("age", 18).or().isNull("email"));

        User user = new User(); // 检查是否有自动填充对象
        int result = userMapper.update(user, wrapper);
        System.out.println(result);
    }

    /**
     * 四、condition
     * 例8：动态组装查询条件
     * 查询名字中包含n，年龄大于10且小于20的用户，查询条件来源于用户输入，是可选的
     */
    @Test
    public void test8() {
//        String username = "n";
        String username = null;
        Integer ageBegin = 10;
        Integer ageEnd = 20;

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.like(StringUtils.isNotBlank(username), "username", username);
        wrapper.ge(ageBegin != null, "age", ageBegin);
        wrapper.le(ageEnd != null, "age", ageEnd);

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 五、LambdaXxxWrapper
     * 1、例9：Query - 需求同例8
     * 查询名字中包含n，年龄大于10且小于20的用户，查询条件来源于用户输入，是可选的
     */
    @Test
    public void test9() {
//        String username = "n";
        String username = null;
        Integer ageBegin = null;
        Integer ageEnd = 20;

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(StringUtils.isNotBlank(username), User::getName, username);
        wrapper.ge(ageBegin != null, User::getAge, ageBegin);
        wrapper.le(ageEnd != null, User::getAge, ageEnd);

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * UpdateWrapper
     * 例7：需求同例4
     * 查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 user@atguigu.com
     */
    @Test
    public void test10() {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper
                .set(User::getAge, 18)
                .set(User::getEmail, "user@atguigu.com")
                .like(User::getName, "n")
                .and(i -> i
                        .lt(User::getAge, 18)
                        .or()
                        .isNull(User::getEmail));

        User user = new User(); // 检查是否有自动填充对象
        int result = userMapper.update(user, wrapper);
        System.out.println(result);
    }
}
