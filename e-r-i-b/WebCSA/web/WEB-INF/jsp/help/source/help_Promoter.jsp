<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>Помощь. Регистрация участника программы продвижения</title>
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
									<li><a class="active-menu" href="/CSAFront/help.do?id=/index/login-form">Регистрация участника программы продвижения</a>
									<ul>
											<li><a href="#p2">Начало работы промоутера</a></a></li>
											<li><a href="#p3">Завершение работы промоутера</a></li>
									</ul>
									</li>
								</ul>

							

		
	</div>
	<!-- end help-left-section -->
	<div id="help-workspace">

        <div class="contextTitle">Регистрация участника программы продвижения</div> 
<p>Для продвижения услуги «Сбербанк ОнЛайн» Вы можете зарегистрироваться в системе в качестве участника программы продвижения и консультировать клиентов. Учет Вашей работоспособности будет определяться по количеству клиентов, подключенных к системе.</p>

<h2><a id="p2">Начало работы промоутера</a></h2>
<p>Для того чтобы отметиться в системе учета Вашего рабочего времени, Вам необходимо в адресную строку браузера ввести ссылку на страницу промоутера. Она должна выглядеть приблизительно так: http://имя_сервера/CSAFront/loginPromo.do. </p>
<p>Откроется страница регистрации и начала работы. На этой странице Вам необходимо заполнить следующие поля:</p>
<ul>
<li>В поле «Выберите канал» укажите из выпадающего списка канал (тип) доступа в систему. Например, если Вы сотрудник внутреннего структурного подразделения банка, то выберите значение «ВСП», если Вы работаете на предприятии и привлекаете клиентов, то выберите значение «B@W», если Вы работаете с массовым высокодоходным сегментом, то укажите значение «МВС».</li>
<li>В поле «Укажите Тербанк» выберите из выпадающего списка название территориального банка, которому подчиняется Ваше отделение банка.</li>
<li>В поле «Укажите код ОСБ» впишите номер отделения Сбербанка, в котором Вы работаете.</li>
<li>В поле «Укажите код ВСП» введите номер внутреннего структурного подразделения банка, клиентов которого Вы обслуживаете.</li>
<li>В поле «Идентификатор» укажите Ваш идентификатор пользователя.</li>
<div class="help-important">
<p>
<span class="help-attention">Примечание</span>: если Вы неправильно заполните параметры при входе в приложение, то учет привлеченных клиентов будет выполняться в адрес другого промоутера.</p>
</div>	
</ul>
<p>После того как все поля заполнены, нажмите на кнопку <b>Начать смену</b>.</p>
<p>В результате откроется страница входа клиентов в систему «Сбербанк ОнЛайн», с помощью которой Вы сможете консультировать клиентов, а также помогать новым клиентам регистрироваться в системе «Сбербанк ОнЛайн».</p>
<p>Если в процессе работы произошла перезагрузка компьютера или какие-либо другие сбои, то Вам необходимо возобновить работу в приложении. Для этого нажмите на ссылку <b>Продолжить смену</b>.</p>

<h2><a id="p3">Завершение работы промоутера</a></h2>
<p>Для того чтобы закончить работу в системе, Вам необходимо на странице входа в систему нажать на ссылку <b>Страница промоутера</b>. В результате откроется страница <b>Завершение работы</b>. На этой странице, чтобы закончить смену, нажмите на кнопку <b>Закрыть смену</b>.</p>





								</div>
	<!-- end help-workspace -->
</div>
<!-- end help-content -->

<div class="clear"></div>

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
</body>			