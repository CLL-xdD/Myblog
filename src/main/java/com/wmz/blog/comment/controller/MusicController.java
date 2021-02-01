package com.wmz.blog.comment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmz.blog.information.model.TInformation;
import com.wmz.blog.information.service.ITInformationService;
import com.wmz.blog.type.service.ITTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MusicController {

    @Autowired
    private ITInformationService itInformationService;

    /**
     * 跳转音乐盒
     * @return
     */
    @GetMapping("/music")
    public String toMusic(){
        return "music";
    }

    /**
     * 跳转关于我
     * @return
     */
    @GetMapping("/about")
    public String toAbout(Model model){

        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());
        return "about";
    }
}
