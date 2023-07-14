<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<!--[if IE 6 ]><html lang="ru-RU" class="ie678 ie6"><![endif]-->
<!--[if IE 7 ]><html lang="ru-RU" class="ie678 ie7"><![endif]-->
<!--[if IE 8 ]><html lang="ru-RU" class="ie678 ie8"><![endif]-->
<!--[if IE 9 ]><html lang="ru-RU" class="ie9"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--><html lang="ru-RU"><!--<![endif]-->
<input type="hidden" name="page_type" value="recover"/>
<c:set var="imagePath" value="${skinUrl}/skins/sbrf/images/selfRegistration"/>
<head>
    <meta charset="windows-1251">
    <title><bean:message bundle="commonBundle" key="application.title"/></title>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/skins/sbrf/images/favicon.ico"/>
    <link rel="stylesheet" href="${skinUrl}/skins/sbrf/selfRegistration/common.css"/>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/selfRegistration/main.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>
</head>
<body onclick="return{token:'xxxxxxxxx'}">
<tiles:insert definition="googleTagManager"/>
<c:set var="form" value="${RecoverPasswordForm}"/>
<c:if test="${form.hintDelay != 0}">
    <input type="hidden" name="hintDelay" value="${form.hintDelay}"/>
</c:if>
<div class="g-wrapper">
    <div class="g-header">
        <div class="g-header_inner">

            <div class="b-logo">
                <a class="logo_link" href="${csa:calculateActionURL(pageContext, 'index')}">Сбербанк Онлайн</a>
            </div><!-- // b-logo -->

            <div class="b-phones">
                <span class="phones_item">+7 (495) <b>500-55-50</b></span>
                <span class="phones_item">8 (800) <b>555-55-50</b></span>
            </div><!-- // b-phones -->

            <div class="b-login">
                <a class="login_in" href="${csa:calculateActionURL(pageContext, 'index')}">Войти в Сбербанк Онлайн</a>
            </div><!-- // b-login -->

        </div><!-- // g-header_inner -->
    </div><!-- // g-header -->
    <div class="g-main">
        <div class="g-main_inner">

            <div class="b-steps">
                <div class="steps_list">
                    <div class="steps_item first current">
                        <span class="steps_text">Ввод логина</span>
                        <i class="steps_border"></i>
                    </div>
                    <div class="steps_item">
                        <span class="steps_text">Подтверждение по SMS</span>
                        <i class="steps_border"></i>
                    </div>
                    <div class="steps_item last">
                        <span class="steps_text">Смена пароля</span>
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
                            <div class="slider_page" id="SliderPasswordAuth" style="visibility:visible">

                                <div class="b-page b-confirm">
                                    <div class="page_head">
                                        <div class="page_head-wrapper">
                                            <h1 class="page_about">Восстановление пароля</h1>

                                            <h2 class="b-title">
                                                <span class="title_text">Ввод идентификатора,<br/> логина</span>
                                            </h2><!-- // b-title -->

                                        </div>
                                    </div>
                                    <div class="page_inner">
                                        <div class="page_inner-wrapper">

                                            <form class="b-form b-auth-form" id="PasswordAuth" onclick="return{step: 0,actionPath:'${csa:calculateActionURL(pageContext, 'async/page/recover')}', captcha: ${csa:isActiveCaptha(pageContext.request, "captchaServlet")}, operation: 'begin'}" autocomplete="off">
                                                <div class="form_inner">

                                                    <div class="b-field" id="AuthLogin" onclick="return{type: 'login', valid:{required: true, maxLen: 30, minLen: 5}, fill: '#SmsConfirmLogin'}">
                                                        <div class="field_inner">
                                                            <input class="field_input" id="AuthLoginInput" name="field(login)" type="text" placeholder="Идентификатор или логин" autocomplete="off"/>
                                                        </div>
                                                    </div><!-- // b-field -->

                                                    <div class="b-field invisible" onclick="return{valid:{}}">
                                                        <div class="field_inner invisible">
                                                            <input class="field_input rsa" id="deviceprint" name="deviceprint" type="hidden"/>
                                                        </div>
                                                    </div>

                                                    <div class="b-field invisible" onclick="return{valid:{}}">
                                                        <div class="field_inner invisible">
                                                            <input class="field_input rsa" id="htmlinjection" name="htmlinjections" type="hidden"/>
                                                        </div>
                                                    </div>

                                                    <div class="b-field invisible" onclick="return{valid:{}}">
                                                        <div class="field_inner invisible">
                                                            <input class="field_input rsa" id="manvsmachinedetection" name="manvsmachinedetection" type="hidden"/>
                                                        </div>
                                                    </div>

                                                    <div class="b-field invisible" onclick="return{valid:{}}">
                                                        <div class="field_inner invisible">
                                                            <input class="field_input rsa" id="domElements" name="domElements" type="hidden"/>
                                                        </div>
                                                    </div>

                                                    <div class="b-field invisible" onclick="return{valid:{}}">
                                                        <div class="field_inner invisible">
                                                            <input class="field_input rsa" id="jsEvents" name="jsEvents" type="hidden"/>
                                                        </div>
                                                    </div>

                                                    <div class="b-field invisible" onclick="return{valid:{}}">
                                                        <div class="field_inner invisible">
                                                            <input class="field_input rsa" id="deviceTokenCookie" name="deviceTokenCookie" type="hidden"/>
                                                        </div>
                                                    </div>

                                                    <div class="b-captcha" id="PasswordAuthCaptcha" onclick="return{valid:{required: true}, replace: /[^\d\wа-яА-Я]/gi, url: '/${csa:loginContextName()}/captcha.png?=<%=java.lang.Math.round(java.lang.Math.random() * 100000)%>'}" style="display:none">
                                                        <div class="captcha_pic"><img class="captcha_image" src="" alt="" title="Обновить код"/></div>
                                                        <div class="captcha_refresh">
                                                            <div class="captcha_ico"></div>
                                                            <span class="lnk">Обновить код</span>
                                                        </div>
                                                        <div class="field_inner">
                                                            <input class="field_input" id="PasswordAuthCaptchaInput" name="field(captchaCode)" type="text" placeholder="Код с картинки" autocomplete="off"/>
                                                        </div>
                                                    </div><!-- // b-captcha -->

                                                    <div class="b-btn disabled" id="PasswordAuthSubmit">
                                                        <div class="btn_text">Продолжить</div>
                                                        <i class="btn_crn"></i>
                                                        <input class="btn_hidden" type="submit" tabindex="3"/>
                                                    </div><!-- // b-btn -->

                                                    <div class="b-back">
                                                        <a class="back_text" href="${csa:calculateActionURL(pageContext, 'index')}">Отменить</a>
                                                    </div><!-- // b-back -->

                                                </div>
                                                <i class="form_pic"></i>
                                            </form><!-- // b-sms-form -->

                                        </div>
                                    </div>
                                    <div class="page_content">
                                        <div class="page_content-wrapper">

                                            <div class="b-helper">
                                                <div class="helper_inner">
                                                    Введите ваш уникальный идентификатор с&nbsp;чека или логин, который вы&nbsp;составили самостоятельно.
                                                </div>
                                                <i class="helper_arr"></i>
                                            </div><!-- // b-helper -->

                                            <div class="b-reminder">
                                                <a class="reminder_text" href="${csa:calculateActionURL(pageContext, '/page/login-error')}">Не получается войти?</a>
                                            </div><!-- // b-reminder -->

                                        </div>
                                        <div class="page_content-rules" style="display:none">

                                        </div>
                                        <div class="page_content-errors" style="display:none">

                                        </div>
                                    </div>
                                </div><!-- // b-page -->

                            </div>
                            <div class="slider_page" id="SliderPasswordConfirm" style="visibility:hidden">

                                <div class="b-page b-confirm">
                                    <div class="page_head">
                                        <div class="page_head-wrapper">
                                            <h1 class="page_about">Восстановление пароля</h1>

                                            <h2 class="b-title">
                                                <span class="title_text">Подтвердите <br/>восстановление пароля
                                                    <i class="b-timer"
                                                       onclick="return{redirect: '${csa:calculateActionURL(pageContext, '/async/page/recovery/timeout')}'}"></i>
                                                </span>
                                            </h2><!-- // b-title -->

                                        </div>
                                    </div>
                                    <div class="page_inner">
                                        <div class="page_inner-wrapper">

                                            <form class="b-form b-auth-form" id="SmsConfirm" onclick="return{step:1, actionPath:'${csa:calculateActionURL(pageContext, '/async/page/recover')}', captcha: false, operation: 'next'}" autocomplete="off">
                                                <div class="form_inner">

                                                    <div class="form_row">
                                                        <span class="form_row-label">Логин:</span>
                                                        <span class="form_row-value" id="SmsConfirmLogin"></span>
                                                    </div>

                                                    <div class="b-field" id="Sms" onclick="return{replace: /[^\d]/gi, valid:{required: true, maxLen: 5, minLen: 5}}">
                                                        <div class="field_inner">
                                                            <input class="field_input" id="SmsInput" name="field(confirmPassword)" type="text" placeholder="SMS-пароль" tabindex="1" autocomplete="off" maxlength="5"/>
                                                        </div>
                                                    </div><!-- // b-field -->

                                                    <div class="b-captcha" id="SmsConfirmCaptcha" onclick="return{valid:{required: true}, replace: /[^\wа-яА-Я]/gi, url: '/${csa:loginContextName()}/captcha.png?=<%=java.lang.Math.round(java.lang.Math.random() * 100000)%>'}" style="display:none">
                                                        <div class="captcha_pic"><img class="captcha_image" src="" alt=""/></div>
                                                        <div class="captcha_refresh">
                                                            <div class="captcha_ico"></div>
                                                            <span class="lnk">Обновить код</span>
                                                        </div>
                                                        <div class="field_inner">
                                                            <input class="field_input" id="SmsConfirmCaptchaInput" name="field(captchaCode)" type="text" placeholder="Код с картинки" autocomplete="off"/>
                                                        </div>
                                                    </div><!-- // b-captcha -->

                                                    <div class="b-btn disabled" id="SmsConfirmSubmit">
                                                        <div class="btn_text">Далее</div>
                                                        <i class="btn_crn"></i>
                                                        <input class="btn_hidden" type="submit" tabindex="3"/>
                                                    </div><!-- // b-btn -->

                                                    <div class="b-back">
                                                        <a class="back_text" href="${csa:calculateActionURL(pageContext, 'index')}">Отменить</a>
                                                    </div><!-- // b-back -->

                                                </div>
                                                <i class="form_pic"></i>
                                            </form><!-- // b-sms-form -->

                                        </div>
                                    </div>
                                    <div class="page_content">
                                        <div class="page_content-wrapper">

                                            <div class="b-helper" onclick="return{interval: 5000, show: $('#SmsConfirmHelper')}">
                                                <div class="helper_inner">
                                                    Для восстановления пароля &laquo;Сбербанк Онлайн&raquo; внимательно введите пятизначный SMS-пароль из&nbsp;сообщения.
                                                </div>
                                                <i class="helper_arr"></i>
                                            </div><!-- // b-helper -->

                                            <div class="b-sms-helper" id="SmsConfirmHelper" style="display:none">
                                                <h3 class="sms-helper_head">Почему не&nbsp;приходит SMS-пароль?</h3>
                                                <div class="sms-helper_inner">
                                                    <div class="sms-helper_item">
                                                        <img class="sms-helper_pic" src="${imagePath}/gray_phones.png" alt="" width="58" height="53"/>
                                                        <h4 class="sms-helper_title">Номер телефона должен быть привязан к&nbsp;карте</h4>
                                                        <p class="sms-helper_description">Отправьте слово &laquo;СПРАВКА&raquo; на&nbsp;номер 900, в&nbsp;ответном сообщении вам придет информация о&nbsp;наличии подключения к&nbsp;Мобильному банку</p>
                                                    </div>
                                                    <div class="sms-helper_item">
                                                        <img class="sms-helper_pic" src="${imagePath}/card.png" alt="" width="62" height="53"/>
                                                        <h4 class="sms-helper_title">Карта должна быть выпущена Сбербанком</h4>
                                                        <p class="sms-helper_description">Убедитесь что на&nbsp;лицевой стороне вашей карты расположен логотип Сбербанка</p>
                                                    </div>
                                                </div>
                                            </div><!-- // b-sms_helper -->

                                        </div>
                                        <div class="page_content-rules" style="display:none">

                                        </div>
                                        <div class="page_content-errors" style="display:none">

                                            <div class="b-message" id="IncorrectSMS">
                                                <h3 class="sys-error_title">Вы&nbsp;ввели неверный SMS-пароль, у&nbsp;вас осталось {param}</h3>
                                                <div class="moved">
                                                    <p>Убедитесь, что вы&nbsp;правильно вводите пятизначный SMS-пароль из&nbsp;сообщения. Пример текста SMS:</p>
                                                    <p>&laquo;Сбербанк Онлайн. Пароль для подтверждения удаленной регистрации&nbsp;&mdash; 12345&raquo;</p>
                                                </div>
                                            </div><!-- // b-message -->


                                        </div>
                                    </div>
                                </div><!-- // b-page -->

                            </div>
                            <div class="slider_page" id="SliderPasswordChange" style="visibility:hidden">

                                <div class="b-page b-confirm">
                                    <div class="page_head">
                                        <div class="page_head-wrapper">
                                            <div class="page_about">Восстановление пароля</div>

                                            <h2 class="b-title">
                                                <span class="title_text">Восстановление&nbsp;пароля <br/>«Сбербанк&nbsp;Онлайн»</span>
                                            </h2><!-- // b-title -->

                                        </div>
                                    </div>
                                    <div class="page_inner">
                                        <div class="page_inner-wrapper">

                                            <form class="b-form b-auth-form" id="PasswordData" onclick="return{step:2, actionPath:'${csa:calculateActionURL(pageContext, '/async/page/recover')}', captcha: false, operation: 'next'}" autocomplete="off">
                                                <div class="form_inner">

                                                    <div class="b-field" id="Password"

                                                         onclick="return{type: 'password', valid:{required: true, password: true, maxLen: 30, minLen: 8, letter: true, number: true, key3: true, let3: true} <c:if test="${csa:isAsyncCheckingFieldsEnabled()}">,avail: {path: '${csa:calculateActionURL(pageContext, 'async/page/recover')}', data:{operation: 'checkPassword'}}</c:if>}"
                                                    >
                                                        <div class="field_inner">
                                                            <input class="field_input" id="PasswordInput" name="field(password)" type="password" placeholder="Новый пароль" autocomplete="off"/>
                                                        </div>
                                                    </div><!-- // b-field -->

                                                    <div class="b-field" id="RepeatPassword" onclick="return{type: 'password', valid:{required: true, maxLen: 30, minLen: 8}, compare:'Password'}">
                                                        <div class="field_inner">
                                                            <input class="field_input" id="RepeatPasswordInput" name="field(confirmPassword)" type="password" placeholder="Повторите пароль" autocomplete="off"/>
                                                        </div>
                                                    </div><!-- // b-field -->

                                                    <div class="b-captcha" id="AuthDataCaptcha" onclick="return{valid:{required: true}, replace: /[^\d\wа-яА-Я]/gi, url: '/${csa:loginContextName()}/captcha.png?=<%=java.lang.Math.round(java.lang.Math.random() * 100000)%>'}" style="display:none">
                                                        <div class="captcha_pic"><img class="captcha_image" src="" alt=""/></div>
                                                        <div class="captcha_refresh">
                                                            <div class="captcha_ico"></div>
                                                            <span class="lnk">Обновить код</span>
                                                        </div>
                                                        <div class="field_inner">
                                                            <input class="field_input" id="AuthDataCaptchaInput" name="field(captchaCode)" type="text" placeholder="Код с картинки" autocomplete="off"/>
                                                        </div>
                                                    </div><!-- // b-captcha -->

                                                    <div class="b-btn disabled" id="PasswordDataSubmit">
                                                        <div class="btn_text">Продолжить</div>
                                                        <i class="btn_crn"></i>
                                                        <input class="btn_hidden" type="submit"/>
                                                    </div><!-- // b-btn -->

                                                    <div class="b-back">
                                                        <a class="back_text" href="${csa:calculateActionURL(pageContext, 'index')}">Отменить</a>
                                                    </div><!-- // b-back -->

                                                </div>
                                                <i class="form_pic"></i>
                                            </form><!-- // b-sms-form -->

                                        </div>
                                    </div>
                                    <div class="page_content">
                                        <div class="page_content-wrapper">

                                            <div class="b-helper" onclick="return{interval: 5000, show: $('#NewPasswordHelper')}">
                                                <div class="helper_inner">
                                                    Придумайте свой новый пароль. В&nbsp;дальнейшем он&nbsp;будет использоваться для входа в&nbsp;&laquo;Сбербанк Онлайн&raquo;.
                                                </div>
                                                <i class="helper_arr"></i>
                                            </div><!-- // b-helper -->

                                            <div class="b-reg-helper" id="NewPasswordHelper" style="display:none">
                                                <h3 class="reg-helper_head">Краткие правила</h3>
                                                <div class="reg-helper_inner">
                                                    <div class="reg-helper_item">
                                                        <h4 class="reg-helper_title">sberbank21</h4>
                                                        <p class="reg-helper_description">Пароль должен содержать буквы и&nbsp;цифры. Минимум 8&nbsp;символов.</p>
                                                        <img class="reg-helper_pic" src="${imagePath}/qwer.png" alt="" width="114" height="44"/>
                                                        <p class="reg_description">Нельзя использовать более 3-х символов подряд в&nbsp;одном ряду клавиатуры.</p>
                                                    </div>
                                                    <div class="reg-helper_item">
                                                        <h4 class="reg-helper_title">A=a</h4>
                                                        <p class="reg-helper_description">Пароль не чувствителен к регистру</p>
                                                    </div>
                                                </div>
                                                <div class="reg-helper_extend b-trigger" data-show="showRules">
                                                    <span class="dash">Правила полностью</span>
                                                </div>
                                            </div><!-- // b-reg-helper -->

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
                                </div><!-- // b-page -->

                            </div>
                        </div>
                    </div>
                </div><!-- // b-slider -->

            </div><!-- // g-content -->
        </div><!-- // g-main-inner -->

        <div class="b-svg" id="Svg"></div><!-- // b-svg -->

    </div><!-- // g-main -->
    <div class="g-footer">
        <div class="g-footer_inner">

            <div class="b-copy">
                <div class="copy_name">© 1997—2015 Сбербанк России</div>
                <div class="copy_description">Россия, Москва, 117997, ул. Вавилова, д. 19,<br/>Генеральная лицензия на осуществление банковских операций от 8 августа 2012 года<br/>Регистрационный номер — 1481</div>
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

<script type="text/javascript">
    <%-- формирование основных данных для ФМ --%>
    new RSAObject().toHiddenParameters();
</script>
<c:if test="${phiz:isFSORSAActive()}">
    <%-- формирование значения поля deviceTokenFSO для ФМ --%>
    <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso.jsp"%>
</c:if>
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

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
</body>
</html>