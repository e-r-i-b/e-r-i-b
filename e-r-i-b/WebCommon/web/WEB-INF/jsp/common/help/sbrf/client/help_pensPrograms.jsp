<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Пенсионные программы</title>
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

    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
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
									<!--<li><a href="/PhizIC/help.do?id=/login">Вход и регистрация</a></li>-->
									<li><a href="/PhizIC/help.do?id=/private/registration">Регистрация</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts">Главная</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments">Переводы и платежи</a></li>
									<li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">Больше Сбербанк Онлайн</a></li>
									<li><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a></li>
									<li><a href="/PhizIC/help.do?id=/private/security/list">Сертификаты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a></li>
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a>
										<ul>
											<li><a href="/PhizIC/help.do?id=/private/npf/view">Информация по программе НПФ Сбербанка</a></li>
											<li><a href="/PhizIC/help.do?id=/private/pfr/pfrHistoryFull">Виды и размеры пенсий</a></li>
											<li><a href="/PhizIC/help.do?id=/private/pfr/list">Выписка из Пенсионного фонда</a></li>
										   <li><a href="/PhizIC/help.do?id=/private/payments/payment/PFRStatementClaim">Заявка на получение выписки из ПФР</a></li>
										</ul>
									</li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">Страховые программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mail/sentList">Вопрос в Контактный центр банка</a></li>
									<li><a href="/PhizIC/help.do?id=/private/news/list">События</a></li>
									<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>

								</ul>

						<a href="/PhizIC/faq.do" class="faq-help">
                    <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
                </a>
            </div>
            <!-- end help-left-section -->
            <div id="help-workspace">
                <div class="contextTitle">Пенсионные программы</div>

									<p>В пункте меню <b>Прочее</b> - <b>Пенсионные программы</b> Вы можете
									просмотреть список Ваших пенсионных продуктов, оформленных в Негосударственном пенсионном фонде Сбербанка России или в Пенсионном фонде России. 
									Также в данном пункте меню Вы можете подать в банк заявку на получение выписки из ПФР, получить справку о видах и размерах пенсионных выплат, просмотреть список последних запросов выписки в ПФР и перейти к странице детальной информации по каждому из продуктов.</p>
									<p>В списке для каждого продукта 
									показана следующая информация: наименование, номер Вашего лицевого счета, открытого в ПФР или НПФ Сбербанка, статус программы, страховая компания и срок окончания страховки (для продуктов НПФ Сбербанка).</P>
									<p>Для того чтобы получить справку о видах и размерах пенсий, в блоке <b>ПФР</b> нажмите на ссылку <b>Виды и размеры пенсий</b>.</p>
							
									<p>На данной странице Вы можете просмотреть список трех последних запросов выписки из ПФР. Для того чтобы развернуть список 
									последних операций, в блоке <b>ПФР - Выписка о состоянии индивидуального лицевого счета</b> щелкните по ссылке <b>
									Показать операции</b>. </P>В этом 
									списке по каждой операции Вы можете увидеть ее
									наименование, дату и время 
									выполнения операции и ее статус.</P>
									<p>Если Вы не хотите, чтобы на данной 
									странице отображались запросы выписки, то 
									щелкните по ссылке <b>Скрыть операции</b>.</P>
									<p>По каждому продукту из списка Вы можете 
									пройти на страницу <b>с детальной 
									информацией</b>. Для этого выберите интересующую Вас 
									пенсионную программу в списке и щелкните по ее названию. В результате 
									система откроет страницу с подробной 
									информацией по данной программе. </P>
										
								<p>Если Вы хотите подать <b>заявку на получение выписки из ПФР</b>, то вверху страницы нажмите на соответствующую ссылку.</p>
								<p>Для получения подробной информации по выполнению операций на любой странице системы нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>

										<p>В системе "Сбербанк Онлайн" на каждой странице можно 
								обратиться к помощи персонального консультанта, который 
								ответит на все Ваши вопросы. Чтобы запустить помощника, 
								нажмите на ссылку <b>Часто задаваемые вопросы</b> в боковом 
								меню. В результате откроется окно, в котором содержатся 
								ответы на часто задаваемые вопросы по работе с системой 
								"Сбербанк Онлайн". </p>
								<!-- Если Вы не хотите, чтобы консультант 
								отображался на страницах системы, то измените настройки 
								отображения помощника, щелкнув по кнопке <b>Настройки</b> - 
								<b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы скрыть помощника, 
								Вам нужно в блоке <b>Дополнительные настройки</b> убрать галочку 
								в поле "Отображать персонального консультанта" и нажать на кнопку 
								<b>Сохранить</b>. --></p>
									<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
											<li><span></span><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
									<li><span><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									</ul>
								</div>
								<!-- <div class="help-to-top"><a href="#top">в начало
                        раздела</a></div> -->
                    <div class="clear"></div>

            </div>
            <!-- end help-workspace -->
        </div>
        <!-- end help-content -->

        <div class="clear"></div>
    </div>
</body>