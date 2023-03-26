package com.ruoyi.lxgl.mapper;

import com.ruoyi.lxgl.domain.CollegeMess;
import com.ruoyi.lxgl.domain.Lxhj;

import java.util.List;

/**
 * 离校资格Mapper接口
 *
 * @author Hainchen
 * @date 2023-03-07
 */
public interface LxhjMapper {
    /**
     * 查询离校资格
     *
     * @param id 离校资格主键
     * @return 离校资格
     */
    public Lxhj selectLxhjById(Long id);

    public Lxhj selectLxhjByStuId(Long stuId);

    public int selectLxhjIsNull();
    /**
     * 查询离校资格列表
     *
     * @param lxhj 离校资格
     * @return 离校资格集合
     */
    public List<Lxhj> selectLxhjList(Lxhj lxhj);

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
     * 删除离校资格
     *
     * @param id 离校资格主键
     * @return 结果
     */
    public int deleteLxhjById(Long id);

    public int deleteLxhjByStuId(Long stuId);
    /**
     * 批量删除离校资格
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLxhjByIds(Long[] ids);

    //获取学院的毕业生数量
    public List<CollegeMess> getcollegePersonNum();

    //获取学院已离校学生数量
    public List<CollegeMess> getcollegeLxss();

}
