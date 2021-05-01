package com.whw.manghuangblog.service;


import com.whw.manghuangblog.po.Blog;
import com.whw.manghuangblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Blog saveBlog(Blog blog);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    Blog getAndConvertBlog(long id);
}
