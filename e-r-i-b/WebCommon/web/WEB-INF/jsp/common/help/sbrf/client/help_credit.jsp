<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
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

    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
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
									<li><a href="/PhizIC/help.do?id=/private/registration">�����������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts">�������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments">�������� � �������</a></li>
										<c:if test="${phiz:impliesService('RemoteConnectionUDBOClaimRemoteConnectionUDBOClaim')}">
										<li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">������ �������� ������</a></li>
										</c:if>
									<li><a href="/PhizIC/help.do?id=/private/cards/list">�����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">������ � �����</a></li>
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/loans/list">�������</a>
											<ul>
											<li><a href="/PhizIC/help.do?id=/private/loans/info">���������� �� �������</a></li>
											<li><a href="/PhizIC/help.do?id=/private/loan/processing/choice">�������� ������ �� ������</a></li>
													<c:if test="${phiz:impliesService('CreditReportOperation')}">
													<li><a href="/PhizIC/help.do?id=/private/credit/report">��������� �������</a></li>
													<!--<li><a href="/PhizIC/help.do?id=/private/payments/creditReportPayment/edit/CreditReportPayment/null">������ ��������� �������</a></li> -->
													</c:if>
											</ul>
									</li>
									<li><a href="/PhizIC/help.do?id=/private/depo/list">����� ����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/security/list ">�����������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">������������� �����</a></li>									
									<li><a href="/PhizIC/help.do?id=/private/npf/list">���������� ���������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">��������� ���������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">��������� ����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">������ ����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/common/all">������� ��������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mail/sentList">������ � ���������� ����� �����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/news/list">�������</a></li>
										<li><a href="/PhizIC/help.do?id=/private/userprofile">���������</a></li>
								</ul>								
				

		<a href="/PhizIC/faq.do" class="faq-help">
                    <span>����� ���������� ������� � �������� ������</span>
                </a>
            </div>

	<!-- end help-left-section -->
	
	<div id="help-workspace">
        <div class="contextTitle">�������</div>   
										<p>����� �������� ���� <b>�������</b> ������������ ��� 
										����� ������� ������ � ������ ���������, ����������� � 
										��������� ������, � ����� � ������������ �������� �� ������. 
										<c:if test="${phiz:impliesService('CreditReportOperation')}"> ����� ����, �� ������ ������������ � ����� ��������� ��������.     
										</c:if>.</p>
										<p> ���� �� ��������� � "�������� ������" ����������� ������ ��� ��������� �������, �� ��� ������ ��������� ����� ������������ ������ ����������� ���� ������.</p>
										<p> ��� ������ ������ � ������ ����� �������� �� ������������, ������, ���� ���������� � ����� �������������� �������.</p>
										<c:if test="${phiz:impliesService('CreditReportOperation')}">
										<p>������ <b>��������� �������</b> ��������� ��� �������� ��������, ���������� ���������� � ��������� ���� ����� ��������� ������������ � ��������� ������. ���������� ������� ��������� ������� �������� ������� � ������� ����������� ������� <a href="/PhizIC/help.do?id=/private/credit/report">��������� �������</a>.</p>
										</c:if>
										<p> ����� �� ���� �������� �� ������ ����������� �������, ���������� � ���������.</p>
										<p>�� ������� ������� � ������ ����� �������� ��������� 
										����������: �������� �������, ��������������� ������, ���� ���� � ��������� �������� (�������, 
										���������, ����������) � ���� ������� �� �������. ���� �� 
										���������� ������, �� ���� "������ ��" � ����� ������� ��� ������� 
										������� �� ����� ������������.</p>
											<div class="help-important">
											<p>
												<span class="help-attention">����������</span>: ���� ����� ������� �� ������� �������� � ������ ������� ������, 
												��� ��������, ��� �� ���������� ������� �� ������� �������, 
												� ��� ���������� �������� �������������. 
											</p>
											</div>
											

										<!--<p>��� ���� ����� �������� ������������ ��� ������ �� ������� 
										��������, ��� ���������� ���������� ������� � ���� "���������� 
										�� �������". � ���������� ���������� ������ ����� ������ 
										������������ �� ������� �������� �������.</p>-->
										<p>�� ������� ������������ ������� �� ������ ����������� ������ ���� ��������� ��������, 
										������� �� ��������� � �������. ��� ���� ����� ���������� ������ ��������� ��������, ����� � ������������ �������� �������� �� ������ <b>�������� ��������</b>. </p>
										<p>� ���� ������ �� ������ �������� 
										�� ������ ������� ������������ ��������, ���� ���������� �������� 
										� �����, �� ������� ��� ���������.</p>
										<p>���� �� �� ������, ����� �� ������ �������� ������������ �������� 
										�� ������������� ��� �������, �� �������� �� ������ <b>������ ��������</b>.</p>
										<p>���� �� ������ <b>������ � ���� ������ �� ��������� �������</b>, �� ������ �������� ������� �� ������ <b>����� ������ � ���������</b>. ���������� ������ �� ������ �������� ������� � ������� ����������� ������� <a href="/PhizIC/help.do?id=/private/loan/processing/choice">�������� ������ �� ������</a>.</p>
											<c:if test="${phiz:impliesService('CreditReportOperation')}">
											<p>��� ���� ����� ��������� <b>������ �� ��������� ��������� �������</b> ��� <b>����������� ��������� �����</b>, ������� �� ������ <b>��������� �������</b> ������ ��������.</p>
											</c:if>
										
										<p>�� ������ �������� �� ������� ������� �� ������ ����������� ������ �������� � ������ ������ �� �������. ��� ����� ������� �� ������ <b>��������</b> � �������� � ������ �� ������ ������:</p>
									<ul class="page-content">   
									<li><a href="/PhizIC/help.do?id=/private/loans/info#p2"><SPAN CLASS="toc2">������</SPAN></A></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/payment/LoanPayment/loan-payment-extended-fields"><SPAN CLASS="toc2">������ ������</SPAN></A></li>
									</ul>
									<p>��� ��������, �� ������� ������������� ����������� �������, �� �� ������ ������ ������, ������ �������� ����������. �� ����� �������� �������� �������������� ������������� � ������ ��� �����, ��������� � ����������� �� �������� ������� �� ��������� �������. ��� ����������� �������� ������ ������ 
									<b>������ ������</b> �� ������� ������ <b>��� ��������?</b>, ��� �������� �� ������� ����� �������� �������������� ���������.</p>
								<p>����� � ���� �������� �� ������ ������� � �������� � ��������� ����������� 
								�� �������, �� ������� ����� ����������� ������ ��������, ����������� �������������� ���������� 
								�� �������, ���������� ����� �� ������������ � ������� "�������� ������" � �.�. 
								��� ���� ����� ������� � ������ ��������, �������� �� �������� ��� ������ ������������� ��� 
								������� � ������.</p>
								<p>� ������ ���� �� ������ ����������� ������ 
								����� ���������� ��������, ������� �� ������ <b>�������� �������</b>, ������� ��������� ��� ������� ����������� ��������. � 
								���������� ��������� ������ �������� ��������. 
								</p>
								<p>���� �� ������ �������� ��������� ���������� �� ���������� �������� �� ����� �������� �������, �� ������� �� ������ <b>������</b> � ������� ���� ��� ����� �������� ������� �� ������ <b>������ ������</b>.</p>
								<p>� ������� "�������� ������" �� ������ �������� ����� ���������� � ������ 
								������������� ������������, ������� ������� �� ��� ���� �������. ����� ��������� 
								���������, ������� �� ������ <b>����� ���������� �������</b> � ������� ����. � 
								���������� ��������� ����, � ������� ���������� ������ �� ����� ���������� 
								������� �� ������ � �������� "�������� ������". 
								<!-- ���� �� �� ������, ����� 
								����������� ����������� �� ��������� �������, �� �������� ��������� ����������� 
								���������, ������� �� ������ <b>���������</b> - <b>��������� ����������</b> - <b>������ ����</b>. 
								��� ���� ����� ������ ���������, ��� ����� � ����� <b>�������������� ���������</b> 
								������ ������� � ���� "���������� ������������� ������������" � ������ �� ������ <b>���������</b>. --></p>

								<div class="help-linked">
									<h3>����� ����������� ����������:</h3>
									<ul class="page-content">
										<li><span> <a href="/PhizIC/help.do?id=/private/loans/info">���������� �� �������</a></li>
										<li><span> <a href="/PhizIC/help.do?id=/private/cards/info">���������� �� �����</a></li>
									</ul>
								</div>

</div>
	<!-- end help-workspace -->
</div>
<!-- end help-content -->

<div class="clear"></div>

</body>					