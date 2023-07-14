<%--
  User: basharin
  Date: 14.02.2014

  �������� ������ �� ����� � �������
--%>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${LoginErrorForm}"/>
<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="urlLater" value="${phiz:calculateActionURL(pageContext,'/private/accounts.do')}"/>
<c:set var="urlLogoff" value="${phiz:calculateActionURL(pageContext,'/logoff.do?toLogin=true')}"/>
<c:set var="urlBack" value="${phiz:calculateActionURL(pageContext,'/login/self-registration.do')}"/>


<!DOCTYPE HTML>
<!--[if IE 6 ]>
<html lang="ru-RU" class="ie678 ie6"><![endif]-->
<!--[if IE 7 ]>
<html lang="ru-RU" class="ie678 ie7"><![endif]-->
<!--[if IE 8 ]>
<html lang="ru-RU" class="ie678 ie8"><![endif]-->
<!--[if IE 9 ]>
<html lang="ru-RU" class="ie9"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="ru-RU"><!--<![endif]-->
<head>
    <meta charset="windows-1251">
    <title><bean:message bundle="commonBundle" key="application.title"/></title>
    <link rel="icon" type="image/x-icon" href="${imagePath}/favicon.ico"/>
    <link rel="stylesheet" href="${skinUrl}/selfRegistration/common.css"/><!--[if lt IE 9 ]>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/jquery-1.10.2.min.js"></script><![endif]--><!--[if (gte IE 9)|!(IE)]><!-->
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/jquery-2.0.3.min.js"></script><!--<![endif]-->
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/main.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
</head>
<body>
<tiles:insert definition="googleTagManager"/>
<div class="g-wrapper">
    <div class="g-header">
        <div class="g-header_inner">

            <div class="b-logo">
                <a class="logo_link" href="${urlLater}">�������� ������</a>
            </div>
            <!-- // b-logo -->

            <div class="b-phones">
                <span class="phones_item">+7 (495) <b>500-55-50</b></span>
                <span class="phones_item">8 (800) <b>555-55-50</b></span>
            </div>
            <!-- // b-phones -->

            <div class="b-login">
                <span class="login_user"><c:out value="${phiz:getFormattedPersonFIO(person.firstName, person.surName, person.patrName)}"/></span>
                <a class="login_out" href="${urlLogoff}">
                    <span class="link">�����</span> <i class="login_out-pic"></i>
                </a>
            </div>
            <!-- // b-login -->

        </div>
    </div>
    <div class="g-main">
        <div class="g-main_inner">


            <div class="g-content">

                <div class="b-message">
                    <h1>������ ��&nbsp;���������� ����� �&nbsp;�������?</h1>

                    <div class="moved">
                        <p>���� �� ������ ���� ������, �������������� <a href="${form.urlRecover}">������ �������������� ������</a>.</p>

                        <p>���� ��&nbsp;������ �����, ������������ ���, �������� �&nbsp;���������� ����� ��������� ��&nbsp;��������&nbsp;<b>+7&nbsp;(495)&nbsp;500-55-50</b>
                            <c:if test="${form.visibleMultipleRegistrationPart}">
                                <br/><i class="positioned">����</i><a
                                        href="${form.urlRegistration}">����������������� ������</a>, ������ ����� ����� �&nbsp;������.
                               ����������� &laquo;�������� ������&raquo; ����� �������� �&nbsp;�������������. �����������
                               ����� �����, ���������� ����� �������� ������� �&nbsp;���������� ����� ���������.
                            </c:if>
                        </p>

                        <p>���� ��� ������� ������������, SMS-������ ��&nbsp;��������, ��� �������� ������ ���������, ��&nbsp;��&nbsp;�������,
                           ��� ������� ��� ������ ���������, ���������� �&nbsp;���������� ����� ��&nbsp;���������: <b>+7&nbsp;(495)&nbsp;500&nbsp;5550,
                                                                                                                      8&nbsp;(800)&nbsp;555
                                                                                                                      5550</b>
                        </p>

                        <div class="b-back">
                            <a class="back_text" href="${urlBack}">�����</a>
                        </div>
                        <!-- // b-back -->

                    </div>
                </div>
                <!-- // b-message -->

            </div><!-- // g-content -->
        </div><!-- // g-main_inner -->

        <div class="b-svg" id="Svg"></div><!-- // b-svg -->

    </div>
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
<div class="g-overlay">

    <div class="b-popup" id="SysError">
        <div class="popup_inner">

            <div class="b-message">
                <h1>������ ����������</h1>

                <p>�� ������� ������������ � �������. ���������� �����</p>
            </div>
            <!-- // b-message -->

        </div>
        <a class="popup_close"></a>
    </div>

</div>
<!-- // g-overlay -->
</body>
</html>