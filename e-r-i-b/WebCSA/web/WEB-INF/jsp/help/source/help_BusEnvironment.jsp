<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>Помощь. Регистарция в портале "Деловая среда"</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">

<link type="text/css" rel="stylesheet" href="${globalUrl}/commonSkin/help.css"/>
<link rel="stylesheet" type="text/css" href="${skinUrl}/roundBorder.css"/>
<link rel="stylesheet" type="text/css" href="${skinUrl}/help.css"/>
<link rel="icon" type="image/x-icon" href="${skinUrl}/images/favicon.ico"/>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.ifixpng.js"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/layout.js"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>

<!--[if IE 6]>
		<c:if test="${contextName eq 'PhizIC'}">
         	<link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
        </c:if>
	<link type="text/css" rel="stylesheet" href="${skinUrl}/ie.css"/>
<![endif]-->

<script type="text/javascript">
var skinUrl = '${skinUrl}';
var globalUrl = '${globalUrl}';
var scroll = new DivFloat("help-left-section");
scroll.doOnScroll();
</script>

<%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>
</head>
<body >
<tiles:insert definition="googleTagManager"/>

<div class="help-container">
        <div id="help-header">
            <p class="helpTitle">Справочное руководство по Сбербанк Онлайн</p>
        </div>

<!-- end help-header -->
<div class="clear"></div>

<div id="help-content">
	<div id="help-left-section">
        <p class="sidebarTitle">разделы руководства</p>


								<ul class="help-menu">
									<li><a class="active-menu" href="/CSAFront/help.do?id=/businessEnvironment/login">Регистрация в портале "Деловая среда"</a></li>
									<li><a href="#p7">События в системе</a></li>
									<li><a href="#p5">Информация о системе</a></li>
								</ul>
									
						

		</div>
	<!-- end help-left-section -->
	<div id="help-workspace">

        <tiles:insert definition="roundBorder" flush="false">
                    <tiles:put name="title" value="Регистрация в портале «Деловая среда»"/>
                    <tiles:put name="data">
                    
<p>С помощью системы "Сбербанк ОнЛайн" Вы можете подтвердить свою регистрацию в портале "Деловая среда". </p>
<p>Для этого Вам необходимо при создании профиля на сайте "Деловая среда" выбрать способ подтверждения  -  через <b>Сбербанк Онлайн</b>. В результате Вы перейдете на страницу подтверждения Вашего профиля, на которой заполните следующие поля:</p>
<ul>
<li>В поле "Идентификатор или логин" введите Ваш идентификатор пользователя, который Вы используете для входа в систему "Сбербанк ОнЛайн";</li>
<li>В поле "Пароль" укажите Ваш пароль, который Вы используете при входе в систему "Сбербанк ОнЛайн". </li>
</ul>
<p>После того как поля заполнены, нажмите на кнопку <b>Продолжить</b>, и Вы перейдете на страницу подтверждения введенных данных.</p>
<p>Если Вы передумали подтверждать профиль, нажмите на ссылку <b>Назад</b>, и Вы вернетесь на сайт портала "Деловая среда"". </p>
<a id="p2"> </a>
<p>Далее Вам необходимо дать согласие на передачу Ваших персональных данных в портал "Деловая среда". Для этого ознакомьтесь с текстом соглашения, щелкнув по соответствующей ссылке, затем установите галочку в поле "Согласен с передачей данных из "Сбербанк Онлайн" в портал "Деловая среда".</p>
<p> После этого Вам необходимо выбрать, каким способом Вы хотите подтвердить создание профиля:</p>
<ul>
<li>для подтверждения SMS-паролем, нажмите на кнопку <b>Подтвердить по SMS</b>;
</li>
<li>если Вы хотите подтвердить операцию паролем с чека, распечатанного в банкомате, нажмите на ссылку <b>Подтвердить чеком</b>.
</li>
</ul>
<p> Затем откроется всплывающее окно, в котором введите нужный пароль и нажмите на кнопку <b>Подтвердить</b>. В результате Вы перейдете в портал "Деловая среда".</p>


<h2><a id="p7">События в системе</a></h2>

<p>С помощью блока <b>События</b> Вы можете просмотреть список всех событий банка, а также полный текст любого события. Данный пункт меню отображается на странице входа и на главной странице системы.</p>
<div class="help-important">
<p>
<span class="help-attention">Примечание</span>: события высокой важности выделены жирным шрифтом.</p>
</div>								
<p>Для того чтобы просмотреть событие, щелкните по его заголовку в блоке <b>События</b>.</p>
									<!-- <p>Если Вам нужно посмотреть события дня, то щелкните по ссылке <b>События дня</b>. Откроется страница со списком событий текущего дня, а также можно с помощью поиска просмотреть события за другую дату.</p> -->
									<p>Если Вы хотите просмотреть все события банка, нажмите на ссылку <b>Все события</b> 
									в блоке <b>События</b>. Откроется страница со списком событий, на которой Вы можете выполнить 
									следующие действия: просмотреть интересующее событие, просмотреть все события банка, а также 
									воспользоваться поиском событий.</p>
									<p>Для каждого события в списке отображается дата его публикации, заголовок и краткий текст события. </p>
	<h2><a id="p5">Информация о системе</a></h2>
<p>На странице входа в систему Вы также можете выполнить следующие действия:</p>		
<ul>
<li>
Для просмотра информации о мерах безопасности при использовании системы "Сбербанк ОнЛайн" в блоке внизу страницы перейдите на вкладку <b>Меры предосторожности</b>. 
</li>
<li>
Для получения информации о том, какие возможности есть у системы "Сбербанк ОнЛайн" в блоке внизу страницы перейдите на вкладку <b>Возможности</b>.
</li>
<li>
Если Вы хотите получить информацию о приложениях системы для мобильных устройств и узнать ссылки на их скачивание, то перейдите на вкладку <b>Мобильные приложения</b>.
</li>
</ul>


	<!-- end help-workspace -->
</div>
<!-- end help-content -->

<div class="clear"></div>

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
</body>