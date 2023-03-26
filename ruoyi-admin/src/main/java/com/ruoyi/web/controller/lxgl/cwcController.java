package com.ruoyi.web.controller.lxgl;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.lxgl.domain.Cwc;
import com.ruoyi.lxgl.service.IcwcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 财务处管理Controller
 *
 * @author Hainchen
 * @date 2023-03-04
 */
@RestController
@RequestMapping("/lxgl/cwc")
public class cwcController extends BaseController
{
    @Autowired
    private IcwcService cwcService;

    @Autowired
    private TokenService tokenService;

    @Log(title = "财务处管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('lxgl:cwc:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Cwc> util = new ExcelUtil<Cwc>(Cwc.class);
        List<Cwc> cwcList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = cwcService.importArrears(cwcList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Cwc> util = new ExcelUtil<Cwc>(Cwc.class);
        util.importTemplateExcel(response,"欠费数据");
    }
    /**
     * 查询财务处管理列表
     */
    @PreAuthorize("@ss.hasPermi('lxgl:cwc:list')")
    @GetMapping("/list")
    public TableDataInfo list(Cwc cwc)
    {
        startPage();
        List<Cwc> list = cwcService.selectcwcList(cwc);
        return getDataTable(list);
    }

    /**
     * 导出财务处管理列表
     */
    @PreAuthorize("@ss.hasPermi('lxgl:cwc:export')")
    @Log(title = "财务处管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Cwc cwc)
    {
        List<Cwc> list = cwcService.selectcwcList(cwc);
        ExcelUtil<Cwc> util = new ExcelUtil<Cwc>(Cwc.class);
        util.exportExcel(response, list, "财务处管理数据");
    }

    /**
     * 获取财务处管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('lxgl:cwc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(cwcService.selectcwcById(id));
    }

    /**
     * 新增财务处管理
     */
    @PreAuthorize("@ss.hasPermi('lxgl:cwc:add')")
    @Log(title = "财务处管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Cwc cwc)
    {
        return toAjax(cwcService.insertcwc(cwc));
    }

    /**
     * 修改财务处管理
     */
    @PreAuthorize("@ss.hasPermi('lxgl:cwc:edit')")
    @Log(title = "财务处管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Cwc cwc)
    {
        return toAjax(cwcService.updatecwc(cwc));
    }

    /**
     * 删除财务处管理
     */
    @PreAuthorize("@ss.hasPermi('lxgl:cwc:remove')")
    @Log(title = "财务处管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cwcService.deletecwcByIds(ids));
    }
}
