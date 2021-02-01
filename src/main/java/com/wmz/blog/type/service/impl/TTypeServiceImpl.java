package com.wmz.blog.type.service.impl;

import com.wmz.blog.type.model.TType;
import com.wmz.blog.type.mapper.TTypeMapper;
import com.wmz.blog.type.service.ITTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2021-01-24
 */
@Service
public class TTypeServiceImpl extends ServiceImpl<TTypeMapper, TType> implements ITTypeService {

    @Autowired
    private TTypeMapper tTypeMapper;

    @Override
    public List<TType> selectTypes() {
        return tTypeMapper.selectTypes();
    }
}
