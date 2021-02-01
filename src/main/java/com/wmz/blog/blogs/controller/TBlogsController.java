package com.wmz.blog.blogs.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wmz.blog.blogs.model.TBlogs;
import com.wmz.blog.blogs.service.ITBlogsService;
import com.wmz.blog.comment.model.Status;
import com.wmz.blog.comment.utils.MarkdownUtils;
import com.wmz.blog.information.model.TInformation;
import com.wmz.blog.information.service.ITInformationService;
import com.wmz.blog.message.model.TMessage;
import com.wmz.blog.message.service.ITMessageService;
import com.wmz.blog.system.model.SysUser;
import com.wmz.blog.type.model.TType;
import com.wmz.blog.type.service.ITTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmz
 * @since 2021-01-24
 */
@Controller
@RequestMapping
public class TBlogsController {

    @Autowired
    private ITTypeService itTypeService;

    @Autowired
    private ITInformationService itInformationService;

    @Autowired
    private ITBlogsService itBlogsService;

    @Autowired
    private ITMessageService itMessageService;


    @GetMapping("/admin/blogs/input")
    public String input(Model model, HttpSession session, String id){
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
            TBlogs blogs = itBlogsService.getById(id);
            model.addAttribute("blog",blogs);
        }else {
            model.addAttribute("blog",new TBlogs());
        }


        return "/admin/blogs-input";
    }

    @GetMapping("/admin/blogs")
    public String toBlogs(Model model,HttpSession session,Integer pageNum,TBlogs tBlog){
        //传递分类数据
        List<TType> list = itTypeService.list();
        model.addAttribute("types",list);

        SysUser user = (SysUser) session.getAttribute("user");
        //查询我的所有博客
        if (pageNum == null||pageNum == 0){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,5);
        QueryWrapper<TBlogs> queryWrapper = new QueryWrapper<TBlogs>().eq("create_user", user.getId());
        if (tBlog.getTitle()!=null&&!tBlog.getTitle().equals("")){
            queryWrapper.like("title",tBlog.getTitle());
        }
        if (tBlog.getTypeId()!=null&&!tBlog.getTypeId().equals("")){
            queryWrapper.eq("type_id",tBlog.getTypeId());
        }
        queryWrapper.eq("status","1");
        List<TBlogs> tBlogs = itBlogsService.list(queryWrapper);
        PageInfo<TBlogs> pageInfo = new PageInfo<>(tBlogs);
        model.addAttribute("pageInfo",pageInfo);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());
        return "/admin/blogs";
    }

    @PostMapping("/admin/blogs")
    public String input(Model model, HttpSession session,TBlogs tBlogs){
        //传递分类数据
        List<TType> list = itTypeService.list();
        model.addAttribute("types",list);
        SysUser sysUser = (SysUser) session.getAttribute("user");
        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());


        tBlogs.setStatus("1");
        tBlogs.setUpdateTime(LocalDateTime.now());
        tBlogs.setUpdateUser(sysUser.getId());
        tBlogs.setAvatar(sysUser.getAvatar());
        tBlogs.setViews("0");
        tBlogs.setNickname(sysUser.getNickname());
        tBlogs.setCommentCount("0");
        if (tBlogs.getId()==null||tBlogs.getId().equals("")){
            tBlogs.setId(IdWorker.getIdStr());
            tBlogs.setCreateTime(LocalDateTime.now());
            tBlogs.setCreateUser(sysUser.getId());
        }

        tBlogs.setTypeName(itTypeService.getById(tBlogs.getTypeId()).getName());
        if (itBlogsService.saveOrUpdate(tBlogs)){
            model.addAttribute("message", Status.SUCCESS);
        }else {
            model.addAttribute("message",Status.WARN);
        }

        model.addAttribute("blog",tBlogs);
        return "/admin/blogs-input";
    }


    @PostMapping("/admin/blogs/search")
    public String toBlogsPost(Model model,HttpSession session,Integer pageNum,TBlogs tBlog){
        //传递分类数据
        List<TType> list = itTypeService.list();
        model.addAttribute("types",list);

        SysUser user = (SysUser) session.getAttribute("user");
        //查询我的所有博客
        if (pageNum == null||pageNum == 0){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,5);
        QueryWrapper<TBlogs> queryWrapper = new QueryWrapper<TBlogs>().eq("create_user", user.getId());
        if (tBlog.getTitle()!=null&&!tBlog.getTitle().equals("")){
            queryWrapper.like("title",tBlog.getTitle());
        }
        if (tBlog.getTypeId()!=null&&!tBlog.getTypeId().equals("")){
            queryWrapper.eq("type_id",tBlog.getTypeId());
        }
        queryWrapper.eq("status","1");
        List<TBlogs> tBlogs = itBlogsService.list(queryWrapper);
        PageInfo<TBlogs> pageInfo = new PageInfo<>(tBlogs);
        model.addAttribute("pageInfo",pageInfo);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());
        return "/admin/blogs";
    }


    @GetMapping("/admin/blogs/{id}/delete")
    public String toBlogsPost(Model model, HttpSession session, @PathVariable("id")String id,Integer pageNum){

        if (!itBlogsService.removeById(id)){
            model.addAttribute("message",Status.WARN);
        }else {
            model.addAttribute("message",Status.SUCCESS);
        }


        //传递分类数据
        List<TType> list = itTypeService.list();
        model.addAttribute("types",list);

        SysUser user = (SysUser) session.getAttribute("user");
        //查询我的所有博客
        if (pageNum == null||pageNum == 0){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,5);
        QueryWrapper<TBlogs> queryWrapper = new QueryWrapper<TBlogs>().eq("create_user", user.getId()).eq("status","1");
        List<TBlogs> tBlogs = itBlogsService.list(queryWrapper);
        PageInfo<TBlogs> pageInfo = new PageInfo<>(tBlogs);
        model.addAttribute("pageInfo",pageInfo);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());
        return "/admin/blogs";
    }


    /**
     * 首页
     * @param model
     * @param
     * @param
     * @return
     */
    @GetMapping("/")
    public String toIndex(Model model,Integer pageNum){

        //查询我的推荐内容

        QueryWrapper<TBlogs> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recommend","1");
        queryWrapper.eq("status","1");
        queryWrapper.eq("published","1");
        List<TBlogs> list = itBlogsService.list(queryWrapper);
        model.addAttribute("recommendedBlogs",list);

        if (pageNum == null || pageNum == 0||pageNum.equals("")){
            pageNum = 1;
        }

        //查询所有
        PageHelper.startPage(pageNum,5);
        QueryWrapper<TBlogs> query = new QueryWrapper<TBlogs>();
        query.eq("status","1");
        query.eq("published","1");
        query.orderByDesc("create_time");
        List<TBlogs> tBlogs = itBlogsService.list(query);
        PageInfo<TBlogs> pageInfo = new PageInfo<>(tBlogs);
        model.addAttribute("pageInfo",pageInfo);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());
        return "index";
    }



    /**
     * 跳转时间轴
     * @param model
     * @param
     * @param
     * @return
     */
    @GetMapping("/archives")
    public String toArchives(Model model){


        //查询所有
        QueryWrapper<TBlogs> query = new QueryWrapper<TBlogs>();
        query.eq("status","1");
        query.eq("published","1");
        query.orderByDesc("create_time");
        List<TBlogs> tBlogs = itBlogsService.list(query);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (TBlogs tBlog : tBlogs) {
            String format = df.format(tBlog.getCreateTime());
            tBlog.setTime(format);
        }

        model.addAttribute("blogs",tBlogs);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());
        return "archives";
    }

    /**
     * 搜索
     * @param model
     * @param
     * @param
     * @return
     */
    @PostMapping("/search")
    public String toSearch(Model model,Integer pageNum,TBlogs tBlogs){


        if (pageNum == null || pageNum == 0||pageNum.equals("")){
            pageNum = 1;
        }

        //查询所有
        PageHelper.startPage(pageNum,5);
        QueryWrapper<TBlogs> query = new QueryWrapper<TBlogs>();
        query.eq("status","1");
        query.eq("published","1");
        query.orderByDesc("create_time");
        if (tBlogs.getQuery()!=null&&!tBlogs.getQuery().equals("")){
            query.like("title",tBlogs.getQuery());
        }
        List<TBlogs> tBlogsList = itBlogsService.list(query);
        PageInfo<TBlogs> pageInfo = new PageInfo<>(tBlogsList);
        model.addAttribute("pageInfo",pageInfo);


        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());
        return "search";
    }



    @GetMapping("/blog/{id}")
    public String blogDetail(Model model,@PathVariable("id") String id){


        TBlogs tBlogs = itBlogsService.getById(id);
        tBlogs.setViews(String.valueOf(Integer.parseInt(tBlogs.getViews())+1));
        itBlogsService.updateById(tBlogs);
        //markdown转换html格式
        tBlogs.setContent(MarkdownUtils.markdownToHtmlExtensions(tBlogs.getContent()));

        model.addAttribute("blog",tBlogs);

        List<TMessage> messageList = itMessageService.selectAll(new TMessage().setType("1").setBlogId(id));
        model.addAttribute("comments",messageList);



        //信息
        TInformation information = itInformationService.getOne(new QueryWrapper<>());
        model.addAttribute("blogTotal",information.getBlogCount());
        model.addAttribute("blogViewTotal",information.getVisitCount());
        model.addAttribute("blogCommentTotal",information.getCommentCount());
        model.addAttribute("blogMessageTotal",information.getMessageCount());
        return "blog";
    }
}
