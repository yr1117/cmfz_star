<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="app" ></c:set>
<script>
    $(function(){
        $("#atricle-show-table").jqGrid({
            styleUI:"Bootstrap",//使用bootstrap样式
            autowidth:true,//自动适应父容器
            url:"${app}/article/queryAll",//发送请求
            datatype:"json",//指定json格式
            colNames:["编号","名称","作者","简介","内容","日期","操作"],//设置列名
            colModel:[
                {name:"id",editable:true},
                {name:"title"},
                {name:"author"},
                {name:"brief"},
                {name:"content",hidden:true},
                {name:"createDate"},
                {name:"options",formatter:function (value,option,rows) {
                        return "<a class='btn btn-primary' onclick=\"openModal('edit','"+rows.id+"')\">修改</a>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger' onclick=\"del('"+rows.id+"')\">删除</a>";
                    }}
            ],//为列赋值
            pager:"#atricle-show-pager",//设置分页栏
            rowNum:"2",//每页显示多少行
            rowList:[2,5,20,50],//下拉列表
            viewrecords:true,//显示总条数
            caption : "文章列表",
        }).jqGrid("navGrid","#atricle-show-pager",{edit:false,add:false,del:false,search:false});//配置增删改
    });

    //打开模态框
    function openModal(oper,id) {
       if("add"==oper){
           //添加时将模态框的值清空
           $("#article-id").val("");
           $("#article-title").val("");
           $("#article-author").val("");
           $("#article-brief").val("");
           KindEditor.html("editor_id","");
       }
       if("edit"==oper){
           //根据id获取到具体某一行的数据
            var article = $("#atricle-show-table").jqGrid("getRowData",id);
           console.log(article);
           //将模态框的值设置为当前的值
           $("#article-id").val(article.id);
           $("#article-title").val(article.title);
           $("#article-author").val(article.author);
           $("#article-brief").val(article.brief);
           KindEditor.html("#editor_id",article.content);
       }
       $("#article-modal").modal("show");
    }

    function save(){
        var id = $("#article-id").val();
        var url = ""
        if(id){
            url = "${app}/article/edit";
        }else{
            url = "${app}/article/add";
        }
        $.ajax({
            url:url,
            type:"post",
            data:$("#article-form").serialize(),
            datatype:"json",
            success:function (result) {
                console.log(result);
            }
        })
    }
    function del(id){
        $.ajax({
            url:"${app}/article/del",
            type:"post",
            data:{id:id},
            datatype:"json",
            success:function (result) {
                console.log(result);
            }
        })
    }
    KindEditor.create('#editor_id',{
        //设置宽度
        width:'460px',
        //点击图片空间按钮时发送请求
        fileManagerJson:"${app}/article/browse",
        //展示图片空间按钮
        allowFileManager:true,
        //上传图片所对应的方法
        uploadJson:"${app}/article/upload",
        //上传图片是图片的额形参名称
        filePostName:"articleImg",
        afterBlur:function () {
            this.sync();
        }
    });
    
</script>


<ul class="nav nav-tabs">
    <li class="active"><a href="" >所有文章</a></li>
    <li><a href="#" onclick="openModal('add','')">添加文章</a></li>
</ul>
<table id="atricle-show-table"></table>
<div id="atricle-show-pager"></div>

<%-- 模态框 --%>
<div id="article-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 683px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">文章操作</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="article-form">
                    <input type="hidden" name="id" id="article-id">
                    <div class="form-group">
                        <label for="article-title" class="col-sm-2 control-label">文章标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control" id="article-title" placeholder="请输入文章标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-author" class="col-sm-2 control-label">文章作者</label>
                        <div class="col-sm-10">
                            <input type="text" name="author" class="form-control" id="article-author" placeholder="请输入文章作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-brief" class="col-sm-2 control-label">文章简介</label>
                        <div class="col-sm-10">
                            <input type="text" name="brief" class="form-control" id="article-brief" placeholder="请输入文章简介">
                        </div>
                    </div>
                    <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="save()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->