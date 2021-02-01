package com.wmz.blog.blogs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wmz.blog.blogs.model.TBlogs;
import com.wmz.blog.blogs.mapper.TBlogsMapper;
import com.wmz.blog.blogs.service.ITBlogsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmz
 * @since 2021-01-24
 */
@Service
public class TBlogsServiceImpl extends ServiceImpl<TBlogsMapper, TBlogs> implements ITBlogsService {

    @Autowired
    private TBlogsMapper tBlogsMapper;

    @Override
    public IPage<TBlogs> selectPage(Page<TBlogs> page, String id) {
        return page.setRecords(tBlogsMapper.selectList(new QueryWrapper<TBlogs>().eq("create_user",id)));
    }
}
