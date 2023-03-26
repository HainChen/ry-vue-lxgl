package com.ruoyi.lxgl.service;

import com.ruoyi.lxgl.domain.Dormitory;

import java.util.List;

/**
 * 家具赔偿信息Service接口
 *
 * @author Hainchen
 * @date 2023-03-06
 */
public interface IDormitoryService
{
    /**
     * 查询家具赔偿信息
     *
     * @param id 家具赔偿信息主键
     * @return 家具赔偿信息
     */
    public Dormitory selectDormitoryById(Long id);

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
     * 批量删除家具赔偿信息
     *
     * @param ids 需要删除的家具赔偿信息主键集合
     * @return 结果
     */
    public int deleteDormitoryByIds(Long[] ids);

    /**
     * 删除家具赔偿信息信息
     *
     * @param id 家具赔偿信息主键
     * @return 结果
     */
    public int deleteDormitoryById(Long id);

    public String importFurniturMsg(List<Dormitory> furnitureList, boolean isUpdateSupport, String operName);
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
