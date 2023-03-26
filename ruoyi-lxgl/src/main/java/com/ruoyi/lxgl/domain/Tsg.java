package com.ruoyi.lxgl.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 图书管理对象 lxgl_tsg
 *
 * @author Hainchen
 * @date 2023-03-06
 */
public class Tsg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 借阅记录ID */
    @Excel(name = "借阅记录ID")
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

    /** 图书名称 */
    @Excel(name = "图书名称")
    private String bookName;

    /** 逾期天数 */
    @Excel(name = "逾期天数")
    private Long overdueDays;

    /** 处理状态 */
    @Excel(name = "处理状态")
    private Long handleStatus;

    /** 状态码 */
    private String status;


    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

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
    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    public String getBookName()
    {
        return bookName;
    }
    public void setOverdueDays(Long overdueDays)
    {
        this.overdueDays = overdueDays;
    }

    public Long getOverdueDays()
    {
        return overdueDays;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("stuId", getStuId())
            .append("bookName", getBookName())
            .append("overdueDays", getOverdueDays())
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
