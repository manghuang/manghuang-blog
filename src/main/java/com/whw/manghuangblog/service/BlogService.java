package com.whw.manghuangblog.service;


import com.whw.manghuangblog.po.Blog;
import com.whw.manghuangblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);
    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);


    void  deleteBlog(Long id);
}
