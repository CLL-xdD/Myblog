package com.wmz.blog.system.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends Model {

    @TableId
    private String id;

    private String username;

    private String nickname;

    private String email;

    private String avatar;

    private String password;

    private String createTime;

    @TableLogic
    private String status;
}
