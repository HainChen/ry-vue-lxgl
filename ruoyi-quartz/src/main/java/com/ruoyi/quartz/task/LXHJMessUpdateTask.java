//package com.ruoyi.quartz.task;
//
//import com.ruoyi.common.utils.spring.SpringUtils;
//import com.ruoyi.lxgl.domain.Lxhj;
//import com.ruoyi.lxgl.service.ILxhjService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
///**
// * @ClassName LXHJMessUpdate
// * @Description TODO
// * @Author hainc
// * @Date 2023/3/17 10:43
// * @Version 1.0
// **/
//@Component
//public class LXHJMessUpdateTask {
//    private static final Logger log = LoggerFactory.getLogger(LXHJMessUpdateTask.class);
//
//    public void updateLxhjMess(){
//        ILxhjService bean = SpringUtils.getBean(ILxhjService.class);
//        bean.initStudentLXMsg(new Lxhj());
//        log.info("定时任务：每隔3分钟对离校申请信息进行更新");
//        System.out.println("每隔3分钟对离校申请信息进行更新--------------------------------------------");
//    }
//
//}
