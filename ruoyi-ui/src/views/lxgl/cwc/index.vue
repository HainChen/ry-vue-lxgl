<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生编号" prop="stuId">
        <el-input style="width: 150px" v-model="queryParams.stuId" placeholder="请输入学生编号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="金额区间" prop="beginTotalArrears">
        <el-input style="width: 120px" placeholder="最低金额" v-model="queryParams.beginTotalArrears" clearable
          @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="~" prop="endTotalArrears">
        <el-input style="width: 120px" placeholder="最高金额" v-model="queryParams.endTotalArrears" clearable
          @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>

      <el-form-item label="处理状态" prop="handleStatus">
        <el-select style="width: 150px" v-model="queryParams.handleStatus" placeholder="请选择处理状态" clearable>
          <el-option v-for="dict in dict.type.lxgl_cwc_status" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['lxgl:cwc:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['lxgl:cwc:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" icon="el-icon-upload2" size="mini" @click="handleImport"
          v-hasPermi="['lxgl:cwc:import']">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['lxgl:cwc:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="cwcList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="50" />
      <el-table-column label="学生编号" align="center" prop="stuId" width="100" />
      <el-table-column label="姓名" align="center" prop="sysUser.userName" width="100" />
      <el-table-column label="班级" align="center" prop="sysUser.dept.deptName" />
      <el-table-column label="欠费条目" align="center" prop="arrears" />
      <el-table-column label="欠费总额" align="center" prop="totalArrears" width="100" />
      <el-table-column label="处理状态" align="center" prop="handleStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.lxgl_cwc_status" :value="scope.row.handleStatus" />
        </template>
      </el-table-column>
      <el-table-column label="处理时间" align="center" prop="updateTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['lxgl:cwc:edit']">审核</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['lxgl:cwc:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改财务处管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生ID" prop="stuId">
          <el-input v-model="form.stuId" placeholder="请输入学生ID" />
        </el-form-item>
        <el-form-item label="欠费条目" prop="arrears">
          <el-input v-model="form.arrears" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="欠费总额" prop="totalArrears">
          <el-input v-model="form.totalArrears" placeholder="请输入欠费总额" />
        </el-form-item>
        <el-form-item label="处理状态" prop="handleStatus">
          <el-select v-model="form.handleStatus" placeholder="请选择处理状态">
            <el-option v-for="dict in dict.type.lxgl_cwc_status" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px">
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
            @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCwc, getCwc, delCwc, addCwc, updateCwc } from "@/api/lxgl/cwc";
import { getToken } from "@/utils/auth";

export default {
  name: "Cwc",
  dicts: ['lxgl_cwc_status'],
  // 用户导入参数
  data() {
    return {
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
      // 财务处管理表格数据
      cwcList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stuId: null,
        arrears: null,
        totalArrears: null,
        beginTotalArrears: null,
        endTotalArrears: null,
        handleStatus: null,
        status: null,
      },
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/lxgl/cwc/importData"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        stuId: [
          { required: true, message: "学生ID不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('/lxgl/cwc/importTemplate', {
      }, `arrears_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 查询财务处管理列表 */
    getList() {
      this.loading = true;
      listCwc(this.queryParams).then(response => {
        this.cwcList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        stuId: null,
        arrears: null,
        totalArrears: null,
        // totalArrears: null,
        beginTotalArrears: null,
        endTotalArrears: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加欠费信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCwc(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "欠费审核";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCwc(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCwc(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除编号为"' + ids + '"的数据项？').then(function () {
        return delCwc(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('lxgl/cwc/export', {
        ...this.queryParams
      }, `cwc_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
