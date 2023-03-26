<template>
  <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
  <div class="app-container">
    <el-row>
      <el-col :span="11">
        <el-card class="box-card">
          <div class="handling" style="width: 500px;height:265px;"></div>
        </el-card>
      </el-col>
      <el-col :span="12" :offset="1">
        <el-card class="box-card">
          <div class="distribute" style="width: 700px;height:265px;"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-card class="my-card">
          <div class="college" style="width:1150px;height:250px;"></div>
        </el-card>
      </el-col>
    </el-row>


  </div>
</template>
<script>
import * as echarts from 'echarts';
import { getLxhjHandleStatus, getcollegePersonNum, getcollegeLxss } from "@/api/lxgl/hj";
export default {
  data() {
    return {
      collegeGraduateNumList: [],
      collegeNameList: [],//学院名称列表
      // collegeNameAbbList: [],//学院名称缩写列表
      collegeFinishedList: [],
      collegeIncompleteList: [],
      collegeLxMessList: []
    }
  },
  mounted() {
    this.StudentDistribution(),
      this.LinkHandling(),
      this.CollegeLxqk()
  },
  methods: {
    //离校环节业务办理情况：每个环节的业务办理情况（已办理，未办理）
    LinkHandling() {
      getLxhjHandleStatus().then(res => {
        this.lxhj = res;

        /**echart */
        var handling = document.querySelector(".handling");
        var handlingChart = echarts.init(handling);

        let option = {
          title: {
            text: '业务办理情况',
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: { type: 'shadow' }
          },
          // 图例
          legend: {
            data: ['已办理', '未办理']
          },
          // X 轴
          xAxis: {
            type: 'category',
            data: ['图书馆', '教务处', '财务处', '宿舍']
          },
          // Y 轴
          yAxis: {
            type: 'value',
            name: '办理数量',

          },
          // 系列
          series: [{
            name: '已办理',
            type: 'bar',
            stack: '办理情况',
            itemStyle: {
              color: '#32CD32'
            },
            data: [this.lxhj.tsgFinished, this.lxhj.jwcFinished, this.lxhj.cwcFinished, this.lxhj.dormitoryFinished]
          }, {
            name: '未办理',
            type: 'bar',
            stack: '办理情况',
            itemStyle: {
              color: '#D3D3D3'
            },
            data: [this.lxhj.tsgIncomplete, this.lxhj.jwcIncomplete, this.lxhj.cwcIncomplete, this.lxhj.dormitoryIncomplete]
          }, {
            name: '办理总数量',
            type: 'bar',
            stack: '办理情况',
            itemStyle: {
              color: '#F7BA2A'
            },
            label: {
              show: true,
              position: 'inside',
              formatter: function (params) {
                return params.data[0] + params.data[1];
              }
            },
            data: [
              [this.lxhj.tsgFinished, this.lxhj.tsgIncomplete],
              [this.lxhj.jwcFinished, this.lxhj.jwcIncomplete],
              [this.lxhj.cwcFinished, this.lxhj.cwcIncomplete],
              [this.lxhj.dormitoryFinished, this.lxhj.dormitoryIncomplete]
            ]
          }]
        }
        handlingChart.setOption(option);
      })
    },

    //毕业生在学院分布情况:查看每个学院有多少毕业生
    StudentDistribution() {
      getcollegePersonNum().then(response => {
        this.collegeNameList = response.map(college => college.collegeName)

        const frontendData = response.map(data => {
          return { value: data.collegeGraduateNum, name: data.collegeName };
        });


        var distribute = document.querySelector(".distribute");
        var distributeChart = echarts.init(distribute);//初始化echart对象

        /*对ECharts进行一些配置*/
        let option = {
          title: {
            text: '毕业生分布情况',
            left: "center"
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
          },
          legend: {
            orient: 'vertical',
            left: 10,
            data: this.collegeNameList
          },
          series: [
            {
              name: '学院',
              type: 'pie',
              radius: '55%',
              center: ['50%', '60%'],
              data: frontendData,
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        };
        distributeChart.setOption(option);
      })
    },

    //各个学院学生的离校情况：查看每个学院有多少已经完成离校申请
    CollegeLxqk() {
      getcollegeLxss().then(res => {
        this.collegeFinishedList = res.map(college => college.collegeLxFinished)
        this.collegeIncompleteList = res.map(college => college.collegeLxIncomplete)
        this.collegeNameAbbList = res.map(college => {
          if (college.collegeName === '计算机学院') {
            return '计算机系'
          } else if (college.collegeName === '体育学院') {
            return '体育系'
          } else if (college.collegeName === '管理学院') {
            return '管理系'
          } else if (college.collegeName === '美术学院') {
            return '美术系'
          } else if (college.collegeName === '音乐学院') {
            return '音乐系'
          } else if (college.collegeName === '文学院') {
            return '文学系'
          } else if (college.collegeName === '数学学院') {
            return '数学系'
          } else if (college.collegeName === '外国语言文化学院') {
            return '外国语言系'
          } else if (college.collegeName === '物理与信息工程学院') {
            return '物理系'
          } else if (college.collegeName === '生物与食品工程学院') {
            return '生物系'
          } else if (college.collegeName === '化学与材料科学学院') {
            return '化学系'
          } else {
            return college
          }
        })

        const frontendData = res.map(data => {
          return [[data.collegeLxFinished, data.collegeLxIncomplete]];
        });


        var college = document.querySelector(".college");
        var collegeChart = echarts.init(college);

        let option = {
          title: {
            text: '学生离校情况',
            left: "center"
          },
          // 图例
          legend: {
            data: ['已离校', '未离校'],
            left: 'left'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: { type: 'shadow' }
          },
          // X 轴
          xAxis: {
            type: 'category',
            // data: ['计算机系', '体育系', '管理系', '美术系', '音乐系', '文学系', '数学系', '外国语言系', '物理系', '生物系', '化学系']
            data: this.collegeNameAbbList
          },
          // Y 轴
          yAxis: {
            type: 'value',
            name: '离校数量',
          },
          // 系列
          series: [{
            name: '已离校',
            type: 'bar',
            stack: '离校情况',
            itemStyle: {
              color: '#228B22'
            },
            data: this.collegeFinishedList
          }, {
            name: '未离校',
            type: 'bar',
            stack: '离校情况',
            itemStyle: {
              color: '#FA8072'
            },
            data: this.collegeIncompleteList
          }, {
            name: '离校总数量',
            type: 'bar',
            stack: '离校情况',
            itemStyle: {
              color: '#F7BA2A'
            },
            label: {
              show: true,
              position: 'inside',
              formatter: function (params) {
                return params.data[0] + params.data[1];
              }
            },
            data: frontendData
          }]
        }
        collegeChart.setOption(option);
      })
    }
  },
}
</script>
<style scoped>
.my-card {
  margin-top: 20px;
}
</style>