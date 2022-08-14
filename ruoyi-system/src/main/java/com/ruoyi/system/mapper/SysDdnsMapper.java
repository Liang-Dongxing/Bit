package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysDdns;

import java.util.List;

/**
 * ddns 解析配置Mapper接口
 *
 * @author ruoyi
 * @date 2022-08-14
 */
public interface SysDdnsMapper {
    /**
     * 查询ddns 解析配置
     *
     * @param ddnsId ddns 解析配置主键
     * @return ddns 解析配置
     */
    public SysDdns selectSysDdnsByDdnsId(Long ddnsId);

    /**
     * 查询ddns 解析配置列表
     *
     * @param sysDdns ddns 解析配置
     * @return ddns 解析配置集合
     */
    public List<SysDdns> selectSysDdnsList(SysDdns sysDdns);

    /**
     * 新增ddns 解析配置
     *
     * @param sysDdns ddns 解析配置
     * @return 结果
     */
    public int insertSysDdns(SysDdns sysDdns);

    /**
     * 修改ddns 解析配置
     *
     * @param sysDdns ddns 解析配置
     * @return 结果
     */
    public int updateSysDdns(SysDdns sysDdns);

    /**
     * 删除ddns 解析配置
     *
     * @param ddnsId ddns 解析配置主键
     * @return 结果
     */
    public int deleteSysDdnsByDdnsId(Long ddnsId);

    /**
     * 批量删除ddns 解析配置
     *
     * @param ddnsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysDdnsByDdnsIds(Long[] ddnsIds);
}
