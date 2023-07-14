<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. События</title>
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
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a>
									<li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mail/sentList">Вопрос в Контактный центр банка</a></li>
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/news/list">События</a></li>
									<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>


								</ul>								

                              <a href="/PhizIC/faq.do" class="faq-help">
                    <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
                </a>
            </div>
            <!-- end help-left-section -->
            <div id="help-workspace">
                <div class="contextTitle">События</div>
                               
								<ul class="page-content">
								<li><a href="#p2">Все события</a></li>
								<li><a href="#p3">События дня</a></li>
                                <li><a href="#p4">Просмотр события</a></li>
								</ul>
								<p>С помощью блока <b>События</b> Вы можете 
								просмотреть список всех событий (новостей) банка, события дня, а также 
								полный текст любого события. Данный блок 
								отображается на главной странице и на странице входа в систему.</p>
									
									<div class="help-important">
									<p>
										<span class="help-attention">Примечание</span>: события высокой важности выделены в блоке 
										событий жирным шрифтом.
									</p>
								</div>	
								<h2><a id="p2">Все события</a></h2>
								<ul class="page-content">
								<li><a href="#p5">Поиск всех событий</a></li>
								</ul>
								<p>Для того чтобы просмотреть все события банка, на главной странице нажмите на ссылку <b>Все события</b> 
								в блоке <b>События</b>. Откроется страница со списком событий, на которой Вы можете выполнить 
								следующие действия: просмотреть интересующее событие, просмотреть все события банка, а также 
								воспользоваться поиском событий.</p>
								<p>Для каждого события в списке отображается дата его публикации, заголовок и краткий текст события. </p>
								<p>Если Вы хотите просмотреть полный текст события (новости банка), в списке щелкните по его заголовку.</p>
							
							<h3><a id="p5">Поиск всех событий</a></h3>
								<p>В системе "Сбербанк Онлайн" Вы можете найти интересующее Вас событие в списке. Для этого заполните критерии поиска: </p>
								<ul>
								<li>"За период" - поиск событий, произошедших за конкретный период. Для того чтобы задать период, в списке всех событий нажмите на календарь 
								<IMG border="0" src="${globalUrl}/commonSkin/images/datePickerCalendar.gif" alt=" height="20" width="20""> в данном поле и выберите из 
								календаря месяц и дату начала и окончания периода публикации события. Также Вы можете ввести даты вручную. Затем нажмите на кнопку <IMG border="0" src="${globalUrl}/commonSkin/images/period-find.gif" alt=" height="25" width="25"">.
								</li>
								<li>"Искать среди важных событий" - поиск по важности события. Для того чтобы найти событие высокой важности, щелкните по ссылке <b>Показать</b> и установите флажок в поле "Искать среди важных событий".</li>
								<li>Вы можете ввести в строку поиска слова, встречающиеся в тексте или в названии события. Для этого нажмите на ссылку <b>Показать</b> и введите несколько первых букв или слово целиком.
								</li>
								</ul>
								<p>После того как все параметры заданы, нажмите на кнопку <b>Найти</b>. Система выведет на экран все интересующие Вас события. </p>
								<p>Вы можете выбрать, сколько событий будет показано на странице - 10, 20 или 50. 
								Например, если Вы хотите просмотреть 20 событий, то в строке "Показать по" выберите значение "20". 
								Система выведет на экран 20 последних событий банка. </p>
								
								<h2><a id="p3">События дня</a></h2>
								<ul class="page-content">
								<li><a href="#p6">Поиск событий дня</a></li>
								</ul>
								<p>В списке событий дня Вы можете просмотреть события (новости) банка текущего дня, а также воспользоваться поиском событий за конкретную дату. Для просмотра событий дня на главной странице в блоке <b>События</b> нажмите на ссылку <b>События дня</b>.</p>
								<p>Для каждого события в списке отображается дата его публикации, заголовок и краткий текст события. Если событие опубликовано в текущий день, то Вы увидите также время его публикации.</p>
								<p>Если Вы хотите просмотреть полный текст события (новости банка), в списке щелкните по его заголовку.</p>
	
								<h3><a id="p6">Поиск событий дня</a></h3>
								<p>В системе "Сбербанк Онлайн" Вы можете найти интересующее Вас событие в списке несколькими способами: </p>
								<ul>
								<li>"На дату" - поиск событий, произошедших в конкретный день. Для того чтобы указать дату, в списке событий дня в данном поле нажмите на календарь 
								<IMG border="0" src="${globalUrl}/commonSkin/images/datePickerCalendar.gif" alt=" height="20" width="20""> и выберите из 
								календаря нужную дату. Также Вы можете ввести дату вручную. Затем нажмите на кнопку <IMG border="0" src="${globalUrl}/commonSkin/images/period-find.gif" alt=" height="25" width="25"">.
								</li>
								<li>"Искать среди важных событий" - поиск по важности события. Для того чтобы найти событие высокой важности, щелкните по ссылке <b>Показать</b> и установите флажок в поле "Искать среди важных событий".</li>
								<li>Вы можете ввести в строку поиска слова, встречающиеся в тексте или в названии события. Для этого нажмите на ссылку <b>Показать</b> и введите несколько первых букв или слово целиком.
								</li>
								</ul>
								<p>После того как все параметры заданы, нажмите на кнопку <b>Найти</b>. Система выведет на экран все интересующие Вас события. </p>
								<p>Вы можете выбрать, сколько событий будет показано на странице - 10, 20 или 50. 
								Например, если Вы хотите просмотреть 20 событий, то в строке "Показать по" выберите значение "20". 
								Система выведет на экран 20 последних событий банка. </p>

								<H2><a id="p4">Просмотр события</a></h2>
								<p>Для того чтобы просмотреть полный текст события, щелкните по его заголовку. Откроется страница, на 
								которой Вы увидите название события, краткий и полный текст выбранного события. Для того чтобы перейти 
								к списку событий банка, нажмите на ссылку <b>К списку событий</b>.</p>
								<p>В системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
								который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые вопросы</b> в 
								боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые вопросы по работе с системой 
								"Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы консультант отображался на страницах системы, то измените настройки отображения 
								помощника, щелкнув по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы скрыть помощника, Вам нужно в блоке 
								<b>Дополнительные настройки</b> убрать галочку в поле "Отображать персонального консультанта" и нажать на кнопку <b>Сохранить</b>.</p> -->
								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
									<li><span><a href="/PhizIC/help.do?id=/private/accounts">Главная</a></li>
									<li><span><a href="/PhizIC/help.do?id=/private/payments">Переводы и платежи</a></li>
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