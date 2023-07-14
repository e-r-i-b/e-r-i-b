<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>������. �������</title>
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
																	<!--<li><a href="/PhizIC/help.do?id=/login">���� � �����������</a></li>-->

									<li><a href="/PhizIC/help.do?id=/private/accounts">�������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments">������� � ��������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/cards/list">�����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">������ � �����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/loans/list">�������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">������������� �����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/depo/list">����� ����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/pfr/list">���������� �����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">��������� ����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">������ ����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/common/all">������� ��������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mail/sentList">��������� � ������ ������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/news/list">�������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/userprofile">���������</a></li>

								</ul>
                                
							
      <a href="/PhizIC/faq.do" class="faq-help">
                <span>����� ���������� ������� � �������� ������</span>
            </a>
       	</div>
	<!-- end help-left-section -->
	<div id="help-workspace">

        <div class="contextTitle">����������</div>

								<ul class="page-content">
									<li><h3><a href="/PhizIC/help.do?id=/private/accounts">�������</a></h3></li>
									<li><h3><a href="/PhizIC/help.do?id=/private/payments">������� � ��������</a></h3></li>
										<ul class="page-content">
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/category/TRANSFER">�������� � ����� �����</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/category/DEPOSITS_AND_LOANS">�������� �� �������, ������, �������� � ���</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/category/COMMUNICATION">�����, �������� � �����������</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/category/TAX_PAYMENT">������, ������, �����</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/category/EDUCATION">�����������</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/category/SERVICE_PAYMENT">������ ������� � �����</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/category/PFR">���������� �����</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/category/OTHER">������</a></li>
										</ul>	
									<li><h3><a href="/PhizIC/help.do?id=/private/cards/list">�����</a></h3></li>
										<ul>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/cards/detail">���������� �� �����</a></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/accounts/list">������ � �����</a></h3></li>
										<ul>
										<li><span>� <a href="/PhizIC/help.do?id=/private/accounts/info">���������� �� ������/�����</a></span></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/loans/list">�������</a></h3></li>
									<li><h3><a href="/PhizIC/help.do?id=/private/depo/list">����� ����</a></h3></li>
										<ul>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/depo/info/position">���������� �� ������� ����� ����</a></li>	
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/depo/info/debt">���������� � �������������</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/payment/DepositorFormClaim">������ ���������</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/payment/SecuritiesTransferClaim">��������� �� �������/ ����� �������� ������ �����</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/payment/SecurityRegistrationClaim">����������� ������ �����</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/depo/documents">������ ���������� �� ����� ����</a></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/ima/list">������������� �����</a></h3></li>
										<ul>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/ima/info">���������� �� �������������� �����</a></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/payments/category">���������� �����</a></h3></li>
										<ul>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/payment/pfr">������ ������� �� ����������� �����</a></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/mobilebank/main">��������� ����</a></h3></li>
									<li><h3><a href="/PhizIC/help.do?id=/private/PersonMenu">������ ����</a></h3></li>
										<ul>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/graphics/finance">��� �������</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/payment/BlockingCardClaim">������������� �����</a></li>
										<li><span>� </span><a href="/PhizIC/help.do?id=/private/payments/payment/LossPassbookApplication">������� �� ����� ����������</a></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/payments/common/all">������� ��������</a></h3></li>
									<li><h3><a href="/PhizIC/help.do?id=/private/news/list">�������</a></h3></li>
									<li><h3><a href="/PhizIC/help.do?id=/private/userprofile">���������</a></h3></li>
									
								</ul>


								 </div>
        <!-- end help-workspace -->
    </div>
    <!-- end help-content -->

    <div class="clear"></div>
</div>

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
</body>