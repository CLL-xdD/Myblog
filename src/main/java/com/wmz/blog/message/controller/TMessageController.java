package com.wmz.blog.message.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wmz.blog.blogs.model.TBlogs;
import com.wmz.blog.blogs.service.ITBlogsService;
import com.wmz.blog.comment.utils.MD5Util;
import com.wmz.blog.information.model.TInformation;
import com.wmz.blog.information.service.ITInformationService;
import com.wmz.blog.message.model.TMessage;
import com.wmz.blog.message.service.ITMessageService;
import com.wmz.blog.user.model.SysUser;
import com.wmz.blog.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  留言表前端控制器
 * </p>
 *
 * @author wmz
 * @since 2021-01-28
 */
@Controller
@RequestMapping
public class TMessageController {

    @Autowired
    private ITInformationService itInformationService;

    @Autowired
    private ITMessageService itMessageService;

    @Autowired
    private ITBlogsService itBlogsService;

    @Autowired
    private ISysUserService iSysUserService;

    @GetMapping("/message")
    public String toMessage(Model model){

        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());

        List<TMessage> messageList = itMessageService.selectAll(new TMessage().setType("0"));
        model.addAttribute("messages",messageList);
        return "message";
    }

    @PostMapping("/message")
    public String saveMessage(Model model,TMessage tMessage){

        //添加新的留言信息 根据邮箱查询用户信息
        SysUser sysUser = iSysUserService.getOne(new QueryWrapper<SysUser>().eq("status", "1").eq("email", tMessage.getEmail()).eq("nickname",tMessage.getNickname()));
        if (sysUser == null){
            //第一次发留言自动添加用户
            sysUser = new SysUser();
            sysUser.setAvatar("/images/avatar.png");
            sysUser.setCreateTime(LocalDateTime.now());
            sysUser.setEmail(tMessage.getEmail());
            sysUser.setNickname(tMessage.getNickname());
            sysUser.setId(IdWorker.getIdStr());
            sysUser.setIdentity("tourist");
            sysUser.setStatus("1");
            //密码默认123
            sysUser.setPassword(MD5Util.encode("123"));
            //用户名默认为该邮箱
            sysUser.setUsername(tMessage.getEmail());
            iSysUserService.save(sysUser);
        }

        //添加留言
        tMessage.setId(IdWorker.getIdStr());
        tMessage.setCreateTime(LocalDateTime.now());
        tMessage.setCreateUser(sysUser.getId());
        tMessage.setStatus("1");
        tMessage.setType("0");
        itMessageService.save(tMessage);

        //信息
/*        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());

        List<TMessage> messageList = itMessageService.selectAll();
        model.addAttribute("messages",messageList);*/
        return "message";
    }


    /**
     * 博客评论
     * @param model
     * @param tMessage
     * @return
     */
    @PostMapping("/comments")
    public String comments(Model model, TMessage tMessage){


        //添加新的留言信息 根据邮箱查询用户信息
        SysUser sysUser = iSysUserService.getOne(new QueryWrapper<SysUser>().eq("status", "1").eq("email", tMessage.getEmail()).eq("nickname",tMessage.getNickname()));
        if (sysUser == null){
            //第一次发留言自动添加用户
            sysUser = new SysUser();
            sysUser.setAvatar("/images/avatar.png");
            sysUser.setCreateTime(LocalDateTime.now());
            sysUser.setEmail(tMessage.getEmail());
            sysUser.setNickname(tMessage.getNickname());
            sysUser.setId(IdWorker.getIdStr());
            sysUser.setIdentity("tourist");
            sysUser.setStatus("1");
            //密码默认123
            sysUser.setPassword(MD5Util.encode("123"));
            //用户名默认为该邮箱
            sysUser.setUsername(tMessage.getEmail());
            iSysUserService.save(sysUser);
        }

        //添加留言
        tMessage.setId(IdWorker.getIdStr());
        tMessage.setCreateTime(LocalDateTime.now());
        tMessage.setCreateUser(sysUser.getId());
        tMessage.setStatus("1");
        tMessage.setType("1");
        itMessageService.save(tMessage);

        TBlogs tBlogs = itBlogsService.getById(tMessage.getBlogId());
        tBlogs.setCommentCount(String.valueOf(Integer.parseInt(tBlogs.getCommentCount())+1));
        itBlogsService.updateById(tBlogs);

        model.addAttribute("blog",tBlogs);



        List<TMessage> messageList = itMessageService.selectAll(tMessage);
        model.addAttribute("messages",messageList);

        //信息
/*        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());

        List<TMessage> messageList = itMessageService.selectAll();
        model.addAttribute("messages",messageList);*/
        return "blog";
    }
}
