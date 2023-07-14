<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<!--[if IE 7 ]><html lang="ru-RU" class="ie78 ie7"><![endif]-->
<!--[if IE 8 ]><html lang="ru-RU" class="ie78 ie8"><![endif]-->
<!--[if IE 9 ]><html lang="ru-RU" class="ie9"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--><html lang="ru-RU"><!--<![endif]-->
<head>
    <meta charset="windows-1251">
    <title></title>
    <link rel="stylesheet" href="${skinUrl}/skins/sbrf/selfRegistration/common.css"/>
    <script type="text/javascript" src="${skinUrl}/scripts/selfRegistration/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/selfRegistration/main.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>
</head>
<body class="error">
<tiles:insert definition="googleTagManager"/>
<div class="g-wrapper">
    <div class="g-header">
        <div class="g-header_inner">

            <div class="b-logo">
                <a class="logo_link" href="${csa:calculateActionURL(pageContext,'index')}">�������� ������</a>
            </div><!-- // b-logo -->

            <div class="b-phones">
                <a href="tel:+74955005550" class="phones_item">+7 (495) <b>500-55-50</b></a>
                <a href="tel:88005555550" class="phones_item">8 (800) <b>555-55-50</b></a>
            </div>
            <!-- // b-phones -->

            <div class="b-login">
                <a class="login_in" href="${csa:calculateActionURL(pageContext,'index')}">����� � �������� ������</a>
            </div><!-- // b-login -->

        </div>
    </div>
    <div class="b-message">
    	<h1>SMS-������ ������ �����������, ��&nbsp;��������� ��� ������� �����</h1>

    	<div class="moved">
    		<p><a href="${csa:calculateActionURL(pageContext, 'async/page/registration')}">������� ����������� ������</a> ��� ���������� �&nbsp;���������� ����� ���������.</p>

    		<p>���� �� ��&nbsp;�������� ��������� �&nbsp;SMS-�������, ��� ����� ���� ������� ���������� ���������:</p>

    		<div class="b-accordion">
    			<div class="accordion_item">
    				<div class="accordion_title"><span class="dash">��� ����� �������� ��&nbsp;�������� �&nbsp;�����, ��� ��&nbsp;��������� �&nbsp;���������� �����</span>
    				</div>
    				<div class="accordion_body" style="display:none">��������� ����� &laquo;�������&raquo; ��&nbsp;����� 900, �&nbsp;�������� ��������� ��� ������ ���������� �&nbsp;������� ����������� �&nbsp;���������� �����.
    				</div>
    			</div>
    			<div class="accordion_item">
    				<div class="accordion_title"><span class="dash">��&nbsp;������� ����� ���������� ��&nbsp;����������</span>
    				</div>
    				<div class="accordion_body" style="display:none">���������, ��� ��&nbsp;������� ������� ����� ����� ������� ���������.
    				</div>
    			</div>
    		</div>
    	</div>
    </div><!-- / b-message -->
    <div class="g-footer">
        <div class="g-footer_inner">

            <div class="b-copy">
                <div class="copy_name">� 1997�2015 �������� ������</div>
                <div class="copy_description">������, ������, 117997, ��. ��������, �. 19,<br/>����������� �������� ��
                                              ������������� ���������� �������� �� 8 ������� 2012 ����<br/>���������������
                                              ����� � 1481
                </div>
            </div>
            <!-- // b-copy -->

        </div>
    </div>
</div>
<div class="b-petal petal__tl"></div>
<div class="b-petal petal__br"></div>
<div class="b-petal petal__tr"></div>

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
</body>
</html>