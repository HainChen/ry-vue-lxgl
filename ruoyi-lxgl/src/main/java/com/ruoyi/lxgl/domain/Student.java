package com.ruoyi.lxgl.domain;

import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生管理对象 lxgl_student
 *
 * @author Hainchen
 * @date 2023-03-03
 */
public class Student extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 学生外键
     */
    private Long userId;

    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private Long age;

    /**
     * 身份证号码
     */
    @Excel(name = "身份证号码")
    private String idCard;

    private SysUser sysUser;

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getAge() {
        return age;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("age", getAge())
                .append("idCard", getIdCard())
                .append("sysUser", getSysUser())
                .toString();
    }
}
