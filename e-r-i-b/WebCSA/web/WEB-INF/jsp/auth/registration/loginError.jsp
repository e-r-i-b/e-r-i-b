<%--
  User: basharin
  Date: 14.02.2014

  страница помощи по входу в систему
--%>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<c:set var="urlBack" value="${csa:calculateActionURL(pageContext,'/index.do')}"/>
<c:set var="urlRecover" value="${csa:calculateActionURL(pageContext,'/async/page/recover.do')}"/>
<c:set var="urlRegistration" value="${csa:calculateActionURL(pageContext,'/async/page/registration.do')}"/>
<c:set var="isAccessInternalRegistration" value="${csa:isAccessInternalRegistration()}"/>
<c:set var="isAccessRecoverPassword" value="${csa:isAccessRecoverPassword()}"/>

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
    <link rel="icon" type="image/x-icon" href="${skinUrl}/skins/sbrf/images/favicon.ico"/>
    <link rel="stylesheet" href="${skinUrl}/skins/sbrf/selfRegistration/common.css"/>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/selfRegistration/main.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>
</head>
<body>
<tiles:insert definition="googleTagManager"/>
<div class="g-wrapper">
    <div class="g-header">
        <div class="g-header_inner">

            <div class="b-logo">
                <a class="logo_link" href="${csa:calculateActionURL(pageContext, 'index')}">Сбербанк Онлайн</a>
            </div>
            <!-- // b-logo -->
            <div class="b-phones">
                <span class="phones_item">+7 (495) <b>500-55-50</b></span>
                <span class="phones_item">8 (800) <b>555-55-50</b></span>
            </div>

            <div class="b-login">
                <a class="login_in" href="${csa:calculateActionURL(pageContext, 'index')}">Войти в Сбербанк Онлайн</a>
            </div>
            <!-- // b-login -->

        </div>
    </div>
    <div class="g-main">
        <div class="g-main_inner">


            <div class="g-content">

                <div class="b-message">
                    <h1>Почему не&nbsp;получается войти в&nbsp;систему?</h1>

                    <div class="moved">
                        <c:if test="${isAccessRecoverPassword}">
                            <p>Если вы забыли свой пароль, воспользуйтесь <a href="${urlRecover}">формой восстановления пароля</a>.</p>
                        </c:if>

                        <p>Если вы&nbsp;забыли логин, восстановите его, позвонив в&nbsp;Контактный центр Сбербанка по&nbsp;телефону&nbsp;<b>+7&nbsp;(495)&nbsp;500-55-50</b>
                            <c:if test="${isAccessInternalRegistration}">
                                <br/><i class="positioned">либо</i><a
                                        href="${urlRegistration}">Зарегистрируйтесь заново</a>, создав новые логин и&nbsp;пароль.
                               Возможности &laquo;Сбербанк Онлайн&raquo; будут доступны с&nbsp;ограничениями. Ограничения
                               можно снять, подтвердив любую операцию звонком в&nbsp;Контактный центр Сбербанка.
                            </c:if>
                        </p>

                        <p>Если ваш профиль заблокирован, SMS-пароль не&nbsp;приходит, или возникли другие неполадки, но&nbsp;вы&nbsp;уверены,
                           что вводите все данные правильно, обратитесь в&nbsp;Контактный центр по&nbsp;телефонам: <b>+7&nbsp;(495)&nbsp;500&nbsp;5550,
                                                                                                                      8&nbsp;(800)&nbsp;555
                                                                                                                      5550</b>
                        </p>

                        <div class="b-back">
                            <a class="back_text" href="${urlBack}">Назад</a>
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
<div class="g-overlay">

    <div class="b-popup" id="SysError">
        <div class="popup_inner">

            <div class="b-message">
                <h1>Ошибка соединения</h1>

                <p>Не удалось подключиться к серверу. Попробуйте позже</p>
            </div>
            <!-- // b-message -->

        </div>
        <a class="popup_close"></a>
    </div>

</div>
<!-- // g-overlay -->

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
</body>
</html>