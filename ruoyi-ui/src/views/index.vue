<template>
  <div class="app-container">
    <el-row>
      <el-col :span="11">
        <el-card class="box-card">
          <h1>最新公告</h1>
          <div class="leftBox">
            <el-scrollbar style="height:437px">
              <p v-html="newNotice.noticeContent" class="scrollbar-demo-item" ></p>
            </el-scrollbar>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12" :offset="1">
        <el-card class="box-card">
          <h1 align="center">公告列表</h1>
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
            label-width="68px">
            <el-form-item label="公告标题" prop="noticeTitle">
              <el-input v-model="queryParams.noticeTitle" placeholder="请输入公告标题" clearable
                @keyup.enter.native="handleQuery" style="width: 140px" />
            </el-form-item>
            <el-form-item label="发布者" prop="createBy">
              <el-input v-model="queryParams.createBy" placeholder="请输入发布者" clearable @keyup.enter.native="handleQuery"
                style="width: 125px" />
            </el-form-item>
            <el-form-item label="类型" prop="noticeType">
              <el-select v-model="queryParams.noticeType" placeholder="公告类型" clearable style="width: 110px">
                <el-option v-for="dict in dict.type.sys_notice_type" :key="dict.value" :label="dict.label"
                  :value="dict.value" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="noticeList">
            <el-table-column label="公告标题" align="center" prop="noticeTitle" :show-overflow-tooltip="true" width="200" />
            <el-table-column label="公告类型" align="center" prop="noticeType" width="80">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_notice_type" :value="scope.row.noticeType" />
              </template>
            </el-table-column>
            <el-table-column label="发布者" align="center" prop="createBy" width="70" />
            <el-table-column label="发布时间" align="center" prop="createTime" width="100">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="50">
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="handleNotice(scope.row)"
                  v-hasPermi="['system:notice:edit']">查看</el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :page-sizes="[5, 10, 20, 30,50]"
            :limit.sync="queryParams.pageSize" @pagination="getList" style="align:center" />

          <!-- 查看公告对话框 -->
          <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
              <el-row>
                <el-col :span="24">
                  <el-input v-html="form.noticeContent" autosize type="textarea" />
                </el-col>
              </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button type="primary" @click="cancel">关闭</el-button>
            </div>
          </el-dialog>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listNotice, getNotice, getNewNotice } from "@/api/system/notice";

export default {
  name: "Notice",
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      //最新公告
      newNotice: {},
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 公告表格数据
      noticeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        noticeTitle: undefined,
        createBy: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        noticeTitle: [
          { required: true, message: "公告标题不能为空", trigger: "blur" }
        ],
        noticeType: [
          { required: true, message: "公告类型不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.handleNewNotice();
  },
  methods: {
    /** 查询公告列表 */
    getList() {
      this.loading = true;
      listNotice(this.queryParams).then(response => {
        this.noticeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //查询最新公告
    handleNewNotice() {
      getNewNotice().then(res => {
        this.newNotice = res.data
        console.log(this.newNotice)
      }).catch(err => {
        console.log(err)
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: undefined,
        noticeContent: undefined,
        status: "0"
      };
      this.resetForm("form");
    },
    /**查看通知、公告操作 */
    handleNotice(row) {
      let typeNotie = ""
      const noticeId = row.noticeId || this.ids
      getNotice(noticeId).then(response => {
        this.form = response.data;
        this.open = true;
        if (row.noticeType == 1) {
          typeNotie = "通知"
        } else {
          typeNotie = "公告"
        }
        this.title = typeNotie + "详情";
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  }
};
</script>
