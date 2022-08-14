package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 第三方AccessKey管理对象 sys_third_access
 * 
 * @author ruoyi
 * @date 2022-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SysThirdAccess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Access ID */
    private Long accessId;

    /** AccessKey Secret */
    @Excel(name = "AccessKey Secret")
    private String accessKeySecret;

    /** AccessKey ID */
    @Excel(name = "AccessKey ID")
    private String accessKeyId;

    /** AccessKey 平台 */
    @Excel(name = "AccessKey 平台")
    private String accessKeyType;

}
