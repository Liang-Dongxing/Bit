package com.bit.system.service.impl;

import com.bit.common.utils.DateUtils;
import com.bit.system.domain.SysThirdAccess;
import com.bit.system.mapper.SysThirdAccessMapper;
import com.bit.system.service.ISysThirdAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 第三方AccessKey管理Service业务层处理
 *
 * @author bit
 * @date 2022-08-14
 */
@Service
public class SysThirdAccessServiceImpl implements ISysThirdAccessService {
    @Autowired
    private SysThirdAccessMapper sysThirdAccessMapper;

    /**
     * 查询第三方AccessKey管理
     *
     * @param accessId 第三方AccessKey管理主键
     * @return 第三方AccessKey管理
     */
    @Override
    public SysThirdAccess selectSysThirdAccessByAccessId(Long accessId) {
        return sysThirdAccessMapper.selectSysThirdAccessByAccessId(accessId);
    }

    /**
     * 查询第三方AccessKey管理列表
     *
     * @param sysThirdAccess 第三方AccessKey管理
     * @return 第三方AccessKey管理
     */
    @Override
    public List<SysThirdAccess> selectSysThirdAccessList(SysThirdAccess sysThirdAccess) {
        return sysThirdAccessMapper.selectSysThirdAccessList(sysThirdAccess);
    }

    /**
     * 新增第三方AccessKey管理
     *
     * @param sysThirdAccess 第三方AccessKey管理
     * @return 结果
     */
    @Override
    public int insertSysThirdAccess(SysThirdAccess sysThirdAccess) {
        sysThirdAccess.setCreateTime(DateUtils.getNowDate());
        return sysThirdAccessMapper.insertSysThirdAccess(sysThirdAccess);
    }

    /**
     * 修改第三方AccessKey管理
     *
     * @param sysThirdAccess 第三方AccessKey管理
     * @return 结果
     */
    @Override
    public int updateSysThirdAccess(SysThirdAccess sysThirdAccess) {
        sysThirdAccess.setUpdateTime(DateUtils.getNowDate());
        return sysThirdAccessMapper.updateSysThirdAccess(sysThirdAccess);
    }

    /**
     * 批量删除第三方AccessKey管理
     *
     * @param accessIds 需要删除的第三方AccessKey管理主键
     * @return 结果
     */
    @Override
    public int deleteSysThirdAccessByAccessIds(Long[] accessIds) {
        return sysThirdAccessMapper.deleteSysThirdAccessByAccessIds(accessIds);
    }

    /**
     * 删除第三方AccessKey管理信息
     *
     * @param accessId 第三方AccessKey管理主键
     * @return 结果
     */
    @Override
    public int deleteSysThirdAccessByAccessId(Long accessId) {
        return sysThirdAccessMapper.deleteSysThirdAccessByAccessId(accessId);
    }
}
