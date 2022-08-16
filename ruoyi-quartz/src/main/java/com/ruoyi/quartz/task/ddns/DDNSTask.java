package com.ruoyi.quartz.task.ddns;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.ruoyi.system.domain.SysDdns;
import com.ruoyi.system.domain.SysThirdAccess;
import com.ruoyi.system.service.ISysDdnsService;
import com.ruoyi.system.service.ISysThirdAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DDNS 定时调度任务
 *
 * @author ruoyi
 */
@Component("ddnsTask")
@Slf4j
public class DDNSTask {

    @Autowired
    private ISysDdnsService iSysDdnsService;
    @Autowired
    private ISysThirdAccessService iSysThirdAccessService;

    public void ryNoParams() {
        SysThirdAccess sysThirdAccess = new SysThirdAccess();
        sysThirdAccess.setAccessKeyType("aliyun");
        List<SysThirdAccess> sysThirdAccesses = iSysThirdAccessService.selectSysThirdAccessList(sysThirdAccess);
        for (SysThirdAccess thirdAccess : sysThirdAccesses) {
            SysDdns sysDdns = new SysDdns();
            sysDdns.setAccessId(thirdAccess.getAccessId());
            List<SysDdns> sysDdnsList = iSysDdnsService.selectSysDdnsList(sysDdns);
            for (SysDdns sysDdn : sysDdnsList) {
                SysThirdAccess sysThirdAccessTemp = iSysThirdAccessService.selectSysThirdAccessByAccessId(sysDdn.getAccessId());
                getDdns(sysDdn, sysThirdAccessTemp);
                iSysDdnsService.updateSysDdns(sysDdn);
            }
        }

    }

    private void getDdns(SysDdns sysDdns, SysThirdAccess sysThirdAccess) {
        // 设置鉴权参数，初始化客户端
        DefaultProfile profile = DefaultProfile.getProfile("cn-qingdao", sysThirdAccess.getAccessKeyId(), sysThirdAccess.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        AliyunDDNS ddns = new AliyunDDNS();

        // 查询指定二级域名的最新解析记录
        DescribeDomainRecordsRequest describeDomainRecordsRequest = new DescribeDomainRecordsRequest();
        // 主域名
        describeDomainRecordsRequest.setDomainName(sysDdns.getDomain());
        // 主机记录
        describeDomainRecordsRequest.setRRKeyWord(sysDdns.getHostRecord());
        // 解析记录类型
        describeDomainRecordsRequest.setType(sysDdns.getParseRecordType());
        DescribeDomainRecordsResponse describeDomainRecordsResponse = ddns.describeDomainRecords(describeDomainRecordsRequest, client);
        List<DescribeDomainRecordsResponse.Record> domainRecords = describeDomainRecordsResponse.getDomainRecords();
        // 在测试ddns更新的时候发现有时候， 一级域名@有的时候更新会变成以域名.之前的字符串生成二级域名，需要重新更具二级域名查询进行二次修改。
        if ("@".equals(sysDdns.getHostRecord()) && domainRecords.size() == 0) {
            String[] split = sysDdns.getDomain().split("\\.");
            describeDomainRecordsRequest.setRRKeyWord(split[0]);
            describeDomainRecordsResponse = ddns.describeDomainRecords(describeDomainRecordsRequest, client);
            domainRecords = describeDomainRecordsResponse.getDomainRecords();
        }
        log.info("describeDomainRecords");
        log.info(JSON.toJSONString(describeDomainRecordsResponse, JSONWriter.Feature.PrettyFormat));
        for (DescribeDomainRecordsResponse.Record record : domainRecords) {
            // 当前主机公网IP
            String currentHostIP = ddns.getCurrentHostIP();
            log.info("当前主机公网IP为：{}", currentHostIP);
            if (!currentHostIP.equals(record.getValue()) || !record.getRR().equals(sysDdns.getHostRecord())) {
                // 修改解析记录
                UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest();
                // 主机记录
                updateDomainRecordRequest.setRR(sysDdns.getHostRecord());
                // 记录ID
                updateDomainRecordRequest.setRecordId(record.getRecordId());
                // 将主机记录值改为当前主机IP
                updateDomainRecordRequest.setValue(currentHostIP);
                // 解析记录类型
                updateDomainRecordRequest.setType(sysDdns.getParseRecordType());
                UpdateDomainRecordResponse updateDomainRecordResponse = ddns.updateDomainRecord(updateDomainRecordRequest, client);
                log.info("updateDomainRecord");
                log.info(JSON.toJSONString(updateDomainRecordResponse, JSONWriter.Feature.PrettyFormat));
                sysDdns.setRecordTheValue(currentHostIP);
                if ("@".equals(sysDdns.getHostRecord())) {
                    getDdns(sysDdns, sysThirdAccess);
                }
            }
        }
    }
}
