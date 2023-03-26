package com.ruoyi.lxgl.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.lxgl.domain.*;
import com.ruoyi.lxgl.mapper.*;
import com.ruoyi.lxgl.service.ILxhjService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 离校资格Service业务层处理
 *
 * @author Hainchen
 * @date 2023-03-07
 */
@Service
public class LxhjServiceImpl implements ILxhjService {
    @Autowired
    private LxhjMapper lxhjMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private JwcMapper jwcMapper;

    @Autowired
    private cwcMapper cwcMapper;

    @Autowired
    private TsgMapper tsgMapper;

    /**
     * 查询离校资格
     *
     * @param id 离校资格主键
     * @return 离校资格
     */
    @Override
    public Lxhj selectLxhjById(Long id) {
        return lxhjMapper.selectLxhjById(id);
    }

    /**
     * 查询离校资格列表
     *
     * @param lxhj 离校资格
     * @return 离校资格
     */
    @Override
    public List<Lxhj> selectLxhjList(Lxhj lxhj) {
        //每次查询列表进行一次初始化检查，为空则插入，否则检查学生每个环节的状态
        return lxhjMapper.selectLxhjList(lxhj);
    }


    public void initStudentLXMsg(Lxhj lxhj) {

        //获取学生列表
        SysUser sysUser = new SysUser();
        List<SysUser> sysUsers = sysUserMapper.selectUserListIsStudent(sysUser);
        //初始化数据，第一次查询离校申请表时，如果为空，根据学生表，导入学生对应的数据，进行初始化

        //遍历学生表，获取学生id，
        for (int i = 0; i < sysUsers.size(); i++) {
            SysUser sysUserPer = sysUsers.get(i);
            Long userId = sysUserPer.getUserId();
            //并根据id获取其他环节的数据id，对离校申请对象进行数据封装

            //第二次查询时，判断每个环节是否都完成，如果有些环节查询为空（对象为null），代表已完成（如图书馆没欠书，默认设置图书馆状态为审核通过）
            Cwc cwc = cwcMapper.selectcwcByStuId(userId);
            Dormitory dormitory = dormitoryMapper.selectDormitoryByStuId(userId);
            Jwc jwc = jwcMapper.selectJwcByStuId(userId);
            Tsg tsg = tsgMapper.selectTsgByStuId(userId);
            //如果某个环节为空，则在对应环节插入一条记录（stuId，handle_status为1）代表该环节没有问题，已完成
            if (cwc == null) {
                cwc = new Cwc();
                cwc.setStuId(userId);
                cwc.setUpdateTime(new Date());
                cwc.setHandleStatus(Long.parseLong("1"));
                cwcMapper.insertcwc(cwc);
            }
            if (dormitory == null) {
                dormitory = new Dormitory();
                dormitory.setStuId(userId);
                dormitory.setUpdateTime(new Date());
                dormitory.setHandleStatus(Long.parseLong("1"));
                dormitoryMapper.insertDormitory(dormitory);
            }
            if (jwc == null) {
                jwc = new Jwc();
                jwc.setStuId(userId);
                jwc.setUpdateTime(new Date());
                jwc.setHandleStatus(Long.parseLong("1"));
                jwcMapper.insertJwc(jwc);
            }
            if (tsg == null) {
                tsg = new Tsg();
                tsg.setStuId(userId);
                tsg.setUpdateTime(new Date());
                tsg.setHandleStatus(Long.parseLong("1"));
                tsgMapper.insertTsg(tsg);
            }

            //每个环节都不为空，则封装 离校申请对象，初始化数据
            Lxhj stuMsg = new Lxhj();
            stuMsg.setStuId(userId);
            stuMsg.setCwcId(cwc.getId());
            stuMsg.setSsId(dormitory.getId());
            stuMsg.setJwcId(jwc.getId());
            stuMsg.setTsgId(tsg.getId());

            //插入封装离校申请对象数据进行初始化
            //判断前置环节全部完成，完成才可以插入
            if (cwc.getHandleStatus() == 1 && dormitory.getHandleStatus() == 1 && jwc.getHandleStatus() == 1 && tsg.getHandleStatus() == 1) {
                //前置环节完成后，修改状态为“待审核”
                stuMsg.setHandleStatus(Long.parseLong("0"));
                //插入之前，判断是否已经存在过记录
                Lxhj flag = lxhjMapper.selectLxhjByStuId(userId);
                if (flag == null) {
                    //不存在则插入记录
                    lxhjMapper.insertLxhj(stuMsg);
                } else {
                    //存在则修改
                    stuMsg.setId(flag.getId());
                    lxhjMapper.updateLxhj(stuMsg);
                }
            } else {
                //环节中存在一条办理状态为“待审核” ，修改离校申请的处理状态，从待审核修改为前置环节未完成

                Lxhj stuMess = lxhjMapper.selectLxhjByStuId(userId);
                stuMess.setHandleStatus(Long.parseLong("2"));
                lxhjMapper.updateLxhj(stuMess);
            }
        }
    }

    /**
     * 新增离校资格
     *
     * @param lxhj 离校资格
     * @return 结果
     */
    @Override
    public int insertLxhj(Lxhj lxhj) {
        lxhj.setCreateTime(DateUtils.getNowDate());
        return lxhjMapper.insertLxhj(lxhj);
    }

    /**
     * 修改离校资格
     *
     * @param lxhj 离校资格
     * @return 结果
     */
    @Override
    public int updateLxhj(Lxhj lxhj) {
        lxhj.setUpdateTime(DateUtils.getNowDate());
        return lxhjMapper.updateLxhj(lxhj);
    }

    /**
     * 批量删除离校资格
     *
     * @param ids 需要删除的离校资格主键
     * @return 结果
     */
    @Override
    public int deleteLxhjByIds(Long[] ids) {
        return lxhjMapper.deleteLxhjByIds(ids);
    }

    /**
     * 删除离校资格信息
     *
     * @param id 离校资格主键
     * @return 结果
     */
    @Override
    public int deleteLxhjById(Long id) {
        return lxhjMapper.deleteLxhjById(id);
    }

    /**
     * @param id 当前登录用户id(就是stu_id)
     * @return com.ruoyi.lxgl.domain.Lxhj
     * @decription 每个环节单独查询
     * @date 2023/3/8 1:54
     */
    @Override
    public Lxhj selectLxhjByStuId(Long id) {
        //判断是否为学生查看自己的环节信息，如果是其他人员则返回null。

        Long role_id = sysUserRoleMapper.selectByUserId(id);
        if (role_id != 2) {
            return null;
        }

        //封装对象
        Lxhj lxhj = new Lxhj();
        Cwc cwc = cwcMapper.selectcwcByStuId(id);
        Dormitory dormitory = dormitoryMapper.selectDormitoryByStuId(id);
        Jwc jwc = jwcMapper.selectJwcByStuId(id);
        Tsg tsg = tsgMapper.selectTsgByStuId(id);
        Lxhj endStatu = lxhjMapper.selectLxhjByStuId(id);

        lxhj.setStuId(id);
        lxhj.setCwc(cwc);
        lxhj.setDormitory(dormitory);
        lxhj.setJwc(jwc);
        lxhj.setTsg(tsg);

        if (endStatu != null) {//不为null存在两种情况，一种是数据没有及时更新最新状态，一种是已经更新状态
            if (cwc.getHandleStatus() == 1 && dormitory.getHandleStatus() == 1 && jwc.getHandleStatus() == 1 && tsg.getHandleStatus() == 1) {

                lxhj.setHandleStatus(endStatu.getHandleStatus());
                lxhj.setUpdateTime(endStatu.getUpdateTime());
            } else {
                lxhj.setHandleStatus(Long.parseLong("2")); //前置手续没完成
            }
        } else {
            lxhj.setHandleStatus(Long.parseLong("2")); //前置手续没完成
        }

        return lxhj;
    }

    //统计各环节业务办理情况，封装状态对象返回
    @Override
    public LxhjHandleStatus getLxhjHandleStatus() {
        //财务处办理状态
        int cwcFinished = cwcMapper.selectCwcHandleCountFinished();
        int cwcIncomplete = cwcMapper.selectCwcHandleCountIncomplete();
        //宿舍办理状态
        int dormitoryFinished = dormitoryMapper.selectDormitoryHandleCountFinished();
        int dormitoryIncomplete = dormitoryMapper.selectDormitoryHandleCountIncomplete();
        //教务处办理状态
        int jwcFinished = jwcMapper.selectJwcHandleCountFinished();
        int jwcIncomplete = jwcMapper.selectJwcHandleCountIncomplete();
        //图书室办理状态
        int tsgFinished = tsgMapper.selectTsgHandleCountFinished();
        int tsgIncomplete = tsgMapper.selectTsgHandleCountIncomplete();

        LxhjHandleStatus lxhjHandleStatus = new LxhjHandleStatus(cwcFinished, cwcIncomplete, jwcFinished, jwcIncomplete, dormitoryFinished, dormitoryIncomplete, tsgFinished, tsgIncomplete);

        return lxhjHandleStatus;
    }

    //获取学院离校情况
    @Override
    public List<CollegeMess> getcollegeLxss() {
        return lxhjMapper.getcollegeLxss();
    }

    @Override
    public List<CollegeMess> getcollegePersonNum() {
        List<CollegeMess> collegeMesses = lxhjMapper.getcollegePersonNum();
        return collegeMesses;
    }


}
