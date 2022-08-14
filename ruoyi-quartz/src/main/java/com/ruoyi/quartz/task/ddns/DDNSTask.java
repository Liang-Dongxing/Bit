package com.ruoyi.quartz.task.ddns;

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
        List<SysDdns> sysDdns = iSysDdnsService.selectSysDdnsList(null);
        for (SysDdns sysDdn : sysDdns) {
            SysThirdAccess sysThirdAccess = iSysThirdAccessService.selectSysThirdAccessByAccessId(sysDdn.getAccessId());
            getDdns(sysDdn, sysThirdAccess);
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
        ddns.log_print("describeDomainRecords", describeDomainRecordsResponse);

        List<DescribeDomainRecordsResponse.Record> domainRecords = describeDomainRecordsResponse.getDomainRecords();
        for (DescribeDomainRecordsResponse.Record record : domainRecords) {
            // 当前主机公网IP
            String currentHostIP = ddns.getCurrentHostIP();
            log.info("-------------------------------当前主机公网IP为：" + currentHostIP + "-------------------------------");
            if (!currentHostIP.equals(record.getValue())) {
                // 修改解析记录
                UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest();
                // 主机记录
                updateDomainRecordRequest.setRR(record.getRR());
                // 记录ID
                updateDomainRecordRequest.setRecordId(record.getRecordId());
                // 将主机记录值改为当前主机IP
                updateDomainRecordRequest.setValue(currentHostIP);
                // 解析记录类型
                updateDomainRecordRequest.setType(sysDdns.getParseRecordType());
                UpdateDomainRecordResponse updateDomainRecordResponse = ddns.updateDomainRecord(updateDomainRecordRequest, client);
                ddns.log_print("updateDomainRecord", updateDomainRecordResponse);
            }
        }
    }
}
