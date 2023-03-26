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
import com.ruoyi.lxgl.domain.Tsg;
import com.ruoyi.lxgl.service.ITsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 图书管理Controller
 *
 * @author Hainchen
 * @date 2023-03-06
 */
@RestController
@RequestMapping("/lxgl/tsg")
public class TsgController extends BaseController
{
    @Autowired
    private ITsgService tsgService;


    @Autowired
    private TokenService tokenService;


    @Log(title = "借阅记录管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('lxgl:tsg:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Tsg> util = new ExcelUtil<Tsg>(Tsg.class);
        List<Tsg> tsgList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = tsgService.importBorrowed(tsgList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Tsg> util = new ExcelUtil<Tsg>(Tsg.class);
        util.importTemplateExcel(response,"借阅数据");
    }

    /**
     * 查询图书管理列表
     */
    @PreAuthorize("@ss.hasPermi('lxgl:tsg:list')")
    @GetMapping("/list")
    public TableDataInfo list(Tsg tsg)
    {
        startPage();
        List<Tsg> list = tsgService.selectTsgList(tsg);
        return getDataTable(list);
    }

    /**
     * 导出图书管理列表
     */
    @PreAuthorize("@ss.hasPermi('lxgl:tsg:export')")
    @Log(title = "图书管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Tsg tsg)
    {
        List<Tsg> list = tsgService.selectTsgList(tsg);
        ExcelUtil<Tsg> util = new ExcelUtil<Tsg>(Tsg.class);
        util.exportExcel(response, list, "图书管理数据");
    }

    /**
     * 获取图书管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('lxgl:tsg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tsgService.selectTsgById(id));
    }

    /**
     * 新增图书管理
     */
    @PreAuthorize("@ss.hasPermi('lxgl:tsg:add')")
    @Log(title = "图书管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Tsg tsg)
    {
        return toAjax(tsgService.insertTsg(tsg));
    }

    /**
     * 修改图书管理
     */
    @PreAuthorize("@ss.hasPermi('lxgl:tsg:edit')")
    @Log(title = "图书管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Tsg tsg)
    {
        return toAjax(tsgService.updateTsg(tsg));
    }

    /**
     * 删除图书管理
     */
    @PreAuthorize("@ss.hasPermi('lxgl:tsg:remove')")
    @Log(title = "图书管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsgService.deleteTsgByIds(ids));
    }
}
