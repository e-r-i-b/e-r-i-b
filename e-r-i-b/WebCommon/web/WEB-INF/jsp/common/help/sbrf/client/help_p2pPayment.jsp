<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<head>
<title>Помощь. Подключение автоплатежа по карте</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">


<link type="text/css" rel="stylesheet" href="${globalUrl}/commonSkin/help.css"/>
<link rel="stylesheet" type="text/css" href="${skinUrl}/roundBorder.css"/>
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
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
										<ul class="page-content">
											<li><a href="/PhizIC/help.do?id=/private/graphics/finance">Мои финансы</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/favouriteLinks#p5">Мобильные приложения</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/internetShops/orderList">Мои Интернет-заказы</a></li>
											<li><a href="/PhizIC/help.do?id=/private/loyalty/detail">Спасибо от Сбербанка</a></li>											
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/favouriteLinks#p2">Избранное</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/PaymentsAndTemplates">Мои шаблоны</a></li>
											<li><a class="parentItem" href="/PhizIC/help.do?id=/private/favourite/list/AutoPayments">Мои автоплатежи</a>
												<ul>
													<li><a class="active-menu" href="/PhizIC/help.do?id=/private/longOffers/info">Подключение автоплатежа по карте</a></li>
												</ul>	
											</li>
										</ul>
									</li>
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
                <div class="contextTitle">Подключение автоплатежа по карте</div>


								<ul class="page-content">
															
									<li><a href="#p1">Создать заявку</a></li>
									<li><a href="#p2">Подтвердить заявку</a></li>
									<li><a href="#p3">Просмотреть заявку</a></li>

								</ul>

								
								<h3>Подключение автоплатежа по карте</h3>
								<p>В системе Вы можете создать заявку на выполнение автоматических переводов на Вашу карту или на карту клиента Сбербанка.</p>
								<p>Для того чтобы подключить автоплатеж по карте, на странице создания автоплатежа щелкните по ссылке <b>Перевод между своими картами</b> или <b>Перевод клиенту Сбербанка</b>, и Вы перейдете к созданию заявки. </p>
								
								 <h2><a id="p1">Создать заявку</a></h2>
								 <p>Для того чтобы подать в банк заявку на подключение автоплатежа, выполните следующие действия:</p>
								 <p><b>Шаг 1. Заполните параметры перевода:</b></p>
								 	<ul>
										<li>Выберите, кому Вы хотите перевести деньги. Для этого нажмите на одну из кнопок: <b>На свою карту</b> или <b>Клиенту Сбербанка</b>.</li>
										<li>В поле "Карта зачисления" или "Получатель" выберите из выпадающего списка карту, на которую Вы хотите переводить деньги. При создании автоплатежа на карту клиента Сбербанка Вы можете выбрать получателя из адресной книги.
										 Для этого нажмите на ссылку <b>Выбрать из адресной книги</b> и укажите получателя.</li>
										<li>В поле "Карта списания" укажите карту, с которой Вы хотите переводить деньги.</li>
									</ul>
									<p>После того как параметры указаны, нажмите на кнопку <b>Продолжить</b>, и Вы перейдете на страницу ввода параметров автоплатежа.</p>
									<p>Если Вы передумали подключать автоплатеж, нажмите на ссылку <b>Отменить</b>.</p>
									<p>Для того чтобы вернуться на страницу <b>Подключение автоплатежа</b>, нажмите на ссылку <b>Назад</b>.</p>
									<b>Шаг 2. Укажите настройки автоплатежа:</b></p>
									<ul>
										<li>В поле "Переводить" из выпадающего списка выберите периодичность совершения автоплатежа, например, "раз в неделю", "раз в месяц" или "раз в год".</li>
										<li>В поле "Дата ближайшего перевода" введите дату первого автоплатежа. Для этого нажмите на кнопку <IMG border="0" src="${globalUrl}/commonSkin/images/datePickerCalendar.gif" alt=" height="20" width="20""> и выберите из календаря нужную дату либо введите дату вручную.</li>
										<li>В поле "Сумма" укажите сумму, на которую должен выполняться перевод.</li>
										<li>В поле "Название" впишите название создаваемого платежа.</li>
										<li>Если Вы создаете автоплатеж на карту клиента Сбербанка, в поле "Написать сообщение получателю" укажите сообщение, которое будет отправлено получателю денежных средств на мобильный телефон, например, "Перевел 1000 рублей".</li>
									</ul>
								<p>После того как все настройки автоплатежа заданы, нажмите на кнопку <b>Подключить</b>. В результате Вы перейдете на страницу подтверждения заявки.</p>
								<p>Если Вы хотите изменить параметры автоплатежа, нажмите на ссылку <b>Редактировать</b>, Вы вернетесь на страницу заполнения реквизитов.</p>
								<p>Если Вы передумали подключать автоплатеж, нажмите на ссылку <b>Отменить</b>.</p>
								
								
								 <h2><a id="p2">Подтвердить заявку</a></h2>
								<p>Далее необходимо подтвердить заявку. После того как Вы нажали на кнопку <b>Подключить</b>, Вам откроется заполненная форма заявки, в которой нужно проверить правильность указанных сведений, после чего выполнить одно из следующих действий:</p>
								<ul>
									<li><b>Подтвердить заявку</b>. Для того чтобы подтвердить заявку для автоплатежа между своими картами, нажмите на кнопку <b>Подтвердить</b>, Вы перейдете на страницу просмотра заявки. </li>
									 <p>Для подтверждения заявки для перевода клиенту Сбербанка, Вам необходимо выбрать, каким способом Вы хотите ее подтвердить:</p>
										 <ul>
											 <li>если Вы хотите подтвердить операцию SMS-паролем, нажмите на кнопку <b>Подтвердить по SMS</b>;
											 <div class="help-important">
											 <p><span class="help-attention">Обратите внимание</span>: перед вводом пароля убедитесь, что реквизиты операции совпадают с 	текстом SMS-сообщения. Будьте осторожны, если данные не совпадают, ни в коем случае не вводите пароль и никому его не сообщайте, даже сотрудникам банка. 
											 </p>
											 </div> 
								             </li>
											 <li>если Вы хотите подтвердить операцию другим способом, нажмите на 
                                        ссылку <b>Другой способ подтверждения</b>. Затем 
                                        выберите один из предложенных вариантов:</p>
                                        <ul><li><p><b>Пароль с чека</b> - подтверждение паролем с чека, распечатанного в банкомате.</li>
                                        <li><p><b>Push-пароль из уведомления в мобильном приложении</b> - подтверждение 
                                        паролем, полученным на мобильное устройство в виде Push-сообщения.</p></li>
                                        </ul></ul>
											Затем откроется всплывающее окно,	в котором укажите нужный пароль и нажмите на кнопку <b>Подтвердить</b>. В результате Вы перейдете на страницу просмотра заявки.</p>

									<li><b>Редактировать заявку</b>. Если Вы хотите изменить параметры заявки, то нажмите на ссылку <b>Редактировать</b>. В результате Вы вернетесь на страницу заполнения реквизитов заявки.</li>
									<li><b>Отменить операцию</b>. Если Вы передумали совершать операцию, то нажмите на ссылку <b>Отменить</b>. В результате Вы вернетесь на страницу <b>Переводы и платежи</b>.</li>
								</ul>
								
								<h2><a id="p3">Просмотреть заявку</a></h2>
								<p>После подтверждения заявки Вы перейдете на страницу просмотра заявки, на которой увидите заполненный документ.</p>
								<p>О том, что заявка отправлена в банк, свидетельствует отображаемый на форме документа штамп "Принято к исполнению" и статус "Исполняется банком".</p>
								<p>Для того чтобы просмотреть созданные заявки, щелкните в <b>Личном меню</b> по ссылке <b>История операций в Сбербанк Онлайн</b> или перейдите на страницу <b>Мои автоплатежи</b>.</p>
								<p>На странице просмотра заявки Вы можете выполнить следующие действия:</p>
								<ul>
									<li><b>Напечатать чек</b>. В системе "Сбербанк Онлайн" для подтверждения совершения операций предусмотрена печать чеков. Если Вы хотите напечатать чек, то нажмите на ссылку <b>Печать</b>. 
									Система выведет на экран печатную форму документа, которую Вы сможете распечатать на принтере.</li>
									<li><b>Перейти к платежам</b>. Со страницы просмотра заявки Вы можете вернуться на страницу <b>Переводы и платежи</b>. Для этого щелкните по ссылке <b>Перейти к странице платежей</b>.
									</li>
								</ul>

								
								<div class="help-linked">
									<h2>Также рекомендуем посмотреть</h2>
									<ul class="page-content">
											<li><a href="/PhizIC/help.do?id=/private/longOffers/info">Просмотр регулярного платежа</a></li>

										<li><a href="/PhizIC/help.do?id=/private/payments/payment/RefuseLongOffer/refuse-long-offer-extended-fields">Отмена регулярного платежа</a></li>

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