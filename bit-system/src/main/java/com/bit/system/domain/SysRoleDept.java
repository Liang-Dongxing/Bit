package com.bit.system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色和部门关联 sys_role_dept
 *
 * @author bit
 */
@Data
@NoArgsConstructor
public class SysRoleDept {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;
}
