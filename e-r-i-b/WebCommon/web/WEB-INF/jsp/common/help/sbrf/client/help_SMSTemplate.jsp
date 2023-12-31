<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>������. �������� SMS-�������</title>
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
									<li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">������ �������� ������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/cards/list">�����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">������ � �����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/loans/list">�������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/depo/list">����� ����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/security/list">�����������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">������������� �����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/npf/list">���������� ���������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">��������� ���������</a></li>
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/mobilebank/main">��������� ����</a>
												<ul>
													<li><a href="/PhizIC/help.do?id=login/register-mobilebank">����������� � ���������� �����</a></li>
													<li><a class="active-menu" href="/PhizIC/help.do?id=/private/mobilebank/payments/select-service-provider">�������� SMS-�������</a></li>									
												</ul>
									</li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">������ ����</a>
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
                <div class="contextTitle">�������� SMS-�������</div>

						<p>� ������� "�������� ������" �������� �������� SMS-������� ��� ������ ����� ����� ���������:</P>
									<ul class="page-content">
										<li><a href="#p1">������� 
										SMS-������ �� ������ ������������� 
										������� �������</a></li>
										<li><a href="#p2">������� ����� 
										SMS-������</a></li>
									</ul>
																<div class="help-important">
									<p>
										<span class="help-attention">�������� 
										��������</span>: � ������� "�������� ������" �� ������ ���������� �� ������ �������� �� ������ � ������� 
										������� �������� ����, ������ �������� ����, �� � � ������� ������, ������������� ��� ������� ����. ������ 
										������ ���������� ���� �� ������� �������� �� ���, � ������� �� ������� �� ������� ��������. �� ������ ������������ ��� ������ ��� 
										�������� �� ������������ ��������.  
									</p>
								</div>
									
							<h3><a id="p1">�������� SMS-�������� �� ������ 
							&quot;�������� ��������&quot;</a></h3>
								<p>��� ���� ����� ������� SMS-������ �� ������ 
								&quot;�������� ��������&quot;, �� �������� <b>��������� ����</b> 
								�� ������� "SMS-������� � �������" �������� �� ������ <b>������� SMS-������</b>. ��������� �������� ��� �������� 
								SMS-������� �� ������ ������������� ������� 
								�������.</P>
								<p>�� ������ �������� ��� ���������� � 
								������ ������ ��  ������ ������ �������, 
								������� �� �������� � ������ &quot;�������&quot;. 
								����� 
								��������� ��������, �� ������� ��������� ������ ���� � ������� �� ������ <b>��������� SMS-������</b>. ��������� �������� 
									�������������, �� ������� �������� �� ������ <b>����������� �� SMS</b>, 
									������� ������� ����������� ����, � ������� ������� SMS-������ � ������� �� ������ <b>�����������</b>.  
									� ���������� ��������� �������� <b>��������� ����</b>, � �� ������� "SMS-������� � �������"
									 �������� ����� ������.
									</p>	
																	
								<p>���� �� �� ������ ��������� SMS-������� �� 
								������ &quot;�������� ��������&quot;, �� ������� �� ������ <b>
								��������</b>.</P>
								
							<h3><a id="p2">�������� ������ SMS-�������</a></h3>
								<p>��� ���� ����� ������� ����� SMS-������, ��� 
								����� �� �������� <b>�������� SMS-�������</b> 
								������ �� ������ <b>������� ����� SMS-������</b> 
								� ��������� ��������� ��������:</P>
								<ul>
								<li><b>������� ��������� �����</b>. ��� ����� �������� �� �������� ��������� 
								��������� �����, ��������, "������ ������� � �����". � ���������� 
								��������� ��������, �� ������� ����� ������� 
								���������� ������ � ������ ��������.</P>
								</li>
								<li><b>����� ���������� ������</b>. ��� ���� ����� ��������� ����� 
										������������ ��� �����������, ������ 
										������� �� ������ ��������, ������� � 
										���� ������ �������� ������, 
										��� ��� ��������� ���� ���������� ������ 
										� ������� �� ������ <b>�����</b>. �� ������ ������ 
										� ���� ������ ������ ��������� ������ ���� ������������ ���������� ������.</p>
										<p>� ����� ������� ������� �� ����� 
										������ ������������ ��� �����������.</p> 
										</li>
										<li><b>������� ������</b>. �� ������ ��������� ����� ���������� 
										������ �� �������, � ������� ��������������� ������ 
										���������. ��� ����� ������� �� ������ <b>��� �������</b>, ������������� � ������ ������. ��������� ���������� ��������, 
										� ������� ����� �������� ��� ������� � ���������� �������. ��� ���� ����� ������� ������ 
										� ������ �����������, �������� �� ��� ��������. ��������� ������ ����������� ��������. 
										�����, ���� �� ������ ������� ������ �������� ������ ��� ������ ������������ ������, �������� �� ������������ ������� �������. 
										���� �� ������ ������� 
										��� �������, �� �������� �� ������ <b>������� ��� �������</b>. ����� ��������� ����������� ����, � ������� ������� �� ������ <b>���������</b>, ���� �� ������ ��������� ���������� ������ � ��������� �������. ���� �� ������ ��������� ������ � ������ ������� ���� ���, �� ������� �� ������ <b>��������</b>. � ���������� �������� ���������� ������� ����� �������� ����� �� ������� ������, � ����� ����������� ����� ����� ����������� � ��������� ���� �������. 
										</li>
								<li><b>����� ��������� ����������� ����</b>, ��������, ��� 
								������ ����� ��� ����� ��������� ���� "����� ��������". ��� ����� �� �������� ������ ���������� ������ � ���� 
								&quot;����� ��������&quot; ������� ����� ��������, ������� 
								�� ������ ���������� � ������� ������������ 
								SMS-�������.
							<div class="help-important">
									<p>
										<span class="help-attention">����������</span>: 
										����� �������� ��������, ������� � ���� 
										���������, ��������, 9167898080.</i> </P>

									</p>									
								</div>
								</li>
								</ul>
								<p>����� ���� ��� ��� �������� ���������, 
								������� �� ������ <b>��������� SMS-������</b>. ����� 
								��������� �������� �������������, �� ������� ��� ����� ������
									����������� ������ �� SMS-���������. ��� ����� �������� �� ������ <b>����������� �� SMS</b>, 
									��������� ����������� ����, � ������� ������� SMS-������ � ������� �� ������ <b>�����������</b>.  
									 � ���������� ��������� SMS-������ ����� ������� � ������ SMS-�������� �� �������� 
									 <b>��������� ����</b> �� ������� "SMS-������� � �������".</P>
								<p>���� �� ������ �� ��������� SMS-������, �� 
								������� �� ������ <b>��������</b>.</P>
								<p>� ������ �������� �� ������ ��������� �� 
								�������� ������ ���� ������, ������� �� ������ <b>
								����� � ������ �����</b>.</P>
								<p>� ������� "�������� ������" �� ������ �������� ����� 
								���������� � ������ ������������� ������������, ������� 
								������� �� ��� ���� �������. ����� ��������� ���������, ������� 
								�� ������ <b>����� ���������� �������</b> � ������� ����. � ���������� 
								��������� ����, � ������� ���������� ������ �� ����� ���������� ������� 
								�� ������ � �������� "�������� ������". 
								<!-- ���� �� �� ������, ����� ����������� 
								����������� �� ��������� �������, �� �������� ��������� ����������� ���������, 
								������� �� ������ <b>���������</b> - <b>��������� ����������</b> - <b>������ ����</b>. ��� ���� 
								����� ������ ���������, ��� ����� � ����� <b>�������������� ���������</b> ������ 
								������� � ���� "���������� ������������� ������������" � ������ �� ������ <b>���������</b>. -->.</p>

								<div class="help-linked">
									<h3>����� ����������� ����������:</h3>
									<ul class="page-content">
											<li><span></span><a href="/PhizIC/help.do?id=/private/receivers/list">������� ��������</a></li>
										<li><span></span><a href="/PhizIC/help.do?id=/private/userprofile">���������</a></li>
									</ul>
								</div>
								   <!-- <div class="help-to-top"><a href="#top">� ������
                        �������</a></div> -->
                    <div class="clear"></div>

            </div>
            <!-- end help-workspace -->
        </div>
        <!-- end help-content -->

        <div class="clear"></div>
    </div>
</body>