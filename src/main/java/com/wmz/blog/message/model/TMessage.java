package com.wmz.blog.message.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wmz
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TMessage extends Model {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 留言人头像
     */
    @TableField(exist = false)
    private String avatar;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 留言人名称
     */
    @TableField(exist = false)
    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 子关联
     */
    private String pid;

    private String createUser;

    private String type;

    @TableLogic
    private String status;

    @TableField(exist = false)
    private List<TMessage> replyMessages;

    @TableField(exist = false)
    private String parentNickname;


    @TableField(exist = false)
    private String identity;

    @TableField(exist = false)
    private String email;

    private String blogId;
}
