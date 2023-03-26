package com.ruoyi.lxgl.service;

import com.ruoyi.lxgl.domain.CollegeMess;
import com.ruoyi.lxgl.domain.Lxhj;
import com.ruoyi.lxgl.domain.LxhjHandleStatus;

import java.util.List;

/**
 * 离校资格Service接口
 *
 * @author Hainchen
 * @date 2023-03-07
 */
public interface ILxhjService
{
    /**
     * 查询离校资格
     *
     * @param id 离校资格主键
     * @return 离校资格
     */
    public Lxhj selectLxhjById(Long id);

    /**
     * 查询离校资格列表
     *
     * @param lxhj 离校资格
     * @return 离校资格集合
     */
    public List<Lxhj> selectLxhjList(Lxhj lxhj);

    //更新离校申请表
    public void initStudentLXMsg(Lxhj lxhj);
    /**
     * 新增离校资格
     *
     * @param lxhj 离校资格
     * @return 结果
     */
    public int insertLxhj(Lxhj lxhj);

    /**
     * 修改离校资格
     *
     * @param lxhj 离校资格
     * @return 结果
     */
    public int updateLxhj(Lxhj lxhj);

    /**
     * 批量删除离校资格
     *
     * @param ids 需要删除的离校资格主键集合
     * @return 结果
     */
    public int deleteLxhjByIds(Long[] ids);

    /**
     * 删除离校资格信息
     *
     * @param id 离校资格主键
     * @return 结果
     */
    public int deleteLxhjById(Long id);

    public Lxhj selectLxhjByStuId(Long id);

    public LxhjHandleStatus getLxhjHandleStatus();

    public List<CollegeMess> getcollegeLxss();

    public List<CollegeMess> getcollegePersonNum();

}
