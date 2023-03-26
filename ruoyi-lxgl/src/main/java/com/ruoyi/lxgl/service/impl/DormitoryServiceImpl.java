package com.ruoyi.lxgl.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.lxgl.domain.Dormitory;
import com.ruoyi.lxgl.mapper.DormitoryMapper;
import com.ruoyi.lxgl.service.IDormitoryService;
import com.ruoyi.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;

/**
 * 家具赔偿信息Service业务层处理
 *
 * @author Hainchen
 * @date 2023-03-06
 */
@Service
public class DormitoryServiceImpl implements IDormitoryService
{
    private static final Logger log = LoggerFactory.getLogger(cwcServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    protected Validator validator;

    @Autowired
    private DormitoryMapper dormitoryMapper;

    /**
     * 查询家具赔偿信息
     *
     * @param id 家具赔偿信息主键
     * @return 家具赔偿信息
     */
    @Override
    public Dormitory selectDormitoryById(Long id)
    {
        return dormitoryMapper.selectDormitoryById(id);
    }

    /**
     * 查询家具赔偿信息列表
     *
     * @param dormitory 家具赔偿信息
     * @return 家具赔偿信息
     */
    @Override
    public List<Dormitory> selectDormitoryList(Dormitory dormitory)
    {
        return dormitoryMapper.selectDormitoryList(dormitory);
    }

    /**
     * 新增家具赔偿信息
     *
     * @param dormitory 家具赔偿信息
     * @return 结果
     */
    @Override
    public int insertDormitory(Dormitory dormitory)
    {
        dormitory.setCreateTime(DateUtils.getNowDate());
        SysUser sysUser = sysUserMapper.selectUserById(dormitory.getStuId());
        if(sysUser==null)return 0;
        Dormitory isNull = dormitoryMapper.selectDormitoryByStuId(dormitory.getStuId());
        if(isNull!=null)return 0;

        return dormitoryMapper.insertDormitory(dormitory);
    }

    /**
     * 修改家具赔偿信息
     *
     * @param dormitory 家具赔偿信息
     * @return 结果
     */
    @Override
    public int updateDormitory(Dormitory dormitory)
    {
        dormitory.setUpdateTime(DateUtils.getNowDate());
        return dormitoryMapper.updateDormitory(dormitory);
    }

    /**
     * 批量删除家具赔偿信息
     *
     * @param ids 需要删除的家具赔偿信息主键
     * @return 结果
     */
    @Override
    public int deleteDormitoryByIds(Long[] ids)
    {
        return dormitoryMapper.deleteDormitoryByIds(ids);
    }

    /**
     * 删除家具赔偿信息信息
     *
     * @param id 家具赔偿信息主键
     * @return 结果
     */
    @Override
    public int deleteDormitoryById(Long id)
    {
        return dormitoryMapper.deleteDormitoryById(id);
    }

    /**
     * @param furnitureList
     * @param updateSupport
     * @param operName
     * @return java.lang.String
     * @decription  导入家具情况数据
     * @date 2023/3/6 21:00
    */
    @Override
    public String importFurniturMsg(List<Dormitory> furnitureList, boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(furnitureList) || furnitureList.size() == 0) {
            throw new ServiceException("导入挂科数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (Dormitory  dormitory: furnitureList) {
            try {
                SysUser sysUser = sysUserMapper.selectUserById(dormitory.getStuId());
                //验证学生是否存在,存在则可以导入学生的挂科信息
                if (!StringUtils.isNull(sysUser)) {
                    //判断是更改还是插入
                    if (isUpdateSupport)
                    {
                        BeanValidators.validateWithException(validator, dormitory);
                        dormitory.setUpdateBy(operName);
                        dormitory.setUpdateTime(new Date());
                        dormitoryMapper.updateDormitory(dormitory);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、id: " + dormitory.getId() + " 更新成功");
                    }else{
                        BeanValidators.validateWithException(validator, dormitory);
                        dormitory.setUpdateBy(operName);
                        SysUser User = sysUserMapper.selectUserById(dormitory.getStuId());
                        if(User==null){
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、stu_id: " + dormitory.getStuId() + " 不存在");
                        }
                        Dormitory isNull = dormitoryMapper.selectDormitoryByStuId(dormitory.getStuId());
                        if(isNull!=null){
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、stu_id: " + dormitory.getStuId() + " 已存在,不可重复导入");
                        }else{
                            dormitoryMapper.insertDormitory(dormitory);
                            successNum++;
                            successMsg.append("<br/>" + successNum + "、id: " + dormitory.getId() + " 导入成功");
                        }
                    }
                }
                else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、id: " + dormitory.getId() + " 不存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、id: " + dormitory.getId()  + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public int selectDormitoryHandleCountFinished() {
        return dormitoryMapper.selectDormitoryHandleCountFinished();
    }

    @Override
    public int selectDormitoryHandleCountIncomplete() {
        return dormitoryMapper.selectDormitoryHandleCountIncomplete();
    }
}
