package com.ruoyi.lxgl.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 教务管理对象 lxgl_jwc
 *
 * @author Hainchen
 * @date 2023-03-05
 */
public class Jwc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    @Excel(name = "ID")
    private Long id;

    /** 学生ID */
    @Excel(name = "学生ID")
    private Long stuId;

    /**  学生对象信息
     * */
    @Excels({
            @Excel(name = "姓名", targetAttr = "userName", type = Type.EXPORT),
            @Excel(name = "班级", targetAttr = "dept.deptName", type = Type.EXPORT)
    })
    private SysUser sysUser;

    /** 挂科科目 */
    @Excel(name = "挂科科目")
    private String course;

    /** 未修学分 */
    @Excel(name = "未修学分")
    private Long credit;

    /** 处理状态 */
    @Excel(name = "处理状态")
    private Long handleStatus;

    /** 状态码 */
    private String status;


    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setStuId(Long stuId)
    {
        this.stuId = stuId;
    }

    public Long getStuId()
    {
        return stuId;
    }
    public void setCourse(String course)
    {
        this.course = course;
    }

    public String getCourse()
    {
        return course;
    }
    public void setCredit(Long credit)
    {
        this.credit = credit;
    }

    public Long getCredit()
    {
        return credit;
    }
    public void setHandleStatus(Long handleStatus)
    {
        this.handleStatus = handleStatus;
    }

    public Long getHandleStatus()
    {
        return handleStatus;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("stuId", getStuId())
            .append("course", getCourse())
            .append("credit", getCredit())
            .append("handleStatus", getHandleStatus())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
                .append("sysUser",getSysUser())
            .toString();
    }
}
