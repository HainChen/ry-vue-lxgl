package com.ruoyi.lxgl.service;

import com.ruoyi.lxgl.domain.Tsg;

import java.util.List;

/**
 * 图书管理Service接口
 *
 * @author Hainchen
 * @date 2023-03-06
 */
public interface ITsgService
{
    /**
     * 查询图书管理
     *
     * @param id 图书管理主键
     * @return 图书管理
     */
    public Tsg selectTsgById(Long id);

    /**
     * 查询图书管理列表
     *
     * @param tsg 图书管理
     * @return 图书管理集合
     */
    public List<Tsg> selectTsgList(Tsg tsg);

    /**
     * 新增图书管理
     *
     * @param tsg 图书管理
     * @return 结果
     */
    public int insertTsg(Tsg tsg);

    /**
     * 修改图书管理
     *
     * @param tsg 图书管理
     * @return 结果
     */
    public int updateTsg(Tsg tsg);

    /**
     * 批量删除图书管理
     *
     * @param ids 需要删除的图书管理主键集合
     * @return 结果
     */
    public int deleteTsgByIds(Long[] ids);

    /**
     * 删除图书管理信息
     *
     * @param id 图书管理主键
     * @return 结果
     */
    public int deleteTsgById(Long id);


    public String importBorrowed(List<Tsg> borrowedList, Boolean isUpdateSupport, String operName);
    //以下为统计方法
    /**
     * @return int
     * @decription 统计图书馆办理完成业务的人数
     * @date 2023/3/21 20:27
     */
    public int selectTsgHandleCountFinished();

    /**
     * @return int
     * @decription  统计图书馆未完成业务人数
     * @date 2023/3/21 20:30
     */
    public int selectTsgHandleCountIncomplete();
}
