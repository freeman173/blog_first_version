<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-pie-chart"></i> schart图表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="plugins-tips">
                vue-schart：vue.js封装sChart.js的图表组件。
                访问地址：
                <a href="https://github.com/lin-xin/vue-schart" target="_blank">vue-schart</a>
            </div>
            <div class="schart-box">
                <div class="content-title">柱状图</div>
                <schart class="schart" canvasId="bar" :options="options1"></schart>
            </div>
            <div class="schart-box">
                <div class="content-title">折线图</div>
                <schart class="schart" canvasId="line" :options="options2"></schart>
            </div>
            <div class="schart-box">
                <div class="content-title">饼状图</div>
                <schart class="schart" canvasId="pie" :options="options3"></schart>
            </div>

<!--            <div class="schart-box">-->
<!--                <div class="content-title">环形图</div>-->
<!--                <schart class="schart" canvasId="ring" :options="options4"></schart>-->
<!--            </div>-->
        </div>
    </div>
</template>

<script>
import Schart from "vue-schart";
import {reactive, ref} from "vue";
import {getLineData, getPieData,getBarData} from "../api";
export default {
    name: "basecharts",
    components: {
        Schart,
    },

  setup() {


        const options1 = reactive({
            type: "bar",
            title: {
                text: "近期文章浏览量",
            },
            bgColor: "#fbfbfb",
            //文章标题
            labels: [],
            datasets: [
                {
                    label: "评论数量",
                    fillColor: "rgba(241, 49, 74, 0.5)",
                    data: [],
                },
                {
                    label: "浏览量",
                    data: [],
                },
                // {
                //     label: "食品",
                //     data: [144, 198, 150, 235, 120],
                // },
            ],
        });
        const options2 = reactive({
            type: "line",
            title: {
                text: "近段时间文章注册情况",
            },
            bgColor: "#fbfbfb",
            labels: [],
            datasets: [
                {
                    label: "文章数据",
                    data: [],
                },
            ],
        });

        //小细节：使用reactive定义变量，才能在setup模块中改变变量的值！！
        const options3 = reactive({
            type: "pie",
            title: {
                text: "文章类别饼状图",
            },
            legend: {
                position: "left",
            },
            bgColor: "#fbfbfb",
            labels: [],
            datasets: [
                {
                    data: [],
                },
            ],
        });
        const options4 = {
            type: "ring",
            title: {
                text: "环形三等分",
            },
            showValue: false,
            legend: {
                position: "bottom",
                bottom: 40,
            },
            bgColor: "#fbfbfb",
            labels: ["vue", "react", "angular"],
            datasets: [
                {
                    data: [500, 500, 500],
                },
            ],
        };
        let pieData = ref([]);
        let lineData=ref([]);
        let barData=ref([]);


      //  自定义函数;小细节：放到变量后面才能打印变量
      //获取绘图数据
        const getData = () => {
            getPieData().then((res) => {
                pieData=res.data
                for( let i in pieData){
                  // console.log(pieData[i].categoryName)
                  options3.labels.push(pieData[i].categoryName)
                  options3.datasets[0].data.push(pieData[i].categoryIdNumber)
                }
            }
            ),

            getLineData().then((res) => {
                  lineData=res.data
                  // console.log(lineData)
                  for( let i in lineData){
                // console.log(pieData[i].categoryName)
                  options2.labels.push(lineData[i].time)
                  options2.datasets[0].data.push(lineData[i].numbers)

              }

            },
        ),


          getBarData().then((res) => {
                barData=res.data
                console.log(barData)
                for( let i in barData){
                  // console.log(pieData[i].categoryName)

                  options1.labels.push(barData[i].id)
                  options1.datasets[1].data.push(barData[i].viewCounts)
                  options1.datasets[0].data.push(barData[i].reviewCounts)
                }

              },

          )


      };
      //从后台拿到绘制图表所需要的数据
      getData()

       return {
            options1,
            options2,
            options3,
            options4,
        };
    },
};
</script>

<style scoped>
.schart-box {
    display: inline-block;
    margin: 20px;
}
.schart {
    width: 600px;
    height: 400px;
}
.content-title {
    clear: both;
    font-weight: 400;
    line-height: 50px;
    margin: 10px 0;
    font-size: 22px;
    color: #1f2f3d;
}
</style>