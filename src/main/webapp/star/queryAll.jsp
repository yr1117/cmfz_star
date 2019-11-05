<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%-- 创建表格 --%>
<script>
    $(function(){
        pageInit();
    });
    function pageInit(){
        jQuery("#star-table").jqGrid(
            {
                url: '${app}/star/queryAll',
                datatype: "json",
                height: 300,
                colNames: ['编号', '艺名', '真名', '性别', '生日','头像'],
                colModel: [
                    {name: 'id'},
                    {name: 'nickname',editable:true},
                    {name: 'realname',editable:true},
                    {name: 'sex',editable:true, edittype: "select" ,editoptions:{value: "男:男;女:女"}},
                    {name: 'bir',editable:true},
                    {name:"photo",
                        editable:true,
                        index:"photo",
                        edittype:"file",
                        editoptions:{enctype:"multipart/form-data"},
                        formatter:function (value,options,rows) {
                            return '<img src="${app}/star/image/'+rows.photo+'"  style="width:200px;height:70px;" />';
                        }}
                ],
                styleUI:"Bootstrap",
                autowidth:true,
                rowNum : 2,
                rowList : [ 2, 5, 10],
                pager: '#pager',
                multiselect:false,
                viewrecords: true,
                subGrid: true,
                editurl:"${app}/star/edit",
                caption: "所有明星列表",
                subGridRowExpanded : function(subgrid_id, id) {
                    var subgrid_table_id, pager_id;
                    subgrid_table_id = subgrid_id + "_t";
                    pager_id = "p_" + subgrid_table_id;
                    $("#" + subgrid_id).html(
                        "<table id='" + subgrid_table_id
                        + "' class='scroll'></table><div id='"
                        + pager_id + "' class='scroll'></div>");
                    jQuery("#" + subgrid_table_id).jqGrid(
                        {
                            url : "${app}/user/queryAllByStarId?starId="+id,
                            datatype : "json",
                            colNames : [ '编号', '用户名', '昵称', '头像','电话 ','性别','省份','城市','签名' ],
                            colModel : [
                                {name : "id"},
                                {name : "username",editable:true},
                                {name : "nickname",editable:true},
                                {name : "photo",
                                    editoptions:{enctype:"multipart/form-data"},
                                    formatter:function (value,options,rows) {
                                        return '<img src="${app}/user/image/'+rows.photo+'"  style="width:200px;height:70px;" />';
                                    }},
                                {name : "phone",editable:true},
                                {name : "sex",editable:true,edittype:"select",editoptions: {value:"男:男;女:女"}},
                                {name : "province",editable:true},
                                {name : "city",editable:true},
                                {name : "sign",editable:true}
                            ],
                            styleUI:"Bootstrap",
                            rowNum : 2,
                            pager : pager_id,
                            viewrecords: true,
                            autowidth:true,
                            height : '100%'
                        });
                    jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                        "#" + pager_id, {
                            edit : false,
                            add : true,
                            del : false,
                            search:false
                        });
                },
            });
        jQuery("#star-table").jqGrid('navGrid', '#pager',{add : true,edit : true,del : false,search: false},{
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
                        url:"${app}/star/upload",
                        type:"post",
                        fileElementId:"photo",
                        data:{id:id},
                        success:function () {
                            $("#star-table").trigger("reloadGrid");//刷新表格
                        }
                    })
                }else{
                    alert(data.responseJSON.message);
                }
                return "12312"
            }
        });
    }

</script>

<!--页头-->
<div class="page-header" style="margin: -20px 0px 0px">
    <h3>明星管理列表</h3>
</div>
<table id="star-table"></table>
<div id="pager"></div>