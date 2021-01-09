package com.whw.manghuangblog.service;

import com.whw.manghuangblog.dao.BlogReponsitory;
import com.whw.manghuangblog.po.Blog;
import com.whw.manghuangblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
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
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return  blogReponsitory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + blog.getTitle()  + "%"));
                }
                if(blog.getTypeId()!= null){
                    predicates.add(criteriaBuilder.equal(root.get("type"), blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);

    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);

        return blogReponsitory.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogReponsitory.getOne(id);
        BeanUtils.copyProperties(blog, blog1);
        return blogReponsitory.save(blog1);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogReponsitory.deleteById(id);
    }
}
