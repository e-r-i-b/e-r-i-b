<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Автоплатежи</title>
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
									<li><a href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">Страховые программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a>
									<ul>
											<li><a href="/PhizIC/help.do?id=/private/payments/internetShops/orderList">Мои интернет-заказы</a></li>
											<li><a href="/PhizIC/help.do?id=/private/loyalty/detail">Спасибо от Сбербанка</a></li>											
											<li><a href="/PhizIC/help.do?id=/private/graphics/finance">Мои финансы</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/favouriteLinks#p2">Избранное</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/PaymentsAndTemplates">Мои шаблоны</a></li>
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/favourite/list/AutoPayments">Мои автоплатежи</a></li>										
									</ul>
									<li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mail/sentList">Вопрос в Контактный центр банка</a></li>
									<li><a href="/PhizIC/help.do?id=/private/news/list">События</a></li>
									<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>


								</ul>								

                           	<a href="/PhizIC/faq.do" class="faq-help"><span>Часто задаваемые вопросы о Сбербанк Онлайн</span></a>
            </div>
            <!-- end help-left-section -->
            <div id="help-workspace">
                <div class="contextTitle">Мои автоплатежи</div>
									<ul class="page-content">
											<li><a href="/PhizIC/help.do?id=/private/autopayment/select-category-provider">Подключение автоплатежа</a></li>
											<li><a href="/PhizIC/help.do?id=/private/longOffers/info">Просмотр автоплатежа</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/EditAutoSubscriptionPayment/null/LongOffer">Редактирование автоплатежа</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/DelayAutoSubscriptionPayment/null/LongOffer">Приостановление автоплатежа</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/RecoveryAutoSubscriptionPayment/null/LongOffer">Возобновление автоплатежа</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/RefuseLongOffer/refuse-long-offer-extended-fields">Отмена автоплатежа</a></li>

									</ul>
									
										<p>На странице <b>Мои автоплатежи</b> можно просмотреть созданные Вами 
										автоплатежи (регулярные операции). <b>Автоплатеж</b> 
										- это платеж, который выполняется автоматически по заданным Вами параметрам.</p>
										<p>Существует два типа автоплатежей:</p>
										<ul>
											<li><b>Автоплатежи на карты</b> - это автоматические переводы между Вашими банковскими картами или на карты других клиентов Сбербанка. </li>
											<li><b>Автоплатежи за услуги</b> - это платежи для автоматической оплаты услуг, например, мобильной связи, коммунальных платежей, налогов или штрафов.</li>
										</ul>
										<p><b>Автоплатежи на карты</b> и <b>автоплатежи за услуги</b> показаны в отдельных блоках.</p>
										<p>На данной странице автоматически отображается список автоплатежей, которые <b>ожидают подтверждения</b> и список <b>активных автоплатежей</b>. Список заблокированных или удаленных платежей отображается в блоке <b>Архив автоплатежей</b>.</p>
										<p>По каждому автоплатежу в списке Вы можете просмотреть его название, условия выполнения и заданную сумму, а также выполнить различные операции. </p>
										<p>Вы можете в списке расположить автоплатежи в нужном Вам порядке. Для этого наведите курсор на выбранный автоплатеж, нажмите на левую кнопку мыши и, не отпуская, перетащите его в любое место в списке. После этого в Личном меню автоплатежи будут расположены в заданной Вами последовательности.</p>
										<p> Для того чтобы оформить новый автоплатеж, нажмите на кнопку <b>Подключить автоплатеж</b>, и 
										Вы перейдете к выбору организаций, услуги которых можно оплачивать автоматически.</p>
										<div class="help-important">
										<p>
										<span class="help-attention">Обратите 
										внимание</span>: в зависимости от того, какую услугу или какой платеж Вы выберете для создания автоплатежа, поля в заявке будут отличаться. 
									</p>
								</div>
								<p>Если Вы хотите создать заявку на автоплатеж по свободным реквизитам, то нажмите на кнопку <b>Подключить автоплатеж</b>, а затем нажмите на изображение квитанции или на ссылку <b>Не нашли подходящий раздел, но знаете реквизиты?</b></p>
									<p>Для того чтобы просмотреть автоплатеж, 
						                щелкните по его названию. В результате откроется страница, 
						                на которой Вы увидите детальную информацию по созданному Вами регулярному платежу
						                 и график его исполнения. </p>
										<p>С каждым автоплатежом в списке Вы можете выполнить следующие действия, набор которых зависит от выбранного поставщика услуг и вида платежа: </p>
										<ul> 
										<li> 
										<b>Отредактировать</b>. Если Вы хотите изменить параметры автоплатежа, нажмите на кнопку <b>Операции</b> напротив него, а затем щелкните по ссылке <b>Редактировать</b>. 
										</li>
										<li> 
										<b>Отключить</b>. Для того чтобы прекратить выполнение интересующего Вас автоплатежа, нажмите на кнопку <b>Операции</b> напротив него, а затем щелкните по ссылке <b>Отключить</b>. 
										</li>
										<li> 
										<b>Приостановить</b>. Если Вы хотите временно прекратить выполнение автоплатежа, нажмите на кнопку <b>Операции</b> напротив него, а затем щелкните по ссылке <b>Приостановить</b>. 
										</li>
										<li> 
										<b> Возобновить</b>. Для того чтобы возобновить выполнение приостановленного платежа, нажмите на кнопку <b>Операции</b> напротив него, а затем щелкните по ссылке <b>Возобновить</b>.
										</li>
										</ul>
							<div class="help-important"> 
							<p> 
							<span class="help-attention">Обратите внимание</span>: список операций, которые Вы можете выполнить с 
							автоплатежом, зависит от типа и статуса (состояния) автоплатежа.
								</p>	
							</div>									
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>Кроме того, в системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
								который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые вопросы</b> 
								в боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые вопросы по работе с 
								системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы консультант отображался на страницах системы, то измените 
								настройки отображения помощника, щелкнув по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы 
								скрыть помощника, Вам нужно в блоке <b>Дополнительные настройки</b> убрать галочку в поле "Отображать персонального 
								консультанта" и нажать на кнопку <b>Сохранить</b>. --></p>


								<div class="help-linked">
									<h2>Также рекомендуем посмотреть</h2>
									<ul class="page-content">
										<li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
										<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>

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