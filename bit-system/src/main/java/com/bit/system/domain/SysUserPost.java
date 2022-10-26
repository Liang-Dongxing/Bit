package com.bit.system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户和岗位关联 sys_user_post
 *
 * @author bit
 */
@Data
@NoArgsConstructor
public class SysUserPost {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;
}
