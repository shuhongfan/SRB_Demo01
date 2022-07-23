package com.shf.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.mybatisplus.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> listAllByName(String name);
}
