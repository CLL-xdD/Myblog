package com.wmz.blog.message.service;

import com.wmz.blog.message.model.TMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmz
 * @since 2021-01-28
 */
public interface ITMessageService extends IService<TMessage> {

    List<TMessage> selectAll(TMessage type);

}
