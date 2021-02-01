package com.wmz.blog.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmz.blog.system.model.SysUser;
import com.wmz.blog.system.mapper.LoginMapper;
import com.wmz.blog.system.service.LoginService;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, SysUser> implements LoginService {
}
