package com.bit.system.domain;

import com.bit.common.annotation.Excel;
import com.bit.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * ddns 解析配置对象 sys_ddns
 *
 * @author bit
 * @date 2022-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SysDdns extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ddns ID
     */
    private Long ddnsId;

    /**
     * 域名
     */
    @Excel(name = "域名")
    private String domain;

    /**
     * 主机记录
     */
    @Excel(name = "主机记录")
    private String hostRecord;

    /**
     * 解析记录类型
     */
    @Excel(name = "解析记录类型")
    private String parseRecordType;

    /**
     * 记录值
     */
    @Excel(name = "记录值")
    private String recordTheValue;

    /**
     * Access ID
     */
    @Excel(name = "Access ID")
    private Long accessId;

}
