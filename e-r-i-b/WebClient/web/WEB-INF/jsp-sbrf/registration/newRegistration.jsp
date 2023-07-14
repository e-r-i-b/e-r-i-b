<%--
  User: basharin
  Date: 22.01.2014

  страница самостоятельной регистрации. Новый вариант.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="form" value="${SelfRegistrationForm}"/>
<c:set var="urlLogoff" value="${phiz:calculateActionURL(pageContext,'/logoff.do?toLogin=true')}"/>
<c:set var="urlLater" value="${phiz:calculateActionURL(pageContext,'/private/accounts.do')}"/>
<c:set var="isActiveCaptha" value="${phiz:isActiveCaptha(pageContext.request, 'selfRegistrationCaptchaServlet')}"/>
<c:set var="captchaUrl" value="${phiz:calculateActionURL(pageContext,'/registration/captcha')}"/>
<c:set var="captchaUrl" value="${fn:replace(captchaUrl, '.do', '.png')}"/>

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
    <title><bean:message key="application.title" bundle="commonBundle"/></title>
    <link rel="icon" type="image/x-icon" href="${imagePath}/favicon.ico"/>
    <link rel="stylesheet" href="${skinUrl}/selfRegistration/common.css"/>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/main.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/windows.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
</head>
<body onclick="return{token:'xxxxxxxx'}">
<div  id="workspace">
<tiles:insert definition="googleTagManager"/>
<input type="hidden" name="PAGE_TOKEN" value="${form.pageToken}"/>
<c:if test="${form.hintDelay != 0}">
    <input type="hidden" name="hintDelay" value="${form.hintDelay}"/>
</c:if>
<div id="loading" style="left:-3300px;">
    <div id="loadingImg"><img src="${imagePath}/ajax-loader64.gif" alt="загрузка"/></div>
    <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
</div>
<div class="g-wrapper" id="workspaceCSA">

<div class="g-header">
    <div class="g-header_inner">

        <div class="b-logo">
            <a class="logo_link" href="${urlLater}">Сбербанк Онлайн</a>
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
                <span class="link">Выход</span> <i class="login_out-pic"></i>
            </a>
        </div>
        <!-- // b-login -->

    </div>
    <!-- // g-header_inner -->
</div>
<!-- // g-header -->
<div class="g-main">
    <div class="g-main_inner">

        <div class="b-steps">
            <div class="steps_list">
                <div class="steps_item first current">
                    <span class="steps_text">Создание логина</span>
                    <i class="steps_border"></i>
                </div>
                <div class="steps_item last">
                    <span class="steps_text">Подтверждение по SMS</span>
                    <i class="steps_border"></i>
                </div>
                <div class="steps_mark">
                    <div class="steps_oval">
                        <i class="steps_round"></i>
                        <b class="steps_num">1</b>
                    </div>
                    <i class="steps_ico"></i>
                </div>
            </div>
        </div><!-- // b-steps -->
        <div class="g-content">

            <div class="b-slider">
                <div class="slider_outer">
                    <div class="slider_inner">
                        <div class="slider_page" id="SliderAuthData" style="visibility:visible">

                            <div class="b-page b-reg">
                                <div class="page_head">
                                    <div class="page_head-wrapper">

                                        <h2 class="b-title">
                                            <span class="title_text">Установите логин <br/>и&nbsp;пароль</span>
                                        </h2><!-- // b-title -->

                                    </div>
                                </div>
                                <div class="page_inner">
                                    <div class="page_inner-wrapper">

                                        <form class="b-form b-auth-form" id="AuthData" onclick="return{step:0, actionPath:'async/registration.do', captcha: ${isActiveCaptha}, operation: 'save'}">
                                            <div class="form_inner">

                                                <div class="b-field" id="Login"
                                                     onclick="return{type: 'login', valid:{required: true, maxLen: 30, minLen: ${form.minLoginLength}, letter: true, number: true, key3: true, let3: true }, avail: {path: 'async/registration.do', data:{operation: 'checkLogin'}}}">
                                                    <div class="field_inner">
                                                        <input class="field_input" id="field(login)" name="field(login)" type="text"
                                                               placeholder="Новый логин" autocomplete="off" value="${form.fields.login}"/>
                                                    </div>
                                                </div>
                                                <!-- // b-field -->

                                                <div class="b-field" id="Password"
                                                     onclick="return{type: 'password', valid:{required: true, maxLen: 30, minLen: 8, letter: true, number: true, key3: true, let3: true}, differ: 'Login'}">
                                                    <div class="field_inner">
                                                        <input class="field_input" id="field(password)" name="field(password)" type="password"
                                                               placeholder="Введите новый пароль" autocomplete="off"/>
                                                    </div>
                                                </div>
                                                <!-- // b-field -->

                                                <div class="b-field" id="RepeatPassword"
                                                     onclick="return{type: 'password', valid:{required: true, maxLen: 30, minLen: 8}, compare:'Password'}">
                                                    <div class="field_inner">
                                                        <input class="field_input" id="field(confirmPassword)" name="field(confirmPassword)"
                                                               type="password" placeholder="Повторите пароль" autocomplete="off"/>
                                                    </div>
                                                </div>
                                                <!-- // b-field -->
                                                <c:if test="${form.needShowEmailAddress}">
                                                    <div class="b-field" id="Email" onclick="return{type:'email', valid:{required: true, maxLen: 30}}">
                                                        <div class="field_inner">
                                                            <input class="field_input" id="field(email)" name="field(email)" type="text"
                                                                   placeholder="E-mail" autocomplete="off"/>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <!-- // b-field -->

                                                <div class="b-captcha" id="AuthDataCaptcha"
                                                     onclick="return{valid:{required: true}, replace: /[^\wа-яА-Я]/gi, url: '${captchaUrl}?=<%=java.lang.Math.round(java.lang.Math.random() * 100000)%>'}"
                                                     style="display:none">
                                                    <div class="captcha_pic"><img class="captcha_image" src="" alt=""/></div>
                                                    <div class="captcha_refresh">
                                                        <div class="captcha_ico"></div>
                                                        <span class="lnk">Обновить код</span>
                                                    </div>
                                                    <div class="field_inner">
                                                        <input class="field_input" id="field(captchaCode)" name="field(captchaCode)"
                                                               type="text" placeholder="Код с картинки" autocomplete="off"/>
                                                    </div>
                                                </div>
                                                <!-- // b-captcha -->

                                                <div class="b-btn disabled" id="AuthDataSubmit">
                                                    <div class="btn_text">Продолжить</div>
                                                    <i class="btn_crn"></i>
                                                    <input class="btn_hidden" type="submit"/>
                                                </div>
                                                <!-- // b-btn -->

                                                <c:choose>
                                                    <c:when test="${not form.hardRegistrationMode}">
                                                        <div class="b-back">
                                                            <a class="back_text" onclick="showOrHideAjaxPreloader();location.href = '${urlLater}'">Напомнить позже</a>
                                                        </div>
                                                        <!-- // b-back -->

                                                        <div class="b-trigger" data-popup="CancelReasonPopup">
                                                            <span class="dot">Отказаться от создания логина и&nbsp;пароля</span>
                                                        </div>
                                                        <!-- // b-back -->
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="b-back">
                                                            <a class="back_text" href="${urlLogoff}">Отменить и выйти</a>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <i class="form_pic"></i>
                                        </form>
                                        <!-- // b-sms-form -->

                                    </div>
                                </div>
                                <div class="page_content">
                                    <div class="page_content-wrapper">

                                        <div class="b-helper" onclick="return{show: $('.b-reg-helper'), interval: 7000}">
                                            <div class="helper_inner">
                                                Теперь вы можете установить свой собственный логин и&nbsp;пароль. Это повысит вашу безопасность, сэкономит время и&nbsp;значительно упростит вход в&nbsp;Сбербанк Онлайн.
                                            </div>
                                            <i class="helper_arr"></i>
                                        </div><!-- // b-helper -->

										<div class="b-reg-helper" style="display:none">
                                            <h3 class="reg-helper_head">Краткие правила</h3>

                                            <div class="reg-helper_inner">
                                                <div class="reg-helper_item">
                                                    <h4 class="reg-helper_title">sberbank21</h4>

                                                    <p class="reg-helper_description">Логин и&nbsp;пароль должны содержать буквы и&nbsp;цифры.
                                                                                      Минимум 8&nbsp;символов.</p>
                                                    <img class="reg-helper_pic" src="${imagePath}/selfRegistration/qwer.png" alt="" width="114" height="44"/>

                                                    <p class="reg_description">Нельзя использовать более 3-х символов подряд в&nbsp;одном ряду
                                                                               клавиатуры.</p>
                                                </div>
                                                <div class="reg-helper_item">
                                                    <h4 class="reg-helper_title">A=a</h4>

                                                    <p class="reg-helper_description">Логин и пароль не&nbsp;чувствительны к&nbsp;регистру</p>
                                                </div>
                                            </div>
                                            <div class="reg-helper_extend b-trigger" data-show="showRules">
                                                <span class="dash">Правила полностью</span>
                                            </div>
                                        </div>
                                        <!-- // b-reg-helper -->

                                    </div>
                                    <div class="page_content-rules" style="display:none">

                                        <div class="b-rules" id="AuthRules">
                                        	<h3 class="rules_title">Правила составления логина и&nbsp;пароля</h3>

                                        	<div class="rules_cols">
                                        		<div class="rules_col">
                                        			<h4 class="rules_col-title">Логин</h4>
                                        			<ul class="rules_list">
                                        				<li class="rules_item">Длина от&nbsp;8&nbsp;до&nbsp;30 символов.</li>
                                        				<li class="rules_item">Логин должен содержать как минимум одну цифру и&nbsp;как минимум одну букву.
                                        				</li>
                                        				<li class="rules_item">Буквы должны быть только из&nbsp;латинского алфавита.</li>
                                        				<li class="rules_item">Не может состоять из&nbsp;10&nbsp;цифр.</li>
                                        				<li class="rules_item">Не должен содержать более 3-х одинаковых символов подряд.</li>
                                        				<li class="rules_item">Может содержать элементы пунктуации из&nbsp;списка – «&nbsp;–&nbsp;@&nbsp;_&nbsp;-&nbsp;.»</li>
                                        				<li class="rules_item">Не чувствителен к&nbsp;регистру.</li>
                                        			</ul>
                                        		</div>
                                        		<div class="rules_col">
                                        			<h4 class="rules_col-title">Пароль</h4>
                                        			<ul class="rules_list">
                                        				<li class="rules_item">Длина не&nbsp;менее 8 символов.</li>
                                        				<li class="rules_item">Пароль должен содержать как минимум одну цифру и&nbsp;как минимум одну
                                        									   букву.</li>
                                        				<li class="rules_item">Не должен содержать более 3-х одинаковых символов подряд, и&nbsp;более
                                        									   3-х символов, расположенных рядом в&nbsp;одном ряду клавиатуры.
                                        				</li>
                                        				<li class="rules_item">Должен отличаться от&nbsp;логина.</li>
                                        				<li class="rules_item">Может содержать элементы пунктуации из&nbsp;списка «&nbsp;–&nbsp;!&nbsp;@&nbsp;#&nbsp;$&nbsp;%&nbsp;^&nbsp;&&nbsp;*&nbsp;(&nbsp;)&nbsp;_&nbsp;-&nbsp;+&nbsp;:&nbsp;;&nbsp;,&nbsp;.&nbsp;»</li>
                                        				<li class="rules_item">Не должен повторять старые пароли за&nbsp;последние 3 месяца.
                                        				</li>
                                        			</ul>
                                        		</div>
                                        	</div>

                                        	<div class="b-return b-trigger" data-show="showHelper"><i class="return_pic"></i></div>

                                        </div><!-- // b-rules -->


                                    </div>
                                    <div class="page_content-errors" style="display:none">

                                    </div>
                                </div>
                            </div>
                            <!-- // b-page -->

                        </div>
                        <div class="slider_page" id="SliderSmsConfirm" style="visibility:hidden">

                            <div class="b-page b-confirm">
                                <div class="page_head">
                                    <div class="page_head-wrapper">

                                        <h2 class="b-title">
                                            <span class="title_text">Подтвердите изменение <br/>SMS-паролем
                                                <i class="b-timer"></i>
                                            </span>
                                        </h2><!-- // b-title -->

                                    </div>
                                </div>
                                <div class="page_inner">
                                    <div class="page_inner-wrapper">

                                        <form class="b-form b-confirm-form" id="SmsConfirm" onclick="return{step:1, actionPath:'async/registration.do', captcha: false, operation:'sms'}">
                                            <div class="form_inner">

                                                <div class="b-field" id="Sms"
                                                     onclick="return{replace: /[^\d]/gi, valid:{required: true, maxLen: 5, minLen: 5}}">
                                                    <div class="field_inner">
                                                        <input class="field_input" id="$$confirmSmsPassword" name="$$confirmSmsPassword" type="text"
                                                               placeholder="SMS-пароль" maxlength="5" autocomplete="off"/>
                                                    </div>

                                                    <div class="b-action b-action-sms" onclick="return{actionPath:'async/registration.do', data:{operation:'sendNewSms'}}">
                                                        <div class="action_refresh">
                                                            <i class="action_ico"></i>
                                                            <span class="link">Выслать новый SMS-пароль</span>
                                                        </div>
                                                    </div>
                                                    <!-- // b-action-sms -->

                                                </div>
                                                <!-- // b-field -->

                                                <div class="b-btn disabled" id="SmsConfirmSubmit">
                                                    <div class="btn_text">Подтвердить</div>
                                                    <i class="btn_crn"></i>
                                                    <input class="btn_hidden" type="submit"/>
                                                </div>
                                                <!-- // b-btn -->

                                            </div>
                                            <i class="form_pic"></i>
                                        </form>
                                        <!-- // b-sms-form -->

                                    </div>
                                </div>
                                <div class="page_content">
                                    <div class="page_content-wrapper">

                                        <div class="b-sms-helper">
                                            <h3 class="sms-helper_head">Почему не&nbsp;приходит SMS-пароль?</h3>

                                            <div class="sms-helper_inner">
                                                <div class="sms-helper_item">
                                                    <img class="sms-helper_pic" src="${imagePath}/selfRegistration/gray_phones.png" alt="" width="58" height="53"/>
                                                    <h4 class="sms-helper_title">Номер телефона должен быть привязан к&nbsp;карте</h4>

                                                    <p class="sms-helper_description">Отправьте слово &laquo;СПРАВКА&raquo; на&nbsp;номер 900, в&nbsp;ответном
                                                                                      сообщении вам придет информация о&nbsp;наличии подключения
                                                                                      к&nbsp;Мобильному банку</p>
                                                </div>
                                                <div class="sms-helper_item">
                                                    <img class="sms-helper_pic" src="${imagePath}/selfRegistration/card.png" alt="" width="62" height="53"/>
                                                    <h4 class="sms-helper_title">Карта должна быть выпущена Сбербанком</h4>

                                                    <p class="sms-helper_description">Убедитесь что на&nbsp;лицевой стороне вашей карты
                                                                                      расположен логотип Сбербанка</p>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- // b-sms_helper -->

                                    </div>
                                    <div class="page_content-rules" style="display:none">

                                    </div>
                                    <div class="page_content-errors" style="display:none">

                                        <div class="b-message" id="IncorrectSMS">
                                            <h3 class="sys-error_title">Вы&nbsp;ввели неверный SMS-пароль, у&nbsp;вас осталось {sms_try}</h3>

                                            <div class="moved">
                                                <p>Убедитесь, что вы&nbsp;правильно вводите пятизначный SMS-пароль из&nbsp;сообщения. Пример
                                                   текста SMS:</p>

                                                <p>&laquo;Сбербанк Онлайн. Пароль для подтверждения удаленной регистрации&nbsp;&mdash;
                                                          12345&raquo;</p>
                                            </div>
                                        </div>
                                        <!-- // b-message -->

                                    </div>
                                </div>
                            </div>
                            <!-- // b-page -->

                        </div>
                    </div>
                </div>
            </div><!-- // b-slider -->

        </div><!-- // g-content -->
    </div><!-- // g-main-inner -->

    <div class="b-svg" id="Svg"></div><!-- // b-svg -->

</div>
<!-- // g-main -->
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

        <div class="b-social">
            <div class="social_title">Мы в социальных сетях</div>
            <div class="social_links">
                <a class="social_link tw" href="https://twitter.com/sberbank/" target="_blank"></a>
                <a class="social_link vk" href="http://vk.com/bankdruzey" target="_blank"></a>
                <a class="social_link fb" href="https://www.facebook.com/bankdruzey" target="_blank"></a>
                <a class="social_link ok" href="http://www.odnoklassniki.ru/bankdruzey" target="_blank"></a>
                <a class="social_link yt" href="http://www.youtube.com/sberbank" target="_blank"></a>
            </div>
        </div>
        <!-- // b-social -->

    </div>
    <!-- // g-footer-inner -->
</div>
<!-- // g-footer -->
</div>
<!-- // g-wrapper -->

<div class="g-overlay">

    <div class="b-popup" id="CancelReasonPopup">
        <div class="popup_inner">

            <form class="b-cancel-form" action="" id="CancelReason">
                <h2 class="cancel-form_title">Я не&nbsp;хочу создавать новые логин и&nbsp;пароль, <br/>потому что:</h2>
                <input type="hidden" name="operation" value="cancelReason"/>
                <input type="hidden" name="PAGE_TOKEN" value="${form.pageToken}"/>
                <div class="cancel-form_line">
                    <label class="cancel-form_row">
                        <input class="cancel-form_radio" type="radio" name="reason" value="1"/>
                        Мне удобно пользоваться цифровым идентификатором и&nbsp;паролем с чека.
                    </label>
                </div>
                <div class="cancel-form_line">
                    <label class="cancel-form_row">
                        <input class="cancel-form_radio" type="radio" name="reason" value="2"/>
                        Не вижу смысла.
                    </label>
                </div>
                <div class="cancel-form_line">
                    <label class="cancel-form_row">
                        <input class="cancel-form_radio" type="radio" name="reason" value="3"/>
                        Мне бы не хотелось тратить на&nbsp;это время.
                    </label>
                </div>
                <div class="cancel-form_line">
                    <label class="cancel-form_row">
                        <input class="cancel-form_radio" type="radio" name="reason" value="4"/>
                        Не понимаю, чего от&nbsp;меня хотят.
                    </label>
                </div>
                <div class="cancel-form_line">
                    <label class="cancel-form_row">
                        <input class="cancel-form_radio" type="radio" name="reason" value="5"/>
                    </label>

                    <div class="cancel-form_area">
                        <textarea name="another" id="another" placeholder="Другая причина"></textarea>
                    </div>
                </div>
                <script type="text/javascript">
                    $(document).ready(function()
                    {
                        initAreaMaxLengthRestriction("another", 250);
                    });
                </script>
                <div class="cancel-form_bottom">

                    <div class="b-back">
                        <button class="back_text" type="reset">Отменить</button>
                    </div><!-- // b-back -->

                    <div class="b-btn disabled" id="CancelReasonSubmit">
                        <div class="btn_text">Отправить</div>
                        <i class="btn_crn"></i>
                        <input class="btn_hidden" type="submit" disabled>
                    </div><!-- // b-btn -->

                </div>
            </form><!-- // b-cancel-form -->

        </div>
        <a class="popup_close"></a>
    </div><!-- // b-popup -->

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
</div>
</body>
</html>