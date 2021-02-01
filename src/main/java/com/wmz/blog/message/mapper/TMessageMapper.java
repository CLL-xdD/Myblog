package com.wmz.blog.message.mapper;

import com.wmz.blog.message.model.TMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wmz
 * @since 2021-01-28
 */
public interface TMessageMapper extends BaseMapper<TMessage> {

    List<TMessage> selectAll(TMessage type);

    List<TMessage> selectSon(String pid);

}
