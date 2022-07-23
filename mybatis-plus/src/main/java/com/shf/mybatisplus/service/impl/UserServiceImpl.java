package com.shf.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.mybatisplus.entity.User;
import com.shf.mybatisplus.mapper.UserMapper;
import com.shf.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public List<User> listAllByName(String name) {
        return baseMapper.selectAllByName(name);
    }
}
