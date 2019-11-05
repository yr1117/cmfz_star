<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <script charset="utf-8" src="${app}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${app}/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.create('#editor_id',{
                //设置宽度
                width:'500px',
                //点击图片空间按钮时发送请求
                fileManagerJson:"${app}/article/browse",
                //展示图片空间按钮
                allowFileManager:true,
                //上传图片所对应的方法
                uploadJson:"${app}/article/upload",
                //上传图片是图片的额形参名称
                filePostName:"articleImg"
                });
    </script>
</head>
    <body>
        <textarea id="editor_id" name="content" style="width:700px;height:300px;">
        &lt;strong&gt;HTML内容&lt;/strong&gt;
        </textarea>
    </body>
</html>