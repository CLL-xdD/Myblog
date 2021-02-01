package com.wmz.blog.information.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmz.blog.comment.model.ResultInfo;
import com.wmz.blog.comment.model.Status;
import com.wmz.blog.information.model.TInformation;
import com.wmz.blog.information.service.ITInformationService;
import com.wmz.blog.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2021-01-24
 */
@RestController
@RequestMapping("/information")
public class TInformationController {

    @Autowired
    private ITInformationService itInformationService;

    @GetMapping("/getAll")
    public ResultInfo getAll(HttpSession session){
        SysUser user = (SysUser) session.getAttribute("user");
        if (user == null){
            return new ResultInfo(Status.LOGIN_EXPIRE);
        }
        TInformation createUser = itInformationService.getOne(new QueryWrapper<TInformation>().eq("create_user", user.getId()));
        if (createUser != null){
            return new ResultInfo(Status.SUCCESS.code,Status.SUCCESS.message,createUser);
        }
        return new ResultInfo(Status.LOGIN_EXPIRE);
    }

}
