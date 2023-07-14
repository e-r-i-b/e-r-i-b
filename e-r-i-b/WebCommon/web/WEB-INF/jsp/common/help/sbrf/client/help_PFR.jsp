<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Пенсионный фонд</title>
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
	<link type="text/css" rel="stylesheet" href="/${skinUrl}/ie.css"/>
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
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/pfr/list">Выписка из Пенсионного фонда</a></li>
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
                <div class="contextTitle">Выписка о состоянии индивидуального лицевого счета</div>


								<p>На странице <b>Выписка о состоянии индивидуального лицевого счета</b> Вы можете 
								просмотреть список запросов, отправленных Вами в Пенсионный 
								фонд России (ПФР) за 
								указанный период, просмотреть интересующий 
								запрос, а также воспользоваться фильтром/поиском. Перейти на данную страницу Вы можете 
								из пункта главного меню <b>Прочее</b> - <b>Пенсионные программы</b>, щелкнув по ссылке <b>Выписка о состоянии индивидуального лицевого счета</b>.</p>
								<!--<div class="help-important">
									<p>
										<span class="help-attention">Примечание</span>: для более безопасного совершения платежей 
										в системе "Сбербанк Онлайн" предусмотрено подтверждение операций  SMS-паролем или паролем 
										с чека, распечатанного в банкомате. Обратите внимание, возможность подтверждения операций 
									одноразовыми паролями ограничена: в течение дня Вы можете подтверждать операции, пока сумма по ним 
										не превысит установленный банком лимит. Операции, совершаемые после превышения суммы лимита, нужно
										дополнительно подтверждать через Контактный центр банка по телефонам +7(495)500 5550, 8(800)555 5550. 
									</p>
								</div>	-->							
								<p>Вы можете добавить эту страницу  
									 в Личное меню, что 
										позволит Вам перейти к ней с 
										любой страницы системы "Сбербанк 
										Онлайн", щелкнув по ссылке в боковом меню в разделе <b>Избранное</b>. 
										Для выполнения операции нажмите на 
										ссылку <b>Добавить в избранное</b> и подтвердите ее. Если страница уже добавлена в Личное меню, то на этой странице будет отображаться ссылка <IMG border="0" src="${globalUrl}/commonSkin/images/favouriteHover.gif" alt=""><b>В избранном</b>. 
									</p>											
									<p>Также Вы можете добавить информацию по Вашему
									лицевому счету на <b>Главную</b> страницу системы. Для этого
									установите галочку в поле "Показывать на главной", после этого 
									Ваш лицевой счет будет отображаться на главной странице.
									</p>
								<p>На странице <b>Выписка о состоянии индивидуального лицевого счета</b> система выведет на экран
								индивидуальный номер Вашего лицевого счета, открытого в Пенсионном фонде России,
								а также список 10 последних запросов на получение выписки по счету.
								</p>
								<p>В списке для каждого запроса отображается дата и время его создания,
								а также статус, отражающий стадию обработки запроса.</p>
								<p>Вы можете выбрать, сколько запросов будет показано на странице - 10, 20 или 50.
									Например, если Вы хотите просмотреть 20 запросов на получение выписки,
									то внизу списка в строке "Показывать по" выберите значение "20". Система выведет на экран 
									20 последних запросов.</p>
																<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" Вы можете переходить на нужную страницу не только с помощью 
										пунктов верхнего меню, ссылок бокового меню, но и с помощью ссылок, расположенных под главным меню. Данные 
										ссылки показывают путь от главной страницы до той, с которой Вы перешли на текущую страницу. Вы можете использовать эти ссылки для 
										перехода на интересующую страницу.  
									</p>
								</div>
								
									<h3>Поиск запросов</h3>
									<p>Вы можете просмотреть запросы за определенный период. Для того чтобы задать временной интервал, 
									нажмите на календарь <IMG border="0" src="${globalUrl}/commonSkin/images/datePickerCalendar.gif" alt=" height="20" width="20"">
									рядом с полем и выберите из календаря месяц и дату начала и окончания периода выполнения 
									операций. Также Вы можете ввести даты вручную. После того как Вы указали период, нажмите на кнопку <IMG border="0" src="${globalUrl}/commonSkin/images/period-find.gif" alt=" height="25" width="25"">.</li>
									</ul>
									<p>Ниже система покажет список запросов, которые были созданы в указанный Вами промежуток времени.</p>
									
								<p>На данной странице
								Вы также можете выполнить следующие действия:</p>
								<ul>
								<li><b>просмотреть</b> выбранный 
								запрос, для этого щелкните по интересующему Вас запросу в списке. Откроется 
								страница просмотра, содержащая 
								реквизиты документа.</li>
								<li><b>просмотреть выписку</b> по Вашему лицевому счету. Для этого в списке щелкните по 
								ссылке <b>Посмотреть выписку</b>. Откроется страница с выпиской из Пенсионного фонда.
								<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: просмотреть выписку можно
										только для запросов со статусом "Исполнен".
									</p>
								</div>	
								</li>
								<li><b>отправить запрос</b> на получение выписки. Для этого щелкните по кнопке <b>Получить выписку</b> или нажмите на кнопку <b>Операции с пенсионным фондом</b>, затем нажмите на ссылку <b>Получить выписку</b> (подробную информацию по созданию запроса смотрите в разделе контекстной справки
									<a href="/PhizIC/help.do?id=/private/payments/payment/PFRStatementClaim">Запрос выписки из Пенсионного фонда России</a>).							
									</li>
								</ul>
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>

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