package com.wmz.blog.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wmz.blog.blogs.model.TBlogs;
import com.wmz.blog.blogs.service.ITBlogsService;
import com.wmz.blog.comment.model.ResultInfo;
import com.wmz.blog.comment.model.Status;
import com.wmz.blog.comment.utils.MD5Util;
import com.wmz.blog.information.model.TInformation;
import com.wmz.blog.information.service.ITInformationService;
import com.wmz.blog.system.model.SysUser;
import com.wmz.blog.system.service.LoginService;
import com.wmz.blog.type.model.TType;
import com.wmz.blog.type.service.ITTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ITInformationService itInformationService;

    @Autowired
    private ITTypeService itTypeService;

    @Autowired
    private ITBlogsService itBlogsService;

    @GetMapping
    public String toLogin(){
        return "/admin/login";
    }

    @GetMapping("/index")
    public String index(){
        return "/admin/index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password") String password,
                        RedirectAttributes attributes,
                        HttpSession httpSession,
                        Model model){
        //查询数据库
        SysUser sysUser = loginService.getOne(new QueryWrapper<SysUser>().eq("username", username).eq("password", MD5Util.encode(password)));
        if (sysUser !=null){
            sysUser.setPassword(null);
            TInformation information = itInformationService.getOne(new QueryWrapper<>());            model.addAttribute("blogTotal",information.getBlogCount());
            model.addAttribute("blogViewTotal",information.getVisitCount());
            model.addAttribute("blogCommentTotal",information.getCommentCount());
            model.addAttribute("blogMessageTotal",information.getMessageCount());
            httpSession.setAttribute("user",sysUser);
            return "/admin/index";
        }else {
            attributes.addFlashAttribute("用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "/admin/login";
    }





}
