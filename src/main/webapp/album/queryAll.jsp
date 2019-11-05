<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%-- 创建表格 --%>
<script>
    $(function(){
        pageInit();
    });
    function pageInit(){
        jQuery("#album-table").jqGrid(
            {
                url : "${app}/album/queryAll",
                datatype : "json",
                height : 300,
                colNames : [ '编号', '标题', '封面', '章节数', '得分','原创' ,'简介','时间'],
                colModel : [
                    {name : 'id'},
                    {name : 'title',editable:true},
                    {name : 'cover',editable:true,edittype:"file",
                        editoptions:{enctype:"multipart/form-data"},
                        formatter:function (value,options,rows) {
                            return '<img src="${app}/album/image/'+rows.cover+'"  style="width:200px;height:70px;" />';
                        }
                    },
                    {name : 'count',editable:true},
                    {name : 'score',editable:true},
                    {name : 'starId',editable:true,
                        edittype: "select",
                        editoptions: {
                            dataUrl:"${app}/star/queryStarName"
                        },
                        formatter:function (value,options,rows) {
                            return rows.star.realname;
                        }
                    },
                    {name : 'brief',editable:true},
                    {name : 'createDate',edittype:"date"}
                ],
                styleUI:"Bootstrap",
                autowidth:true,
                rowNum : 2,
                rowList : [ 2, 5, 10, 15 ],
                pager : '#pager',
                viewrecords : true,
                multiselect : false,
                editurl:"${app}/album/edit",
                subGrid : true,
                caption : "专辑列表",
                subGridRowExpanded : function(subgrid_id, row_id) {
                    var subgrid_table_id, pager_id;
                    subgrid_table_id = subgrid_id + "_t";
                    pager_id = "p_" + subgrid_table_id;
                    $("#" + subgrid_id).html(
                        "<table id='" + subgrid_table_id
                        + "' class='scroll'></table><div id='"
                        + pager_id + "' class='scroll'></div>");
                    jQuery("#" + subgrid_table_id).jqGrid(
                        {
                            url : "${app}/chapter/queryAll?albumId=" + row_id,
                            datatype : "json",
                            colNames : [ '编号', '名称','singer', '大小', '时长','日期','在线播放'],
                            colModel : [
                                {name : "id"},
                                {name : "name",editable:true,edittype:"file"},
                                {name : "singer",editable:true},
                                {name : "size"},
                                {name : "duration"},
                                {name : "createDate"},
                                {name : "options",width:300,formatter:function (value,option,rows) {
                                        return "<audio controls>\n" +
                                            "  <source src='${pageContext.request.contextPath}/album/music/"+rows.name+"' >\n" +
                                            "</audio>";
                                }}
                            ],
                            rowNum : 20,
                            pager : pager_id,
                            viewrecords: true,
                            styleUI:"Bootstrap",
                            autowidth:true,
                            editurl:"${app}/chapter/edit?adlumId="+row_id,
                            height : '100%'
                        });
                    jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                        "#" + pager_id, {edit : false,add : true,del : false},{},
                    {
                        //控制添加
                        closeAfterAdd: true,
                        afterSubmit:function (data) {
                            var status = data.responseJSON.status;
                            if(status){
                                var cid = data.responseJSON.message;
                                $.ajaxFileUpload({
                                    url:"${app}/chapter/upload",
                                    type:"json",
                                    fileElementId:"name",
                                    data:{id:cid,albumId:row_id},
                                    success:function (data) {
                                        $("#subgrid_table_id").trigger("reloadGrid");
                                    }
                                })
                            }
                            return "123";
                        }
                    });
                },
            });
        jQuery("#album-table").jqGrid('navGrid', '#pager', { add : true, edit : true, del : false },{
            //控制修改
            closeAfterEdit:true,
            beforeShowForm:function (fmt) {
                fmt.find("#cover").attr("disable",true);
            }
        },{
            //控制添加
            closeAfterAdd:close,
            afterSubmit:function (data) {
                console.log(data);
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if(status){
                    $.ajaxFileUpload({
                        url:"${app}/album/upload",
                        type:"post",
                        fileElementId:"cover",
                        data:{id:id},
                        success:function () {
                            $("#album-table").trigger("reloadGrid");
                        }
                    })
                }else{
                    alert(data.responseJSON.message);
                }
                return "12331";
            }
        });
    }
</script>

<!--页头-->
<div class="page-header" style="margin: -20px 0px 0px">
    <h3>专辑管理列表</h3>
</div>
<table id="album-table"></table>
<div id="pager"></div>