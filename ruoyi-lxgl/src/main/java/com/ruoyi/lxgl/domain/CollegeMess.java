package com.ruoyi.lxgl.domain;

/**
 * @ClassName CollegeMess
 * @Description TODO
 * @Author hainc
 * @Date 2023/3/22 14:44
 * @Version 1.0
 **/
public class CollegeMess {
    //学院名称
    private String collegeName;
    //学院的毕业生人数
    private Integer collegeGraduateNum;

    //学院已完成离校学生人数
    private Integer collegeLxFinished;

    //学院未完成离校学生人数
    private Integer collegeLxIncomplete;

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public Integer getCollegeGraduateNum() {
        return collegeGraduateNum;
    }

    public void setCollegeGraduateNum(Integer collegeGraduateNum) {
        this.collegeGraduateNum = collegeGraduateNum;
    }

    public Integer getCollegeLxFinished() {
        return collegeLxFinished;
    }

    public void setCollegeLxFinished(Integer collegeLxFinished) {
        this.collegeLxFinished = collegeLxFinished;
    }

    public Integer getCollegeLxIncomplete() {
        return collegeLxIncomplete;
    }

    public void setCollegeLxIncomplete(Integer collegeLxIncomplete) {
        this.collegeLxIncomplete = collegeLxIncomplete;
    }

    @Override
    public String toString() {
        return "CollegeMess{" +
                "collegeName='" + collegeName + '\'' +
                ", collegeGraduateNum=" + collegeGraduateNum +
                ", collegeLxFinished=" + collegeLxFinished +
                ", collegeLxIncomplete=" + collegeLxIncomplete +
                '}';
    }
}
