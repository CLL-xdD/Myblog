package com.wmz.blog.blogs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wmz.blog.blogs.model.TBlogs;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmz
 * @since 2021-01-24
 */
public interface ITBlogsService extends IService<TBlogs> {

    IPage<TBlogs> selectPage(Page<TBlogs> page, String id);
}
