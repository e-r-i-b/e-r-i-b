<%--
  User: basharin
  Date: 01.03.2014

   страница с сообщением, что пароль устарел
  отсылаем пароль в CSABack
  после того как пароль изменен продолжаем загрузку.
--%>


<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${CheckOldPasswordForm}"/>
<c:set var="urlSber" value="${phiz:calculateActionURL(pageContext,'/private/accounts.do')}"/>
<c:set var="urlLogoff" value="${phiz:calculateActionURL(pageContext,'/logoff.do?toLogin=true')}"/>

<!DOCTYPE html>
<!--[if IE 6 ]><html lang="ru-RU" class="ie678 ie6"><![endif]-->
<!--[if IE 7 ]><html lang="ru-RU" class="ie678 ie7"><![endif]-->
<!--[if IE 8 ]><html lang="ru-RU" class="ie678 ie8"><![endif]-->
<!--[if IE 9 ]><html lang="ru-RU" class="ie9"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--><html lang="ru-RU"><!--<![endif]-->
<head>
    <meta charset="windows-1251">
    <title><bean:message bundle="commonBundle" key="application.title"/></title>
    <link rel="icon" type="image/x-icon" href="${imagePath}/favicon.ico"/>
    <link rel="stylesheet" href="${skinUrl}/selfRegistration/common.css"/>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/svg.js"></script>
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
            <a class="logo_link" href="${urlSber}">Сбербанк Онлайн</a>
        </div><!-- // b-logo -->

        <div class="b-phones">
            <span class="phones_item">+7 (495) <b>500-55-50</b></span>
            <span class="phones_item">8 (800) <b>555-55-50</b></span>
        </div><!-- // b-phones -->

        <div class="b-login">
            <a class="login_out" href="${urlLogoff}">
                <span class="link">Выход</span> <i class="login_out-pic"></i>
            </a>
        </div>

    </div><!-- // g-header_inner -->
</div><!-- // g-header -->
<div class="g-main">
<div class="g-main_inner">


<div class="g-content">

	<div class="b-page b-reg">
        <div class="page_head">
            <div class="page_head-wrapper">

                <h2 class="b-title">
                    <span class="title_text">Установка нового пароля</span>
                </h2><!-- // b-title -->

            </div>
        </div>
        <div class="page_inner">
            <div class="page_inner-wrapper">

                <form class="b-form b-auth-form" id="PasswordData" onclick="return{step:3,  actionPath:'async/checkOldPassword.do', captcha: false, operation: 'changePassword'}">
                    <div class="form_inner">

                        <div class="b-field" id="Password" onclick="return{type: 'password', edit:['lowercase'], valid:{required: true, password: true, maxLen: 30, minLen: 8, letter: true, number: true, key3: true, let3: true}}">
                            <div class="field_inner">
                                <input class="field_input" id="pswd" name="pswd" type="password" placeholder="Новый пароль" autocomplete="off"/>
                            </div>
                        </div><!-- // b-field -->

                        <div class="b-field" id="RepeatPassword" onclick="return{type: 'password', edit:['lowercase'], valid:{required: true, maxLen: 30, minLen: 8}, compare:'Password'}">
                            <div class="field_inner">
                                <input class="field_input" id="pswd2" name="pswd2" type="password" placeholder="Повторите пароль" autocomplete="off"/>
                            </div>
                        </div><!-- // b-field -->


                        <div class="b-btn disabled" id="PasswordDataSubmit">
                            <div class="btn_text">Продолжить</div>
                            <i class="btn_crn"></i>
                            <input class="btn_hidden" type="submit"/>
                        </div><!-- // b-btn -->

                        <div class="b-back">
                            <a class="back_text" href="${urlLogoff}">Отменить</a>
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
							<img class="reg-helper_pic" src="${imagePath}/selfRegistration/qwer.png" alt="" width="114" height="44"/>
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
					<h3 class="rules_title">Правила составления пароля</h3>
						<ul class="rules_list">
							<li class="rules_item">Длина пароля должна быть не менее 8 символов.</li>
							<li class="rules_item">Должен содержать минимум 1 цифру.</li>
							<li class="rules_item">Не должен содержать более 3-х одинаковых символов подряд, и более 3-х символов, расположенных рядом в одном ряду клавиатуры.</li>
							<li class="rules_item">Должен отличаться от логина.</li>
							<li class="rules_item">Может содержать элементы пунктуации из списка « – ! @ # $ % ^ & * ( ) _ - + : ; , . »</li>
							<li class="rules_item">Не должен повторять старые пароли за последние 3 месяца.</li>
						</ul>

					<div class="b-return b-trigger" data-show="showHelper"><i class="return_pic"></i></div>

				</div><!-- // b-rules -->

			</div>
			<div class="page_content-errors" style="display:none">

			</div>
		</div>
    </div><!-- // b-page -->



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