<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Заявка на получение выписки из Пенсионного фонда России</title>
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
											<li><a href="/PhizIC/help.do?id=/private/npf/view">Информация по программе НПФ Сбербанка</a></li>
											<li><a href="/PhizIC/help.do?id=/private/pfr/pfrHistoryFull">Виды и размеры пенсий</a></li>
											<li><a href="/PhizIC/help.do?id=/private/pfr/list">Выписка из Пенсионного фонда</a></li>
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/payments/payment/PFRStatementClaim">Заявка на получение выписки из ПФР</a></li>
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
                <div class="contextTitle">Заявка на получение выписки из ПФР</div>

								<ul class="page-content">
									<li><a href="#p5">Отправить запрос</a></li>
									<li><a href="#p6">Подтвердить запрос</a></li>
									<li><a href="#p7">Просмотреть запрос</a></li>
								</ul>
									<p>С помощью системы "Сбербанк Онлайн" Вы можете отправить
									в Пенсионный фонд России запрос на получение выписки по Вашему лицевому 
									счету.</p>
																									<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" Вы можете переходить на нужную страницу не только с помощью 
										пунктов верхнего меню, ссылок бокового меню, но и с помощью ссылок, расположенных под главным меню. Данные 
										ссылки показывают путь от главной страницы до той, с которой Вы перешли на текущую страницу. Вы можете использовать эти ссылки для 
										перехода на интересующую страницу.  
									</p>
								</div>
								<h3><a id="p5">Отправить запрос</a></h3>

									<p>Для того чтобы отправить запрос, на странице <b>Выписка о состоянии индивидуального лицевого счета</b>
									нажмите на кнопку <b>Получить выписку</b> или нажмите на кнопку <b>Операции с пенсионным фондом</b>, затем перейдите по ссылке <b>Получить выписку</b>. Откроется страница <b>Заявка на получение выписки из ПФР</b>, на которой Вам нужно нажать на кнопку <b>Подтвердить</b>.
									 В результате Вы перейдете на страницу подтверждения.</P>
									<p>Если Вы передумали запрашивать выписку, щелкните по ссылке <b>Отменить</b>,
									и Вы вернетесь на страницу <b>Переводы и платежи</b>.
									</p>
									<p>Если Вы хотите вернуться к выбору услуги, то нажмите на ссылку <b>Назад к выбору услуг</b>.</p>		
									<h3><a id="p6">Подтвердить запрос</a></h3>
									
									<p>После того как Вы нажали на кнопку <b>Подтвердить</b>,
									 Вы попадаете на страницу подтверждения. Здесь Вам будет показана 
									 заполненная форма запроса, 
									на которой Вам нужно проверить параметры Вашего запроса 
									 и выполнить одно из следующих действий:
									</p>

									<ul>
									<li><b>Подтвердить операцию</b>. Убедитесь, что вся информация указана 
									верно. Затем, для подтверждения операции, нажмите на 
											кнопку <b>Подтвердить</b>. В результате Вам 
									откроется страница просмотра заявки.
 
									</i>
									<li><b>Отменить операцию</b>. Если Вы передумали отправлять запрос,
									 то нажмите на ссылку <b>Отменить</b>. В результате Вы перейдете на 
									 страницу <b>Переводы и платежи</b>.</li>
										
									</ul>
										<div class="help-important">
										<p>
										<span class="help-attention">Обратите 
										внимание</span>: Вы можете контролировать процесс выполнения операции с помощью
										линии вверху формы, на которой будет выделено состояние операции на данный момент.
										Например, если Вы находитесь на странице подтверждения, то будет выделен отрезок 
										"Подтверждение".  
										</p>
										</div> 
										<h3><a id="p7">Просмотреть запрос</a></h3>

										<p>После подтверждения Вы попадаете на страницу просмотра, где 
										отображается заполненный документ. 
										Отследить состояние запроса Вы можете по его статусу (см. раздел контекстной справки <a href="#p4">Статусы запросов)</a>.</p>
										<p>После того как Ваш запрос будет исполнен, Вы сможете
										просмотреть выписку по Вашему лицевому счету. Для этого на странице <b>Выписка о состоянии индивидуального лицевого счета</b> щелкните по ссылке <b>Посмотреть выписку</b>.</p>
								
								<h2><a id="p3">Выписка по лицевому счету</a></h2>
										<p>После того как Вы нажали на ссылку <b>Посмотреть выписку</b>,
										откроется страница <b>Выписка из Пенсионного фонда</b>, на которой Вы
										можете просмотреть следующую информацию:</p>
										<ul>
										<li>Дату запроса, по которому сформирована выписка;
										</li>
										<li>Ниже отображается Ваша фамилия, имя и отчество,
										страховой номер индивидуального лицевого счета и детальная информация суммы накоплений
										за текущий год.
										</li>
										<li>Далее за каждый год отображается сумма средств на Вашем счете
										на начало года и сумма средств, поступивших за год. 
										<div class="help-important">
										<p>
										<span class="help-attention">Обратите 
										внимание</span>: сумма Ваших пенсионных накоплений рассчитывается следующим 
										образом: к
										сумме средств на счете на начало предыдущего года прибавляется
										сумма средств, поступивших в прошлом году. Например, сумма накоплений на начало 2010
										года будет получена сложением суммы средств на начало 2009 года и суммы средств, поступивших за 2009 год.
										</p>
										</div> 
										</li>
										
										</ul>
										<p>Если Вы хотите распечатать выписку, нажмите на кнопку <b>Печать</b>. Система выведет
										на экран печатную форму, которую Вы сможете распечатать на принтере.</p>
										<p>Для того чтобы закрыть выписку, щелкните по ссылке
										<b>Закрыть</b>.</p>

								<H2><a id="p4">Статусы запросов</a></h2>
										<p>В системе &quot;Сбербанк Онлайн&quot; Вы можете 
										отследить состояние Ваших запросов. Состояние - стадия обработки платежа или заявки. Запросы могут иметь
										следующие статусы:
										</p>
										<ul>
										<li>"Черновик" - документ создан, но не 
										отправлен на обработку в банк;
										</li>
										<li>"Исполняется банком" - документ подтвержден и 
										отправлен на обработку в банк;
										</li>
										<li>"Исполнен"  - документ успешно исполнен банком: выписка по счету сформирована;</li>
										<li>"Отказан" - Вам отказано в исполнении операции по какой-либо причине.</li>	
										</ul>
								<p>В системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, который 
								ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые вопросы</b> в боковом меню. 
								В результате откроется окно, в котором содержатся ответы на часто задаваемые вопросы по работе с системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы консультант отображался на страницах системы, то измените настройки отображения помощника, щелкнув 
								по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы скрыть помощника, Вам нужно в блоке <b>Дополнительные 
								настройки</b> убрать галочку в поле "Отображать персонального консультанта" и нажать на кнопку <b>Сохранить</b>. --></p>
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