<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!--创建表格-->
<script>
    $(function(){
        $("#tt").jqGrid({
            styleUI:"Bootstrap",//使用bootstrap样式
            autowidth:true,//自动适应父容器
            url:"${app}/user/queryAll",//发送请求
            datatype:"json",//指定json格式
            colNames:[ '编号', '用户名', '昵称', '头像','电话 ','性别','省份','城市','签名'],//设置列名
            colModel:[
                {name : "id"},
                {name : "username",editable:true},
                {name : "nickname",editable:true},
                {name : "photo",
                    editable:true,
                    index:"photo",
                    edittype:"file",
                    editoptions:{enctype:"multipart/form-data"},
                    formatter:function (value,options,rows) {
                        return '<img src="${app}/user/image/'+rows.photo+'"  style="width:200px;height:70px;" />';
                    }},
                {name : "phone",editable:true},
                {name : "sex",editable:true,edittype:"select",editoptions: {value:"男:男;女:女"}},
                {name : "province",editable:true},
                {name : "city",editable:true},
                {name : "sign",editable:true}

            ],//为列赋值
            pager:"#pager",//设置分页栏
            rowNum:"2",//每页显示多少行
            rowList:[2,5,20,50],//下拉列表
            viewrecords:true,//显示总条数
            editurl:"${app}/user/edit",//编辑时的url
            caption : "用户列表",
            datefmt:"yyyy/mm/dd",//指定日期格式
        }).jqGrid("navGrid","#pager",{edit:true,add:true,del:true,search:false},{
            //控制修改
            closeAfterEdit:true,
            beforeShowForm:function (fmt) {
                fmt.find("#photo").attr("disable",true);
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
                        url:"${app}/user/upload",
                        type:"post",
                        fileElementId:"photo",
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



        $("#export").click(function () {
            window.location.href="${pageContext.request.contextPath}/user/export";

        })

</script>

<!--页头-->
<ul class="nav nav-tabs">
    <li class="active"><a href="" >所有用户</a></li>
    <li><a href="#" id="export">导出用户信息</a></li>
</ul>
<table id="tt"></table>
<div id="pager"></div>
