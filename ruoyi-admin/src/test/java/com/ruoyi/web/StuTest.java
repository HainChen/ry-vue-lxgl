package com.ruoyi.web;


import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.lxgl.domain.CollegeMess;
import com.ruoyi.lxgl.domain.Student;
import com.ruoyi.lxgl.service.ILxhjService;
import com.ruoyi.lxgl.service.IStudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @ClassName StuTest
 * @Description TODO
 * @Author hainc
 * @Date 2023/3/2 19:40
 * @Version 1.0
 **/
@SpringBootTest
public class StuTest{

  @Autowired
  private IStudentService studentService;
@Autowired
  private ILxhjService lxhjService;

  @Test
  public  void test(){
    Student student = new Student();
    SysUser sysUser = new SysUser();
    SysDept sysDept = new SysDept();
    sysDept.setDeptName("广东第二师范学院");
    sysUser.setDept(sysDept);
    student.setSysUser(sysUser);
    List<Student> students = studentService.selectStudentList(student);
    System.out.println(students);
  }

  @Test
  public void test1(){
    List<CollegeMess> collegeMesses = lxhjService.getcollegePersonNum();
    System.out.println(collegeMesses);
  }
}
