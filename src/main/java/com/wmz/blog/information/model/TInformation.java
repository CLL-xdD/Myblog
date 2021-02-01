package com.wmz.blog.information.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author astupidcoder
 * @since 2021-01-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TInformation extends Model {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    //文章数量
    private String blogCount;
    //访客数量
    private String visitCount;
    //评论总数
    private String commentCount;
    //留言总数
    private String messageCount;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}
