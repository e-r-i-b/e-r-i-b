<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
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
                <a class="logo_link" href="${csa:calculateActionURL(pageContext,'index')}">Сбербанк Онлайн</a>
            </div><!-- // b-logo -->

            <div class="b-phones">
                <a href="tel:+74955005550" class="phones_item">+7 (495) <b>500-55-50</b></a>
                <a href="tel:88005555550" class="phones_item">8 (800) <b>555-55-50</b></a>
            </div>
            <!-- // b-phones -->

            <div class="b-login">
                <a class="login_in" href="${csa:calculateActionURL(pageContext,'index')}">Войти в Сбербанк Онлайн</a>
            </div><!-- // b-login -->

        </div>
    </div>
    <div class="g-main">
        <div class="g-main_inner">

            <div class="g-content">

                <div class="b-message">
                    <h1>Вы&nbsp;указали дополнительную карту</h1>

                    <div class="moved">
                        <p><a href="${csa:calculateActionURL(pageContext, '/async/page/registration')}">Начните процесс регистрации заново</a> с&nbsp;номером основной действующей карты. Если решить проблему не&nbsp;удалось, обратитесь в&nbsp;Контактный центр Сбербанка по&nbsp;телефонам:<br><br>+7&nbsp;(495)&nbsp;500 5550,&nbsp;8&nbsp;(800)&nbsp;555 5550</p>
                    </div>
                </div>

            </div>

        </div>

    </div>
    <div class="g-footer">
        <div class="g-footer_inner">

            <div class="b-copy">
                <div class="copy_name">© 1997—2015 Сбербанк России</div>
                <div class="copy_description">Россия, Москва, 117997, ул. Вавилова, д. 19,<br/>Генеральная лицензия на
                                              осуществление банковских операций от 8 августа 2012 года<br/>Регистрационный
                                              номер — 1481
                </div>
            </div>
            <!-- // b-copy -->

        </div>
    </div>
</div>
<div class="b-petal petal__tl"></div>
<div class="b-petal petal__br"></div>
<div class="b-petal petal__tr"></div>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
</body>
</html>