<template>
    <div class="app-container" v-if="!isBlank">
        <div class="left" style="width: 50%; float: left;">
            <el-timeline>
                <el-timeline-item :timestamp="lxhj.cwc.updateTime" placement="top"
                    :icon="lxhj.cwc.handleStatus === 1 ? 'el-icon-check' : ''"
                    :type="lxhj.cwc.handleStatus === 1 ? 'success' : ''" size="large">
                    <el-card>
                        <h1>财务环节</h1>
                        <p v-if="lxhj.cwc.handleStatus === 0">存在欠费情况，欠费条目：{{ lxhj.cwc.arrears }}，欠费总金额：{{
                            lxhj.cwc.totalArrears }}</p>
                    </el-card>
                </el-timeline-item>
                <el-timeline-item :timestamp="lxhj.dormitory.updateTime" placement="top"
                    :icon="lxhj.dormitory.handleStatus === 1 ? 'el-icon-check' : ''"
                    :type="lxhj.dormitory.handleStatus === 1 ? 'success' : ''" size="large">
                    <el-card>
                        <h1>宿舍环节</h1>
                        <p v-if="lxhj.dormitory.handleStatus === 0">存在损坏家具情况，损坏家具：{{ lxhj.dormitory.furniture }}，赔偿金额：{{
                            lxhj.dormitory.indemnity }}</p>
                    </el-card>
                </el-timeline-item>
                <el-timeline-item :timestamp="lxhj.jwc.updateTime" placement="top"
                    :icon="lxhj.jwc.handleStatus === 1 ? 'el-icon-check' : ''"
                    :type="lxhj.jwc.handleStatus === 1 ? 'success' : ''" size="large">
                    <el-card>
                        <h1>教务处环节</h1>
                        <p v-if="lxhj.jwc.handleStatus === 0">存在挂科情况，挂科科目：{{ lxhj.jwc.course }}，未修学分：{{ lxhj.jwc.credit }}
                        </p>
                    </el-card>
                </el-timeline-item>
                <el-timeline-item :timestamp="lxhj.tsg.updateTime" placement="top"
                    :icon="lxhj.tsg.handleStatus === 1 ? 'el-icon-check' : ''"
                    :type="lxhj.tsg.handleStatus === 1 ? 'success' : ''" size="large">
                    <el-card>
                        <h1>图书馆环节</h1>
                        <p v-if="lxhj.tsg.handleStatus === 0">存在欠书情况，所借阅的图书：{{ lxhj.tsg.bookName }}，逾期天数：{{
                            lxhj.tsg.overdueDays }}</p>
                    </el-card>
                </el-timeline-item>
                <el-timeline-item :timestamp="lxhj.updateTime" placement="top"
                    :icon="lxhj.handleStatus === 2 ? 'el-icon-warning-outline' : (lxhj.handleStatus === 1 ? 'el-icon-check' : '')"
                    :type="lxhj.handleStatus === 2 ? 'warning' : (lxhj.handleStatus === 1 ? 'success' : '')" size="large">
                    <el-card>
                        <h1>离校申请环节</h1>
                        <p v-if="lxhj.handleStatus === 1">离校申请已经通过审核，可离校。</p>
                        <p v-if="lxhj.handleStatus === 2">前置环节未完成。</p>
                        <p v-else>等待辅导员审核离校申请。</p>
                    </el-card>
                </el-timeline-item>
            </el-timeline>
        </div>
        <div class="right" style="width: 40%; float: right; margin-top: 20px;">
            <el-card>
                <div>
                    <h1 style="text-align:center ;">流程说明</h1>
                </div>
                <div>
                    <p> 每个环节都存在状态,状态分为3种（"待审核","审核通过<i style="color: green;" class="el-icon-success"></i>","前置手续未完成<i
                            style="color: yellow;" class="el-icon-warning"></i>"）。</p>
                    <p>前面4个环节只有"待审核"和"审核通过<i style="color: green;" class="el-icon-success"></i>"两种状态，
                        而最后的离校申请环节包含3种状态。</p>
                    <p> 离校申请默认状态为"前置手续未完成<i style="color: yellow;" class="el-icon-warning"></i>", 前面4个环节的状态都为"审核通过<i
                            style="color: green;" class="el-icon-success"></i>"时，自动发起离校申请给辅导员。</p>
                    <p>辅导员审核通过后，毕业生即可领取毕业证和学位证完成离校。
                    </p>
                </div>
            </el-card>
        </div>
    </div>
    <div v-else>
        <h1 style="color: red;margin-left: 30px;">非毕业生，不可查看离校环节业务情况。</h1>
    </div>
</template>
  
<script>
import { getLxhj } from "@/api/lxgl/hj";
export default {
    name: "Hj",
    dicts: ['lxgl_cwc_status'],
    data() {
        return {
            isBlank: false,
            lxhj: {
                cwc: {
                    arrears: null,
                    totalArrears: null,
                    handleStatus: null,
                    updateTime: null
                },
                dormitory: {
                    furniture: null,
                    indemnity: null,
                    handleStatus: null,
                    updateTime: null
                },
                jwc: {
                    course: null,
                    credit: null,
                    handleStatus: null,
                    updateTime: null

                },
                tsg: {
                    bookName: null,
                    overdueDays: null,
                    handleStatus: null,
                    updateTime: null
                },
                updateTime: null,
                handleStatus: null
            },
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                stuId: null,
                handleStatus: null,
            },
        };
    },
    mounted() {
        getLxhj().then(res => {
            if (res === null|| res==='') {
               this.isBlank = true;
            } else {
                this.lxhj = res;
            }
        });
    },
};
</script>
