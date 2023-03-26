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
import com.ruoyi.lxgl.domain.Dormitory;
import com.ruoyi.lxgl.service.IDormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 家具赔偿信息Controller
 *
 * @author Hainchen
 * @date 2023-03-06
 */
@RestController
@RequestMapping("/lxgl/ss")
public class DormitoryController extends BaseController
{
    @Autowired
    private IDormitoryService dormitoryService;

    @Autowired
    private TokenService tokenService;

    @Log(title = "宿舍管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('lxgl:ss:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Dormitory> util = new ExcelUtil<Dormitory>(Dormitory.class);
        List<Dormitory> furnitureList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = dormitoryService.importFurniturMsg(furnitureList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Dormitory> util = new ExcelUtil<Dormitory>(Dormitory.class);
        util.importTemplateExcel(response,"家具数据");
    }
    /**
     * 查询家具赔偿信息列表
     */
    @PreAuthorize("@ss.hasPermi('lxgl:ss:list')")
    @GetMapping("/list")
    public TableDataInfo list(Dormitory dormitory)
    {
        startPage();
        List<Dormitory> list = dormitoryService.selectDormitoryList(dormitory);
        return getDataTable(list);
    }

    /**
     * 导出家具赔偿信息列表
     */
    @PreAuthorize("@ss.hasPermi('lxgl:ss:export')")
    @Log(title = "家具赔偿信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Dormitory dormitory)
    {
        List<Dormitory> list = dormitoryService.selectDormitoryList(dormitory);
        ExcelUtil<Dormitory> util = new ExcelUtil<Dormitory>(Dormitory.class);
        util.exportExcel(response, list, "家具赔偿信息数据");
    }

    /**
     * 获取家具赔偿信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('lxgl:ss:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dormitoryService.selectDormitoryById(id));
    }

    /**
     * 新增家具赔偿信息
     */
    @PreAuthorize("@ss.hasPermi('lxgl:ss:add')")
    @Log(title = "家具赔偿信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Dormitory dormitory)
    {
        return toAjax(dormitoryService.insertDormitory(dormitory));
    }

    /**
     * 修改家具赔偿信息
     */
    @PreAuthorize("@ss.hasPermi('lxgl:ss:edit')")
    @Log(title = "家具赔偿信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Dormitory dormitory)
    {
        return toAjax(dormitoryService.updateDormitory(dormitory));
    }

    /**
     * 删除家具赔偿信息
     */
    @PreAuthorize("@ss.hasPermi('lxgl:ss:remove')")
    @Log(title = "家具赔偿信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dormitoryService.deleteDormitoryByIds(ids));
    }
}
