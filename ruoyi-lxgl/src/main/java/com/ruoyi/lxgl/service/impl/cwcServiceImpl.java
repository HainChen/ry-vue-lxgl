package com.ruoyi.lxgl.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.lxgl.domain.Cwc;
import com.ruoyi.lxgl.mapper.cwcMapper;
import com.ruoyi.lxgl.service.IcwcService;
import com.ruoyi.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;

/**
 * 财务处管理Service业务层处理
 *
 * @author Hainchen
 * @date 2023-03-04
 */
@Service
public class cwcServiceImpl implements IcwcService {

    private static final Logger log = LoggerFactory.getLogger(cwcServiceImpl.class);

    @Autowired
    private cwcMapper cwcMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询财务处管理
     *
     * @param id 财务处管理主键
     * @return 财务处管理
     */
    @Override
    public Cwc selectcwcById(Long id) {
        return cwcMapper.selectcwcById(id);
    }

    /**
     * 查询财务处管理列表
     *
     * @param cwc 财务处管理
     * @return 财务处管理
     */
    @Override
    public List<Cwc> selectcwcList(Cwc cwc) {
        return cwcMapper.selectcwcList(cwc);
    }

    /**
     * 新增财务处管理
     *
     * @param cwc 财务处管理
     * @return 结果
     */
    @Override
    public int insertcwc(Cwc cwc) {
        cwc.setCreateTime(DateUtils.getNowDate());
        SysUser user = sysUserMapper.selectUserById(cwc.getStuId());//插入记录之前，查询对应学生是存在
        if (user == null) {
            return 0;
        }
        Cwc isNull = cwcMapper.selectcwcByStuId(cwc.getStuId());
        if(isNull!=null){
            return 0;
        }
        return cwcMapper.insertcwc(cwc);
    }

    /**
     * 修改财务处管理
     *
     * @param cwc 财务处管理
     * @return 结果
     */
    @Override
    public int updatecwc(Cwc cwc) {
        cwc.setUpdateTime(DateUtils.getNowDate());
        return cwcMapper.updatecwc(cwc);
    }

    /**
     * 批量删除财务处管理
     *
     * @param ids 需要删除的财务处管理主键
     * @return 结果
     */
    @Override
    public int deletecwcByIds(Long[] ids) {
        return cwcMapper.deletecwcByIds(ids);
    }

    /**
     * 删除财务处管理信息
     *
     * @param id 财务处管理主键
     * @return 结果
     */
    @Override
    public int deletecwcById(Long id) {
        return cwcMapper.deletecwcById(id);
    }

    /**
     * @param arrearsList
     * @param isUpdateSupport
     * @param operName
     * @return java.lang.String
     * @decription 导入学生欠费信息
     * @date 2023/3/5 14:35
     */
    @Override
    public String importArrears(List<Cwc> arrearsList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(arrearsList) || arrearsList.size() == 0) {
            throw new ServiceException("导入欠费数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (Cwc cwc : arrearsList) {
            try {
                SysUser sysUser = sysUserMapper.selectUserById(cwc.getStuId());
                //验证学生是否存在,存在则可以导入学生的欠费信息
                if (!StringUtils.isNull(sysUser)) {
                    //判断是更改还是插入
                    if (isUpdateSupport) {
                        BeanValidators.validateWithException(validator, cwc);
                        cwc.setUpdateBy(operName);
                        cwc.setUpdateTime(new Date());
                        cwcMapper.updatecwc(cwc);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、id: " + cwc.getId() + " 更新成功");
                    } else {
                        BeanValidators.validateWithException(validator, cwc);
                        cwc.setUpdateBy(operName);
                        SysUser user = sysUserMapper.selectUserById(cwc.getStuId());//插入记录之前，查询对应学生是存在
                        if (user == null) {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、stu_id: " + cwc.getStuId() + " 不存在");
                        } else {
                            Cwc isNull = cwcMapper.selectcwcByStuId(cwc.getStuId());
                            if (isNull != null) {
                                failureNum++;
                                failureMsg.append("<br/>" + failureNum + "、stu_id: " + cwc.getStuId() + " 已存在，不可重复导入");
                            } else {
                                cwcMapper.insertcwc(cwc);
                                successNum++;
                                successMsg.append("<br/>" + successNum + "、id: " + cwc.getId() + " 导入成功");
                            }
                        }
                    }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、id: " + cwc.getId() + " 不存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、id: " + cwc.getId() + " 导入失败：";
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
    public int selectCwcHandleCountFinished() {
        return cwcMapper.selectCwcHandleCountFinished();
    }

    @Override
    public int selectCwcHandleCountIncomplete() {
        return cwcMapper.selectCwcHandleCountIncomplete();
    }
}
