package com.ruoyi.web.controller.ops;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysThirdAccess;
import com.ruoyi.system.service.ISysThirdAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 第三方AccessKey管理Controller
 *
 * @author ruoyi
 * @date 2022-08-14
 */
@RestController
@RequestMapping("/ops/access")
public class SysThirdAccessController extends BaseController {
    @Autowired
    private ISysThirdAccessService sysThirdAccessService;

    /**
     * 查询第三方AccessKey管理列表
     */
    @PreAuthorize("@ss.hasPermi('ops:access:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysThirdAccess sysThirdAccess) {
        startPage();
        List<SysThirdAccess> list = sysThirdAccessService.selectSysThirdAccessList(sysThirdAccess);
        return getDataTable(list);
    }

    /**
     * 导出第三方AccessKey管理列表
     */
    @PreAuthorize("@ss.hasPermi('ops:access:export')")
    @Log(title = "第三方AccessKey管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysThirdAccess sysThirdAccess) {
        List<SysThirdAccess> list = sysThirdAccessService.selectSysThirdAccessList(sysThirdAccess);
        ExcelUtil<SysThirdAccess> util = new ExcelUtil<SysThirdAccess>(SysThirdAccess.class);
        util.exportExcel(response, list, "第三方AccessKey管理数据");
    }

    /**
     * 获取第三方AccessKey管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ops:access:query')")
    @GetMapping(value = "/{accessId}")
    public AjaxResult getInfo(@PathVariable("accessId") Long accessId) {
        return AjaxResult.success(sysThirdAccessService.selectSysThirdAccessByAccessId(accessId));
    }

    /**
     * 新增第三方AccessKey管理
     */
    @PreAuthorize("@ss.hasPermi('ops:access:add')")
    @Log(title = "第三方AccessKey管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysThirdAccess sysThirdAccess) {
        return toAjax(sysThirdAccessService.insertSysThirdAccess(sysThirdAccess));
    }

    /**
     * 修改第三方AccessKey管理
     */
    @PreAuthorize("@ss.hasPermi('ops:access:edit')")
    @Log(title = "第三方AccessKey管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysThirdAccess sysThirdAccess) {
        return toAjax(sysThirdAccessService.updateSysThirdAccess(sysThirdAccess));
    }

    /**
     * 删除第三方AccessKey管理
     */
    @PreAuthorize("@ss.hasPermi('ops:access:remove')")
    @Log(title = "第三方AccessKey管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{accessIds}")
    public AjaxResult remove(@PathVariable Long[] accessIds) {
        return toAjax(sysThirdAccessService.deleteSysThirdAccessByAccessIds(accessIds));
    }
}
