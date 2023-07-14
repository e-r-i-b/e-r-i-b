<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Создание SMS-шаблона</title>
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
													<li><a href="/PhizIC/help.do?id=login/register-mobilebank">Подключение к Мобильному банку</a></li>
													<li><a class="active-menu" href="/PhizIC/help.do?id=/private/mobilebank/payments/select-service-provider">Создание SMS-шаблона</a></li>									
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
                <div class="contextTitle">Создание SMS-шаблона</div>

						<p>В системе "Сбербанк Онлайн" доступно создание SMS-шаблона для оплаты услуг двумя способами:</P>
									<ul class="page-content">
										<li><a href="#p1">Создать 
										SMS-шаблон на основе существующего 
										шаблона платежа</a></li>
										<li><a href="#p2">Создать новый 
										SMS-шаблон</a></li>
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
									
							<h3><a id="p1">Создание SMS-шаблонов на основе 
							&quot;Шаблонов платежей&quot;</a></h3>
								<p>Для того чтобы создать SMS-шаблон на основе 
								&quot;Шаблонов платежей&quot;, на странице <b>Мобильный банк</b> 
								на вкладке "SMS-запросы и шаблоны" щелкните по кнопке <b>Создать SMS-шаблон</b>. Откроется страница для создания 
								SMS-шаблона на основе существующего шаблона 
								платежа.</P>
								<p>На данной странице Вам необходимо в 
								списке нажать на  нужный шаблон платежа, 
								который Вы добавили в раздел &quot;Шаблоны&quot;. 
								Затем 
								откроется страница, на которой заполните нужные поля и нажмите на кнопку <b>Сохранить SMS-шаблон</b>. Откроется страница 
									подтверждения, на которой щелкните по кнопке <b>Подтвердить по SMS</b>, 
									система покажет всплывающее окно, в котором введите SMS-пароль и нажмите на кнопку <b>Подтвердить</b>.  
									В результате откроется страница <b>Мобильный банк</b>, и на вкладке "SMS-запросы и шаблоны"
									 появится новый шаблон.
									</p>	
																	
								<p>Если Вы не хотите создавать SMS-шаблоны на 
								основе &quot;Шаблонов платежей&quot;, то нажмите на ссылку <b>
								Отменить</b>.</P>
								
							<h3><a id="p2">Создание нового SMS-шаблона</a></h3>
								<p>Для того чтобы создать новый SMS-шаблон, Вам 
								нужно на странице <b>Создание SMS-шаблона</b> 
								нажать на кнопку <b>Создать новый SMS-шаблон</b> 
								и выполнить следующие действия:</P>
								<ul>
								<li><b>Выбрать категорию услуг</b>. Для этого щелкните по названию выбранной 
								категории услуг, например, "Оплата товаров и услуг". В результате 
								откроется страница, на которой нужно выбрать 
								поставщика услуги и нужную операцию.</P>
								</li>
								<li><b>Найти поставщика услуги</b>. Для того чтобы выполнить поиск 
										интересующей Вас организации, услуги 
										которой Вы хотите оплатить, введите в 
										поле поиска название услуги, 
										ИНН или расчетный счет поставщика услуги 
										и нажмите на кнопку <b>Найти</b>. Вы можете ввести 
										в поле поиска только несколько первых букв наименования поставщика услуги.</p>
										<p>В итоге система выведет на экран 
										список интересующих Вас организаций.</p> 
										</li>
										<li><b>Выбрать регион</b>. Вы можете выполнить поиск поставщика 
										услуги по региону, в котором зарегистрирован данный 
										поставщик. Для этого нажмите на кнопку <b>Все регионы</b>, расположенную в строке поиска. Откроется справочник регионов, 
										в котором будут показаны все регионы в алфавитном порядке. Для того чтобы выбрать регион 
										в данном справочнике, щелкните по его названию. Откроется список подчиненных регионов. 
										Затем, если Вы хотите выбрать регион верхнего уровня или регион подчиненного уровня, щелкните по наименованию нужного региона. 
										Если Вы хотите выбрать 
										все регионы, то щелкните по ссылке <b>Выбрать все регионы</b>. Далее откроется всплывающее окно, в котором нажмите на кнопку <b>Сохранить</b>, если Вы хотите постоянно оплачивать услуги в выбранном регионе. Если Вы хотите совершить оплату в данном регионе один раз, то нажмите на кнопку <b>Отменить</b>. В результате название выбранного региона будет показано рядом со строкой поиска, и поиск поставщиков услуг будет выполняться в выбранном Вами регионе. 
										</li>
								<li><b>Затем заполнить необходимые поля</b>, например, для 
								оплаты услуг МТС нужно заполнить поле "Номер телефона". Для этого на странице выбора поставщика услуги в поле 
								&quot;Номер телефона&quot; введите номер телефона, который 
								Вы будете оплачивать с помощью создаваемого 
								SMS-шаблона.
							<div class="help-important">
									<p>
										<span class="help-attention">Примечание</span>: 
										номер телефона вводится, начиная с кода 
										оператора, например, 9167898080.</i> </P>

									</p>									
								</div>
								</li>
								</ul>
								<p>После того как все действия выполнены, 
								нажмите на кнопку <b>Сохранить SMS-шаблон</b>. Далее 
								откроется страница подтверждения, на которой Вам нужно ввести
									одноразовый пароль из SMS-сообщения. Для этого щелкните по кнопке <b>Подтвердить по SMS</b>, 
									откроется всплывающее окно, в котором введите SMS-пароль и нажмите на кнопку <b>Подтвердить</b>.  
									 В результате созданный SMS-шаблон будет показан в списке SMS-шаблонов на странице 
									 <b>Мобильный банк</b> на вкладке "SMS-запросы и шаблоны".</P>
								<p>Если Вы решили не создавать SMS-шаблон, то 
								нажмите на ссылку <b>Отменить</b>.</P>
								<p>С данной страницы Вы можете вернуться на 
								страницу выбора вида услуги, щелкнув по ссылке <b>
								Назад к выбору услуг</b>.</P>
								<p>В системе "Сбербанк Онлайн" на каждой странице можно 
								обратиться к помощи персонального консультанта, который 
								ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите 
								на ссылку <b>Часто задаваемые вопросы</b> в боковом меню. В результате 
								откроется окно, в котором содержатся ответы на часто задаваемые вопросы 
								по работе с системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы консультант 
								отображался на страницах системы, то измените настройки отображения помощника, 
								щелкнув по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того 
								чтобы скрыть помощника, Вам нужно в блоке <b>Дополнительные настройки</b> убрать 
								галочку в поле "Отображать персонального консультанта" и нажать на кнопку <b>Сохранить</b>. -->.</p>

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