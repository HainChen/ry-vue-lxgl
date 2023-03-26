package com.ruoyi.lxgl.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.lxgl.domain.Jwc;
import com.ruoyi.lxgl.mapper.JwcMapper;
import com.ruoyi.lxgl.service.IJwcService;
import com.ruoyi.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;

/**
 * 教务管理Service业务层处理
 *
 * @author Hainchen
 * @date 2023-03-05
 */
@Service
public class JwcServiceImpl implements IJwcService
{
    private static final Logger log = LoggerFactory.getLogger(cwcServiceImpl.class);

    @Autowired
    private JwcMapper jwcMapper;


    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    protected Validator validator;
    /**
     * 查询教务管理
     *
     * @param id 教务管理主键
     * @return 教务管理
     */
    @Override
    public Jwc selectJwcById(Long id)
    {
        return jwcMapper.selectJwcById(id);
    }

    /**
     * 查询教务管理列表
     *
     * @param jwc 教务管理
     * @return 教务管理
     */
    @Override
    public List<Jwc> selectJwcList(Jwc jwc)
    {
        return jwcMapper.selectJwcList(jwc);
    }

    /**
     * 新增教务管理
     *
     * @param jwc 教务管理
     * @return 结果
     */
    @Override
    public int insertJwc(Jwc jwc)
    {
        jwc.setCreateTime(DateUtils.getNowDate());
        SysUser sysUser = sysUserMapper.selectUserById(jwc.getStuId());
        if(sysUser==null)return 0;
        Jwc isNull = jwcMapper.selectJwcByStuId(jwc.getStuId());
        if(isNull!=null)return 0;
        return jwcMapper.insertJwc(jwc);
    }

    /**
     * 修改教务管理
     *
     * @param jwc 教务管理
     * @return 结果
     */
    @Override
    public int updateJwc(Jwc jwc)
    {
        jwc.setUpdateTime(DateUtils.getNowDate());
        return jwcMapper.updateJwc(jwc);
    }

    /**
     * 批量删除教务管理
     *
     * @param ids 需要删除的教务管理主键
     * @return 结果
     */
    @Override
    public int deleteJwcByIds(Long[] ids)
    {
        return jwcMapper.deleteJwcByIds(ids);
    }

    /**
     * 删除教务管理信息
     *
     * @param id 教务管理主键
     * @return 结果
     */
    @Override
    public int deleteJwcById(Long id)
    {
        return jwcMapper.deleteJwcById(id);
    }

    /**
     * @param courseList
     * @param isUpdateSupport
     * @param operName
     * @return java.lang.String
     * @decription 挂科信息导入
     * @date 2023/3/5 21:04
    */
    @Override
    public String importCourse(List<Jwc> courseList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(courseList) || courseList.size() == 0) {
            throw new ServiceException("导入挂科数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (Jwc jwc : courseList) {
            try {
                SysUser sysUser = sysUserMapper.selectUserById(jwc.getStuId());
                //验证学生是否存在,存在则可以导入学生的挂科信息
                if (!StringUtils.isNull(sysUser)) {
                    //判断是更改还是插入
                    if (isUpdateSupport)
                    {
                        BeanValidators.validateWithException(validator, jwc);
                        jwc.setUpdateBy(operName);
                        jwc.setUpdateTime(new Date());
                        jwcMapper.updateJwc(jwc);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、id: " + jwc.getId() + " 更新成功");
                    }else{
                        BeanValidators.validateWithException(validator, jwc);
                        jwc.setUpdateBy(operName);
                        SysUser User = sysUserMapper.selectUserById(jwc.getStuId());
                        if(User==null){
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、stu_id: " + jwc.getStuId() + " 不存在");
                        }
                        Jwc isNull = jwcMapper.selectJwcByStuId(jwc.getStuId());
                        if(isNull!=null) {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、id: " + jwc.getStuId() + " 已存在");
                        }else{
                            jwcMapper.insertJwc(jwc);
                            successNum++;
                            successMsg.append("<br/>" + successNum + "、id: " + jwc.getId() + " 导入成功");
                        }
                    }
                }
                else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、id: " + jwc.getId() + " 不存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、id: " + jwc.getId()  + " 导入失败：";
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
    public int selectJwcHandleCountFinished() {
        return jwcMapper.selectJwcHandleCountFinished();
    }

    @Override
    public int selectJwcHandleCountIncomplete() {
        return jwcMapper.selectJwcHandleCountIncomplete();
    }
}
