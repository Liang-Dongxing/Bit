package com.bit.web.controller.ops;

import com.bit.common.annotation.Log;
import com.bit.common.core.controller.BaseController;
import com.bit.common.core.domain.AjaxResult;
import com.bit.common.core.page.TableDataInfo;
import com.bit.common.enums.BusinessType;
import com.bit.common.utils.poi.ExcelUtil;
import com.bit.system.domain.SysThirdAccess;
import com.bit.system.service.ISysThirdAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 第三方AccessKey管理Controller
 *
 * @author bit
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
    @PreAuthorize("@ss.hasPermi('tool:ak:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysThirdAccess sysThirdAccess) {
        startPage();
        List<SysThirdAccess> list = sysThirdAccessService.selectSysThirdAccessList(sysThirdAccess);
        return getDataTable(list);
    }

    /**
     * 导出第三方AccessKey管理列表
     */
    @PreAuthorize("@ss.hasPermi('tool:ak:export')")
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
    @PreAuthorize("@ss.hasPermi('tool:ak:query')")
    @GetMapping(value = "/{accessId}")
    public AjaxResult getInfo(@PathVariable("accessId") Long accessId) {
        return AjaxResult.success(sysThirdAccessService.selectSysThirdAccessByAccessId(accessId));
    }

    /**
     * 新增第三方AccessKey管理
     */
    @PreAuthorize("@ss.hasPermi('tool:ak:add')")
    @Log(title = "第三方AccessKey管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysThirdAccess sysThirdAccess) {
        return toAjax(sysThirdAccessService.insertSysThirdAccess(sysThirdAccess));
    }

    /**
     * 修改第三方AccessKey管理
     */
    @PreAuthorize("@ss.hasPermi('tool:ak:edit')")
    @Log(title = "第三方AccessKey管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysThirdAccess sysThirdAccess) {
        return toAjax(sysThirdAccessService.updateSysThirdAccess(sysThirdAccess));
    }

    /**
     * 删除第三方AccessKey管理
     */
    @PreAuthorize("@ss.hasPermi('tool:ak:remove')")
    @Log(title = "第三方AccessKey管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{accessIds}")
    public AjaxResult remove(@PathVariable Long[] accessIds) {
        return toAjax(sysThirdAccessService.deleteSysThirdAccessByAccessIds(accessIds));
    }
}
