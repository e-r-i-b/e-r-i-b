<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<head>
<title>������. �������������� �����������</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">


<link type="text/css" rel="stylesheet" href="${globalUrl}/commonSkin/help.css"/>
<link rel="stylesheet" type="text/css" href="${skinUrl}/roundBorder.css"/>
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
									<li><a href="/PhizIC/help.do?id=/private/payments">
									�������� � �������</a>
										</li>
									<li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">������ �������� ������</a></li>									
									<li><a href="/PhizIC/help.do?id=/private/cards/list">�����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">������ � �����</a></li>
									
									<li><a href="/PhizIC/help.do?id=/private/loans/list">�������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/depo/list">����� ����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/security/list ">�����������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">������������� �����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/npf/list">���������� ���������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">��������� ���������</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">��������� ����</a></li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">������ ����</a></li>
																		<ul class="page-content">
											<li><a href="/PhizIC/help.do?id=/private/graphics/finance">��� �������</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/favouriteLinks#p5">��������� ����������</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/internetShops/orderList">��� ��������-������</a></li>
											<li><a href="/PhizIC/help.do?id=/private/loyalty/detail">������� �� ���������</a></li>											
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/favouriteLinks#p2">���������</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/PaymentsAndTemplates">��� �������</a></li>
											
											<li><a class="parentItem" href="/PhizIC/help.do?id=/private/favourite/list/AutoPayments">��� �����������</a>
									<ul>
										
										<li><a class="active-menu" href="/PhizIC/help.do?id=/private/payments/payment/EditAutoSubscriptionPayment/null/LongOffer">
											�������������� �����������</a></li>
										</ul>	
</li>
										</ul></li>
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
                <div class="contextTitle">������ �� �������������� �����������</div>
								<ul class="page-content">
									<li><a href="#p2">������� ������</a></li>
									<li><a href="#p3">����������� ������</a></li>
									<li><a href="#p4">����������� ������</a></li>

								</ul>

										<p> �� ���� �������� �� ������ ������ � ���� ������ �� ��������� ���������� �����������. 
										 ��� ����� ��� ����� �� �������� ��������� ���������� �� ����������� ������ �� ������ 
										<b>�������������</b>. ��������� ����� �������������� �����������.</p>
								<div class="help-important">
									<p>
										<span class="help-attention">�������� 
										��������</span>: � ������� "�������� ������" �� ������ ���������� �� ������ �������� �� ������ � ������� 
										������� �������� ����, ������ �������� ����, �� � � ������� ������, ������������� ��� ������� ����. ������ 
										������ ���������� ���� �� ������� �������� �� ���, � ������� �� ������� �� ������� ��������. �� ������ ������������ ��� ������ ��� 
										�������� �� ������������ ��������.  
									</p>
								</div>
										<h2><a id="p2">������� ������</a></h2>
									<p>�� ������ �������� ��������� �����������. ��� ����� �� �������� ��������� ����������� ������� �� ������ <b>�������������</b>. 
									��������� �������� �������� ������ �� �������������� �����������, �� ������� �� ������ �������� ���� �������� � ����������� ��������� �����������: </p>
									<ul>
									<li> 
									� ���� "���� ��������" �� ������ �������� ���� ��� �����, � ������� ����� �������������� ����������.
									</li> 
									
									<li>
									� ���� "��� �����������" ������������� ����� ���������� ��� �������������� �����������: "���������� �� ������������� �����" ��� "�� ������������� �����".
									</li>
									<li>
									� ���� "�����������" �������� �� ����������� ������, ��� ����� ����� ����������� ����������: "�����������", "����������" ��� "�������������". 
									</li>
									<li>
									��� ���� ������� "���������� �� ������������� �����" �� ������ �������� ������� ���������� �����������, ������ � ���� "����������" ������ ��������. ����� � ���� "���� ���������� �������" ������� ����, ����� �������� ���������� �����������. 
									��� ����� ������� �� ������ <IMG border="0" src="${globalUrl}/commonSkin/images/datePickerCalendar.gif" alt=" height="20" width="20""> � �������� �� ������������ ��� ���� ��� ������� ���� ������ ������� �������.										 
									<p>����� � ���� "�����" ������� �����, ������� ����� ������������� ���������� ����� �� ������� �����������.</p>
									</li>
									<li>
									��� ������� "�� ������������� �����" �� ������ �������� ������� ���������� �����������, ������ � ���� "����������" ������ ��������. ����� � ���� "��������� ���� ������ �����" ������� ����, ����� ����� ����������� ����������. 
									��� ����� ������� �� ������ <IMG border="0" src="${globalUrl}/commonSkin/images/datePickerCalendar.gif" alt=" height="20" width="20""> � �������� �� ������������ ��� ���� ��� ������� ���� �������.										 
									<p>����� � ���� "������������ ������ �������" ������� ������������ �����, �� ������� ����� ������������� �������� �� ������� ����������� � ������� ������.</p>
									</li>
									<li>� ���� "��������" ������� ��������, ��� ������� ���������� ����� �������� � �������.
									</li>	
									</ul>
									<p>����� ���� ��� ��� ����������� ��������� �������, ������� �� ������ <b>�������� ������</b>. � ���������� �� ��������� �� �������� ������������� ������. 
									<p>��� ���� ����� ��������� �� �������� ��������� ���������� �� �����������, ������� �� ������  <b>�����</b>.</p>
									<p> ���� �� ������ ������� �� �������� <b>
									�������� � �������</b>, ������� �� ������ <b>��������</b>.</p>


												<div class="help-important">
													<p>
														<span class="help-attention">�������� 
																		��������</span>: �� ������ �������������� ������� ���������� �������� � �������
																		����� ������ �����, �� ������� ����� �������� ��������� �������� �� ������ ������.
																		��������, ���� �� ���������� �� �������� �������������, �� ����� ������� ������� 
																		"�������������".  
		</p>
		</div>
										<h2><a id="p3">����������� ������</a></h2>
										

											<p>����� ���������� ����������� ������. ����� 
								���� ��� �� ������ �� ������ <b>�������� ������</b>, 
								��� ��������� ����������� ����� ������, � 
								������� ����� ��������� ������������ ��������� 
								��������, ����� ���� ��������� ���� �� ��������� 
								��������:
									</p>
									<ul>
									<li><b>����������� ������</b>.
									��� ���� ����� ����������� ������, ������� �� ������ <b>�����������</b>.
									����� ��� ���������� �������, 
									����� �������� �� ������ �� �����������:</p>
										<ul>
									<li>���� �� ������ ����������� �������� SMS-�������, 
										������� �� ������ <b>����������� �� SMS</b>;
										<div class="help-important">
									<p>
										<span class="help-attention">�������� 
										��������</span>: ����� ������ ������ ���������, ��� ��������� �������� ��������� � 
										������� SMS-���������. ������ ���������, ���� ������ �� ���������, �� � ���� ������ �� ������� ������
										� ������ ��� �� ���������, ���� ����������� �����. 
									</p>
								</div> 

									</li>
										<li>���� �� ������ ����������� �������� ������ ��������, ������� �� 
                                        ������ <b>������ ������ �������������</b>. ����� 
                                        �������� ���� �� ������������ ���������:</p>
                                        <ul><li><p><b>������ � ����</b> - ������������� ������� � ����, �������������� � ���������.</li>
                                        <li><p><b>Push-������ �� ����������� � ��������� ����������</b> - ������������� 
                                        �������, ���������� �� ��������� ���������� � ���� Push-���������.</p></li>
                                        </ul>
										</ul>
											����� ��������� ����������� ����,	� ������� ������� ������ ������ � ������� �� ������ <b>�����������</b>. 
											� ���������� �� ��������� �� �������� ��������� ������.
									</li>
								<div class="help-important">
									<p>
										<span class="help-attention">�������� 
										��������</span>: ���� ����� ������� ��������� ������������� �����, �� ��� ���������� ����������� ������,
										�������� � ���������� ����� �����. 
									</p>
								</div> 

									<li><b>�������� ���������</b>. ���� ��� �������� ������ ����������, 
									��� �������� ���������� ���������������, �� 
									������� �� ������ <b>�������������</b>. � ���������� 
									�� ��������� �� �������� ���������� 
									���������� ������.
									</li>
									<li><b>�������� ��������</b>. ���� �� ���������� ��������� ��������, �� 
									������� �� ������ <b>��������</b>. � 
									���������� �� ��������� �� �������� <b>
									�������� � �������</b>.
									</li>
									</ul>
									
														
								<h2><a id="p4">����������� ������</a></h2>

										 <p>����� ������������� ������ �� ��������� 
									�� �������� ��������� ������, �� ������� 
									������� ����������� ��������.</p>

 										   <p>� ���, ��� ������ �������� � ����, 
 										   ��������������� ������������ �� ����� ��������� ����� "������� � ����������". </p>
 										   <p>�� �������� ��������� ������ �� ������ 
									��������� �� �������� <b>�������� � �������</b>. 
									��� ����� �������� �� ������ <b>������� � 
									�������� ��������</b>.</p>
									
								<p>���� �� ������ �������� ��������� ���������� �� ���������� �������� �� ����� �������� �������, �� ������� �� ������ <b>������</b> � ������� ���� ��� ����� �������� ������� �� ������ <b>������ ������</b>.</p>
								<p>����� ����, � ������� "�������� ������" �� ������ �������� ����� ���������� � ������ ������������� ������������, ������� ������� �� ��� ���� �������. 
								����� ��������� ���������, ������� �� ������ <b>����� ���������� �������</b> � ������� ����. � ���������� ��������� ����, � ������� ���������� 
								������ �� ����� ���������� ������� �� ������ � �������� "�������� ������". 
								<!-- ���� �� �� ������, ����� ����������� ����������� �� ��������� �������, 
								�� �������� ��������� ����������� ���������, ������� �� ������ <b>���������</b> - <b>��������� ����������</b> - <b>������ ����</b>. ��� ���� ����� 
								������ ���������, ��� ����� � ����� <b>�������������� ���������</b> ������ ������� � ���� "���������� ������������� ������������" � ������ �� ������ <b>���������</b>. --></p>

								<div class="help-linked">
									<h2>����� ����������� ����������</h2>
									<ul class="page-content">
										<li><a href="/PhizIC/help.do?id=/private/longOffers/info">�������� ����������� �������</a></li>
										<li><a href="/PhizIC/help.do?id=/private/payments/payment/RefuseLongOffer/refuse-long-offer-extended-fields">������ ����������� �������</a></li>

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