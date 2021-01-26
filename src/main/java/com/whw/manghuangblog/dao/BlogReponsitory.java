package com.whw.manghuangblog.dao;

import com.whw.manghuangblog.po.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogReponsitory extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {


    @Query("select b from Blog  b where b.recommend = true ")
    List<Blog> findTop(Pageable pageable);
}
