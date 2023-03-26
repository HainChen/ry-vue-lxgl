package com.ruoyi.lxgl.service.impl;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.lxgl.domain.Student;
import com.ruoyi.lxgl.mapper.StudentMapper;
import com.ruoyi.lxgl.service.IStudentService;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生管理Service业务层处理
 *
 * @author Hainchen
 * @date 2023-03-03
 */
@Service
public class StudentServiceImpl implements IStudentService
{
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 查询学生管理
     *
     * @param id 学生管理主键
     * @return 学生管理
     */
    @Override
    public Student selectStudentById(Long id)
    {

        Student student = studentMapper.selectStudentById(id);
        SysUser sysUser = sysUserMapper.selectUserById(student.getUserId());
        SysDept sysDept = sysDeptMapper.selectDeptById(sysUser.getDeptId());
        sysUser.setDept(sysDept);
        student.setSysUser(sysUser);
        return student;
    }

    /**
     * 查询学生管理列表
     *
     * @param student 学生管理
     * @return 学生管理
     */
    @Override
    public List<Student> selectStudentList(Student student)
    {
        return studentMapper.selectStudentList(student);
    }

    /**
     * 新增学生管理
     *
     * @param student 学生管理
     * @return 结果
     */
    @Override
    public int insertStudent(Student student)
    {
        return studentMapper.insertStudent(student);
    }

    /**
     * 修改学生管理
     *
     * @param student 学生管理
     * @return 结果
     */
    @Override
    public int updateStudent(Student student)
    {
        return studentMapper.updateStudent(student);
    }

    /**
     * 批量删除学生管理
     *
     * @param ids 需要删除的学生管理主键
     * @return 结果
     */
    @Override
    public int deleteStudentByIds(Long[] ids)
    {
        return studentMapper.deleteStudentByIds(ids);
    }

    /**
     * 删除学生管理信息
     *
     * @param id 学生管理主键
     * @return 结果
     */
    @Override
    public int deleteStudentById(Long id)
    {
        return studentMapper.deleteStudentById(id);
    }
}
