package com.ruoyi.web.controller.ops;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysDdns;
import com.ruoyi.system.service.ISysDdnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ddns 解析配置Controller
 *
 * @author ruoyi
 * @date 2022-08-14
 */
@RestController
@RequestMapping("/ops/ddns")
public class SysDdnsController extends BaseController {
    @Autowired
    private ISysDdnsService sysDdnsService;

    /**
     * 查询ddns 解析配置列表
     */
    @PreAuthorize("@ss.hasPermi('ops:ddns:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDdns sysDdns) {
        startPage();
        List<SysDdns> list = sysDdnsService.selectSysDdnsList(sysDdns);
        return getDataTable(list);
    }

    /**
     * 导出ddns 解析配置列表
     */
    @PreAuthorize("@ss.hasPermi('ops:ddns:export')")
    @Log(title = "ddns 解析配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDdns sysDdns) {
        List<SysDdns> list = sysDdnsService.selectSysDdnsList(sysDdns);
        ExcelUtil<SysDdns> util = new ExcelUtil<SysDdns>(SysDdns.class);
        util.exportExcel(response, list, "ddns 解析配置数据");
    }

    /**
     * 获取ddns 解析配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('ops:ddns:query')")
    @GetMapping(value = "/{ddnsId}")
    public AjaxResult getInfo(@PathVariable("ddnsId") Long ddnsId) {
        return AjaxResult.success(sysDdnsService.selectSysDdnsByDdnsId(ddnsId));
    }

    /**
     * 新增ddns 解析配置
     */
    @PreAuthorize("@ss.hasPermi('ops:ddns:add')")
    @Log(title = "ddns 解析配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDdns sysDdns) {
        return toAjax(sysDdnsService.insertSysDdns(sysDdns));
    }

    /**
     * 修改ddns 解析配置
     */
    @PreAuthorize("@ss.hasPermi('ops:ddns:edit')")
    @Log(title = "ddns 解析配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDdns sysDdns) {
        return toAjax(sysDdnsService.updateSysDdns(sysDdns));
    }

    /**
     * 删除ddns 解析配置
     */
    @PreAuthorize("@ss.hasPermi('ops:ddns:remove')")
    @Log(title = "ddns 解析配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ddnsIds}")
    public AjaxResult remove(@PathVariable Long[] ddnsIds) {
        return toAjax(sysDdnsService.deleteSysDdnsByDdnsIds(ddnsIds));
    }
}
