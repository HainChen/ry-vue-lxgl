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
import com.ruoyi.lxgl.domain.Jwc;
import com.ruoyi.lxgl.service.IJwcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 教务管理Controller
 *
 * @author Hainchen
 * @date 2023-03-05
 */
@RestController
@RequestMapping("/lxgl/jwc")
public class JwcController extends BaseController
{
    @Autowired
    private IJwcService jwcService;

    @Autowired
    private TokenService tokenService;

    @Log(title = "教务处管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('lxgl:jwc:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Jwc> util = new ExcelUtil<Jwc>(Jwc.class);
        List<Jwc> jwcList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = jwcService.importCourse(jwcList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Jwc> util = new ExcelUtil<Jwc>(Jwc.class);
        util.importTemplateExcel(response,"挂科数据");
    }
    /**
     * 查询教务管理列表
     */
    @PreAuthorize("@ss.hasPermi('lxgl:jwc:list')")
    @GetMapping("/list")
    public TableDataInfo list(Jwc jwc)
    {
        startPage();
        List<Jwc> list = jwcService.selectJwcList(jwc);
        return getDataTable(list);
    }

    /**
     * 导出教务管理列表
     */
    @PreAuthorize("@ss.hasPermi('lxgl:jwc:export')")
    @Log(title = "教务管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Jwc jwc)
    {
        List<Jwc> list = jwcService.selectJwcList(jwc);
        ExcelUtil<Jwc> util = new ExcelUtil<Jwc>(Jwc.class);
        util.exportExcel(response, list, "教务管理数据");
    }

    /**
     * 获取教务管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('lxgl:jwc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jwcService.selectJwcById(id));
    }

    /**
     * 新增教务管理
     */
    @PreAuthorize("@ss.hasPermi('lxgl:jwc:add')")
    @Log(title = "教务管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Jwc jwc)
    {
        return toAjax(jwcService.insertJwc(jwc));
    }

    /**
     * 修改教务管理
     */
    @PreAuthorize("@ss.hasPermi('lxgl:jwc:edit')")
    @Log(title = "教务管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Jwc jwc)
    {
        return toAjax(jwcService.updateJwc(jwc));
    }

    /**
     * 删除教务管理
     */
    @PreAuthorize("@ss.hasPermi('lxgl:jwc:remove')")
    @Log(title = "教务管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jwcService.deleteJwcByIds(ids));
    }
}
