package com.wmz.blog.type.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wmz.blog.blogs.model.TBlogs;
import com.wmz.blog.blogs.service.ITBlogsService;
import com.wmz.blog.comment.model.ResultInfo;
import com.wmz.blog.comment.model.Status;
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
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2021-01-24
 */
@Controller
@RequestMapping
public class TTypeController {

    @Autowired
    private ITTypeService itTypeService;

    @Autowired
    private ITInformationService itInformationService;

    @Autowired
    private ITBlogsService blogsService;

    @GetMapping("/admin/types")
    public String toType(Model model,Integer pageNum,String id){

        model.addAttribute("message",null);
        if (pageNum == null||pageNum == 0){
            pageNum = 1;
        }
        //查询类型列表
        PageHelper.startPage(pageNum,5);
        List<TType> list = itTypeService.list(new QueryWrapper<TType>().eq("status","1"));
        PageInfo<TType> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());

        return "/admin/types";
    }


    @GetMapping("/admin/types/input")
    public String toTypeInput(Model model,String id ){

        model.addAttribute("message",null);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());

        model.addAttribute("type",new TType());
        return "/admin/types-input";
    }

    @PostMapping("/admin/types")
    public String save(Model model , TType tType , HttpSession session){

        if (tType.getId() == null||tType.getId().equals("")){
            tType.setId(IdWorker.getIdStr());
        }
        SysUser sysUser = (SysUser) session.getAttribute("user");
        tType.setStatus("1");
        tType.setCreateTime(LocalDateTime.now());
        tType.setCreateUser(sysUser.getId());
        if (!itTypeService.saveOrUpdate(tType)){
            model.addAttribute("message",Status.WARN);
        }
        model.addAttribute("message",Status.SUCCESS);
        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());

        model.addAttribute("type",new TType());
        return "/admin/types-input";

    }

    @GetMapping("/admin/types/{id}/input")
    public String toTypeInputUpdate(Model model,@PathVariable("id") String id ){

        model.addAttribute("message",null);

        //根据id查询
        TType tType = itTypeService.getById(id);
        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());

        model.addAttribute("type",tType);
        return "/admin/types-input";
    }

    @GetMapping("/admin/types/{id}/delete")
    public String toTypeInputDelete(Model model,@PathVariable("id") String id ){


        //根据id删除
        if (itTypeService.removeById(id)){
            model.addAttribute("message",Status.SUCCESS);
        }

        //查询类型列表
        PageHelper.startPage(1,5);
        List<TType> list = itTypeService.list(new QueryWrapper<TType>().eq("status","1"));
        PageInfo<TType> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);

        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());


        return "/admin/types";
    }


    /**
     * 跳转分类页面
     * @param model
     * @param
     * @return
     */
    @GetMapping("/types/{id}")
    public String toTypeIndex(Model model,@PathVariable("id") String id,Integer pageNum  ){


        //查询类型列表
        /*List<TType> list = itTypeService.list(new QueryWrapper<TType>().eq("status","1").orderByDesc("create_time"));
        model.addAttribute("types",list);*/
        List<TType> list = itTypeService.selectTypes();
        model.addAttribute("types",list);

        if (pageNum == null||pageNum==0){
            pageNum = 1;
        }
        if (id == null||id.equals("-1")){
                //根据类型查询
                PageHelper.startPage(pageNum,5);
                List<TBlogs> blogs = blogsService.list(new QueryWrapper<TBlogs>().eq("status", "1").eq("published", "1"));
                PageInfo<TBlogs> pageInfo = new PageInfo<>(blogs);
                model.addAttribute("pageInfo",pageInfo);

        }else {
            PageHelper.startPage(pageNum,5);
            List<TBlogs> blogs = blogsService.list(new QueryWrapper<TBlogs>().eq("status", "1").eq("published", "1").eq("type_id", id));
            PageInfo<TBlogs> pageInfo = new PageInfo<>(blogs);
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("activeTypeId",id);
        }


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());


        return "types";
    }


}
