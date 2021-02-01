package com.wmz.blog.type.mapper;

import com.wmz.blog.type.model.TType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2021-01-24
 */
public interface TTypeMapper extends BaseMapper<TType> {

    List<TType> selectTypes();

}
