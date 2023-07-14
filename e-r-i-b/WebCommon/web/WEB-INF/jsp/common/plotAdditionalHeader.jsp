<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:importAttribute/>
<!--[if lte IE 9]><script language="javascript" type="text/javascript" src="${initParam.resourcesRealPath}/scripts/excanvas.js"></script><![endif]-->

<%--<link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/graphics.css"/>--%>
<%--<link rel="stylesheet" type="text/css" href="${skinUrl}/graphics.css"/>--%>
<!-- Pie Plugin -->

    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/graphics.css"/>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.jqplot.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/graphics.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.pieRenderer.js"></script>

    <!-- Bar -->
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.barRenderer.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.categoryAxisRenderer.js"></script>

    <!-- date Plugin -->
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.dateAxisRenderer.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.canvasAxisTickRenderer.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.canvasTextRenderer.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.canvasAxisLabelRenderer.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.donutRenderer.js"></script>

    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.pointLabels.js"></script>

    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/MyFinancesDiagram.js"></script>

<%--
  Вот так рассчитываются коэффициенты матрицы
  /* var deg2radians = Math.PI * 2 / 360;
     var deg = -60;
     var rad = deg * deg2radians;
     var costheta = Math.cos(rad);
     var sintheta = Math.sin(rad);

     document.getElementById("test").innerHTML='progid:DXImageTransform.Microsoft.Matrix(M11='+costheta+', M12='+(-1)*sintheta+', M21='+sintheta+', M22='+costheta+', SizingMethod="auto expand", enabled=true)';
     */
--%>

<!--[if lte IE 9]>
<style type="text/css" media="screen">
    .vertical-90 span { filter:flipv() fliph(); writing-mode:tb-rl; display:block; height:250px; width:30px; line-height:30px; text-align: center; background-color:white;}
    .vertical90 span { writing-mode:tb-rl; display:block; height:250px; width:30px; text-align: center;}
    .vertical-60 span { position: relative; display:block; font-size: 12px; font-family: "Arial"; left: -20px; width:90px; writing-mode:lr-tb; filter:progid:DXImageTransform.Microsoft.Matrix(M11=0.5000000000000001, M12=0.8660254037844386, M21=-0.8660254037844386, M22=0.5000000000000001, SizingMethod="auto expand", enabled=true); background-color:white; text-align: right;}
</style>
<![endif]-->