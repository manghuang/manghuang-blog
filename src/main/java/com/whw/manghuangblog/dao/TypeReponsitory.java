package com.whw.manghuangblog.dao;

import com.whw.manghuangblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeReponsitory extends JpaRepository<Type, Long> {
    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
