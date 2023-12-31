<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>������. ����������� � ������� "������� �����"</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">

<link type="text/css" rel="stylesheet" href="${globalUrl}/commonSkin/help.css"/>
<link rel="stylesheet" type="text/css" href="${skinUrl}/roundBorder.css"/>
<link rel="stylesheet" type="text/css" href="${skinUrl}/help.css"/>
<link rel="icon" type="image/x-icon" href="${skinUrl}/images/favicon.ico"/>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.ifixpng.js"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/layout.js"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>

<!--[if IE 6]>
		<c:if test="${contextName eq 'PhizIC'}">
         	<link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
        </c:if>
	<link type="text/css" rel="stylesheet" href="${skinUrl}/ie.css"/>
<![endif]-->

<script type="text/javascript">
var skinUrl = '${skinUrl}';
var globalUrl = '${globalUrl}';
var scroll = new DivFloat("help-left-section");
scroll.doOnScroll();
</script>

<%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>
</head>
<body >
<tiles:insert definition="googleTagManager"/>

<div class="help-container">
        <div id="help-header">
            <p class="helpTitle">���������� ����������� �� �������� ������</p>
        </div>

<!-- end help-header -->
<div class="clear"></div>

<div id="help-content">
	<div id="help-left-section">
        <p class="sidebarTitle">������� �����������</p>


								<ul class="help-menu">
									<li><a class="active-menu" href="/CSAFront/help.do?id=/businessEnvironment/login">����������� � ������� "������� �����"</a></li>
									<li><a href="#p7">������� � �������</a></li>
									<li><a href="#p5">���������� � �������</a></li>
								</ul>
									
						

		</div>
	<!-- end help-left-section -->
	<div id="help-workspace">

        <tiles:insert definition="roundBorder" flush="false">
                    <tiles:put name="title" value="����������� � ������� �������� �����"/>
                    <tiles:put name="data">
                    
<p>� ������� ������� "�������� ������" �� ������ ����������� ���� ����������� � ������� "������� �����". </p>
<p>��� ����� ��� ���������� ��� �������� ������� �� ����� "������� �����" ������� ������ �������������  -  ����� <b>�������� ������</b>. � ���������� �� ��������� �� �������� ������������� ������ �������, �� ������� ��������� ��������� ����:</p>
<ul>
<li>� ���� "������������� ��� �����" ������� ��� ������������� ������������, ������� �� ����������� ��� ����� � ������� "�������� ������";</li>
<li>� ���� "������" ������� ��� ������, ������� �� ����������� ��� ����� � ������� "�������� ������". </li>
</ul>
<p>����� ���� ��� ���� ���������, ������� �� ������ <b>����������</b>, � �� ��������� �� �������� ������������� ��������� ������.</p>
<p>���� �� ���������� ������������ �������, ������� �� ������ <b>�����</b>, � �� ��������� �� ���� ������� "������� �����"". </p>
<a id="p2"> </a>
<p>����� ��� ���������� ���� �������� �� �������� ����� ������������ ������ � ������ "������� �����". ��� ����� ������������ � ������� ����������, ������� �� ��������������� ������, ����� ���������� ������� � ���� "�������� � ��������� ������ �� "�������� ������" � ������ "������� �����".</p>
<p> ����� ����� ��� ���������� �������, ����� �������� �� ������ ����������� �������� �������:</p>
<ul>
<li>��� ������������� SMS-�������, ������� �� ������ <b>����������� �� SMS</b>;
</li>
<li>���� �� ������ ����������� �������� ������� � ����, �������������� � ���������, ������� �� ������ <b>����������� �����</b>.
</li>
</ul>
<p> ����� ��������� ����������� ����, � ������� ������� ������ ������ � ������� �� ������ <b>�����������</b>. � ���������� �� ��������� � ������ "������� �����".</p>


<h2><a id="p7">������� � �������</a></h2>

<p>� ������� ����� <b>�������</b> �� ������ ����������� ������ ���� ������� �����, � ����� ������ ����� ������ �������. ������ ����� ���� ������������ �� �������� ����� � �� ������� �������� �������.</p>
<div class="help-important">
<p>
<span class="help-attention">����������</span>: ������� ������� �������� �������� ������ �������.</p>
</div>								
<p>��� ���� ����� ����������� �������, �������� �� ��� ��������� � ����� <b>�������</b>.</p>
									<!-- <p>���� ��� ����� ���������� ������� ���, �� �������� �� ������ <b>������� ���</b>. ��������� �������� �� ������� ������� �������� ���, � ����� ����� � ������� ������ ����������� ������� �� ������ ����.</p> -->
									<p>���� �� ������ ����������� ��� ������� �����, ������� �� ������ <b>��� �������</b> 
									� ����� <b>�������</b>. ��������� �������� �� ������� �������, �� ������� �� ������ ��������� 
									��������� ��������: ����������� ������������ �������, ����������� ��� ������� �����, � ����� 
									��������������� ������� �������.</p>
									<p>��� ������� ������� � ������ ������������ ���� ��� ����������, ��������� � ������� ����� �������. </p>
	<h2><a id="p5">���������� � �������</a></h2>
<p>�� �������� ����� � ������� �� ����� ������ ��������� ��������� ��������:</p>		
<ul>
<li>
��� ��������� ���������� � ����� ������������ ��� ������������� ������� "�������� ������" � ����� ����� �������� ��������� �� ������� <b>���� ����������������</b>. 
</li>
<li>
��� ��������� ���������� � ���, ����� ����������� ���� � ������� "�������� ������" � ����� ����� �������� ��������� �� ������� <b>�����������</b>.
</li>
<li>
���� �� ������ �������� ���������� � ����������� ������� ��� ��������� ��������� � ������ ������ �� �� ����������, �� ��������� �� ������� <b>��������� ����������</b>.
</li>
</ul>


	<!-- end help-workspace -->
</div>
<!-- end help-content -->

<div class="clear"></div>

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
</body>