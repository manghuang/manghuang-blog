package com.whw.manghuangblog.service;

import com.whw.manghuangblog.dao.BlogReponsitory;
import com.whw.manghuangblog.po.Blog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogReponsitory blogReponsitory;

    @Override
    public Blog getBlog(Long id) {
        return blogReponsitory.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Blog blog) {
        return  blogReponsitory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if("".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + blog.getTitle()  + "%"));
                }
                if(blog.getType() != null){
                    predicates.add(criteriaBuilder.equal(root.get("type"), blog.getType().getId()));
                }
                if(blog.getRecommened()){
                    predicates.add(criteriaBuilder.equal(root.get("recommened"), blog.getRecommened()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);

    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogReponsitory.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogReponsitory.getOne(id);
        BeanUtils.copyProperties(blog, blog1);
        return blogReponsitory.save(blog1);
    }

    @Override
    public void deleteBlog(Long id) {
        blogReponsitory.deleteById(id);
    }
}
