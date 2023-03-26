package com.ruoyi.lxgl.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 离校资格对象 lxgl_hj
 *
 * @author Hainchen
 * @date 2023-03-07
 */
public class Lxhj extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
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


    /** 宿舍ID */
    private Long ssId;

    private Dormitory dormitory;



    /** 财务处ID */
    private Long cwcId;

    private Cwc cwc;

    /** 图书馆ID */
    private Long tsgId;

    private Tsg tsg;

    /** 教务处ID */
    private Long jwcId;

    private Jwc jwc;

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

    public Dormitory getDormitory() {
        return dormitory;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }

    public Cwc getCwc() {
        return cwc;
    }

    public void setCwc(Cwc cwc) {
        this.cwc = cwc;
    }

    public Tsg getTsg() {
        return tsg;
    }

    public void setTsg(Tsg tsg) {
        this.tsg = tsg;
    }

    public Jwc getJwc() {
        return jwc;
    }

    public void setJwc(Jwc jwc) {
        this.jwc = jwc;
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
    public void setSsId(Long ssId)
    {
        this.ssId = ssId;
    }

    public Long getSsId()
    {
        return ssId;
    }
    public void setCwcId(Long cwcId)
    {
        this.cwcId = cwcId;
    }

    public Long getCwcId()
    {
        return cwcId;
    }
    public void setTsgId(Long tsgId)
    {
        this.tsgId = tsgId;
    }

    public Long getTsgId()
    {
        return tsgId;
    }
    public void setJwcId(Long jwcId)
    {
        this.jwcId = jwcId;
    }

    public Long getJwcId()
    {
        return jwcId;
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
            .append("ssId", getSsId())
            .append("cwcId", getCwcId())
            .append("tsgId", getTsgId())
            .append("jwcId", getJwcId())
            .append("handleStatus", getHandleStatus())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
                .append("sysUser",getSysUser())
                .append("dormitory",getDormitory())
                .append("jwc",getJwc())
                .append("cwc",getCwc())
                .append("tsg",getTsg())
            .toString();
    }
}
