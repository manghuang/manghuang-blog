package com.whw.manghuangblog.service;


import com.whw.manghuangblog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, Blog blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);


    void  deleteBlog(Long id);
}
