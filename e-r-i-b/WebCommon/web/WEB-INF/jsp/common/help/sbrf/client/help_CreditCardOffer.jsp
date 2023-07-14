<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<head>
<title>Помощь. Заявка на кредитную карту по предложению банка</title>
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
									<!--<li><a href="/PhizIC/help.do?id=/login">Вход и регистрация</a></li>-->
									<li><a href="/PhizIC/help.do?id=/private/registration">Регистрация</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts">Главная</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments">Переводы и платежи</a></li>
										<c:if test="${phiz:impliesService('RemoteConnectionUDBOClaimRemoteConnectionUDBOClaim')}">
										<li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">Больше Сбербанк Онлайн</a></li>
										</c:if>
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/cards/list">Карты</a>
										<ul class="page-content">
											
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/payments/loan_card_offer">Заявка на кредитную карту по предложению банка</a>
											</li>
										</ul>
									</li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></li>
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
                <div class="contextTitle">Заявка на кредитную карту по предложению банка</div>
								<ul class="page-content">
									<li><a href="#p2">Создать заявку</a></li>
									<li><a href="#p4">Просмотреть заявку</a></li>

								</ul>
								
								<p>В системе "Сбербанк Онлайн" Вы можете подать заявку на кредитную карту,
								 а также просмотреть и согласиться на кредитное предложение банка. </p>
								 	<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" Вы можете переходить на нужную страницу не только с помощью 
										пунктов верхнего меню, ссылок бокового меню, но и с помощью ссылок, расположенных под главным меню. Данные 
										ссылки показывают путь от главной страницы до той, с которой Вы перешли на текущую страницу. Вы можете использовать эти ссылки для 
										перехода на интересующую страницу.  
									</p>
								</div>
									<p>Банк может предложить Вам открыть кредитную карту на индивидуальных условиях. Такие предложения банка 
							 отображаются на главной странице в блоке <b>Предложения банка</b> или в информационном сообщении вверху главной страницы.</p>
							 <p>Если Вам понравилось предложение, и Вы хотите получить карту на этих условиях, то Вам необходимо на
							 странице <b>Предложения банка</b> напротив нужной карты нажать на кнопку <b>Оформить</b> или в 
								уведомлении вверху <b>Главной</b> страницы нажать на соответствующую кнопку.</p>											
								<p>Также Вы можете оформить заявку на кредитную карту на странице <b>Карты</b>, щелкнув по ссылке <b>Заявка на кредитную карту</b>.
								</p>
								<p>В результате откроется форма заявки на оформление кредитной карты.
								</p>
								
								<h2><a id="p2">Создать заявку</a></h2>
								<p>Для того чтобы оформить заявку на кредитную карту, нужно указать следующую информацию: 
								<ul>
								<li>В поле "Кредитная карта" Вы можете выбрать из списка кредитную карту на интересующих Вас условиях. 
								
								<div class="help-important">
								<p>
								<span class="help-attention">Обратите 
								внимание</span>: после того как Вы выбрали кредитную карту, поля "Условия и преимущества", "Процентная ставка"
								 и "Годовое обслуживание" заполнятся автоматически.  
								</p>
								</div></li>
								<li>В поле "Условия и преимущества" Вы можете просмотреть льготный период и процентную ставку в льготный период по карте. Если Вы хотите 
								просмотреть дополнительную информацию по карте, 
								нажмите на ссылку <b>Подробнее</b>. 
								</li>
								<li>В поле "Кредитный лимит" укажите сумму, на которую Вы хотели бы получить кредит. Для этого переместите ползунок до нужного значения или введите сумму вручную.
								</li>
								<li>Далее в блоке "Дополнительная информация" автоматически будет указаны Ваша фамилия, имя, отчество. 
								</li>
								<li> В поле "Где я хочу получить карту" укажите отделение банка, где Вам удобно получить карту. Для этого нажмите на ссылку <b>Выберите отделение</b>,
								откроется справочник подразделений банка. Чтобы найти нужное отделение, укажите регион обслуживания, щелкнув по ссылке <b>Все регионы</b> и выбрав из списка регион, в котором Вы обслуживаетесь. Затем впишите в поля поиска название отделения, в котором хотите получить карту, город или другой населенный пункт, в котором Вы проживаете, и название улицы. После того как поля заполнены, нажмите на кнопку 
								<b>Применить</b>. В результате Вы увидите список отделений банка, удовлетворяющий заданным параметрам. Если Вы хотите указать другое подразделение банка, то нажмите на ссылку <b>Очистить параметры поиска</b> и заполните поля заново.</p>
								<p>Если в результате поиска отобразилось много 
								отделений банка, Вы можете просматривать список 
								при помощи кнопок <b>></b> или <b><</b>, расположенных под 
								ним. Также Вы можете изменить количество отделений на странице, выбрав под списком значение 10, 20 или 50. </p>
								<p>Далее установите переключатель напротив интересующего Вас филиала, и нажмите на кнопку <b>Выбрать</b>.
								<div class="help-important">
								<p>
								<span class="help-attention">Обратите 
								внимание</span>: после того как Вы указали подразделение для получения кредитной карты, поле "Срок выпуска карты" заполнится автоматически.  
								</p>
								</div></li>
								</ul>							
							
									<p>После того как все реквизиты заявки заполнены, нажмите на кнопку <b>Заказать карту</b>. 
									Система выведет на экран страницу 
									просмотра заявки.</p>
									
								
								 

								
									<p>Если Вы передумали создавать заявку, то 
									нажмите на ссылку <b>Отменить</b>. В 
									результате Вы вернетесь на предыдущую страницу.</p>
									
							
								<h2><a id="p4">Просмотреть заявку</a></h2>
									<p>После оформления заявки Вы перейдете 
									на страницу просмотра, на которой 
									увидите заполненный документ. </p>
									<p>На этой странице Вы 
									сможете <b>просмотреть реквизиты</b> выполненной 
									заявки.</p>
									<p>Для того чтобы распечатать заявку на кредитную карту,
									нажмите на ссылку <b>Печать</b>. В результате откроется печатная форма кредитной
									заявки, которую Вы сможете распечатать на принтере.</p>
								<p>Если Вы хотите перейти к списку Ваших карт, то щелкните по ссылке <b>Раздел карт</b>.</p>
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>Кроме того, в системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
								который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые 
								вопросы</b> в боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые 
								вопросы по работе с системой "Сбербанк Онлайн". 
								<!--  Если Вы не хотите, чтобы консультант отображался на страницах 
								системы, то измените настройки отображения помощника, щелкнув по кнопке 
								<b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. 
								Для того чтобы скрыть помощника, Вам нужно в блоке <b>Дополнительные настройки</b> убрать 
								галочку в поле "Отображать 
								персонального консультанта" и нажать на кнопку <b>Сохранить</b>.</p> -->

								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
										<li><span></span><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></li>
											<li><span></span><a href="/PhizIC/help.do?id=/private/accounts#p9">Предложения банка</a></li>
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