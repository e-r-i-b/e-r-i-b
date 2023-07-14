<%--
  User: basharin
  Date: 13.12.2013

  страница для пользователя, который зашел по логину iPas, у клиента есть регистрации в ЦСА
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="form" value="${LoginSelfRegistrationForm}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="urlLogoff" value="${phiz:calculateActionURL(pageContext,'/logoff.do?toLogin=true')}"/>
<c:set var="urlLater" value="${phiz:calculateActionURL(pageContext,'/private/accounts.do')}"/>
<c:set var="urlLoginError" value="${phiz:calculateActionURL(pageContext,'/login/login-error.do')}"/>
<c:set var="urlThis" value="${phiz:calculateActionURL(pageContext,'/login/self-registration.do')}"/>

<!DOCTYPE HTML>
<!--[if IE 7 ]>
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
    <link rel="stylesheet" href="${skinUrl}/selfRegistration/common.css"/>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/main.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
</head>
<body onclick="return{token:'xxxxxxxxx'}">
<tiles:insert definition="googleTagManager"/>
<input type="hidden" name="PAGE_TOKEN" value="${form.pageToken}"/>
<c:if test="${form.hintDelay != 0}">
    <input type="hidden" name="hintDelay" value="${form.hintDelay}"/>
</c:if>
<div class="g-wrapper">
    <div class="g-header">
        <div class="g-header_inner">

            <div class="b-logo">
                <a class="logo_link" href="${urlLater}">Сбербанк Онлайн</a>
            </div><!-- // b-logo -->

            <div class="b-phones">
                <span class="phones_item">+7 (495) <b>500-55-50</b></span>
                <span class="phones_item">8 (800) <b>555-55-50</b></span>
            </div><!-- // b-phones -->

            <div class="b-login">
                <span class="login_user"><c:out value="${phiz:getFormattedPersonFIO(person.firstName, person.surName, person.patrName)}"/></span>
                <a class="login_out" href="${urlLogoff}">
                    <span class="link">Выход</span> <i class="login_out-pic"></i>
                </a>
            </div><!-- // b-login -->

        </div><!-- // g-header_inner -->
    </div><!-- // g-header -->
    <div class="g-main">
        <div class="g-main_inner">

            <div class="g-content">

                <div class="b-page b-auth">
                    <div class="page_head">
                        <c:choose>
                            <c:when test="${form.hardRegistrationMode}">
                                <h2 class="b-title">
                                    <span class="title_text">Ошибка. <br/>Вход невозможен</span>
                                </h2><!-- // b-title -->
                            </c:when>
                            <c:otherwise>
                                <h2 class="b-title">
                                    <span class="title_text">Войдите по&nbsp;индивидуальному <br/>логину</span>
                                </h2><!-- // b-title -->
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="page_inner">

                        <form class="b-form b-auth-form" id="Auth"
                              onclick="return{actionPath:'async/self-registration.do', captcha: false, operation: 'login'}">
                            <div class="form_inner">

                                <div class="b-field" id="AuthLogin"
                                     onclick="return{edit:['lowercase'], valid:{required: true }}">
                                    <div class="field_inner">
                                        <input class="field_input" id="field(AuthLoginInput)" name="field(AuthLoginInput)" type="text"
                                               placeholder="Введите логин" autocomplete="off"/>
                                    </div>
                                </div><!-- // b-field -->

                                <div class="b-field" id="AuthPassword"
                                     onclick="return{type: 'password', edit:['lowercase'], valid:{required: true}}">
                                    <div class="field_inner">
                                        <input class="field_input" id="field(AuthPasswordInput)" name="field(AuthPasswordInput)"
                                               type="password" placeholder="Введите пароль" autocomplete="off"/>
                                    </div>
                                </div><!-- // b-field -->

                                <div class="b-captcha" id="AuthCaptcha"
                                     onclick="return{valid:{required: true}, replace: /[^\d]/gi, url: '${imagePath}/selfRegistration/captcha.png'}"
                                     style="display:none">
                                    <div class="captcha_pic"><img class="captcha_image" src="${imagePath}/selfRegistration/captcha.png" alt=""/>
                                    </div>
                                    <div class="captcha_refresh">
                                        <div class="captcha_ico"></div>
                                        <span class="lnk">Обновить код</span>
                                    </div>
                                    <div class="field_inner">
                                        <input class="field_input" id="AuthCaptchaInput" name="AuthDataCaptchaInput"
                                               type="text" placeholder="Код с картинки" autocomplete="off"/>
                                    </div>
                                </div><!-- // b-captcha -->

                                <div class="b-btn" id="AuthSubmit">
                                    <div class="btn_text">Войти</div>
                                    <i class="btn_crn"></i>
                                    <input class="btn_hidden" type="submit"/>
                                </div><!-- // b-btn -->

                                <c:if test="${not form.hardRegistrationMode}">
                                    <div class="b-back">
                                        <a class="back_text" href="${urlThis}?operation=cancel&PAGE_TOKEN=${form.pageToken}">Отказаться и&nbsp;продолжить</a>
                                    </div><!-- // b-back -->
                                </c:if>

                            </div>
                            <i class="form_pic"></i>
                        </form><!-- // b-sms-form -->

                    </div>
                    <div class="page_content">
                        <div class="page_content-wrapper">

                            <div class="b-helper">
                                <c:choose>
                                    <c:when test="${form.hardRegistrationMode}">
                                        <div class="helper_inner">
                                            <p>Вы&nbsp;пытаетесь войти с&nbsp;цифровым идентификатором или неактивным логином. Вход невозможен,
                                            так как вы&nbsp;ранее создали новый логин. Воспользуйтесь им&nbsp;для входа в&nbsp;&laquo;Сбербанк Онлайн&raquo;.</p>
                                            <c:choose>
                                                <c:when test="${form.denyMultipleRegistration}">
                                                    <p>Если вы&nbsp;забыли логин, восстановите его звонком в&nbsp;Контактный центр Сбербанка.</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p>Если вы&nbsp;забыли логин, восстановите его звонком в&nbsp;Контактный центр Сбербанка, либо
                                                    <a href="${form.urlRegistration}">пройдите процедуру регистрации повторно</a>.</p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="helper_inner">
                                            Ранее вы&nbsp;уже создали свой собственный логин для входа в&nbsp;&laquo;Сбербанк
                                            Онлайн&raquo;. Для вашей безопасности рекомендуем использовать его вместо старого
                                            логина или цифрового идентификатора.
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <i class="helper_arr"></i>
                            </div><!-- // b-helper -->

                            <div class="b-reminder">
                                <a class="reminder_text" href="${urlLoginError}">Забыли логин и&nbsp;пароль?</a>
                            </div><!-- // b-reminder -->

                        </div>
                    </div>
                </div><!-- // b-auth -->

            </div><!-- // g-content -->
        </div><!-- // g-main-inner -->

        <div class="b-svg" id="Svg"></div><!-- // b-svg -->

    </div><!-- // g-main -->
    <div class="g-footer">
        <div class="g-footer_inner">

            <div class="b-copy">
                <div class="copy_name">© 1997—2015 Сбербанк России</div>
                <div class="copy_description">Россия, Москва, 117997, ул. Вавилова, д. 19,<br/>Генеральная лицензия на
                                              осуществление банковских операций от 8 августа 2012 года<br/>Регистрационный
                                              номер — 1481
                </div>
            </div><!-- // b-copy -->

            <div class="b-social">
                <div class="social_title">Мы в социальных сетях</div>
                <div class="social_links">
                    <a class="social_link tw" href="https://twitter.com/sberbank/" target="_blank"></a>
                    <a class="social_link vk" href="http://vk.com/bankdruzey" target="_blank"></a>
                    <a class="social_link fb" href="https://www.facebook.com/bankdruzey" target="_blank"></a>
                    <a class="social_link ok" href="http://www.odnoklassniki.ru/bankdruzey" target="_blank"></a>
                    <a class="social_link yt" href="http://www.youtube.com/sberbank" target="_blank"></a>
                </div>
            </div><!-- // b-social -->

        </div><!-- // g-footer-inner -->
    </div><!-- // g-footer -->
</div><!-- // g-wrapper -->

<div class="g-overlay">

    <div class="b-popup" id="SysError">
        <div class="popup_inner">

            <div class="b-message">
                <h1>Ошибка соединения</h1>
                <p>Не удалось подключиться к серверу. Попробуйте позже</p>
            </div><!-- // b-message -->

        </div>
        <a class="popup_close"></a>
    </div><!-- // b-popup -->

    <div class="b-popup" id="CustomMessage">
        <div class="popup_inner">

            <div class="b-message"></div><!-- // b-message -->

        </div>
        <a class="popup_close"></a>
    </div><!-- // b-popup -->

</div><!-- // g-overlay -->

</body>
</html>