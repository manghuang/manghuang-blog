package com.whw.manghuangblog.service;

import com.whw.manghuangblog.Exception.NotFoundException;
import com.whw.manghuangblog.dao.BlogReponsitory;
import com.whw.manghuangblog.po.Blog;
import com.whw.manghuangblog.util.MakedownUtils;
import com.whw.manghuangblog.util.MyBeanUtils;
import com.whw.manghuangblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogReponsitory blogReponsitory;

    /**
     * 按id查询blog
     *
     * @param id
     * @return
     */
    @Override
    public Blog getBlog(Long id) {
        return blogReponsitory.getOne(id);
    }

    /**
     * 查询某一页的blog
     *
     * @param pageable
     * @param blog
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogReponsitory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);

    }

    /**
     * 查询所有blog
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogReponsitory.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        // 关联查询
        return blogReponsitory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");

                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        Page<Blog> page = blogReponsitory.findByQuery(query, pageable);
        return page;
    }

    /**
     * 查询一定数目的推荐blog
     *
     * @param size
     * @return
     */
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Pageable pageable = PageRequest.of(0, 8, Sort.by(
                Sort.Direction.DESC, "updateTime"
        ));
        return blogReponsitory.findTop(pageable);
    }

    /**
     * 新增blog
     *
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);

        return blogReponsitory.save(blog);
    }

    /**
     * 更新blog
     *
     * @param id
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogReponsitory.getOne(id);
        BeanUtils.copyProperties(blog, blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return blogReponsitory.save(blog1);
    }

    /**
     * 删除blog
     *
     * @param id
     */
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogReponsitory.deleteById(id);
    }

    /**
     * 按id查询blog，并将content从makedown格式转换为html格式
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog getAndConvertBlog(long id) {
        Blog blog = blogReponsitory.getOne(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        b.setContent(MakedownUtils.makedownToHtml(b.getContent()));
        blogReponsitory.updateViews(id);
        return b;
    }
}
