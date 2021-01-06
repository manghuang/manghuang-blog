package com.whw.manghuangblog.dao;

import com.whw.manghuangblog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReponsitory extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}
