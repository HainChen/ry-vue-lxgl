package com.ruoyi.lxgl.service;

import com.ruoyi.lxgl.domain.Cwc;

import java.util.List;

/**
 * 财务处管理Service接口
 *
 * @author Hainchen
 * @date 2023-03-04
 */
public interface IcwcService
{
    /**
     * 查询财务处管理
     *
     * @param id 财务处管理主键
     * @return 财务处管理
     */
    public Cwc selectcwcById(Long id);

    /**
     * 查询财务处管理列表
     *
     * @param cwc 财务处管理
     * @return 财务处管理集合
     */
    public List<Cwc> selectcwcList(Cwc cwc);

    /**
     * 新增财务处管理
     *
     * @param cwc 财务处管理
     * @return 结果
     */
    public int insertcwc(Cwc cwc);

    /**
     * 修改财务处管理
     *
     * @param cwc 财务处管理
     * @return 结果
     */
    public int updatecwc(Cwc cwc);

    /**
     * 批量删除财务处管理
     *
     * @param ids 需要删除的财务处管理主键集合
     * @return 结果
     */
    public int deletecwcByIds(Long[] ids);

    /**
     * 删除财务处管理信息
     *
     * @param id 财务处管理主键
     * @return 结果
     */
    public int deletecwcById(Long id);

    /**
     * 导入欠费数据
     *
     * @param userList 欠费数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importArrears(List<Cwc> arrearsList, Boolean isUpdateSupport, String operName);

    //以下为统计方法
    /**
     * @return int
     * @decription 统计财务处办理完成业务的人数
     * @date 2023/3/21 20:27
     */
    public int selectCwcHandleCountFinished();

    /**
     * @return int
     * @decription  统计财务处未完成业务人数
     * @date 2023/3/21 20:30
     */
    public int selectCwcHandleCountIncomplete();
}
