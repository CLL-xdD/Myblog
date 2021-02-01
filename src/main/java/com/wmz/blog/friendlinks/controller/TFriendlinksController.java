package com.wmz.blog.friendlinks.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wmz.blog.comment.model.Status;
import com.wmz.blog.friendlinks.model.TFriendlinks;
import com.wmz.blog.friendlinks.service.ITFriendlinksService;
import com.wmz.blog.information.model.TInformation;
import com.wmz.blog.information.service.ITInformationService;
import com.wmz.blog.system.model.SysUser;
import com.wmz.blog.type.model.TType;
import com.wmz.blog.type.service.ITTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  友链 前端控制器
 * </p>
 *
 * @author wmz
 * @since 2021-01-25
 */
@Controller
@RequestMapping
public class TFriendlinksController {

    @Autowired
    private ITTypeService itTypeService;

    @Autowired
    private ITFriendlinksService itFriendlinksService;

    @Autowired
    private ITInformationService itInformationService;

    /**
     * 跳转友链地址
     * @param model
     * @param session
     * @param pageNum
     * @param
     * @return
     */
    @GetMapping("/admin/friendlinks")
    public String toFriendlinks(Model model, HttpSession session, Integer pageNum, TFriendlinks tFriendlinks){
        //传递分类数据
        List<TType> list = itTypeService.list();
        model.addAttribute("types",list);

        SysUser user = (SysUser) session.getAttribute("user");
        //查询我的所有友链
        if (pageNum == null||pageNum == 0){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,5);
        QueryWrapper<TFriendlinks> queryWrapper = new QueryWrapper<TFriendlinks>().eq("create_user", user.getId());
        if (tFriendlinks.getBlogname()!=null&&!tFriendlinks.getBlogname().equals("")){
            queryWrapper.like("blogname",tFriendlinks.getBlogname());
        }

        List<TFriendlinks> tFriendlinks1 = itFriendlinksService.list(queryWrapper);
        PageInfo<TFriendlinks> pageInfo = new PageInfo<>(tFriendlinks1);
        model.addAttribute("pageInfo",pageInfo);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());
        return "/admin/friendlinks";
    }


    /**
     * 跳转新增页面
     * @param model
     * @param session
     * @param id
     * @return
     */
    @GetMapping("/admin/friendlinks/input")
    public String inputFriendlinks(Model model, HttpSession session, String id){
        //传递分类数据
        List<TType> list = itTypeService.list();
        model.addAttribute("types",list);
        SysUser user = (SysUser) session.getAttribute("user");
        model.addAttribute("message",null);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());

        if (id !=null &&!id.equals("")){
            TFriendlinks tFriendlinks = itFriendlinksService.getById(id);
            model.addAttribute("friendlink",tFriendlinks);
        }else {
            model.addAttribute("friendlink",new TFriendlinks());
        }


        return "/admin/friendlinks-input";
    }


    /**
     * t添加友链
     * @param model
     * @param session
     * @param
     * @param tFriendlinks
     * @return
     */
    @PostMapping("/admin/friendlinks")
    public String saveFriendlinks(Model model, HttpSession session, TFriendlinks tFriendlinks){
        //传递分类数据
        List<TType> list = itTypeService.list();
        model.addAttribute("types",list);
        SysUser user = (SysUser) session.getAttribute("user");

        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());


        tFriendlinks.setStatus("1");
        tFriendlinks.setUpdateTime(LocalDateTime.now());
        tFriendlinks.setUpdateUser(user.getId());
        if (tFriendlinks.getId()==null||tFriendlinks.getId().equals("")){
            tFriendlinks.setId(IdWorker.getIdStr());
            tFriendlinks.setCreateTime(LocalDateTime.now());
            tFriendlinks.setCreateUser(user.getId());
        }

        if (itFriendlinksService.saveOrUpdate(tFriendlinks)){
            model.addAttribute("message", Status.SUCCESS);
        }else {
            model.addAttribute("message",Status.WARN);
        }

        model.addAttribute("friendlink",tFriendlinks);
        return "/admin/friendlinks-input";
    }


    /**
     * 删除友链
     * @param model
     * @param session
     * @param id
     * @return
     */
    @GetMapping("/admin/friendlinks/{id}/delete")
    public String toBlogsPost(Model model, HttpSession session, @PathVariable("id")String id){

        if (!itFriendlinksService.removeById(id)){
            model.addAttribute("message",Status.WARN);
        }else {
            model.addAttribute("message",Status.SUCCESS);
        }


        //传递分类数据
        List<TType> list = itTypeService.list();
        model.addAttribute("types",list);

        SysUser user = (SysUser) session.getAttribute("user");
        //查询我的所有友链

        PageHelper.startPage(1,5);
        QueryWrapper<TFriendlinks> queryWrapper = new QueryWrapper<TFriendlinks>().eq("create_user", user.getId());
        List<TFriendlinks> tBlogs = itFriendlinksService.list(queryWrapper);
        PageInfo<TFriendlinks> pageInfo = new PageInfo<>(tBlogs);
        model.addAttribute("pageInfo",pageInfo);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());
        return "/admin/friendlinks";
    }
}
