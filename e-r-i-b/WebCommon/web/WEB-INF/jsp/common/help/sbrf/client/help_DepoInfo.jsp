<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Информация по позиции счета депо</title>
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
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a>
										<ul>
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/depo/info/position">Информация по позиции счета депо</a></li>	
											<li><a href="/PhizIC/help.do?id=/private/depo/info/debt">Информация о задолженности</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/DepositorFormClaim">Анкета депонента</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/SecuritiesTransferClaim/null">Поручение на перевод/ прием перевода ценных бумаг</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/SecurityRegistrationClaim">Регистрация ценной бумаги</a></li>
											<li><a href="/PhizIC/help.do?id=/private/depo/documents">Список документов по счету депо</a></li>
										</ul>
									</li>
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

									</li>

								</ul>

                          <a href="/PhizIC/faq.do" class="faq-help">
                    <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
                </a>
            </div>
            <!-- end help-left-section -->
            <div id="help-workspace">
                <div class="contextTitle">Счета депо. Информация по позиции</div>
                								<ul class="page-content">
									<li>
								<a href="#p2">Остальные счета депо</a></li>
								</ul>
									<p>По каждому Вашему счету депозитарного учета Вы можете просмотреть информацию по его позиции: 
									сколько ценных бумаг у Вас сейчас на счете.
											 Для этого в пункте меню <b>Счета депо</b> нажмите на кнопку <b>Операции</b>, откроется список операций по счету.
											 Далее нажмите на ссылку <b>Позиции</b> рядом с выбранным Вами счетом.
											Просмотреть информацию по позиции счета Вы также можете щелкнув по номеру или названию счета. 
											В результате Вы перейдете на страницу с детальной информацией по позиции счета. </P>
																	
									<p>На странице с детальной информацией автоматически откроется вкладка "Информация по позиции",
									на которой система выведет на экран название и номер счета, номер и дату заключения договора по счету депо,
									состояние счета (закрыт) и сумму задолженности перед депозитарием.</P>
									
									<p>На этой странице Вы можете выполнить следующие действия:</p>
					<ul class="page-content">
									
									
									<li><A HREF="/PhizIC/help.do?id=/private/depo/info/debt"><SPAN CLASS="toc2">Получить информацию о Вашей задолженности</SPAN></A></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/DepositorFormClaim"><SPAN CLASS="toc2">Просмотреть сведения из анкеты депонента</SPAN></A></li>
									<li><A href="/PhizIC/help.do?id=/private/depo/documents"><SPAN CLASS="toc2">Просмотреть список документов по счету депо</SPAN></A></li>
									
								</ul>
					
						<p>Для выполнения этих операций нажмите на кнопку <b>Операции по счету депо</b> и выберите из списка нужную операцию.</p>									
									<p>На этой странице также можно установить
									галочку в поле "Показывать на главной". В
									результате отмеченный счет будет отображаться
									на главной странице в блоке <b>Счета депо</b>.</P>

									<p>Вы можете добавить страницу с информацией по позиции счета депо 
									 в личное меню, что
										позволит Вам перейти к ней с
										любой страницы системы &quot;Сбербанк
										Онлайн&quot;, щелкнув по ссылке в боковом меню.
										Для выполнения операции нажмите на
										ссылку <b>Добавить в избранное</b>.
									</p>
									
									<p>
										На странице с информацией по позиции счета депо Вы можете просмотреть сведения по количеству ценных бумаг, 
										которые учитываются на выбранном счете депо. 
										</p>
									<div class="help-important">
									<p>
										<span class="help-attention">Примечание:</span> позиция – количество учитываемых на счете ценных бумаг по их видам.
										
									</p>
								</div>

									<p>
										Все ценные бумаги сгруппированы по разделам счета. На экран система автоматически выведет информацию для 
										базового раздела по остаткам ценных бумаг на текущую дату. Для каждой ценной бумаги в списке отображается 
										ее наименование, регистрационный номер (номер, присвоенный ценной бумаге в депозитарии) и количество ценных бумаг, 
										которые остались на счете депо.
										
									</p>
									
									<p>
										Вы можете просмотреть информацию по остаткам ценных бумаг  в каждом разделе выбранного счета депо. 
										Для того чтобы изменить раздел, в поле "Раздел счета депо" выберите из выпадающего списка нужный 
										раздел и нажмите на ссылку <b>Показать</b>. 
										
									</p>
									<p>
										Также Вы можете сформировать список ценных бумаг по способу их учета на счете депо. 
										В системе предусмотрено 3 способа учета ценных бумаг:
									</p>
									
									<ul>
											<li>Открытый учет – способ учета, при котором Вы можете давать поручения на покупку или 
											продажу определенного количества ценных бумаг без указания их характеристик (серия, номер и т.д.).</li>
											<li>Закрытый учет – способ учета, при котором Вы можете давать поручения на покупку или продажу 
											любой ценной бумаги, хранящейся на Вашем счете депо, но ценная бумага при этом должна быть удостоверена сертификатом.</li>
											<li>Маркированный учет – способ учета, при котором для оформления поручения необходимо указать количество ценных
											 бумаг и признак, по которому они объединены в группу.</li>
									</ul>
											
									<p>
										На экран автоматически выводятся ценные бумаги, которые учитываются открытым способом. 
									</p>
									<p>
										Если Вы хотите просмотреть Ваши ценные бумаги на закрытом учете, установите галочку в поле "Показывать закрытое хранение". 
									</p>
									<p>
										Для того чтобы просмотреть информацию по количеству ценных бумаг, учитываемых маркированным способом, установите галочку
										 в поле "Показывать маркированный способ учета". При этом способе ценные бумаги объединяются в группы. Для того чтобы 
										 ознакомиться с детальной информацией, Вам нужно нажать на название ценной бумаги. На экране появятся сведения о том, 
										 по какому признаку (маркеру) объединены данные ценные бумаги.
									</p>
									
									<p>
										Для того чтобы свернуть подробную разбивку группы, нажмите на ссылку <b>Скрыть детализацию</b>. 
									</p>
									<p>
										Если Вы хотите получить обновленные сведения о состоянии Вашего счета, нажмите на кнопку <b>Обновить</b>, 
										и система покажет последнюю информацию по остаткам ценных бумаг.  
									</p>
									
									<p>
										Также на данной странице Вы можете изменить название счета депо. Для этого в поле "Название" введите название счета депо, 
										которое будет отображаться на главной странице и в списке счетов депо. Затем нажмите на кнопку <b>Сохранить</b>. 
										Если Вы не укажете название, то в списке счетов и на главной странице для счета депо вместо названия будет показан его номер. 
									</p>
								<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" Вы можете переходить на нужную страницу не только с помощью 
										пунктов верхнего меню, ссылок бокового меню, но и с помощью ссылок, расположенных под главным меню. Данные 
										ссылки показывают путь от главной страницы до той, с которой Вы перешли на текущую страницу. Вы можете использовать эти ссылки для 
										перехода на интересующую страницу.  
									</p>
								</div>								
								
										<h2><a id="p2">Остальные счета депо</a></h2>
									<p>На странице с информацией по позиции счета в блоке <b>Остальные счета депо</b> Вы можете просмотреть список остальных 
									Ваших счетов депозитарного учета, подключенных к услуге "Сбербанк Онлайн". Для того чтобы просмотреть информацию 
									о каждом из этих счетов, щелкните по наименованию или номеру выбранного счета.</P>
								
								<P>Если Вы хотите перейти на страницу со списком счетов депо, щелкните по ссылке <b>Показать все</b>.</P>
								
								<p>В системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
								который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые вопросы</b> 
								в боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые вопросы по работе 
								с системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы консультант отображался на страницах системы, то измените 
								настройки отображения помощника, щелкнув по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы 
								скрыть помощника, Вам нужно в блоке <b>Дополнительные настройки</b> убрать галочку в поле "Отображать персонального консультанта" 
								и нажать на кнопку <b>Сохранить</b>. --></p>

								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
											<li><span><a href="/PhizIC/help.do?id=/private/payments/payment/ConvertCurrencyPayment">Обмен валюты</a></li>
											<li><span><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
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