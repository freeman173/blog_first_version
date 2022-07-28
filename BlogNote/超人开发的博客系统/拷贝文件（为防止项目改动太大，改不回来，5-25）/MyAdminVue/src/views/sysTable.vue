<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 人员管理表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
<!--                <el-select v-model="query.mobileNumber" placeholder="地址" class="handle-select mr10">-->
<!--                    <el-option key="1" label="广东省" value="广东省"></el-option>-->
<!--                    <el-option key="2" label="湖南省" value="湖南省"></el-option>-->
<!--                </el-select>-->
                <el-input v-model="query.mobileNumber" placeholder="手机号" class="handle-input mr10"></el-input>
                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="nickName" label="用户名"></el-table-column>
              <el-table-column prop="mobilePhoneNumber" label="手机号"></el-table-column>
<!--                <el-table-column label="手机号">-->
<!--                    <template #default="scope">￥{{ scope.row.money }}</template>-->
<!--                </el-table-column>-->
                <el-table-column label="头像(查看大图)" align="center">
                    <template #default="scope">
                        <el-image class="table-td-thumb" :src="avatar" :preview-src-list="[scope.row.thumb]">
                        </el-image>
                    </template>
                </el-table-column>
<!--                <el-table-column prop="address" label="地址"></el-table-column>-->
<!--                <el-table-column label="状态" align="center">-->
<!--                    <template #default="scope">-->
<!--                        <el-tag :type="-->
<!--                                scope.row.state === '成功'-->
<!--                                    ? 'success'-->
<!--                                    : scope.row.state === '失败'-->
<!--                                    ? 'danger'-->
<!--                                    : ''-->
<!--                            ">{{ scope.row.state }}</el-tag>-->
<!--                    </template>-->
<!--                </el-table-column>-->

                <el-table-column prop="createDate" label="注册时间"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row,scope.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form label-width="70px">
              <el-form-item label="用户名">
                <el-input v-model="form.id"></el-input>
              </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="form.nickName"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="form.mobilePhoneNumber"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import {ref, reactive} from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { fetchData,editData,deleteData } from "../api/index";

export default {
    name: "basetable",

  setup() {

        // onMounted(()=>{
        //   window.vue=this
        // })
    // 获取表格数据
        const query = reactive({
            mobileNumber: "",
            name: "",
            pageIndex: 1,
            pageSize: 10,
        });
        const tableData = ref([]);
        const pageTotal = ref(0);


        const getData = () => {
            fetchData(query).then((res) => {
                tableData.value = res.data;
                pageTotal.value = res.pageTotal || 50;
            });
        };
        getData();


    // 查询操作
        const handleSearch = () => {
            query.pageIndex = 1;
            getData();
        };
        // 分页导航
        const handlePageChange = (val) => {
            query.pageIndex = val;
            getData();
        };


        // 删除操作
        const handleDelete = (index) => {
            // 二次确认删除
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            })
                .then(() => {
                    //删除元素的id传过去
                    console.log(tableData.value[index].id)
                    //后台删除数据
                    deleteData(tableData.value[index].id).then((res) => {
                    console.log(res.data)
                    });
                    //前台删数据
                    tableData.value.splice(index, 1);
                    ElMessage.success("删除成功");
                })
                .catch(() => {});
        };


        // 表格编辑时弹窗和保存
        let form = reactive({
            id:"",
            nickName: "",
            mobilePhoneNumber: "",
         });

        const editVisible = ref(false);

        let idx = -1;
        const handleEdit = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            editVisible.value = true;
        };

        const EditData = () => {
          editData(form).then((res) => {
            //调试一下而已，没问题了
            // console.log(tableData.value[idx])
            tableData.value[idx]=res.data
            // console.log(res.data)
            });
          };
        const saveEdit = () => {
            editVisible.value = false;
            Object.keys(form).forEach((item) => {
                // tableData.value[idx][item] = form[item];
                EditData()
            });
          ElMessage.success(`修改第 ${idx + 1} 行成功`);
        };

        return {
            query,
            tableData,
            pageTotal,
            editVisible,
            form,
            handleSearch,
            handlePageChange,
            handleDelete,
            handleEdit,
            saveEdit,
        };
    },


};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
