package com.whw.manghuangblog.dao;

import com.whw.manghuangblog.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagReponsitory extends JpaRepository<Tag, Long> {

    Tag findByName(String name);
}
