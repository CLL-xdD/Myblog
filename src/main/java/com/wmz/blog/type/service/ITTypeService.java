package com.wmz.blog.type.service;

import com.wmz.blog.type.model.TType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2021-01-24
 */
public interface ITTypeService extends IService<TType> {

    List<TType> selectTypes();


}
