<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Информация о задолженности</title>
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
											<li><a href="/PhizIC/help.do?id=/private/depo/info/position">Информация по позиции счета депо</a></li>	
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/depo/info/debt">Информация о задолженности</a></li>
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
                <div class="contextTitle">Счета депо. Информация о задолженности</div>
									<p>По каждому счету депозитарного учета Вы можете просмотреть и оплатить задолженность 
									перед депозитарием. 
									Для этого в пункте меню <b>Счета депо</b> нажмите на кнопку <b>Операции</b>, откроется список операций по счету депо.
									Далее щелкните по ссылке <b>Задолженность</b> рядом с выбранным Вами счетом. </P>
									<p>Система выведет на экран информацию по задолженности на текущую дату, номер счета депо, 
									итоговую сумму задолженности перед депозитарием и список выставленных счетов на оплату.</P>
									
									
								
									<p>В списке выставленных на оплату счетов отображается номер счета, дата его выставления, 
									расчетный период (период, за который была начислена задолженность) и сумма счета (сумма задолженности). </P>
									
									<p>На экране отображается 10 последних счетов на оплату. Для того чтобы посмотреть остальные счета, 
									Вы можете перейти на следующую страницу списка с помощью кнопки "<b>></b>".</P>
									<p>Вы можете выбрать, сколько счетов будет показано на странице - 10, 20 или 50.
									Например, если Вы хотите просмотреть 20 счетов на оплату,
									то внизу списка в строке "Показывать по" выберите значение "20". Система выведет на экран 
									20 последних счетов на оплату.</p>
									<p>Для получения обновленных сведений о Вашей задолженности перед депозитарием нажмите 
									на кнопку <b>Обновить задолженность</b>, и система покажет последнюю информацию по Вашей задолженности.</P>
									<p>Для того чтобы погасить задолженность за услуги по счету депо перед Депозитарием, Вам нужно 
									напротив интересующей Вас задолженности нажать на ссылку <b>Оплатить</b>. Откроется форма перевода, на которой заполните
									реквизиты и нажмите на кнопку <b>Перевести</b>. (Заполнение формы описано в разделе справки 
									<a class="active-menu" href="/PhizIC/help.do?id=/private/payments/payment/JurPayment/null">Перевод организации и оплата услуг</a>).</p>

									<p>
									Если Вы хотите вернуться к списку Ваших счетов депозитарного обслуживания, нажмите на ссылку <b>Назад к списку счетов депо</b>. </p>
									
									
									
									<p>Вы можете добавить страницу с информацией о задолженности перед депозитарием 
									 в личное меню, что
										позволит Вам перейти к ней с
										любой страницы системы &quot;Сбербанк
										Онлайн&quot;, щелкнув по ссылке в боковом меню.
										Для выполнения операции нажмите на
										ссылку <b>Добавить в избранное</b>. Если страница уже добавлена в Личное меню, то на этой странице будет отображаться ссылка <IMG border="0" src="${globalUrl}/commonSkin/images/favouriteHover.gif" alt=""><b>В избранном</b>.
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
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>Кроме того, в системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
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
											<li><span><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
											<li><span></span><a href="/PhizIC/help.do?id=/private/accounts">Главная</a></li>
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