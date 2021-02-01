package com.wmz.blog.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmz.blog.message.model.TMessage;
import com.wmz.blog.message.mapper.TMessageMapper;
import com.wmz.blog.message.service.ITMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.MapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmz
 * @since 2021-01-28
 */
@Service
public class TMessageServiceImpl extends ServiceImpl<TMessageMapper, TMessage> implements ITMessageService {

    @Autowired
    private TMessageMapper tMessageMapper;


    @Override
    public List<TMessage> selectAll(TMessage tMessages) {
        List<TMessage> list = tMessageMapper.selectAll(tMessages);
        List<TMessage> tMessageList = new ArrayList<>();
        for (TMessage tMessage : list) {
            String pid = tMessage.getPid();
            if (pid == null||pid .equals("0")){
                //是顶级留言
                tMessageList.add(tMessage);
                //continue;
            }

            //不是顶级留言
/*            for (TMessage message : list) {
                if (message.getId()!=null&&message.getId().equals(pid)){
                    tMessage.setParentNickname(message.getNickname());
                    //说明这个留言是上个留言的父级
                    if (message.getReplyMessages() == null){
                        message.setReplyMessages(new ArrayList<>());
                        message.getReplyMessages().add(tMessage);
                    }else {
                        message.getReplyMessages().add(tMessage);
                    }
                }
            }*/
        }
        for (TMessage tMessage : tMessageList) {
            tMessage = getChildIds(tMessage, list, tMessage);

        }

        return tMessageList;
    }




    /**
     * 获取子级ids，含自己
     * @param tMessage 父节点
     * @param messageList 消息列表
     * @return
     */
    public TMessage getChildIds(TMessage tMessage, List<TMessage> messageList,TMessage tMessage2) {

        this.getChildIdsTo(tMessage, messageList,tMessage2);
        //return childIds.toString().substring(0, childIds.length()-1);
        return tMessage2;
    }

    private void getChildIdsTo(TMessage tMessage, List<TMessage> messageList,TMessage tMessage2) {
        for (TMessage bureau : messageList) {
            //过滤父节点为空的数据
            if (bureau.getPid() == null||bureau.getPid().equals("0")){
                continue;
            }
            // 判断是否存在子节点
            if (tMessage.getId().equals(bureau.getPid())) {
                bureau.setParentNickname(tMessage.getNickname());
                if (tMessage2.getReplyMessages() == null){
                    tMessage2.setReplyMessages(new ArrayList<>());
                    tMessage2.getReplyMessages().add(bureau);
                }else {
                    tMessage2.getReplyMessages().add(bureau);
                }
                // 递归遍历下一级
                getChildIds(bureau, messageList,tMessage2);
            }
        }
        return;
    }
}
