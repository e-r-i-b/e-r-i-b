<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<head>
<title>Помощь. Просмотр автоплатежа</title>
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
													<li><a class="active-menu" href="/PhizIC/help.do?id=/private/longOffers/info">Просмотр автоплатежа</a></li>
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
                <div class="contextTitle">Просмотр автоплатежа</div>


									<ul class="page-content">
										
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/InternalPayment/LongOffer">Создание автоплатежа</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/RefuseLongOffer/refuse-long-offer-extended-fields">Отмена автоплатежа</a></li>

											</ul>

										 <p><b>Автоплатеж или регулярная операция</b> 
										- это платеж, который выполняется автоматически по заданным Вами параметрам.</p>
										</p>
										 <p>Для того чтобы 
											просмотреть выбранный Вами платеж, нужно щелкнуть по его названию. Откроется страница, на которой
											 Вы можете выполнить следующие действия: </p>
						               <ul>
						              		<li><b>Просмотреть информацию по автоплатежу</b>: реквизиты платежа и его параметры. </li>
											<li><b>Изменить название автоплатежа</b>. Для того чтобы изменить название автоплатежа,
											 введите в поле "Название" новое значение
						                	 и нажмите на ссылку <b> Сохранить</b>. В результате название платежа изменится.</li>
						                 
						                   <li><b>Напечатать детальную информацию по автоплатежу</b>. Для того чтобы
						                   напечатать детальную информацию по автоплатежу, нажмите на ссылку <b> Печать</b>. 
						                   Откроется печатная форма платежа, которую Вы сможете распечатать на принтере.</li>
 										   
 										   <li><b>Отменить автоплатеж</b>. Если Вы хотите отменить выполнение регулярной операции
 										   до наступления даты ее окончания, то нажмите на кнопку <b>Отменить платеж</b>. Откроется форма 
 										   отмены регулярного платежа. </p>
 										   <p>Подробнее об отмене автоплатежа смотрите в разделе справки 
 										   <a href="/PhizIC/help.do?id=/private/payments/payment/RefuseLongOffer/refuse-long-offer-extended-fields">Отмена автоплатежа</a>.</p>

 										   </li>
										   
										    <li><b>Посмотреть график исполнения автоплатежа</b>. На данной странице по умолчанию отображается график платежей, 
										    совершенных за последние полгода. По каждому платежу отображается дата исполнения платежа, сумма и статус документа.
										    <p>Для просмотра графика за другой период укажите границы периода. Для этого нажмите на кнопку <IMG border="0" src="${globalUrl}/commonSkin/images/datePickerCalendar.gif" alt=" height="20" width="20"">
										     и выберите в календаре дату начала и дату окончания периода построения графика. Также дату можно ввести вручную. 
										     После этого нажмите на ссылку <b>Показать график</b>.</p>
											<p>Вы можете просмотреть список всех платежей, выполненных на основе данного автоплатежа. Для этого нажмите на ссылку 
											<b>Показать весь график</b>.</p>
 										   </li>
 											<li><b>Напечатать график исполнения автоплатежа</b>. Для того чтобы напечатать график исполнения автоплатежа,
 											нажмите на ссылку <b>Печать графика</b>. 
 											Откроется печатная форма графика платежей, которую Вы сможете распечатать на принтере.</li>
 										   </ul>
																<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" Вы можете переходить на нужную страницу не только с помощью 
										пунктов верхнего меню, ссылок бокового меню, но и с помощью ссылок, расположенных под главным меню. Данные 
										ссылки показывают путь от главной страницы до той, с которой Вы перешли на текущую страницу. Вы можете использовать эти ссылки для 
										перехода на интересующую страницу.  
									</p>
								</div>
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>Кроме того, в системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые вопросы</b> в боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые вопросы по работе с системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы консультант отображался на страницах системы, то измените настройки отображения помощника, щелкнув по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы скрыть помощника, Вам нужно в блоке <b>Дополнительные настройки</b> убрать галочку в поле "Отображать персонального консультанта" и нажать на кнопку <b>Сохранить</b>.--></p>
								<div class="help-linked">
									<h2>Также рекомендуем посмотреть</h2>
									<ul class="page-content">
											<li><span></span><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>

										<li><span></span><a href="/PhizIC/help.do?id=/private/receivers/list">Шаблоны платежей</a></li>

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