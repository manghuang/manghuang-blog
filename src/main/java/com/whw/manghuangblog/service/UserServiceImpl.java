package com.whw.manghuangblog.service;

import com.whw.manghuangblog.dao.UserReponsitory;
import com.whw.manghuangblog.po.User;
import com.whw.manghuangblog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserReponsitory userReponsitory;

    @Override
    public User checkUser(String username, String password) {

        User user = userReponsitory.findByUsernameAndPassword(username, MD5Util.code(password));
        return user;
    }
}
