<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="${app}/echarts/echarts.min.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '上半年用户注册量'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
        },
        yAxis: {},
        series: [{
            name: '男',
            type:'bar',//折线图(Line)
            data: [5, 20, 36, 10, 10, 20]
        },{
            name: '女',
            type:'bar',//折线图(Line)
            data: [5, 12, 45, 134, 54, 100]
        }

        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    $.ajax({
        url:"${app}/user/queryByDate",
        datatype:"json",
        type:"post",
        success:function (result) {
            console.log(result);
            myChart.setOption({
                series: [{
                    name: '男',
                    type:'bar',//折线图(Line)
                    data: result.man
                },{
                    name: '女',
                    type:'bar',//折线图(Line)
                    data: result.woman
                }

                ]
            })
        }
    })
</script>
</body>
</html>