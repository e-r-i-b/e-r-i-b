<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Информация по сертификату</title>
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
									<li><a href="/PhizIC/help.do?id=/private/payments">
									Переводы и платежи</a></li>
									<li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">Больше Сбербанк Онлайн</a></li>
									<li><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a></li>
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/security/list ">Сертификаты</a>
									<ul>
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/security/view">Информация по сертификату</a></li>
									</ul>
									</li>
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
                <div class="contextTitle">Сертификаты. Информация по сертификату</div>
                  
																
									<p>По каждому сертификату Вы можете просмотреть <b>детальную 
									информацию</b>. Для этого в пункте главного меню <b>Прочее</b> - <b>
									Сертификаты</b> щелкните по интересующему Вас 
									сберегательному сертификату в списке. В результате откроется 
									страница, на которой по выбранному 
									сертификату будут показаны следующие сведения:</P>
									<ul>
									 <li><b>Название сертификата</b>, которое отображается в списке сертификатов, 
									например, "Сберегательный сертификат". Для того 
									чтобы отредактировать его наименование, рядом с названием нажмите на кнопку <IMG border="0" src="${globalUrl}/commonSkin/images/pencil.gif" alt="">, затем в поле "Название" введите 
									наименование сертификата и нажмите на кнопку <b>Сохранить</b>. Если Вы не укажете название, то в списке для данного сертификата будет 
									вместо названия показан тип сертификата.</li>

									<li><b>Наименование ценной бумаги</b> - название ценной бумаги; 
									<li><b>Идентификатор ценной бумаги</b> - серия и номер сертификата;</li>
									<li><b>Размер вклада, оформленного сертификатом</b> - сумма, размещенная 
									на вкладе, к которому был выпущен данный сертификат;</li>
									<li><b>Реквизиты филиала, выдавшего сертификат</b> - реквизиты подразделения банка, в котором был выдан сертификат; 
									</li>
									<li><b>Дата выдачи ценной бумаги</b> - дата выдачи сертификата;</li>
									<li><b>Срок обращения</b> - срок, на который был выдан сертификат; 
									</li>
									<li><b>Дата востребования суммы по сертификату</b> - дата, начиная с которой Вы можете получить все денежные средства, имеющиеся на вкладе; 
									</li>
									<li><b>Ставка процентов за пользование вкладом</b> - процентная ставка по вкладу; 
									</li>
									<li><b>Сумма причитающихся процентов</b> - доход по вкладу, к которому был выпущен сертификат; 
									</li>
									<li> В блоке <b>Ответственное хранение</b> 
									отображается № и дата договора ответственного 
									хранения, реквизиты и адрес филиала, 
									принявшего данный сертификат на хранение.
									<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: блок <b>Ответственное хранение</b> будет 
										показан только в том случае, если 
										сертификат находится на хранении в 
										банке.</p>
								     </div></li>
									</ul>
									<p>Также на данной странице Вы можете <b>распечатать детальную информацию по сертификату</b>, для этого щелкните по ссылке <b>Печать</b>.
                                    Система выведет на экран печатную форму документа, 
									которую можно распечатать на принтере.</p>	
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
									<p>В системе "Сбербанк Онлайн" на каждой странице можно 
								обратиться к помощи персонального консультанта, который 
								ответит на все Ваши вопросы. Чтобы запустить помощника, 
								нажмите на ссылку <b>Часто задаваемые вопросы</b> в 
								боковом меню. В результате откроется окно, в котором 
								содержатся ответы на часто задаваемые вопросы по работе 
								с системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы 
								консультант отображался на страницах системы, то измените 
								настройки отображения помощника, щелкнув по кнопке <b>Настройки</b> 
								- <b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы скрыть помощника, 
								Вам нужно в блоке <b>Дополнительные настройки</b> убрать галочку в 
								поле "Отображать персонального консультанта" и нажать на кнопку 
								<b>Сохранить</b>. --></p>
	
								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
									<li><span><a href="/PhizIC/help.do?id=/private/deposits/products/list">Открытие вклада</a></li>
									<li><span><a href="/PhizIC/help.do?id=/private/accounts">Главная</a></li>
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