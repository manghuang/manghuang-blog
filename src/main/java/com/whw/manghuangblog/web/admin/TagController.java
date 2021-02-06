package com.whw.manghuangblog.web.admin;



import com.whw.manghuangblog.po.Tag;
import com.whw.manghuangblog.po.Type;
import com.whw.manghuangblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String list(@PageableDefault(size = 3, sort = {"id"}) Pageable pageable,
                       Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "/admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "/admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTagById(id));
        return "/admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTagById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes){
        Tag tagByName = tagService.getTagByName(tag.getName());
        if(tagByName != null){
            result.rejectValue("name", "name error", "不能重复添加Tag");
        }
        if(result.hasErrors()){
            return "/admin/tags-input";
        }
        Tag tag1 = tagService.updateTag(id, tag);
        attributes.addFlashAttribute("message", "更新成功");
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tagByName = tagService.getTagByName(tag.getName());
        if(tagByName != null){
            result.rejectValue("name", "name error", "不能重复添加Tag");
        }
        if(result.hasErrors()){
            return "/admin/tags-input";
        }
        Tag tag1 = tagService.saveTag(tag);
        attributes.addFlashAttribute("message", "添加成功");
        return "redirect:/admin/tags";
    }
}
