package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysThirdAccess;

import java.util.List;

/**
 * 第三方AccessKey管理Service接口
 *
 * @author ruoyi
 * @date 2022-08-14
 */
public interface ISysThirdAccessService {
    /**
     * 查询第三方AccessKey管理
     *
     * @param accessId 第三方AccessKey管理主键
     * @return 第三方AccessKey管理
     */
    public SysThirdAccess selectSysThirdAccessByAccessId(Long accessId);

    /**
     * 查询第三方AccessKey管理列表
     *
     * @param sysThirdAccess 第三方AccessKey管理
     * @return 第三方AccessKey管理集合
     */
    public List<SysThirdAccess> selectSysThirdAccessList(SysThirdAccess sysThirdAccess);

    /**
     * 新增第三方AccessKey管理
     *
     * @param sysThirdAccess 第三方AccessKey管理
     * @return 结果
     */
    public int insertSysThirdAccess(SysThirdAccess sysThirdAccess);

    /**
     * 修改第三方AccessKey管理
     *
     * @param sysThirdAccess 第三方AccessKey管理
     * @return 结果
     */
    public int updateSysThirdAccess(SysThirdAccess sysThirdAccess);

    /**
     * 批量删除第三方AccessKey管理
     *
     * @param accessIds 需要删除的第三方AccessKey管理主键集合
     * @return 结果
     */
    public int deleteSysThirdAccessByAccessIds(Long[] accessIds);

    /**
     * 删除第三方AccessKey管理信息
     *
     * @param accessId 第三方AccessKey管理主键
     * @return 结果
     */
    public int deleteSysThirdAccessByAccessId(Long accessId);
}
