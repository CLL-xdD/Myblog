package com.wmz.blog.blogs.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;

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
 * @since 2021-01-24
 */
@Data

public class TBlogs extends Model {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 发布人头像
     */
    private String avatar;

    /**
     * 发布人名称
     */
    private String nickname;


    /**
     * 是否推荐
     */
    private boolean recommend;

    /**
     * 1=发布 0=草稿
     */
    private boolean published;

    /**
     * 状态
     */
    @TableLogic
    private String status;

    /**
     *类型
     */
    private String typeId;


    /**
     * 标签
     */
    private String flag;

    /**
     * 内容
     */
    private String content;

    private String description;

    //图片地址
    private String firstPicture;

    //转载
    private boolean shareStatement;

    private String views;

    //赞赏
    private boolean appreciation;

    //评论
    private boolean commentabled;

    private String commentCount;

    /**
     * 创建人
     */
    private String createUser;

    private String typeName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String time;

    @TableField(exist = false)
    private String query;

}
