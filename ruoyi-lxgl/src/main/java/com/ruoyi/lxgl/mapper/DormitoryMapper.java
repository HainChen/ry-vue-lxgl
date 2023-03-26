package com.ruoyi.lxgl.mapper;

import com.ruoyi.lxgl.domain.Dormitory;

import java.util.List;

/**
 * 家具赔偿信息Mapper接口
 *
 * @author Hainchen
 * @date 2023-03-06
 */
public interface DormitoryMapper
{
    /**
     * 查询家具赔偿信息
     *
     * @param id 家具赔偿信息主键
     * @return 家具赔偿信息
     */
    public Dormitory selectDormitoryById(Long id);

    public Dormitory selectDormitoryByStuId(Long id);
    /**
     * 查询家具赔偿信息列表
     *
     * @param dormitory 家具赔偿信息
     * @return 家具赔偿信息集合
     */
    public List<Dormitory> selectDormitoryList(Dormitory dormitory);

    /**
     * 新增家具赔偿信息
     *
     * @param dormitory 家具赔偿信息
     * @return 结果
     */
    public int insertDormitory(Dormitory dormitory);

    /**
     * 修改家具赔偿信息
     *
     * @param dormitory 家具赔偿信息
     * @return 结果
     */
    public int updateDormitory(Dormitory dormitory);

    /**
     * 删除家具赔偿信息
     *
     * @param id 家具赔偿信息主键
     * @return 结果
     */
    public int deleteDormitoryById(Long id);

    /**
     * 批量删除家具赔偿信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDormitoryByIds(Long[] ids);

    //以下为统计方法
    /**
     * @return int
     * @decription 统计宿舍办理完成业务的人数
     * @date 2023/3/21 20:27
     */
    public int selectDormitoryHandleCountFinished();

    /**
     * @return int
     * @decription  统计宿舍未完成业务人数
     * @date 2023/3/21 20:30
     */
    public int selectDormitoryHandleCountIncomplete();

}
