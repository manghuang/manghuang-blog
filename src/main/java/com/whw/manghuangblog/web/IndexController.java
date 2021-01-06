package com.whw.manghuangblog.web;

import com.whw.manghuangblog.Exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String  index(){
//        int a = 1/0;
//        String str = null;
//        if(str == null){
//            throw new NotFoundException("资源未发现");
//        }
        System.out.println("---------index----------");
        return "index";
    }
}
