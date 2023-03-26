package com.ruoyi.lxgl.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 家具赔偿信息对象 lxgl_ss
 *
 * @author Hainchen
 * @date 2023-03-06
 */
public class Dormitory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
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

    /** 家具情况 */
    @Excel(name = "家具情况")
    private String furniture;

    /** 赔偿金额 */
    @Excel(name = "赔偿金额")
    private Long indemnity;

    //最低金额
    private BigDecimal beginTotalArrears;
    //最高金额
    private BigDecimal endTotalArrears;

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


    public BigDecimal getBeginTotalArrears() {
        return beginTotalArrears;
    }

    public void setBeginTotalArrears(BigDecimal beginTotalArrears) {
        this.beginTotalArrears = beginTotalArrears;
    }

    public BigDecimal getEndTotalArrears() {
        return endTotalArrears;
    }

    public void setEndTotalArrears(BigDecimal endTotalArrears) {
        this.endTotalArrears = endTotalArrears;
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
    public void setFurniture(String furniture)
    {
        this.furniture = furniture;
    }

    public String getFurniture()
    {
        return furniture;
    }
    public void setIndemnity(Long indemnity)
    {
        this.indemnity = indemnity;
    }

    public Long getIndemnity()
    {
        return indemnity;
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
            .append("furniture", getFurniture())
            .append("indemnity", getIndemnity())
            .append("handleStatus", getHandleStatus())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
                .append("sysUser",getSysUser())
                .append("beginTotalArrears",getBeginTotalArrears())
                .append("endTotalArrears",getEndTotalArrears())
            .toString();
    }
}
