package com.whw.manghuangblog.web.admin;

import com.whw.manghuangblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs")
    public String list(){
        return "admin/blogs";
    }

    @GetMapping("/blogs/input")
    public String input(){
        return "admin/blogs-input";
    }


    @GetMapping("/blogs/{id}/input")
    public String editInput(){
        return "redirect:/admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(){
        return "redirect:/admin/blogs";
    }



}
