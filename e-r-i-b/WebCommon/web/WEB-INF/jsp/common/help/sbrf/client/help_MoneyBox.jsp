<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<head>
<title>Помощь. Подключение копилки</title>
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
            <li><a href="/PhizIC/help.do?id=/private/payments">Переводы и платежи</a>
            <li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">Больше Сбербанк Онлайн</a></li>
            <li><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
            <li><a class="parentItem" href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a> 
            <ul  class="page-content">
					<li><a class="active-menu" href="">Подключение копилки</a></li>
				</ul>           
            </li>
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
            <li><a href="/PhizIC/help.do?id=/private/news/list">События</a></li>
            <li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>


        </ul>

		 <a href="/PhizIC/faq.do" class="faq-help">
                    <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
                </a>
            </div>
	<!-- end help-left-section -->
	<div id="help-workspace">
        <div class="contextTitle">Подключение копилки</div>
      
<p><a href="#p1">Создание  копилки</a></p>
<p><a href="#p2">Подтверждение копилки</a></p>
<p><a href="#p3">Просмотр копилки</a></p>
<p>Сервис <b>Копилка</b> позволит Вам совершать переводы денежных средств с карты на счет по расписанию.</p> 
<p>Для того чтобы подключить копилку, на<b> Главной</b> странице выберите карту или вклад, для которого Вы хотите создать копилку, нажмите на кнопку <b>Операции</b> и выберите <b>Подключить копилку</b>.</p>
<p>Также Вы можете создать копилку на странице <b>Вклады и счета</b>, для этого нажмите на кнопку <b>Операции</b> и выберите <b>Подключить копилку</b> или на странице детальной информации по вкладу  на вкладке <b>Копилка</b>, щелкнув по ссылке <b>Настроить пополнение</b>.
Кроме того, Вы можете подключить копилку со страницы детальной информации по карте.</p>
<div class="help-important">
<p>
<span class="help-attention">Примечание:</span> если к данному счету/вкладу невозможно подключить копилку, Вы увидите соответствующее сообщение.</p>
</div>

	<H3><a id="p1">Создание копилки</a></H3>
<p>Для того чтобы создать копилку, на странице детальной информации по вкладу на вкладке <b>Копилка</b> нажмите на ссылку <b>Настроить пополнение</b>.</p>
<p>Откроется страница создания копилки, на которой Вам необходимо заполнить следующие поля:</p>
	<ul>
		<li>Название копилки, которое Вы указываете самостоятельно, например, "Моя копилка". Для того чтобы задать название копилки, щелкните по значку <IMG border="0" src="${globalUrl}/commonSkin/images/pencil.gif" alt="">, введите название и нажмите на кнопку <b>Сохранить</b>.
		 Если Вы не зададите название копилки, то оно будет присвоено автоматически, в соответствии с заданными Вами параметрами.</li>
		<li>В поле "Карта списания" выберите из выпадающего списка карту, с которой будет пополняться копилка.</li>
		<li>В поле "Вид копилки" выберите из выпадающего списка, каким образом будет пополняться Ваша копилка. Например, она может быть пополнена на <b>фиксированную сумму</b>, <b>процент от расходов</b> или <b>процент от зачислений</b>.</li>
		<p>Затем в зависимости от типа копилки укажите параметры автоперевода.</p>
		<p>Для типа <b>Фиксированная сумма</b> укажите следующие сведения:</p>
			<ul>
				<li>В поле "Периодичность" выберите из выпадающего списка, как часто будет пополняться Ваша копилка, например, <b>раз в месяц</b> или <b>раз в год</b>.</li>
				<li>В поле "Дата ближайшего пополнения" укажите дату первого пополнения копилки. Для этого нажмите на иконку календаря <IMG border="0" src="${globalUrl}/commonSkin/images/datePickerCalendar.gif" alt=" height="20" width="20""> и выберите интересующую Вас дату.</li>
					<div class="help-important">
					<p>
					<span class="help-attention">Обратите внимание</span>: существуют ограничения по выбору даты первого пополнения копилки, так как она не может быть намного позже текущей даты. Например, Вы создаете копилку 15 сентября и
					она будет пополняться раз в месяц, значит первое пополнение должно произойти не позже 15 октября.
					</p>
					</div>
				<li>В поле "Сумма пополнения" впишите сумму, которая будет перечисляться на вклад.</li>
			</ul>						
		<p> Для типа копилки <b>Процент от зачисления</b> Вам необходимо задать следующие настройки копилки:</p> 
			<ul>
				<li>В поле "Процент от суммы" укажите, сколько процентов от суммы зачисления должно переводиться на вклад, по которому Вы создаете копилку.</li>
				<li>В поле "Максимальная сумма" впишите максимальную сумму пополнения копилки.</li>
			</ul>	
		<p>При создании копилки типа <b>Процент от расходов</b> укажите следующие параметры:</p>
			<ul>
				<li>В поле "Процент от суммы" укажите, сколько процентов от суммы списания должно переводиться на вклад, по которому Вы создаете копилку.</li>
				<li>В поле "Максимальная сумма" впишите максимальную сумму пополнения копилки.</li>
		</ul>
	</ul> 
<p>После того как Вы заполнили все необходимые поля, нажмите на кнопку <b>Подключить</b>. После этого заявке будет присвоен статус "Черновик", и Вы перейдете на форму подтверждения,
на которой Вы сможете проверить правильность заполнения полей.</p>

	<H3><a id="p2">Подтверждение копилки</a></H3>
<p>Далее Вам необходимо будет подтвердить заявку на подключение копилки. На странице подтверждения Вы увидите заполненную форму заявки, в которой проверьте правильность указанных сведений. Если все поля заполнены правильно, Вы можете подтвердить заявку одним из способов:</p>
	<ul>
		<li>Если Вы хотите подтвердить подключение SMS-паролем, нажмите на ссылку <b>Подтвердить по SMS</b>;</li>
		<li>Если Вы хотите подтвердить операцию другим способом, нажмите на ссылку <b>Другой способ подтверждения</b> и выберите интересующий Вас способ: подтвердить создание копилки <b>Паролем с чека</b> или <b>Push-паролем из мобильного приложения</b>.</li>
	</ul>
<p>Затем откроется всплывающее окно, в котором введите одноразовый пароль и нажмите на кнопку <b>Подтвердить</b>. После этого Вы увидите форму просмотра заявки на создание копилки.</p>
<p>Для того чтобы изменить данные о копилке, нажмите на ссылку <b>Редактировать</b>, Вы перейдете к форме создания копилки, на которой сможете внести необходимые изменения.</p>
<p>Если Вы передумали подключать копилку, нажмите на ссылку <b>Отменить</b>. После этого Вы вернетесь на страницу, с которой перешли к созданию копилки.</p>	

	<H3><a id="p3">Просмотр копилки</a></H3>					
<p>После создания заявки Вы перейдете к форме ее просмотра. После подтверждения заявка отправляется в банк и ей присваивается статус <b>Подтверждена</b>.</p>
<p>Для того чтобы просмотреть информацию  о копилках, подключенных к вкладу или счету, щелкните по вкладке <b>Копилка</b> на странице детальной информации по счету/вкладу. На открывшейся вкладке Вы увидите список всех подключенных копилок. 
Также Вы можете просмотреть копилки, которые пополняются с определенной карты на странице детальной информации по интересующей Вас карте.</p>
<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
										<li><span> <a href="/PhizIC/help.do?id=/private/accounts/operations">Детальная информация по вкладу</a></li>
										<li><span> <a href="/PhizIC/help.do?id=/private/cards/info">Информация по карте</a></li>
									</ul>
								</div>

</div>
	<!-- end help-workspace -->
</div>
<!-- end help-content -->

<div class="clear"></div>

</body>					