<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<head>
<title>Помощь. Кредиты</title>
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
										<c:if test="${phiz:impliesService('RemoteConnectionUDBOClaimRemoteConnectionUDBOClaim')}">
										<li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">Больше Сбербанк Онлайн</a></li>
										</c:if>
									<li><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a>
											<ul>
											<li><a href="/PhizIC/help.do?id=/private/loans/info">Информация по кредиту</a></li>
											<li><a href="/PhizIC/help.do?id=/private/loan/processing/choice">Оформить заявку на кредит</a></li>
													<c:if test="${phiz:impliesService('CreditReportOperation')}">
													<li><a href="/PhizIC/help.do?id=/private/credit/report">Кредитная история</a></li>
													<!--<li><a href="/PhizIC/help.do?id=/private/payments/creditReportPayment/edit/CreditReportPayment/null">Запрос кредитной истории</a></li> -->
													</c:if>
											</ul>
									</li>
									<li><a href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a></li>
									<li><a href="/PhizIC/help.do?id=/private/security/list ">Сертификаты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a></li>									
									<li><a href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a></li>
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
        <div class="contextTitle">Кредиты</div>   
										<p>Пункт главного меню <b>Кредиты</b> предназначен для 
										более удобной работы с Вашими кредитами, полученными в 
										Сбербанке России, а также с оформленными заявками на кредит. 
										<c:if test="${phiz:impliesService('CreditReportOperation')}"> Кроме того, Вы можете ознакомиться с Вашей кредитной историей.     
										</c:if>.</p>
										<p> Если Вы заполняли в "Сбербанк Онлайн" расширенную анкету для получения кредита, то над Вашими кредитами будет отображаться список оформленных Вами заявок.</p>
										<p> Для каждой заявки в списке будет показано ее наименование, статус, дата оформления и сумма запрашиваемого кредита.</p>
										<c:if test="${phiz:impliesService('CreditReportOperation')}">
										<p>Сервис <b>Кредитная история</b> позволяет Вам получить документ, содержащий информацию о состоянии всех Ваших кредитных обязательств в различных банках. Оформление запроса кредитной истории подробно описано в разделе контекстной справки <a href="/PhizIC/help.do?id=/private/credit/report">Кредитная история</a>.</p>
										</c:if>
										<p> Также на этой странице Вы можете просмотреть кредиты, полученные в Сбербанке.</p>
										<p>По каждому кредиту в списке будет показана следующая 
										информация: название кредита, рекомендованный платеж, Ваша роль в кредитном договоре (заемщик, 
										созаемщик, поручитель) и дата платежа по кредиту. Если Вы 
										просрочили платеж, то поле "Внести до" с датой платежа для данного 
										кредита не будет отображаться.</p>
											<div class="help-important">
											<p>
												<span class="help-attention">Примечание</span>: если сумма платежа по кредиту выделена в списке красным цветом, 
												это означает, что Вы просрочили платежи по данному кредиту, 
												и Вам необходимо погасить задолженность. 
											</p>
											</div>
											

										<!--<p>Для того чтобы добавить интересующий Вас кредит на главную 
										страницу, Вам необходимо установить галочку в поле "Показывать 
										на главной". В результате отмеченный кредит будет всегда 
										отображаться на главной странице системы.</p>-->
										<p>По каждому действующему кредиту Вы можете просмотреть список трех последних операций, 
										которые Вы совершили в системе. Для того чтобы развернуть список последних операций, рядом с интересующим кредитом щелкните по ссылке <b>Показать операции</b>. </p>
										<p>В этом списке по каждой операции 
										Вы можете увидеть наименование операции, дату выполнения операции 
										и сумму, на которую она совершена.</p>
										<p>Если Вы не хотите, чтобы на данной странице отображались операции 
										по интересующему Вас кредиту, то щелкните по ссылке <b>Скрыть операции</b>.</p>
										<p>Если Вы хотите <b>подать в банк заявку на получение кредита</b>, то вверху страницы нажмите на ссылку <b>Взять кредит в Сбербанке</b>. Оформление заявки на кредит подробно описано в разделе контекстной справки <a href="/PhizIC/help.do?id=/private/loan/processing/choice">Оформить заявку на кредит</a>.</p>
											<c:if test="${phiz:impliesService('CreditReportOperation')}">
											<p>Для того чтобы отправить <b>запрос на получение кредитной истории</b> или <b>просмотреть кредитный отчет</b>, нажмите на ссылку <b>Кредитная история</b> вверху страницы.</p>
											</c:if>
										
										<p>На данной странице по каждому кредиту Вы можете просмотреть график платежей и внести платеж по кредиту. Для этого нажмите на кнопку <b>Операции</b> и щелкните в списке по нужной ссылке:</p>
									<ul class="page-content">   
									<li><a href="/PhizIC/help.do?id=/private/loans/info#p2"><SPAN CLASS="toc2">График</SPAN></A></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/payment/LoanPayment/loan-payment-extended-fields"><SPAN CLASS="toc2">Внести платеж</SPAN></A></li>
									</ul>
									<p>Для кредитов, по которым предусмотрены аннуитетные платежи, Вы не можете внести платеж, данная операция недоступна. По таким кредитам списание осуществляется автоматически с вклада или карты, указанной в автоплатеже на списание средств по погашению кредита. Для аннуитетных кредитов вместо ссылки 
									<b>Внести платеж</b> Вы увидите ссылку <b>Как оплатить?</b>, при переходе по которой будет показано информационное сообщение.</p>
								<p>Также с этой страницы Вы можете перейти к странице с детальной информацией 
								по кредиту, на которой можно распечатать график платежей, просмотреть дополнительную информацию 
								по кредиту, посмотреть тариф на обслуживание в системе "Сбербанк Онлайн" и т.д. 
								Для того чтобы перейти к данной странице, щелкните по названию или иконке интересующего Вас 
								кредита в списке.</p>
								<p>В случае если Вы хотите просмотреть список 
								Ваших погашенных кредитов, нажмите на ссылку <b>Закрытые кредиты</b>, которая находится под списком действующих кредитов. В 
								результате откроется список закрытых кредитов. 
								</p>
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>В системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи 
								персонального консультанта, который ответит на все Ваши вопросы. Чтобы запустить 
								помощника, нажмите на ссылку <b>Часто задаваемые вопросы</b> в боковом меню. В 
								результате откроется окно, в котором содержатся ответы на часто задаваемые 
								вопросы по работе с системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы 
								консультант отображался на страницах системы, то измените настройки отображения 
								помощника, щелкнув по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. 
								Для того чтобы скрыть помощника, Вам нужно в блоке <b>Дополнительные настройки</b> 
								убрать галочку в поле "Отображать персонального консультанта" и нажать на кнопку <b>Сохранить</b>. --></p>

								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
										<li><span> <a href="/PhizIC/help.do?id=/private/loans/info">Информация по кредиту</a></li>
										<li><span> <a href="/PhizIC/help.do?id=/private/cards/info">Информация по карте</a></li>
									</ul>
								</div>

</div>
	<!-- end help-workspace -->
</div>
<!-- end help-content -->

<div class="clear"></div>

</body>					