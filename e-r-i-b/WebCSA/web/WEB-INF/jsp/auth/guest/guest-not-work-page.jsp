<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${GuestEntryForm}"/>
<c:set var = "imagesPath" value="${skinUrl}/skins/commonSkin/images/guest"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>�������� ������</title>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/skins/sbrf/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/commonSkin/guest.css"/>
    <script type="text/javascript" src="${skinUrl}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/guest.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/csaUtils.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/windows.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/jquery.maskedinput.js"></script>
</head>


<body class="gPage">
<div id="workspaceCSA" class="fullHeight">
    <div id="popupErrorWin" class="popupError window farAway">
        <div class="crossButton"><img src="${imagesPath}/buttonCross.png" class="crossButtonAction"></div>
        <div class="textArea">
            <div class="headerErrorMessage">�� ����� ��������</div>
            <div class="errorMessage"><p>SMS-������ ������ �����������. �� ��������� ��� ������� �����. ������� ������� ����������� ������ ��� ���������� �� ������� � ���������� ����� ���������: 8
                (800) 555-55-50, +7 (495) 500-55-50</p>

                <p>���� �� �� �������� ��������� � SMS-�������, ��������� � ������������ ���� ������ ���������� ��������.</p></div>
        </div>
        <div id="buttonRestart" class="buttonNext crossButtonAction">
            ������ ������
        </div>
    </div>

    <div class="mainContent">
        <div class="header">
            <a class="logoSB">
                <img src="${imagesPath}/logoSB.png" height="72" width="289"/>
            </a>

            <div class="headPhones">
                <div class="federal">
                    <span class="phoneIco"></span>
                    8 (800) 555 55 50
                </div>
                <div class="regional">+7 (495) 500-55-50</div>
            </div>
        </div>
        <div class="guestContent">
            <!--������ � ���������� �� ������������ �������-->
            <div class="notWorkCloud">
                <p class="textHeaderNotWork">� ���������, ������ �������� ����������</p>
                <span class="guestErrMess">� ������ ������ ������ ������-���������� ����������. ����������, ���������� ����� �����. �� �������� ��� �������� ��������.</span>
            </div>
        </div>
    </div>

    <div class="clear"></div>

    <div class="footer">
        <div class="references">
            <a href="#">���������� � ���������� ������� �� ��������� ����������� ������ � �����������
                ������</a><br/>
            <a href="#">���������� � �����, ��� ��������� ������� ��������� ����</a>

            <div class="copyright">
                &copy 1997 � 2015 �������� ������, ������, ������, 117997, ��. ��������, �.19, ���. +7(495) 500
                5550 <br/>
                8 800 555 5550. ����������� �������� �� ������������� ���������� �������� �� 8 ������� 2012
                ����. <br/>
                ��������������� ����� - 1481.
                <a class="icon" href="http://www.sberbank.ru/moscow/ru/important/"><span>�������� �����</span>.</a>
            </div>
        </div>
    </div>
    <div id="loading" style="left:-3300px;">
        <div id="loadingImg"><img src="${skinUrl}/skins/commonSkin/images/ajax-loader64.gif"/></div>
        <span>����������, ���������,<br/> ��� ������ ��������������.</span>
    </div>
</div>
</body>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp" %>
</html>