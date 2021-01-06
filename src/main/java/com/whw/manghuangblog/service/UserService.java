package com.whw.manghuangblog.service;

import com.whw.manghuangblog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
