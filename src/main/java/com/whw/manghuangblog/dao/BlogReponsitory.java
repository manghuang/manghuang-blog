package com.whw.manghuangblog.dao;

import com.whw.manghuangblog.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlogReponsitory extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
}
