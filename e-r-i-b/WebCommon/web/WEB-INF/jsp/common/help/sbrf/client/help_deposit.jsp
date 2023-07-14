<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<head>
<title>Помощь. Вклады и счета</title>
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
										<c:if test="${phiz:impliesService('RemoteConnectionUDBOClaimRemoteConnectionUDBOClaim')}">
										<li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">Больше Сбербанк Онлайн</a></li>
										</c:if>
									<li><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a>
											<ul>
											<li><a href="/PhizIC/help.do?id=/private/accounts/info">Информация по вкладу/счету</a></li>
											</ul>
									</li>
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
                <div class="contextTitle">Вклады и счета</div>

								<p>На странице <b>Вклады и счета</b> Вы можете
								просмотреть список Ваших активных и 
								заблокированных вкладов и счетов, просмотреть список закрытых вкладов, пополнить вклад, списать деньги со 
								вклада и выполнить другие операции.</p>

								<p>По каждому вкладу на данной странице Вы 
								можете просмотреть следующую информацию: вид 
								вклада, номер счета по вкладу, процентную ставку по вкладу,
								сумму вклада, его статус (на счет наложен арест) и сумму, доступную для 
								снятия.</P>
							<div class="help-important">
									<p>
										<span class="help-attention">Примечание</span>: если статус интересующего
										Вас вклада выделен в списке красным цветом (утеряна сберкнижка, заблокирован), это означает, что 
										операции по нему приостановлены по какой-либо причине. 
									</p>
									</div>
								
								<p>По вкладам, открытым для достижения цели, отображается название и срок достижения цели и шкала достижения цели, на которой обозначена сумма, хранящаяся на вкладе, а также сумма, необходимая для осуществления цели.</p>
								
								<p>На данной странице по каждому вкладу 
									Вы можете просмотреть список трех последних операций, 
									которые Вы совершили в системе. Для того чтобы развернуть список 
									последних операций, рядом 
									с интересующим Вас вкладом щелкните по ссылке <b>
									Показать операции</b>. </p>
									<P>В этом 
									списке по каждой операции Вы можете увидеть 
									наименование операции, дату 
									совершения операции и сумму, на которую она 
									совершена.</P>
									<p>Если Вы не хотите, чтобы на данной 
									странице отображались операции по вкладу, то 
									рядом с выбранным вкладом щелкните по ссылке <b>
									Скрыть операции</b>.</P>
									<!--<p>На этой странице также можно установить 
									галочку в поле &quot;Показывать на главной&quot;. В 
									результате данный вклад будет отображаться 
									на главной странице в блоке <b>Вклады и счета</b>.</P>-->
									<p>По каждому вкладу из списка Вы можете 
									пройти на страницу <b>с детальной 
									информацией по вкладу/счету</b> и выполнить 
									следующие действия: просмотреть список 
									выполненных операций, распечатать выписку, просмотреть 
									дополнительную информацию по данному вкладу и 
									т.д. Для этого выберите интересующий Вас 
									вклад в списке и щелкните по его названию или иконке рядом с ним. В результате 
									система откроет страницу с подробной 
									информацией по данному вкладу. </P>

								<p>С каждым вкладом можно выполнить следующие 
								действия:</P>
								<ul class="page-content">
									<li><A HREF="/PhizIC/help.do?id=//private/payments/servicesPayments/edit/*"><SPAN CLASS="toc2">Оплатить товары или услуги</SPAN></A></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/InternalPayment/null"><SPAN CLASS="toc2">Пополнить вклад</SPAN></A></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/InternalPayment/null"><SPAN CLASS="toc2">Перевести средства между своими счетами и картами</SPAN></A></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/JurPayment"><SPAN CLASS="toc2">Перевести средства организации</SPAN></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/NewRurPayment/null/ourBank"><SPAN CLASS="toc2">Перевести деньги частному лицу</SPAN></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/payment/AccountOpeningClaimWithClose/null">Открыть вклад (закрыть счет списания)</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/payment/AccountClosingPayment/null">Закрыть вклад</a></li>
										<c:if test="${phiz:impliesService('CreateMoneyBoxOperation')}">
										<li><a href="/PhizIC/help.do?id=/private/accounts/moneyBoxList">Подключить копилку</a></li>
										</c:if>
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/payments/payment/LossPassbookApplication">Заявить об утере сберкнижки</a></li>
									
									<!-- <li><A HREF="/PhizIC/help.do?id=/private/payments/payment/ChangeDepositMinimumBalanceClaim"><SPAN CLASS="toc2">Изменить неснижаемый остаток по вкладу</SPAN></A></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/InternalPayment/null"><SPAN CLASS="toc2">Перевести средства между своими счетами и картами</SPAN></A></li> 
									 "/PhizIC/help.do?id=/private/payments/payment/RurPayment/null" -->																	
									

								</ul>
								<p>Для выполнения этих операций нажмите на кнопку <b>Операции</b> и выберите из списка нужную операцию.</p>
								<p>Если Вы хотите <b>подать в банк заявку на открытие или закрытие вклада</b>, то вверху страницы нажмите на ссылку <b>Открытие вклада</b>, <b>Открытие вклада (закрыть счет списания)</b> или <b>Закрытие вклада</b>.</p>
									<c:if test="${phiz:impliesService('ClientPromoCode')}">
									<p>В системе "Сбербанк Онлайн" Вы можете подать заявку на открытие вклада на льготных условиях с помощью промокода.</p>
                                	<div class="help-important">
										<p>
											<span class="help-attention">Обратите 
											внимание</span>: промокод - это специальный код, который позволяет Вам открыть вклад в Сбербанке на наиболее выгодных условиях.  
										</p>
									</div>
									<p>Для того чтобы ввести промокод, нажмите на ссылку <b>У меня есть промокод</b>. Откроется поле, в котором введите Ваш код и нажмите на кнопку <b>Добавить</b>. В результате данный промокод будет сохранен в системе "Сбербанк Онлайн",
									и Вам будет доступно открытие вкладов по специальным ставкам. Для того чтобы свернуть открытый блок, нажмите на ссылку <b>У меня есть промокод</b> еще раз.</p>
									<p>Если Вы уже вводили промокоды раньше, Вы можете просмотреть их в своем <b>Профиле</b>, нажав на ссылку <b>Все мои промокоды</b>.</p>
									</c:if>
								<p>Для того чтобы просмотреть список закрытых вкладов, щелкните по ссылке <b>Закрытые вклады</b> под списком действующих вкладов. В результате откроется список закрытых вкладов.</p>
								<p> По каждому закрытому вкладу будет указана следующая информация: вид вклада, номер счета по вкладу, сумма вклада и его состояние, выделенное красным цветом.</p>
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>Кроме того, в системе "Сбербанк Онлайн" на каждой странице можно обратиться 
								к помощи персонального консультанта, который ответит на все 
								Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку 
								<b>Часто задаваемые вопросы</b> в боковом меню. В результате 
								откроется окно, в котором содержатся ответы на часто задаваемые 
								вопросы по работе с системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, 
								чтобы консультант отображался на страницах системы, то измените 
								настройки отображения помощника, щелкнув по кнопке <b>Настройки</b> - 
								<b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы скрыть помощника, Вам нужно 
								в блоке <b>Дополнительные настройки</b> убрать галочку в поле "Отображать 
								персонального консультанта" и нажать на кнопку <b>Сохранить</b>.  --></p>

								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
									<li><span><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
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