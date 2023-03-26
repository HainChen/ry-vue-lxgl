<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生ID" prop="stuId">
        <el-input v-model="queryParams.stuId" placeholder="请输入学生ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="处理状态" prop="handleStatus">
        <el-select v-model="queryParams.handleStatus" placeholder="请选择处理状态" clearable>
          <el-option v-for="dict in dict.type.lxgl_cwc_status" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" :disabled="isUpdating" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['lxgl:hj:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hjList" @selection-change="handleSelectionChange">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column  label="ID" align="center" prop="id" /> -->
      <el-table-column label="学生ID" align="center" prop="stuId" />
      <el-table-column label="姓名" align="center" prop="sysUser.userName" width="100" />
      <el-table-column label="班级" align="center" prop="sysUser.dept.deptName" />
      <el-table-column label="财务处环节" align="center" prop="cwc.handleStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.lxgl_cwc_status" :value="scope.row.cwc.handleStatus" />
        </template>
      </el-table-column>
      <el-table-column label="教务处环节" align="center" prop="jwc.handleStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.lxgl_cwc_status" :value="scope.row.jwc.handleStatus" />
        </template>
      </el-table-column>
      <el-table-column label="宿舍环节" align="center" prop="dormitory.handleStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.lxgl_cwc_status" :value="scope.row.dormitory.handleStatus" />
        </template>
      </el-table-column>
      <el-table-column label="图书馆环节" align="center" prop="tsg.handleStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.lxgl_cwc_status" :value="scope.row.tsg.handleStatus" />
        </template>
      </el-table-column>
      <el-table-column label="处理状态" align="center" prop="handleStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.lxgl_cwc_status" :value="scope.row.handleStatus" />
        </template>
      </el-table-column>
      <el-table-column label="处理时间" align="center" prop="updateTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button :disabled="scope.row.handleStatus == 2" size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['lxgl:hj:edit']">审核</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改离校资格对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生ID" prop="stuId">
          <el-input v-model="form.stuId" placeholder="请输入学生ID" />
        </el-form-item>

        <el-form-item label="处理状态" prop="handleStatus">
          <el-select v-model="form.handleStatus" placeholder="请选择处理状态">
            <el-option v-for="dict in dict.type.lxgl_cwc_status" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listHj, getHj, delHj, addHj, updateHj, updateLxhjMess } from "@/api/lxgl/hj";

export default {
  name: "Hj",
  dicts: ['lxgl_cwc_status'],
  data() {
    return {
      isUpdating: false,
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
      // 离校资格表格数据
      hjList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stuId: null,
        handleStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        stuId: [
          { required: true, message: "学生ID不能为空", trigger: "blur" }
        ],
        ssId: [
          { required: true, message: "宿舍ID不能为空", trigger: "blur" }
        ],
        cwcId: [
          { required: true, message: "财务处ID不能为空", trigger: "blur" }
        ],
        tsgId: [
          { required: true, message: "图书馆ID不能为空", trigger: "blur" }
        ],
        jwcId: [
          { required: true, message: "教务处ID不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询离校资格列表 */
    getList() {
      this.loading = true;
      listHj(this.queryParams).then(response => {
        this.hjList = response.rows;
        console.log(this.hjList);
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
        ssId: null,
        cwcId: null,
        tsgId: null,
        jwcId: null,
        handleStatus: null,
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
      //数据更新后再获取列表
      this.isUpdating = true; // 设置更新状态为 true
      this.resetForm("queryForm");
      updateLxhjMess().then(res => {
        console.log("更新离校环节最新申请数据");
        this.isUpdating = false; // 更新完成后设置更新状态为 false
      });
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
      this.title = "添加离校资格";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHj(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "离校资格审核";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHj(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHj(this.form).then(response => {
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
        return delHj(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('lxgl/hj/export', {
        ...this.queryParams
      }, `hj_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
