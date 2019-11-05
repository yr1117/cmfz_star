<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <%-- 引入bootstrap核心样式 --%>
    <link rel="stylesheet" href="../statics/boot/css/bootstrap.min.css">
    <%-- 引入jqgrid核心基础样式 --%>
    <link rel="stylesheet" href="../statics/jqgrid/css/trirand/ui.jqgrid.css">
    <%-- 引入jqgrid的bootstarp的皮肤 --%>
    <link rel="stylesheet" href="../statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%-- 引入jquery核心js --%>
    <script src="../statics/jqgrid/js/jquery-3.4.1.min.js"></script>
    <%-- 引入bootstrap的hexinjs --%>
    <script src="../statics/boot/js/bootstrap.min.js"></script>
    <%-- 引入jqgrid核心js --%>
    <script src="../statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%-- 引入i18njs--%>
    <script src="../statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%-- 引入ajaxfileUploadjs --%>
    <script src="../statics/jqgrid/js/ajaxfileupload.js"></script>
    <%-- kindeditor --%>
    <script charset="utf-8" src="${app}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${app}/kindeditor/lang/zh-CN.js"></script>
    <%-- echarts的js文件 --%>
    <script src="${app}/echarts/echarts.min.js"></script>
</head>
<body>
    <%-- 导航条 --%>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <%-- 导航标题 --%>
            <div class="navbar-header">
                <a class="navbar-brand">持明法州后台管理系统</a>
            </div>
            <%-- 导航条中的内容 --%>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="">欢迎:${sessionScope.login.username}</a></li>
                    <li><a href="${app}/Admin/exit">安全退出</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="panel panel-default">
    <%-- 页面内容 --%>
    <%-- 手风琴 --%>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-2">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    轮播图管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse " role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><a href="javascript:$('#center').load('${app}/banner/queryAll.jsp')">所有轮播图</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                    专辑管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><a href="javascript:$('#center').load('${app}/album/queryAll.jsp')">所有专辑</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingThree">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                   文章管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><a href="javascript:$('#center').load('${app}/article/queryAll.jsp')">所有文章</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingFore">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFore" aria-expanded="false" aria-controls="collapseFore">
                                    用户管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFore" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFore">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><a href="javascript:$('#center').load('${app}/user/queryAll.jsp')">所有用户</a></li>
                                    <li class="list-group-item"><a href="javascript:$('#center').load('${app}/user/user-test.jsp')">用户信息注册趋势</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingFive">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                    明星管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><a href="javascript:$('#center').load('${app}/star/queryAll.jsp')">所有明星</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
            <%-- 中心布局 --%>
            <div class="col-sm-10" id="center">
                <%-- 巨幕 --%>
                <div class="jumbotron">
                    <h4>欢迎来到持明法州后台管理系统!</h4>
                </div>

                <%-- 图片 --%>
                <img src="../image/haha.jpg" width="100%" height="400px">
            </div>
        </div>
    </div>
    <%-- 底部 --%>
        <div class="panel-footer">
            <div class="row">
                <div class="col-sm-12 text-center">
                    持明法州后台管理系统@百知教育 2019.10.25
                </div>
            </div>
        </div>
    </div>
</body>
</html>