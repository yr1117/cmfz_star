<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!--创建表格-->
<script>
    $(function(){
        $("#tt").jqGrid({
            styleUI:"Bootstrap",//使用bootstrap样式
            autowidth:true,//自动适应父容器
            url:"${app}/banner/queryAll",//发送请求
            datatype:"json",//指定json格式
            colNames:["名称","封面","描述","状态","日期"],//设置列名
            colModel:[
                {name:"name",editable:true},
                {
                    name:"cover",editable:true,
                    index:'cover',//索引，以后台交互参数
                    edittype:'file',//可以编辑的类型
                    editoptions:{enctype:"multipart/form-data"},
                    formatter:function (value, options, row) { //对列进行格式化时设置的函数名或者类型
                        return '<img src="${app}/banner/img/'+row.cover+'"  style="width:200px;height:70px;" />';
                    }
                },
                {name:"description",editable:true},
                {
                    name:"status",
                    editable:true,
                    edittype:"select",
                    editoptions:{
                        value:"正常:正常;冻结:冻结"  //书写本地数据
                    }
                },
                {name:"createDate"}

            ],//为列赋值
            pager:"#pager",//设置分页栏
            rowNum:"2",//每页显示多少行
            rowList:[2,5,20,50],//下拉列表
            viewrecords:true,//显示总条数
            editurl:"${app}/banner/edit",//编辑时的url
            caption : "轮播图列表",
            datefmt:"yyyy/mm/dd",//指定日期格式
        }).jqGrid("navGrid","#pager",{edit:true,add:true,del:true,search:false},{
                //控制修改
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#cover").attr("disable",true);
                }
            },{

            //控制添加
            closeAfterAdd:close,//关闭添加框
            afterSubmit:function (data) {
                console.log(data);
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if(status){
                    $.ajaxFileUpload({
                        url:"${app}/banner/upload",
                        type:"post",
                        fileElementId:"cover",
                        data:{id:id},
                        success:function () {
                            $("#tt").trigger("reloadGrid");//刷新表格
                        }
                    })
                }else{
                    alert(data.responseJson.message);
                }
                return "12312"
            }
        });//配置增删改
    });
</script>

<!--页头-->
<div class="page-header" style="margin: -20px 0px 0px">
    <h3>轮播图管理</h3>
</div>
<table id="tt"></table>
<div id="pager"></div>
