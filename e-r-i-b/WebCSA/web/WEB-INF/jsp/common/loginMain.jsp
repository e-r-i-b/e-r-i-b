<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>

<!DOCTYPE HTML>
<html lang="ru-RU">

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/skins/sbrf/images/favicon.ico"/>

    <title>Сбербанк Онлайн</title>
    <link rel="stylesheet" href="${skinUrl}/skins/sbrf/csa/loginPage/main.css"/>
    <link rel="stylesheet" href="${skinUrl}/skins/sbrf/csa/loginPage/adaptive.css"/>
    <!--[if lt IE 9]>
        <link rel="stylesheet" href="${skinUrl}/skins/sbrf/csa/loginPage/ie.css"/>
        <style type="text/css">
            .b-bg .bg_slide {
            	-ms-behavior: url(${skinUrl}/scripts/csa/loginPage/libs/backgroundsize.min.htc);
            	behavior: url(${skinUrl}/scripts/csa/loginPage/libs/backgroundsize.min.htc);
            }
        </style>
    <![endif]-->


    <link rel="shortcut icon" href="favicon.ico">
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>

    <script type="text/javascript">
        document.webRoot='/${phiz:loginContextName()}';
        var skinUrl = '${skinUrl}';
        var globalUrl = '${skinUrl}/skins';

        //скриптовое изменение страницы хелпа (когда формы меняются скриптом)
        function setCurrentHelpId(id)
        {
            additionalHelpId = id;
        }

        // убирает console errors в IE6
        (function() {
            var method;
            var noop = function () {};
            var methods = [
                'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
                'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
                'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
                'timeStamp', 'trace', 'warn'
            ];
            var length = methods.length;
            var console = (window.console = window.console || {});

            while (length--) {
                method = methods[length];

                // Only stub undefined methods.
                if (!console[method]) {
                    console[method] = noop;
                }
            }
        }());
    </script>

    <script src="${skinUrl}/scripts/csa/loginPage/libs/jquery-1.11.1.min.js"></script>

    <!--[if IE 6 ]>
    <script type="text/javascript">
        $('html').addClass('ie ie6');
    </script>
    <![endif]-->
    <!--[if IE 7 ]>
    <script type="text/javascript">
        $('html').addClass('ie ie7 ie78');
    </script>
    <![endif]-->
    <!--[if IE 8 ]>
    <script type="text/javascript">
        $('html').addClass('ie ie8 ie78');
    </script>
    <![endif]-->
    <!--[if IE 9 ]>
    <script type="text/javascript">
        $('html').addClass('ie ie9');
    </script>
    <![endif]-->

    <script src="${skinUrl}/scripts/csa/loginPage/libs/respond.min.js"></script>

    <script type="text/javascript" src="${skinUrl}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/jquery.bxSlider.min.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/windows.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/csaUtils.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/csa.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/customPlaceholder.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/serializeToWin.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/json2.js"></script>

    <!--[if IE 6]>
        <script type="text/javascript" src="${skinUrl}/scripts/iepngfix_tilebg.js"></script>
    <![endif]-->

    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>
    <script src="${skinUrl}/scripts/csa/loginPage/common.js"></script>
</head>
<body class="noJS">
<tiles:insert definition="googleTagManager"/>
<div class="g-wrapper" id="workspaceCSA">
    <div id="loading" style="left:-3300px; display: none">
        <div id="loadingImg"><img src="${skinUrl}/skins/sbrf/images/ajax-loader64.gif" alt="загрузка"/></div>
        <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
    </div>

    <div class="g-main">
        <div class="g-side">

            <div class="b-side-box">
                <div class="b-auth" id="sendForm">
                    ${loginFormHeader}

                    <c:if test="${showLoginAndPasswordForm}">
                        <div class="auth_body">
                            <div class="b-logo">
                                <img class="logo_img" width="143" height="41" src="${skinUrl}/skins/sbrf/images/csa/loginPage/b-logo_img.png" alt=""/>
                            </div><!-- // g-logo -->

                            <form class="b-form auth_form" action="${formAction}" onkeypress="validateAndSubmitIfEnter(event)" autocomplete="off" id="loginForm">

                                <!--фэйковые поля логина и пароля. нужны чтоб особо умный google chrome не запоминал пароль -->
                                <input style="display:none" type="text" name="fakeLogin"/>
                                <input style="display:none" type="password" name="fakePassword"/>

                                <div class="b-field" onclick="return{type: 'login', valid:{required: true}}">
                                    <div class="field_inner">
                                        <input type="text" class="field_input" name="field(login)" id="login" maxlength="30"/>
                                        <label class="field_title" for="login">Логин, идентификатор</label>
                                    </div>
                                </div>
                                <!-- // b-field -->

                                <div class="b-field" onclick="return{type: 'password', valid:{required: true}}">
                                    <div class="field_inner">
                                        <input type="password" class="field_input" id="password" maxlength="30"/>
                                        <label class="field_title" for="password">Пароль</label>
                                    </div>
                                </div>
                                <input id="hiddenPasswordField" style="display:none" type="password" name="field(password)" />
                                <!-- // b-field -->

                                <div class="form_submit" id="buttonSubmit">

                                    <div class="b-btn btn-small btn-yellow" onclick="validateAndSubmit()">
                                        <div class="btn_text">Войти</div>
                                        <i class="btn_curve"></i>
                                        <button type="button" class="btn_hidden"></button>
                                    </div>
                                    <!-- // b-btn -->
                                    <c:if test="${showRecoverPassword}">
                                        <div class="form_remind">
                                            <a class="aBlack" href="${csa:calculateActionURL(pageContext, '/page/login-error')}">Не&nbsp;могу войти</a>
                                        </div>
                                    </c:if>
                                </div>
                            </form>
                            <!-- // b-form -->
                        </div>
                    </c:if>
                </div>
                <!-- // b-auth -->

                <c:if test="${showRegistration}">
                    <div class="b-reg">
                        <i class="reg_bg">

                        <span class="b-ie">
                            <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i
                            class="t"></i><i class="b"></i>
                        </span><!-- // b-ie -->

                        </i>

                        <a class="reg_link" href="${csa:calculateActionURL(pageContext, '/async/page/registration')}">
                        <span class="_link">Регистрация</span>
                        <div class="reg_descr">Нужна карта Сбербанка и&nbsp;мобильный телефон</div>
                        </a>
                    </div><!-- // b-reg -->
                </c:if>

            </div><!-- // b-side-box -->

            <div id="blocking-message-restriction-block">
                <c:if test="${not empty message && message ne ''}">
                    <tiles:insert definition="roundBorder" flush="false">
                        <tiles:put name="color" value="yellowTop"/>
                        <tiles:put name="data" type="string">
                            <c:out value="${message}"/>
                        </tiles:put>
                    </tiles:insert>
                </c:if>
            </div>

            <c:if test="${showSecurityBlock}">
                <div class="b-secure" id="Secure">
                    <ul class="secure_blocks">
                        <c:set var="text" value="${csa:getLoginPageMessageSecure()}"/>
                        <c:set var="secureTexts" value="${fn:split(text, '|')}"/>
                        <c:forEach var="secureText" items="${secureTexts}">
                            <li class="secure_block">
                                <h3 class="secure_title">
                                    <a href="http://www.sberbank.ru/moscow/ru/person/dist_services/warning/warning/" target="_blank">Осторожно: мошенники!</a>
                                </h3>

                                <div class="secure_text">
                                    ${secureText}
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                    <a class="secure_next _dot">Еще совет</a>
                </div>
                <!-- // b-secure -->
            </c:if>

        </div><!-- // g-side -->
    <div class="g-content">
        <c:if test="${not empty additionalContent && additionalContent ne ''}">
            ${additionalContent}
        </c:if>
        <div class="g-content-reducer">
            <div class="g-content-inner">

                <c:if test="${showHelp}">
                    <a class="b-help" href="#" onclick="openCSAHelp();">

                        <span class="b-ie">
                            <i class="bl"></i><i class="br"></i><i class="l"></i><i class="r"></i>
                            <i class="tl"></i><i class="tr"></i><i class="t"></i><i class="b"></i>
                        </span><!-- // b-ie -->

                        <i class="help_ico"></i><span class="help_link aBlack">Помощь</span>
                    </a><!-- // b-help -->
                </c:if>

                <div class="b-slides" id="Slides">
                    <div class="slides_slide">
                        <c:set var="url" value="${csa:getLoginPageMessageSlide(1, 'href')}"/>
                        <c:set var="title" value="${csa:getLoginPageMessageSlide(1, 'title')}"/>
                        <c:set var="description" value="${csa:getLoginPageMessageSlide(1, 'description')}"/>
                        <h1 class="slides_title"><a href="${url}">${title}</a></h1>
                        <div class="slides_descr">${description}</div>
                    </div>
                    <div class="slides_slide">
                        <c:set var="url" value="${csa:getLoginPageMessageSlide(2, 'href')}"/>
                        <c:set var="title" value="${csa:getLoginPageMessageSlide(2, 'title')}"/>
                        <c:set var="description" value="${csa:getLoginPageMessageSlide(2, 'description')}"/>
                        <h1 class="slides_title"><a href="${url}">${title}</a></h1>
                        <div class="slides_descr">${description}</div>
                    </div>
                    <div class="slides_slide">
                        <c:set var="url" value="${csa:getLoginPageMessageSlide(3, 'href')}"/>
                        <c:set var="title" value="${csa:getLoginPageMessageSlide(3, 'title')}"/>
                        <c:set var="description" value="${csa:getLoginPageMessageSlide(3, 'description')}"/>
                        <h1 class="slides_title"><a href="${url}">${title}</a></h1>
                        <div class="slides_descr">${description}</div>
                    </div>
                </div>
                <!-- b-slides -->

                <div class="b-news">
                    <div class="news_inner">
                        <div class="news_fade"></div>
                        <div class="news_blur"></div>
                        <span class="b-ie">
                            <i class="bl"></i><i class="br"></i><i class="l"></i><i class="r"></i>
                            <i class="tl"></i><i class="tr"></i><i class="t"></i><i class="b"></i>
                        </span><!-- // b-ie -->


                        <div class="news_content">
                            <h3 class="news_title"><a class="aBlack" href="${csa:calculateActionURL(pageContext, '/news/list')}">События</a></h3>
                            <ul class="news_list" id="MainPageNews">

                            </ul>
                        </div>
                    </div>
                </div>
                <!-- // b-news -->

            </div>
            <!-- // g-content-inner -->
        </div>
        <!-- // g-content-reducer -->
    </div>
    <!-- // g-content -->
    <div class="g-footer">
        <div class="g-footer-inner">

            <div class="b-about">

                <div class="b-phones">
                    <h3 class="phones_title">Круглосуточная помощь</h3>
                    <div class="phones_phone"><span>+</span>7&nbsp;495 <b>500-55-50</b></div>
                    <div class="phones_phone">8&nbsp;800 <b>555-55-50</b></div>
                </div>
                <!-- // b-phones -->

                <div class="b-data">
                    <div class="data_copy">&copy;&nbsp;1997&ndash;2015&nbsp;ОАО &laquo;<a target="_blank" href="http://sberbank.ru">Сбербанк России</a>&raquo;</div>
                    <div class="data_descr">
                        Россия, Москва, 117997, ул. Вавилова&nbsp;19. Генеральная лицензия
                        на&nbsp;осуществление банковских операций от&nbsp;8&nbsp;августа 2012.
                        Регистрационный номер&nbsp;&mdash; 1481. Разработано компанией <nobr>R-Style Softlab</nobr>.
                    </div>
                </div>
                <!-- // b-data -->

            </div>
            <!-- // b-about -->

            <c:if test="${showLinks}">
                <div class="b-social">
                    <a class="social_item fb" href="https://www.facebook.com/bankdruzey" target="_blank"></a>
                    <a class="social_item vk" href="http://vk.com/bankdruzey" target="_blank"></a>
                    <a class="social_item ok" href="http://www.odnoklassniki.ru/bankdruzey" target="_blank"></a>
                    <a class="social_item tw" href="https://twitter.com/sberbank/" target="_blank"></a>
                    <a class="social_item yt" href="http://www.youtube.com/sberbank" target="_blank"></a>
                </div>
            </c:if>
            <!-- // b-social -->
            <c:choose>
                <%--Страница промоутера, только если есть Cookie с данными промоутера--%>
                <c:when test="${showLogoffPromoLink}">
                    <a class="b-invoice-close" href="${csa:calculateActionURL(pageContext, '/logoffPromo.do')}">
                        <span class="invoice-close_text">Завершение смены</span>
                    </a><!-- // b-invoice-close -->
                </c:when>
                <c:otherwise>
                    ${customFooterLogo}
                </c:otherwise>
            </c:choose>
            <c:if test="${needOpenChooseRegionWindow}">
                <script type="text/javascript">
                    $(document).ready(
                        function()
                        {
                            win.open('regionsDiv');
                        }
                    );
                </script>
            </c:if>
        </div>
        <!-- // g-footer-inner -->
    </div>
    <!-- // g-footer -->

    </div>
    <!-- // g-main -->

    <div class="b-bg" id="Backgrounds">
        <div class="bg_list">
            <div class="bg_slide" style="background-image:url(${skinUrl}/skins/sbrf/images/csa/loginPage/slide0.jpg)"></div>
            <div class="bg_slide" style="background-image:url(${skinUrl}/skins/sbrf/images/csa/loginPage/slide1.jpg)"></div>
            <div class="bg_slide" style="background-image:url(${skinUrl}/skins/sbrf/images/csa/loginPage/slide2.jpg)"></div>
        </div>
        <div class="bg_paging"></div>
    </div>
    <!-- // b-bg -->

    <div class="g-overlay">

        <div class="b-popup" id="AccessDenied">
            <div class="popup_inner">
                <h2 class="popup_title c-red">Во&nbsp;входе отказано</h2>

                <div class="popup_content">
                    <p>Вы&nbsp;неправильно указали логин, идентификатор или пароль. Осталось две попытки ввода, после чего
                        логин будет заблокирован на&nbsp;один час.</p>
                </div>
            </div>
            <a class="popup_close" title="Закрыть"></a>
        </div>
        <!-- // b-popup -->

        <div class="b-popup" id="LoginDisabled">
            <div class="popup_inner">
                <h2 class="popup_title c-red">Введенный вами логин заблокирован</h2>

                <div class="popup_content">
                    <p>Логин заблокирован до&nbsp;21:00. Попробуйте войти позже или введите другой логин.</p>
                </div>
            </div>
            <a class="popup_close" title="Закрыть"></a>
        </div>
        <!-- // b-popup -->

        <div class="b-popup" id="MultiRegFix">
            <div class="popup_inner">
                <h2 class="popup_title">Выбор идентификатора, логина</h2>

                <div class="popup_content">
                    <p>Вы&nbsp;повторно зарегистрировались в&nbsp;Сбербанк Онлайн. Выберите один идентификатор (логин),
                        который будет в&nbsp;дальнейшем использоваться при работе с&nbsp;системой. Второй идентификатор
                        будет удален.</p>

                    <form class="b-form form_popup" action="#">
                        <fieldset class="form_fields">
                            <label class="form_label">
                                <input class="form_cbx" name="login" type="radio"/> Bankosberovskiy1943
                            </label>
                            <label class="form_label">
                                <input class="form_cbx" name="login" type="radio"/> Sberobankovsky123
                            </label>
                        </fieldset>
                        <div class="form_submit">

                            <div class="b-btn btn-small btn-yellow">
                                <div class="btn_text">Продолжить с&nbsp;выбранным</div>
                                <i class="btn_curve"></i>
                                <button type="submit" class="btn_hidden"></button>
                            </div>
                            <!-- // b-btn -->

                        </div>
                    </form>
                </div>
            </div>
            <a class="popup_close" title="Закрыть"></a>
        </div>
        <!-- // b-popup -->

        <div class="b-popup" id="CustomPopup">
            <div class="popup_inner"></div>
            <a class="popup_close" title="Закрыть"></a>
        </div>
        <!-- // b-popup -->

    </div>
    <!-- // g-overlay -->

</div>
<!-- // g-wrapper -->

<script type="text/javascript">
    initCSA("${globalUrl}","${skinUrl}");

    var authForm;
    <c:set var="showForm" value="${empty form.form ? 'login-form' : form.form}"/>
    $(document).ready(function(){
       authForm = new AuthForm(${form.fields.needTuringTest == null || form.fields.needTuringTest});
       authForm.showForm('<c:out value="${showForm}"/>', true);
    });

    <c:set var="helpURL" value="${csa:calculateActionURL(pageContext, '/help')}"/>
    function openCSAHelp()
    {
        var helpUrl = '${helpURL}?id=' + '${$$helpId}';
        if (additionalHelpId != '')
            helpUrl += '/' + additionalHelpId;
        return openHelp(helpUrl);
    }

    function validateAndSubmitIfEnter(event)
    {
        event = event || window.event;
        var code = navigator.appName == 'Netscape' ? event.which : event.keyCode;
        // если не enter - пропускаем
        if(code != 13)
            return;

        validateAndSubmit();
    }

    function validateAndSubmit()
    {
        $.data($('#loginForm')[0], 'jForm').submit();
    }

    function displayErrors(errors)
    {
        var html = "<h2 class='popup_title c-red'>Во&nbsp;входе отказано</h2><div class='popup_content'><p>" + errors + "</p></div>";
        overlay.showPopup('AccessDenied', html);
    }
</script>

${scripts}

<tiles:insert page="/WEB-INF/jsp/common/layout/messages.jsp">
    <tiles:put name="bundle"    value="commonBundle" type="string"/>
    <tiles:put name="usePopup"  value="true"/>
</tiles:insert>

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
</body>

</html>
