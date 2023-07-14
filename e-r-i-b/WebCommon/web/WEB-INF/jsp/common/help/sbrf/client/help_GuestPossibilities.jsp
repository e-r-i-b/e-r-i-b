<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
    <title>������. �������� �������� ������</title>
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
                  	
                    <li><a href="/PhizIC/help.do?id=/guest/index">������� ��������</a></li>
                    <li><a class="active-menu" href="/PhizIC/help.do?id=/guest/promo">����������� �������� ������</a></li>
                    <li><a href="/PhizIC/help.do?id=/guest/sberbankForEveryDay">�������� ��������� �����</a></li>
                    <li><a href="/PhizIC/help.do?id=/guest/payments/payment.do?form=LoanCardClaim">�������� ��������� �����</a></li>
                    <li><a href="/PhizIC/help.do?id=/guest/payments/payment.do?form=ExtendedLoanClaim">����� ������ � ���������</a></li>



                </ul>

          <a href="/PhizIC/faq.do" class="faq-help">
                    <span>����� ���������� ������� � �������� ������</span>
                </a>
            </div>
            <!-- end help-left-section -->
            <div id="help-workspace">
                <div class="contextTitle">�������� �������� ������</div>
       
				
				<h3><a id="p4">����������� �������� ������</a></h3>
				<p>�� �������� <b>����������� �������� ������</b> �������� ��������� ���������� � �������. � ����� <b>���������� �������� ������ ������</b> �������� ��� ������� ����������� � �������:</p>
				<ul>
					<li>�� ������ �������� ���������� ����� � ����� ��������� ���������, ���������� ������ "��������� ����" � ������������������ � ������� "�������� ������".</li>
					<li>�� ������ �������� ������ �� ��������� ���������� ����� ������, � ����� ������������������ � �������.</li>
				</ul>
				<p>����� �� ������ ������������� �� ����� ��������, ������� ������������� ������� "�������� ������":</p>
				<ul>
					<li>�� ������ ����������� ������� ����� ������ ������� � �������, ��������� ����������� ������� ��� ��������� ������ �� ���� �������� ��������� ��� ������ ������.</li>
					<li>�� ������ ��������� ������ � ������������ ������������� �����, ������� ��� ���� �������� � ������.</li>
					<li>�� ������ �������� ��������� ������ � ������, �������� �����������, �� ���� �������, ������� ����������� ������������� �� ����� ������.</li>
					<li>�� ������ ������� ������ ��� ����� ����������� ��������, ����� �� ������� ��������� ����� ���.</li>
					<li>�� ������ �������������� ���������� ��������, ����������� � ������ �������, ������� � ��������, ������������� ������� ��������.</li>
				</ul>
				<p>���� �� ������ �������� ������ �� ���� ������������ �������, ������� �� ������ <b>�������� ��� ������� �������� ������</b> � �����������������.</p>
				
				

				
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