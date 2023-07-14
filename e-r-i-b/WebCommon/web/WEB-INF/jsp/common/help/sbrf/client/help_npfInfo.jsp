<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Информация по программе НПФ Сбербанка</title>
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
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a>
										<ul>
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/npf/view">Информация по программе НПФ Сбербанка</a></li>
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

									</li>

								</ul>								

                             <a href="/PhizIC/faq.do" class="faq-help">
                    <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
                </a>
            </div>
            <!-- end help-left-section -->
            <div id="help-workspace">
                <div class="contextTitle">Информация по программе НПФ Сбербанка</div>

									<p>По каждой пенсионной программе, оформленной в Негосударственном пенсионном фонде Сбербанка, Вы можете 
									просмотреть детальную информацию. Для этого 
									в пункте главного меню <b>Пенсионные программы</b> щелкните по названию или номеру 
									выбранной программы. В результате откроется 
									страница детальной информации, на 
									которой будет показано название программы, номер Вашего индивидуального лицевого счета, статус программы и срок окончания ее действия. </P>
																	<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" 
										Вы можете переходить на нужную Вам страницу 
										не только с помощью пунктов верхнего меню, 
										ссылок бокового меню, но и с помощью ссылок, 
										расположенных под главным меню. Данные ссылки 
										показывают путь от главной страницы до той, на 
										которой Вы находитесь. Вы можете использовать 
										эти ссылки для перехода на интересующую Вас страницу.  
									</p>
								</div>
								<p>В блоке <b>Детальная информация</b> система выведет на экран следующие сведения:</P>
										<ul>
									<li><b>Название</b> - название программы, 
									которое Вы указываете самостоятельно, 
									например, "Страхование жизни жены". Для того чтобы его
									отредактировать, рядом с наименованием щелкните по кнопке <IMG border="0" src="${globalUrl}/commonSkin/images/pencil.gif" alt="">, введите название и нажмите на 
									кнопку <b>Сохранить</b>. Если Вы не укажете 
									название, то в списке для данной программы будет отображаться наименование, заданное по умолчанию.
									</li>
									<li><b>Страховая программа</b> - 
									название Вашей страховой программы;</li>
									<li><b>Страховая компания</b> - название компании, обеспечивающей страховые выплаты;
									</li>
									<li><b>Страховая сумма</b> - размер Вашей страховки; 
									</li>
									<li><b>Дата начала действия страховки</b> - дата, когда начала действовать Ваша страховка; 
									</li>
									<li><b>Срок окончания страховки</b> - дата, когда заканчивается договор страхования;
									</li>
									<li><b>Реквизиты договора</b> - номер и дата заключения договора страхования;
									</li>
									<li><b>Страховые риски</b> - список страховых рисков, от которых Вы застрахованы;
									</li>
									<li><b>Дополнительная информация</b> - дополнительная информация о программе пенсионного страхования;
									
									<li><b>Статус</b> - состояние, в котором находится программа на данный момент, например, "Действует";
									</li>
									<li> <b>№ СНИЛС</b> - номер Вашего индивидуального лицевого счета по договору пенсионного страхования;</li>
									</ul>
							<p>Также Вы можете <b>распечатать дополнительную информацию по программе пенсионного страхования</b>, для этого щелкните по ссылке <b>Печать</b>.</p>
								
									<p>Вы можете добавить данную страницу 
									 в Личное меню, что 
										позволит Вам перейти к ней с 
										любой страницы системы "Сбербанк 
										Онлайн", щелкнув по ссылке в боковом меню. 
										Для выполнения операции нажмите на 
										ссылку <b>Добавить в избранное</b>.
									</p>
						
								<p>В системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
								который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые вопросы</b> 
								в боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые вопросы по работе с 
								системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы консультант отображался на страницах системы, то измените 
								настройки отображения помощника, щелкнув по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы 
								скрыть помощника, Вам нужно в блоке <b>Дополнительные настройки</b> убрать галочку в поле "Отображать персонального 
								консультанта" и нажать на кнопку <b>Сохранить</b>. --></p>

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