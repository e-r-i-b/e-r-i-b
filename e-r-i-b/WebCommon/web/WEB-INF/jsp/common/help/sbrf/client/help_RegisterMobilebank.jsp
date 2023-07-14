<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Подключение к Мобильному банку</title>
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
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a>
												<ul>
													<li><a class="active-menu" href="/PhizIC/help.do?id=login/register-mobilebank">Подключение к Мобильному банку</a></li>
													<li><a href="/PhizIC/help.do?id=/private/mobilebank/payments/select-service-provider">Создание SMS-шаблона</a></li>
												</ul>
											</li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a>
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
                <div class="contextTitle">Подключение к Мобильному банку</div>


								<ul class="page-content">
									<li><a href="#p2">Создать заявку</a></li>
									<li><a href="#p3">Подтвердить заявку</a></li>
									<li><a href="#p4">Просмотреть заявку</a></li>

								</ul>
							<p><b>Мобильный банк</b> - это услуга, с помощью которой Вы можете совершать 
							различные платежи и информационные операции 
							со своих банковских карт через мобильный телефон.</P>
																							<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" Вы можете переходить на нужную страницу не только с помощью 
										пунктов верхнего меню, ссылок бокового меню, но и с помощью ссылок, расположенных под главным меню. Данные 
										ссылки показывают путь от главной страницы до той, с которой Вы перешли на текущую страницу. Вы можете использовать эти ссылки для 
										перехода на интересующую страницу.  
									</p>
								</div>
							
								<h2><a id="p2">Создать заявку</a></h2>
								<p>При входе в систему автоматически открывается заявка на подключение к услуге "Мобильный банк". Также Вы можете подключиться к услуге с помощью <b>Личного меню</b>. Для этого в <b>Личном меню</b> нажмите на ссылку <b>Мобильный банк - Детали подключения</b>. Откроется страница, на которой нажмите на ссылку <b>Подключить</b>.</P>
								<p>Для перехода к заявке необходимо сначала выбрать пакет подключения к услуге:
								<ul>
								<li><b>Экономный пакет</b></li>
								<p>При использовании услуги Вы не будете получать SMS-оповещения об операциях по карте. Данный пакет является бесплатным, взимается только комиссия за запрос баланса и выписки.</p>
								<li><b>Полный пакет</b></li>
								<p>Данный пакет обслуживания включается в себя все виды SMS-оповещений. Ежемесячная комиссия составляет от 0 до 60 рублей в зависимости от типа карты. </p>
								<div class="help-important">
									<p>
										<span class="help-attention">Примечание</span>: услуга "Мобильный банк" является бесплатной для всех типов премиальных дебетовых карт (Visa Gold/ Platinum/ Infinite, MasterCard Gold/ Platinum), а также для всех типов кредитных карт Сбербанка.

									</p>									
								</div>
								</ul>
								<p>Ознакомьтесь с тарифами предоставления услуги, затем выберите нужный Вам пакет обслуживания и рядом с выбранным пакетом нажмите на кнопку <b>Подключить</b> или нажмите на весь пакет.</p>
								<p>Если Вы не хотите подключать услугу, то щелкните по ссылке <b>Подключиться позже</b>. В результате Вы перейдете к странице подтверждения входа в систему. Если Вы уже зашли в систему, то перейдете на главную страницу.</p>
								<p>После выбора пакета обслуживания откроется страница с заявкой на подключение к услуге, на которой заполните следующие поля:</p>
								<ul>
								<li>В поле "Пакет обслуживания" будет показан выбранный Вами тариф;</li>
								<li>В поле "Номер телефона" Вы увидите номер мобильного телефона, к которому будет подключена услуга. При этом в номере мобильного телефона будут показаны только первые 3 и последние 4 цифры. Если Вы хотите изменить номер телефона, то Вам нужно обратиться в отделение банка.</li>
								<li>В поле "Номер карты" выберите из выпадающего списка номер карты, к которой Вы хотите подключить услугу.</li>
								</ul>
								<p>После того как все сведения указаны, нажмите на кнопку <b>Подтвердить</b>.</p>
								<p>Если Вы хотите вернуться к выбору пакета обслуживания, то нажмите на ссылку <b>Назад</b>.</p>
								<p>Если Вы не хотите подключать услугу, то нажмите на ссылку <b>Подключиться позже</b>. В результате Вы перейдете к странице подтверждения входа в систему. Если Вы уже зашли в систему, то перейдете на главную страницу.</p>
									<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: Вы можете контролировать процесс выполнения операции с помощью
										линии вверху формы, на которой будет выделено состояние операции на данный момент.
										Например, если Вы находитесь на странице подтверждения, то будет выделен отрезок 
										"Подтверждение".  
									</p>
								</div> 

								<h2><a id="p3">Подтвердить заявку</a></h2>
								<p>Далее необходимо подтвердить заявку на подключение к услуге "Мобильный банк". После 
								того как Вы нажали на кнопку <b>Подтвердить</b>, 
								Вам откроется заполненная форма заявки, в 
								которой нужно проверить правильность указанных 
								сведений, после чего выполнить одно из следующих 
								действий:
									</p>
									<ul>
									<li><b>Подтвердить заявку</b>. Убедитесь, что вся информация указана 
									верно. Затем, для подтверждения операции, нажмите на кнопку <b>Подтвердить по SMS</b>. Откроется всплывающее окно,
									в котором введите пароль, полученный в SMS-сообщении, и нажмите на кнопку <b>Подтвердить</b>.
									В результате Вам откроется страница просмотра заявки.
									<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: перед вводом пароля убедитесь, что реквизиты операции совпадают с 
										текстом SMS-сообщения. Будьте осторожны, если данные не совпадают, ни в коем случае не вводите пароль
										и никому его не сообщайте, даже сотрудникам банка. 
									</p>
								</div> 

									</li>
									<li><b>Изменить реквизиты</b>. Если Вы хотите изменить заявку, то 
									нажмите на ссылку <b>Назад</b>. В результате 
									Вы вернетесь на страницу заполнения 
									полей заявки.
									</li>
									<li><b>Отменить операцию</b>. Если Вы передумали совершать операцию, то 
									нажмите на ссылку <b>Подключиться позже</b>. В результате Вы перейдете к странице подтверждения входа в систему. Если Вы уже вошли в систему, то перейдете на главную страницу.</p>
									</li>
									</ul>
								<h2><a id="p4">Просмотреть заявку</a></h2>
									<p>После подтверждения заявки Вы перейдете 
									на страницу просмотра заявки, на которой 
									увидите заполненный документ. О том, что 
								 операция успешно принята на обработку в банк, свидетельствует сообщение,
									отображаемое вверху страницы.</p>
									<p>На этой странице Вы 
									сможете <b>просмотреть реквизиты</b> выполненной 
									заявки.</p>
								<p>Для того чтобы войти в систему "Сбербанк Онлайн", нажмите на кнопку <b>Продолжить</b>. Если Вы уже вошли в систему, то после нажатия на кнопку <b>Продолжить</b> Вы перейдете на главную страницу системы.</p>
								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
											<li><span></span><a href="/PhizIC/help.do?id=/private/receivers/list">Шаблоны платежей</a></li>
										<li><span></span><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>
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