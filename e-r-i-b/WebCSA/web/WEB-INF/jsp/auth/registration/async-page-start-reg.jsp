<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
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
<c:set var="form" value="${RegistrationForm}"/>
<input type="hidden" name="page_type" value="registration"/>
<c:set var="imagePath" value="${skinUrl}/skins/sbrf/images/selfRegistration"/>
<head>
    <meta charset="windows-1251">
    <title><bean:message bundle="commonBundle" key="application.title"/></title>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/skins/sbrf/images/favicon.ico"/>
    <link rel="stylesheet" href="${skinUrl}/skins/sbrf/selfRegistration/common.css"/>
    <script type="text/javascript" src="${skinUrl}/scripts/selfRegistration/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/selfRegistration/main.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>
</head>
<body onclick="return{token:'xxxxxxxxx'}">
<tiles:insert definition="googleTagManager"/>
<c:if test="${form.hintDelay != 0}">
    <input type="hidden" name="hintDelay" value="${form.hintDelay}"/>
</c:if>
<div class="g-wrapper">
<div class="g-header">
    <div class="g-header_inner">

        <div class="b-logo">
            <a class="logo_link" href="${csa:calculateActionURL(pageContext, 'index')}">Сбербанк Онлайн</a>
        </div>
        <!-- // b-logo -->

        <div class="b-phones">
            <a href="tel:+74955005550" class="phones_item">+7 (495) <b>500-55-50</b></a>
            <a href="tel:88005555550" class="phones_item">8 (800) <b>555-55-50</b></a>
        </div>
        <!-- // b-phones -->

        <div class="b-login">
            <a class="login_in" href="${csa:calculateActionURL(pageContext, 'index')}">Войти в Сбербанк Онлайн</a>
        </div>
        <!-- // b-login -->

    </div>
    <!-- // g-header_inner -->
</div>
<!-- // g-header -->
<c:if test="${csa:isPromoBlockEnabled()}">
    <div class="b-promo">
        <img class="promo_pic"  src="${imagePath}/b-promo.jpg" alt=""/>
    </div><!-- // b-promo -->
</c:if>


<div class="g-main">
    <div class="g-main_inner">

        <div class="b-promo-expander" <c:if test="${!csa:isPromoBlockEnabled()}"> style="visibility: hidden;"</c:if>     >
            <i class="promo-expander_pic"></i>

            <div class="promo-expander_show">Показать преимущества</div>
        </div>
        <!-- // b-steps -->

        <div class="b-steps">
            <div class="steps_list">
                <div class="steps_item first current">
                    <span class="steps_text">Проверка карты</span>
                    <i class="steps_border"></i>
                </div>
                <div class="steps_item">
                    <span class="steps_text">Подтверждение по SMS</span>
                    <i class="steps_border"></i>
                </div>
                <div class="steps_item last">
                    <span class="steps_text">Логин и пароль</span>
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

        <div class="b-slider" onclick="return{start: 0}">
            <div class="slider_outer">
                <div class="slider_inner">
                    <div class="slider_page" id="SliderCardRegistration" style="visibility:visible">

                        <div class="b-page b-card">
                            <div class="page_head">
                                <div class="page_head-wrapper">
                                    <h1 class="page_about">Регистрация в&nbsp;Сбербанк Онлайн</h1>

                                    <h2 class="b-title">
                                        <span class="title_text">Введите номер карты</span>
                                    </h2><!-- // b-title -->

                                </div>
                            </div>
                            <div class="page_inner">
                                <div class="page_inner-wrapper">

                                    <form class="b-form b-card-form" id="Card"
                                          onclick="return{step:1, actionPath:'${csa:calculateActionURL(pageContext, form.actionPath)}', captcha: ${csa:isActiveCaptha(pageContext.request, "captchaServlet")}, operation: 'begin'}">
                                        <div class="form_inner">

                                            <div class="form_text">Номер карты расположен на&nbsp;ее лицевой стороне</div>

                                            <div class="b-field" id="CardNumber"
                                                 onclick="return{type: 'card', replace: /[^\d]/gi, edit:['card'], valid:{required: true, minLen: 15}}">
                                                <div class="field_inner">
                                                    <input class="field_input" id="CardNumberInput" name="field(cardNumber)" type="text"
                                                           placeholder="Введите номер карты" autocomplete="off"/>
                                                </div>
                                            </div>
                                            <!-- // b-field -->

                                            <div class="b-captcha" id="CardCaptcha"
                                                 onclick="return{valid:{required: true}, replace: /[^\d\wа-яА-Я]/gi, url: '/${csa:loginContextName()}/captcha.png?=<%=java.lang.Math.round(java.lang.Math.random() * 100000)%>'}"
                                                 style="display:none">
                                                <div class="captcha_pic"><img class="captcha_image" src="" alt=""/></div>
                                                <div class="captcha_refresh">
                                                    <div class="captcha_ico"></div>
                                                    <span class="lnk">Обновить код</span>
                                                </div>
                                                <div class="field_inner">
                                                    <input class="field_input" id="CardCaptchaInput" name="field(captchaCode)" type="text"
                                                           placeholder="Код с картинки" autocomplete="off"/>
                                                </div>
                                            </div>
                                            <!-- // b-captcha -->

                                            <div class="b-btn disabled" id="CardSubmit">
                                                <div class="btn_text">Продолжить</div>
                                                <i class="btn_crn"></i>
                                                <input class="btn_hidden" type="submit"/>
                                            </div>
                                            <!-- // b-btn -->

                                        </div>
                                        <i class="form_pic"></i>
                                    </form>
                                    <!-- //b-card-form -->

                                </div>
                            </div>
                            <div class="page_content">
                                <div class="page_content-wrapper">

                                    <div class="b-card-helper">
                                        <h3 class="card-helper_title">Проверьте карту и&nbsp;мобильный телефон</h3>

                                        <div class="card-helper_inner">
                                            <div class="card-helper_item">
                                                <img class="card-helper_pic" src="${imagePath}/card.png" alt="" width="55" height="53"/>

                                                <p class="card-helper_description">У&nbsp;вас в&nbsp;руках должна быть основная действующая
                                                                                   карта Сбербанка</p>
                                            </div>
                                            <div class="card-helper_item">
                                                <img class="card-helper_pic" src="${imagePath}/sms_phones.png" alt="" width="50" height="53"/>

                                                <p class="card-helper_description">Телефон, на&nbsp;который подключен Мобильный банк, должен быть под рукой&nbsp;&mdash; на&nbsp;него придет SMS-пароль.
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // b-card-helper -->

                                </div>
                                <div class="page_content-rules" style="display:none">

                                </div>
                                <div class="page_content-errors" style="display:none">

                                </div>
                            </div>
                        </div>
                        <!-- // b-page -->

                    </div>
                    <div class="slider_page" id="SliderSmsConfirm" style="visibility:hidden">

                        <div class="b-page b-sms">
                            <div class="page_head">
                                <div class="page_head-wrapper">
                                    <h1 class="page_about">Регистрация в&nbsp;Сбербанк Онлайн</h1>

                                    <h2 class="b-title">
										<span class="title_text">Введите SMS-пароль
											<i class="b-timer"
											   onclick="return{redirect: '${csa:calculateActionURL(pageContext,'/async/page/timeout')}'}">120</i>
										</span>
                                    </h2><!-- // b-title -->

                                </div>
                            </div>
                            <div class="page_inner">
                                <div class="page_inner-wrapper">

                                    <form class="b-form b-sms-form" id="SmsConfirm"
                                          onclick="return{step: 2, actionPath:'${csa:calculateActionURL(pageContext, form.actionPath)}', operation: 'next'}">
                                        <div class="form_inner">

                                            <div class="b-field" id="Sms"
                                                 onclick="return{type: 'sms', replace: /[^\d]/gi, valid:{required: true, maxLen: 5, minLen: 5}}">
                                                <div class="field_inner">
                                                    <input class="field_input" id="SmsInput" name="field(confirmPassword)" type="text"
                                                           placeholder="SMS-пароль" autocomplete="off" maxlength="5"/>
                                                </div>
                                            </div>
                                            <!-- // b-field -->

                                            <div class="b-btn disabled" id="SmsConfirmSubmit">
                                                <div class="btn_text">Продолжить</div>
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
                                                <img class="sms-helper_pic" src="${imagePath}/gray_phones.png" alt="" width="58" height="53"/>
                                                <h4 class="sms-helper_title">Номер телефона должен быть привязан к&nbsp;карте</h4>

                                                <p class="sms-helper_description">Отправьте слово &laquo;СПРАВКА&raquo; на&nbsp;номер 900, в&nbsp;ответном
                                                                                  сообщении вам придет информация о&nbsp;наличии подключения
                                                                                  к&nbsp;Мобильному банку</p>
                                            </div>
                                            <div class="sms-helper_item">
                                                <img class="sms-helper_pic" src="${imagePath}/card.png" alt="" width="55" height="53"/>
                                                <h4 class="sms-helper_title">Карта должна быть выпущена Сбербанком</h4>

                                                <p class="sms-helper_description">Убедитесь, что на&nbsp;лицевой стороне вашей карты расположен логотип Сбербанка</p>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // b-sms_helper -->

                                </div>
                                <div class="page_content-rules" style="display:none">

                                </div>
                                <div class="page_content-errors" style="display:none">

                                    <div class="b-message" id="IncorrectSMS">
                                        <h3 class="sys-error_title">Вы&nbsp;ввели неверный SMS-пароль, у&nbsp;вас осталось {param}</h3>

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
                    <div class="slider_page" id="SliderAuthData" style="visibility:hidden">

                        <div class="b-page b-reg">
                            <div class="page_head">
                                <div class="page_head-wrapper">
                                    <h1 class="page_about">Регистрация в&nbsp;Сбербанк Онлайн</h1>

                                    <h2 class="b-title">
                                        <span class="title_text">Cоздайте логин и&nbsp;пароль</span>
                                    </h2><!-- // b-title -->

                                </div>
                            </div>
                            <div class="page_inner">
                                <div class="page_inner-wrapper">

                                    <form class="b-form b-auth-form" id="AuthData" onclick="return{step: 3, actionPath:'${csa:calculateActionURL(pageContext, form.actionPath)}', captcha: false, operation: 'next'}">
                                        <div class="form_inner">

                                            <div class="b-field" id="Login"
                                                    onclick="return{type: 'login', valid:{required: true, login: true, maxLen: 30, minLen: ${form.minLoginLength}, letter: true, number: true, key3: true, let3: true } <c:if test="${csa:isAsyncCheckingFieldsEnabled()}">, avail: {path: '${csa:calculateActionURL(pageContext, form.actionPath)}', data:{operation: 'checkLogin'}} </c:if>}"
                                                >
                                                <div class="field_inner">
                                                    <input class="field_input" id="LoginInput" name="field(login)" type="text"
                                                           placeholder="Новый логин" autocomplete="off"/>
                                                </div>
                                            </div>
                                            <!-- // b-field -->

                                            <div class="b-field" id="Password"
                                                    onclick="return{type: 'password', valid:{required: true, password: true, maxLen: 30, minLen: 8, letter: true, number: true, key3: true, let3: true}, differ: 'Login' <c:if test="${csa:isAsyncCheckingFieldsEnabled()}">,avail: {path: '${csa:calculateActionURL(pageContext, form.actionPath)}', data:{operation: 'checkPassword'}}</c:if>}"
                                                 >
                                                <div class="field_inner">
                                                    <input class="field_input" id="PasswordInput" name="field(password)" type="password"
                                                           placeholder="Введите новый пароль" autocomplete="off"/>
                                                </div>
                                            </div>
                                            <!-- // b-field -->

                                            <div class="b-field" id="RepeatPassword"
                                                 onclick="return{type: 'password', valid:{required: true, maxLen: 30, minLen: 8}, compare:'Password'}">
                                                <div class="field_inner">
                                                    <input class="field_input" id="RepeatPasswordInput" name="field(confirmPassword)"
                                                           type="password" placeholder="Повторите пароль" autocomplete="off"/>
                                                </div>
                                            </div>
                                            <!-- // b-field -->

                                            <div class="b-captcha" id="AuthDataCaptcha"
                                                 onclick="return{valid:{required: true}, replace: /[^\d\wа-яА-Я]/gi, url: '/${csa:loginContextName()}/registration/captcha.png?=<%=java.lang.Math.round(java.lang.Math.random() * 100000)%>'}"
                                                 style="display:none">
                                                <div class="captcha_pic"><img class="captcha_image" src="" alt=""/></div>
                                                <div class="captcha_refresh">
                                                    <div class="captcha_ico"></div>
                                                    <span class="lnk">Обновить код</span>
                                                </div>
                                                <div class="field_inner">
                                                    <input class="field_input" id="AuthDataCaptchaInput" name="field(captchaCode)"
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

                                        </div>
                                        <i class="form_pic"></i>
                                    </form>
                                    <!-- // b-sms-form -->

                                </div>
                            </div>
                            <div class="page_content">
                                <div class="page_content-wrapper">

                                    <div class="b-reg-helper">
                                        <h3 class="reg-helper_head">Краткие правила</h3>

                                        <div class="reg-helper_inner">
                                            <div class="reg-helper_item">
                                                <h4 class="reg-helper_title">sberbank21</h4>

                                                <p class="reg-helper_description">Логин и&nbsp;пароль должны содержать буквы и&nbsp;цифры.
                                                                                  Минимум 8&nbsp;символов.</p>
                                                <img class="reg-helper_pic" src="${imagePath}/qwer.png" alt="" width="114" height="44"/>

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

                                    </div>
                                    <!-- // b-rules -->

                                </div>
                                <div class="page_content-errors" style="display:none">

                                </div>
                            </div>
                        </div>
                        <!-- // b-page -->

                    </div>
                </div>
            </div>
        </div>
        <!-- // b-slider -->

        <div class="b-message" id="IsRegisteredUser" style="display:none">
            <div class="popup_inner">

                <div class="b-message">
                    <h1>Вы&nbsp;уже являетесь пользователем Сбербанк Онлайн</h1>
                    <div class="moved">
                        <p>Если вы&nbsp;помните свой логин и&nbsp;пароль, перейдите на&nbsp;страницу <a href="${csa:calculateActionURL(pageContext, '/index')}">входа в&nbsp;Сбербанк Онлайн</a>. </p>
                        <p><i class="positioned">либо</i>Если вы&nbsp;забыли пароль&nbsp;&mdash; воспользуйтесь <a href="${csa:calculateActionURL(pageContext, '/async/page/recover')}">восстановлением пароля</a>.<br/><a class="b-action" onclick="return {actionPath:'${csa:calculateActionURL(pageContext, form.actionPath)}', data:{'operation':'createNewLogin', 'token':'someToken'}}">Создайте новые логин и&nbsp;пароль</a>. Возможности Сбербанк Онлайн будут доступны с&nbsp;ограничениями. Ограничения можно снять, подтвердив любую операцию звонком в&nbsp;Контактный центр Сбербанка.</p>

                    </div>
                </div><!-- // b-message -->

            </div>
            <a class="popup_close"></a>
        </div><!-- // b-message -->

    </div>
    <!-- // g-content -->
    </div>
    <!-- // g-main-inner -->

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

    </div>
    <!-- // g-footer-inner -->
</div>
<!-- // g-footer -->
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

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
</body>
</html>