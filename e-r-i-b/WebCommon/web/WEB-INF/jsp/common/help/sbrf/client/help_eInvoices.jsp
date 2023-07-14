<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Мои интернет-заказы</title>
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
									<li><a href="/PhizIC/help.do?id=/private/security/list ">Сертификаты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">Страховые программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a>
										<ul>
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/payments/internetShops/orderList">Мои интернет-заказы</a></li>
											<li><a href="/PhizIC/help.do?id=/private/loyalty/detail">Спасибо от Сбербанка</a></li>
											<li><a href="/PhizIC/help.do?id=/private/graphics/finance">Мои финансы</a></li>
											<li><a href="#p2">Избранное</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/PaymentsAndTemplates">Мои шаблоны</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/AutoPayments">Мои автоплатежи</a></li>
										</ul>
									</li>
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
                <div class="contextTitle">Мои интернет-заказы</div>
									<p> На странице <b>Мои интернет-заказы</b> Вы можете 
								просмотреть список счетов на оплату товаров и услуг из интернет-магазинов. Перейти на данную страницу можно, нажав в Личном меню на ссылку <b>Мои интернет-заказы</b>.</p>
							
								<p>На этой странице система выведет на экран
								список выставленных Вам счетов на оплату товаров или услуг с сайтов интернет-магазинов.
								</p>
								<p>В списке для каждого счета (заказа) отображается дата и время его формирования, наименование интернет-магазина, код заказа в магазине, сумма, на которую выставлен счет, а также статус, отражающий стадию обработки заказа.</p>
								<p>Вы можете выбрать, сколько счетов будет показано на странице - 10, 20 или 50.
									Например, если Вы хотите просмотреть 20 счетов на оплату,
									то внизу списка в строке "Показывать по" выберите значение "20". Система выведет на экран 
									20 последних счетов.</p>
									<h3><a id="p2">Фильтр/поиск заказов</a></h3>
								<ul class="page-content">
									<li><a href="#p4">Сокращенный фильтр</a></li>
									<li><a href="#p5">Расширенный фильтр</a></li>
								</ul>
										<p>Для поиска интернет-заказов
										Вы можете воспользоваться фильтром. В 
										системе предусмотрен <b>сокращенный 
										фильтр</b>, с помощью которого Вы можете 
										найти счета, выставленные за 
										определенный период, и <b>расширенный 
										фильтр</b>, содержащий дополнительные 
										поля поиска.</p>
								<h4><a id="p4">Сокращенный фильтр</a></h4>
								<p>Вы можете найти счета на оплату, которые были выставлены за интересующий Вас период. Для этого задайте временной интервал, 
								щелкнув по календарю <IMG border="0" src="${globalUrl}/commonSkin/images/calendar.png" alt="">
								рядом с полем и выбрав месяц и дату начала и окончания оформления заказа. 
								Также Вы можете ввести даты вручную.</li>
																
									<p>После того как Вы указали интересующий 
									Вас период, нажмите на кнопку <IMG border="0" src="${skinUrl}/images/period-find.gif" alt="">. 
									Система выведет на экран все интересующие Вас счета на оплату интернет-заказов.
									</p>
								<h4><a id="p5">Расширенный фильтр</a></h4>
									<p>Для того чтобы воспользоваться 
									расширенным фильтром, щелкните по ссылке <b>
									Показать</b> и задайте критерии 
									поиска:</p> 
										<ul>
										<li>"Состояние" - выберите из выпадающего списка статус, в котором находится интересующий Вас счет на оплату. 
										</li>
										<li>"Сумма" - укажите диапазон, куда входит сумма, на которую 
										был оформлен заказ. Также в соседнем поле из выпадающего списка выберите валюту заказа.
										</li>
										</ul>
									<p>Вы можете ввести значения в одно или 
									несколько полей фильтра. Например, можно осуществить поиск счетов в статусе "Введен" за последний месяц. </p>
									<p>После того как все 
									необходимые параметры указаны, нажмите на 
									кнопку <b>Применить</b>. Система выведет на экран 
									список интересующих Вас заказов.</p>

								<p>На странице <b>Мои интернет-заказы</b> 
								Вы также можете выполнить следующие действия:</p>
								<ul>
								<li><b>просмотреть заказ</b>. Для этого щелкните по ссылке <b>Показать</b> под названием интернет-магазина. Откроется 
								раздел, содержащий дополнительную информацию о заказе: наименование товаров или услуг, которых Вы хотите приобрести, их количество, цена и другие сведения.</li>
								
								<li><b>оплатить заказ</b>. Для этого щелкните по названию интернет-магазина. В результате Вы перейдете на форму просмотра заказа, на которой нажмите на кнопку <b>Оплатить</b> (подробную информацию по созданию запроса смотрите в разделе контекстной справки
									<a href="/PhizIC/help.do?id=/private/payments/internetShops/payment/ExternalProviderPayment/null">Оплата счетов интернет-магазинов</a>).							
									</li>
									<li><b>отменить заказ</b>. Для этого щелкните по названию интернет-магазина. В результате Вы перейдете на форму просмотра заказа, на которой щелкните по 
								ссылке <b>Отменить</b>. Затем после подтверждения операции заказ будет отменен.
								
								</li>
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