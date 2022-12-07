package com.bit.web.controller.ops;

import com.bit.common.annotation.Log;
import com.bit.common.core.controller.BaseController;
import com.bit.common.core.domain.AjaxResult;
import com.bit.common.core.page.TableDataInfo;
import com.bit.common.enums.BusinessType;
import com.bit.common.utils.poi.ExcelUtil;
import com.bit.system.domain.SysDdns;
import com.bit.system.service.ISysDdnsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ddns 解析配置Controller
 *
 * @author bit
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
    @PreAuthorize("@ss.hasPermi('tool:ddns:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDdns sysDdns) {
        startPage();
        List<SysDdns> list = sysDdnsService.selectSysDdnsList(sysDdns);
        return getDataTable(list);
    }

    /**
     * 导出ddns 解析配置列表
     */
    @PreAuthorize("@ss.hasPermi('tool:ddns:export')")
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
    @PreAuthorize("@ss.hasPermi('tool:ddns:query')")
    @GetMapping(value = "/{ddnsId}")
    public AjaxResult getInfo(@PathVariable("ddnsId") Long ddnsId) {
        return AjaxResult.success(sysDdnsService.selectSysDdnsByDdnsId(ddnsId));
    }

    /**
     * 新增ddns 解析配置
     */
    @PreAuthorize("@ss.hasPermi('tool:ddns:add')")
    @Log(title = "ddns 解析配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDdns sysDdns) {
        return toAjax(sysDdnsService.insertSysDdns(sysDdns));
    }

    /**
     * 修改ddns 解析配置
     */
    @PreAuthorize("@ss.hasPermi('tool:ddns:edit')")
    @Log(title = "ddns 解析配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDdns sysDdns) {
        return toAjax(sysDdnsService.updateSysDdns(sysDdns));
    }

    /**
     * 删除ddns 解析配置
     */
    @PreAuthorize("@ss.hasPermi('tool:ddns:remove')")
    @Log(title = "ddns 解析配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ddnsIds}")
    public AjaxResult remove(@PathVariable Long[] ddnsIds) {
        return toAjax(sysDdnsService.deleteSysDdnsByDdnsIds(ddnsIds));
    }
}
