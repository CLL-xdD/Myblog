package com.wmz.blog.user.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wmz
 * @since 2021-01-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends Model {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String password;

    private String avatar;

    private String nickname;

    private String email;

    private LocalDateTime createTime;

    private String status;

    private String identity;


}
