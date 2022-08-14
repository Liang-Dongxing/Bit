package com.ruoyi.system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色和菜单关联 sys_role_menu
 * 
 * @author ruoyi
 */
@Data
@NoArgsConstructor
public class SysRoleMenu
{
    /** 角色ID */
    private Long roleId;
    
    /** 菜单ID */
    private Long menuId;
}
