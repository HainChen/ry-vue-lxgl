package com.ruoyi.lxgl.mapper;

import com.ruoyi.lxgl.domain.Jwc;

import java.util.List;

/**
 * 教务管理Mapper接口
 *
 * @author Hainchen
 * @date 2023-03-05
 */
public interface JwcMapper
{
    /**
     * 查询教务管理
     *
     * @param id 教务管理主键
     * @return 教务管理
     */
    public Jwc selectJwcById(Long id);
    public Jwc selectJwcByStuId(Long id);
    /**
     * 查询教务管理列表
     *
     * @param jwc 教务管理
     * @return 教务管理集合
     */
    public List<Jwc> selectJwcList(Jwc jwc);

    /**
     * 新增教务管理
     *
     * @param jwc 教务管理
     * @return 结果
     */
    public int insertJwc(Jwc jwc);

    /**
     * 修改教务管理
     *
     * @param jwc 教务管理
     * @return 结果
     */
    public int updateJwc(Jwc jwc);

    /**
     * 删除教务管理
     *
     * @param id 教务管理主键
     * @return 结果
     */
    public int deleteJwcById(Long id);

    /**
     * 批量删除教务管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJwcByIds(Long[] ids);
    //以下为统计方法
    /**
     * @return int
     * @decription 统计教务处办理完成业务的人数
     * @date 2023/3/21 20:27
     */
    public int selectJwcHandleCountFinished();

    /**
     * @return int
     * @decription  统计教务处未完成业务人数
     * @date 2023/3/21 20:30
     */
    public int selectJwcHandleCountIncomplete();

}
