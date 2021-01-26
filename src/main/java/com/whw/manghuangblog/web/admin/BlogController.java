package com.whw.manghuangblog.web.admin;

import com.whw.manghuangblog.po.Blog;
import com.whw.manghuangblog.po.User;
import com.whw.manghuangblog.service.BlogService;
import com.whw.manghuangblog.service.TagService;
import com.whw.manghuangblog.service.TypeService;
import com.whw.manghuangblog.vo.BlogQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final  String INPUT = "admin/blogs-input";
    private static final  String LIST = "/admin/blogs";
    private static final  String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * nav,进入blog页面
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       BlogQuery blog,
                       Model model){

        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "/admin/blogs";
    }

    /**
     * blog页面:搜索
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog,
                         Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "/admin/blogs :: blogList";
    }


    /**
     *blog页面:新增，进入blog-input页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){

        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());

        return "admin/blogs-input";
    }

    /**
     * blog-input页面，新增，进入blog页面
     * @param blog
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){

        blog.setUser((User) session.getAttribute("user"));

        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1;
        if(blog.getId() == null){
             blog1 = blogService.saveBlog(blog);
        }else {
             blog1 = blogService.updateBlog(blog.getId(), blog);
        }

        if(blog1 == null){
            attributes.addFlashAttribute("message", "新增失败");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
        }

        return "redirect:/admin/blogs";
    }


    /**
     * blog页面：编辑，进入blog-input页面
     * @param blog
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(Blog blog, @PathVariable Long id,  Model model){
        model.addAttribute("blog", blogService.getBlog(id));
        return "admin/blogs-input";
    }

    /**
     * blog页面:删除
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }



}
