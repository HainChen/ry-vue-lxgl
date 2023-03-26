package com.ruoyi.lxgl.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.lxgl.domain.Tsg;
import com.ruoyi.lxgl.mapper.TsgMapper;
import com.ruoyi.lxgl.service.ITsgService;
import com.ruoyi.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;

/**
 * 图书管理Service业务层处理
 *
 * @author Hainchen
 * @date 2023-03-06
 */
@Service
public class TsgServiceImpl implements ITsgService
{
    private static final Logger log = LoggerFactory.getLogger(cwcServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    protected Validator validator;

    @Autowired
    private TsgMapper tsgMapper;

    /**
     * 查询图书管理
     *
     * @param id 图书管理主键
     * @return 图书管理
     */
    @Override
    public Tsg selectTsgById(Long id)
    {
        return tsgMapper.selectTsgById(id);
    }

    /**
     * 查询图书管理列表
     *
     * @param tsg 图书管理
     * @return 图书管理
     */
    @Override
    public List<Tsg> selectTsgList(Tsg tsg)
    {
        return tsgMapper.selectTsgList(tsg);
    }

    /**
     * 新增图书管理
     *
     * @param tsg 图书管理
     * @return 结果
     */
    @Override
    public int insertTsg(Tsg tsg)
    {
        tsg.setCreateTime(DateUtils.getNowDate());
        SysUser sysUser = sysUserMapper.selectUserById(tsg.getStuId());
        if(sysUser==null)return 0;
        Tsg isNull = tsgMapper.selectTsgByStuId(tsg.getStuId());
        if(isNull!=null)return 0;
        return tsgMapper.insertTsg(tsg);
    }

    /**
     * 修改图书管理
     *
     * @param tsg 图书管理
     * @return 结果
     */
    @Override
    public int updateTsg(Tsg tsg)
    {
        tsg.setUpdateTime(DateUtils.getNowDate());
        return tsgMapper.updateTsg(tsg);
    }

    /**
     * 批量删除图书管理
     *
     * @param ids 需要删除的图书管理主键
     * @return 结果
     */
    @Override
    public int deleteTsgByIds(Long[] ids)
    {
        return tsgMapper.deleteTsgByIds(ids);
    }

    /**
     * 删除图书管理信息
     *
     * @param id 图书管理主键
     * @return 结果
     */
    @Override
    public int deleteTsgById(Long id)
    {
        return tsgMapper.deleteTsgById(id);
    }

    /**
     * @param courseList
     * @param isUpdateSupport
     * @param operName
     * @return java.lang.String
     * @decription 导入借阅信息
     * @date 2023/3/6 10:47
    */
    @Override
    public String importBorrowed(List<Tsg> borrowedList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(borrowedList) || borrowedList.size() == 0) {
            throw new ServiceException("导入借阅数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (Tsg tsg : borrowedList) {
            try {
                SysUser sysUser = sysUserMapper.selectUserById(tsg.getStuId());
                //验证学生是否存在,存在则可以导入学生的挂科信息
                if (!StringUtils.isNull(sysUser)) {
                    //判断是更改还是插入
                    if (isUpdateSupport)
                    {
                        //修改
                        BeanValidators.validateWithException(validator, tsg);
                        tsg.setUpdateBy(operName);
                        tsg.setUpdateTime(new Date());
                        tsgMapper.updateTsg(tsg);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、id: " + tsg.getId() + " 更新成功");
                    }else{
                        //新增
                        BeanValidators.validateWithException(validator, tsg);
                        tsg.setUpdateBy(operName);

                        SysUser User = sysUserMapper.selectUserById(tsg.getStuId());
                        if(User==null) {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、stu_id: " + tsg.getStuId()+ " 不存在");
                        }
                        Tsg isNull = tsgMapper.selectTsgByStuId(tsg.getStuId());
                        if(isNull!=null) {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、stu_id: " + tsg.getStuId()+ " 已存在，不可重复导入");
                        }else {
                            tsgMapper.insertTsg(tsg);
                            successNum++;
                            successMsg.append("<br/>" + successNum + "、id: " + tsg.getId() + " 导入成功");
                        }
                    }
                }
                else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、id: " + tsg.getId() + " 不存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、id: " + tsg.getId()  + " 导入失败：";
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
    public int selectTsgHandleCountFinished() {
        return tsgMapper.selectTsgHandleCountFinished();
    }

    @Override
    public int selectTsgHandleCountIncomplete() {
        return tsgMapper.selectTsgHandleCountIncomplete();
    }
}
