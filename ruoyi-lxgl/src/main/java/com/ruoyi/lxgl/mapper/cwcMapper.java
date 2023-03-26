package com.ruoyi.lxgl.mapper;

import com.ruoyi.lxgl.domain.Cwc;

import java.util.List;

/**
 * 财务处管理Mapper接口
 *
 * @author Hainchen
 * @date 2023-03-04
 */
public interface cwcMapper
{
    /**
     * 查询财务处管理
     *
     * @param id 财务处管理主键
     * @return 财务处管理
     */
    public Cwc selectcwcById(Long id);

    public Cwc selectcwcByStuId(Long id);

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
     * 删除财务处管理
     *
     * @param id 财务处管理主键
     * @return 结果
     */
    public int deletecwcById(Long id);

    /**
     * 批量删除财务处管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletecwcByIds(Long[] ids);


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
