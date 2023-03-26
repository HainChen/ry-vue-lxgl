package com.ruoyi.web.controller.lxgl;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.lxgl.domain.CollegeMess;
import com.ruoyi.lxgl.domain.Lxhj;
import com.ruoyi.lxgl.domain.LxhjHandleStatus;
import com.ruoyi.lxgl.service.ILxhjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 离校资格Controller
 *
 * @author Hainchen
 * @date 2023-03-07
 */
@RestController
@RequestMapping("/lxgl/hj")
public class LxhjController extends BaseController {
    @Autowired
    private ILxhjService lxhjService;

    /**
     * 查询离校资格列表
     */
    @PreAuthorize("@ss.hasPermi('lxgl:hj:list')")
    @GetMapping("/list")
    public TableDataInfo list(Lxhj lxhj) {
        startPage();
        List<Lxhj> list = lxhjService.selectLxhjList(lxhj);
        return getDataTable(list);
    }

    /**
     * @return void
     * @decription 获取学院的毕业生人数
     * @date 2023/3/22 15:06
    */
    @GetMapping("/getcollegePersonNum")
    public List<CollegeMess> getcollegePersonNum(){
        return lxhjService.getcollegePersonNum();
    }

    @GetMapping("/getcollegeLxss")
    public List<CollegeMess> getcollegeLxss(){
        return lxhjService.getcollegeLxss();
    }

    /**
     * @return void
     * @decription 更新离校申请信息
     * @date 2023/3/22 16:45
    */
    @GetMapping("/updateList")
    public void updateList() {
       lxhjService.initStudentLXMsg(new Lxhj());
    }

    /**
     * @return com.ruoyi.lxgl.domain.LxhjHandleStatus
     * @decription 获取各环节业务办理情况
     * @date 2023/3/22 14:21
    */
    @GetMapping("/getLxhjHandleStatus")
    public LxhjHandleStatus getLxhjHandleStatus(){
        return lxhjService.getLxhjHandleStatus();
    }

    /**
     * 导出离校资格列表
     */
    @PreAuthorize("@ss.hasPermi('lxgl:hj:export')")
    @Log(title = "离校资格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Lxhj lxhj) {
        List<Lxhj> list = lxhjService.selectLxhjList(lxhj);
        ExcelUtil<Lxhj> util = new ExcelUtil<Lxhj>(Lxhj.class);
        util.exportExcel(response, list, "离校资格数据");
    }

    /**
     * 获取离校资格详细信息
     */
    @PreAuthorize("@ss.hasPermi('lxgl:hj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(lxhjService.selectLxhjById(id));
    }


    //不能根据离校信息表查询，因为只有全部环节完成，才可以查得到，只能用多个mappper查询。
    @GetMapping(value = "/stu")
    public Lxhj getStudentInfo() {
        //获取当前登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser currentUser = loginUser.getUser();
        Lxhj lxhj = lxhjService.selectLxhjByStuId(currentUser.getUserId());
        if(lxhj==null){
            return null;
        }
        return lxhj;
    }

    //更新最新离校申请数据
    @PutMapping("/updateLxhjMess")
    public void updateLxhjMess(){
        lxhjService.initStudentLXMsg(new Lxhj());
    }

    /**
     * 新增离校资格
     */
    @PreAuthorize("@ss.hasPermi('lxgl:hj:add')")
    @Log(title = "离校资格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Lxhj lxhj) {
        return toAjax(lxhjService.insertLxhj(lxhj));
    }

    /**
     * 修改离校资格
     */
    @PreAuthorize("@ss.hasPermi('lxgl:hj:edit')")
    @Log(title = "离校资格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Lxhj lxhj) {
        return toAjax(lxhjService.updateLxhj(lxhj));
    }

    /**
     * 删除离校资格
     */
    @PreAuthorize("@ss.hasPermi('lxgl:hj:remove')")
    @Log(title = "离校资格", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(lxhjService.deleteLxhjByIds(ids));
    }
}
