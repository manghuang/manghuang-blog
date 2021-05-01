package com.whw.manghuangblog.web;

import com.whw.manghuangblog.po.Blog;
import com.whw.manghuangblog.po.Tag;
import com.whw.manghuangblog.service.BlogService;
import com.whw.manghuangblog.service.TagService;
import com.whw.manghuangblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.jws.Oneway;
import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(Integer.MAX_VALUE);
        if(id == -1){
            id = tags.get(0).getId();
        }
        Page<Blog> blogs = blogService.listBlog(id, pageable);
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogs);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
