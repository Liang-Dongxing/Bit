package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysDdns;
import com.ruoyi.system.mapper.SysDdnsMapper;
import com.ruoyi.system.service.ISysDdnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ddns 解析配置Service业务层处理
 *
 * @author ruoyi
 * @date 2022-08-14
 */
@Service
public class SysDdnsServiceImpl implements ISysDdnsService {
    @Autowired
    private SysDdnsMapper sysDdnsMapper;

    /**
     * 查询ddns 解析配置
     *
     * @param ddnsId ddns 解析配置主键
     * @return ddns 解析配置
     */
    @Override
    public SysDdns selectSysDdnsByDdnsId(Long ddnsId) {
        return sysDdnsMapper.selectSysDdnsByDdnsId(ddnsId);
    }

    /**
     * 查询ddns 解析配置列表
     *
     * @param sysDdns ddns 解析配置
     * @return ddns 解析配置
     */
    @Override
    public List<SysDdns> selectSysDdnsList(SysDdns sysDdns) {
        return sysDdnsMapper.selectSysDdnsList(sysDdns);
    }

    /**
     * 新增ddns 解析配置
     *
     * @param sysDdns ddns 解析配置
     * @return 结果
     */
    @Override
    public int insertSysDdns(SysDdns sysDdns) {
        sysDdns.setCreateTime(DateUtils.getNowDate());
        return sysDdnsMapper.insertSysDdns(sysDdns);
    }

    /**
     * 修改ddns 解析配置
     *
     * @param sysDdns ddns 解析配置
     * @return 结果
     */
    @Override
    public int updateSysDdns(SysDdns sysDdns) {
        sysDdns.setUpdateTime(DateUtils.getNowDate());
        return sysDdnsMapper.updateSysDdns(sysDdns);
    }

    /**
     * 批量删除ddns 解析配置
     *
     * @param ddnsIds 需要删除的ddns 解析配置主键
     * @return 结果
     */
    @Override
    public int deleteSysDdnsByDdnsIds(Long[] ddnsIds) {
        return sysDdnsMapper.deleteSysDdnsByDdnsIds(ddnsIds);
    }

    /**
     * 删除ddns 解析配置信息
     *
     * @param ddnsId ddns 解析配置主键
     * @return 结果
     */
    @Override
    public int deleteSysDdnsByDdnsId(Long ddnsId) {
        return sysDdnsMapper.deleteSysDdnsByDdnsId(ddnsId);
    }
}
