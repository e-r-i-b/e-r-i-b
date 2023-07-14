<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<head>
<title>Помощь. Карты</title>
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
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/cards/list">Карты</a>
										<ul>
											<li><a href="/PhizIC/help.do?id=/private/cards/info">Информация по карте</a></li>
										</ul>
									</li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a></li>
									<li><a href="/PhizIC/help.do?id=/private/security/list">Сертификаты</a></li>
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

        <div class="contextTitle">Карты</div> 

									<p>На странице <b>Карты</b> Вы можете 
									просмотреть список Ваших карт, доступ к 
									которым разрешен в системе "Сбербанк 
									Онлайн", увидеть список закрытых и 
									заблокированных карт, оплатить услуги и 
									выполнить многие другие операции. </p>

									<p>В списке для каждой карты отображается следующая информация:</p>
									<ul><li><b>Наименование карты</b>, например, <b>Карта жены</b>.</li>
								 		<li><b>Тип карты</b>. В системе предусмотрено 4 типа карт: 
											<ul>
												<li><b>Дебетовая карта без овердрафта</b> – это платежная карта, с 
										помощью которой Вы можете тратить деньги 
										в пределах суммы на Вашем счете в банке. </li>
												<li><b>Дебетовая карта с овердрафтом</b> - это платежная карта, с которой Вы можете 
										снимать деньги со своего счета карты и еще определенную сумму в кредит, 
										но не выше установленного банком лимита. Овердрафт (сумма перерасхода) 
										должен быть погашен не позднее срока, указанного в договоре с банком. В 
										противном случае на эту сумму будут начислять пени.</li> 
												<li><b>Кредитная карта</b> - это карта, с которой можно расплачиваться за 
										покупки или снимать с нее деньги в кредит. Часто он бывает беспроцентным, 
										если погашается в течение определенного периода времени, например, за 30 
										дней, после чего начинают начислять проценты.</li>
											<li><b>Виртуальная карта</b> - это банковская карта, с помощью которой
								 Вы можете оплачивать товары и услуги
								интернет-магазинов.</li>
											</ul>
											Для основных дебетовых карт отображается значение "дебетовая", а для кредитных - "кредитная". Для дополнительных карт тип не отображается.</li>
								 		<li><b>Номер карты</b> - последние четыре цифры номера карты.</li>
								 		<li><b>Срок ее действия</b> - дата, до которой Вы сможете пользоваться картой.</li>
								 		<li><b>Статус карты</b>, например,  подлежит выдаче, заблокирована, на счет наложен арест.</li>
								 		<li><b>Сумма доступных средств</b> - сколько денег в данный момент у Вас на карте.</li>
										<li>Для кредитных карт отображается <b>установленный кредитный лимит</b> - сумма, которую Вам предоставляет банк на определенных условиях.</li>
										<li>Для дебетовых карт с овердрафтом указан <b>лимит овердрафта</b> - сумма, которую Вам предоставляет банк, если у Вас недостаточно собственных средств на счете.</li>
								 		<li><b>Сумма обязательного платежа</b> - минимальная сумма, которую необходимо внести для погашения кредита по карте.</li>
										<div class="help-important">
												<p>
													<span class="help-attention">Обратите внимание</span>: сумма обязательного платежа отображается только для кредитных и дебетовых карт с овердрафтом.

												</p>
											</div>
									
								 		<li>Для кредитных карт указана <b>дата, до которой необходимо погасить задолженность</b>, то есть дата окончания льготного периода кредитования.</li>
								 	</ul>		
								<p>Если в списке есть и основные, и дополнительные 
								карты, то дополнительные карты будут показаны под основной с надписью "Дополнительная".</P>
								<div class="help-important">
									<p>
										<span class="help-attention">Примечание</span>: если статус интересующей Вас карты выделен 
										в списке красным цветом, это означает, что операции по ней приостановлены по какой-либо причине.

									</p>
								</div>
								
									<p>На данной странице для каждой карты 
									Вы можете просмотреть список трех последних операций, 
									которые Вы совершили в системе. Для того чтобы развернуть список 
									последних операций, рядом 
									с интересующей картой щелкните по ссылке <b>Показать операции</b>. </P>В этом 
									списке по каждой операции Вы можете увидеть 
									наименование операции, дату и время 
									выполнения операции и сумму, на которую она 
									совершена.</P>
									<p>Если Вы не хотите, чтобы на данной 
									странице отображались операции по карте, то 
									рядом с выбранной картой щелкните по ссылке <b>
									Скрыть операции</b>.</P>
									<!--<p>На данной странице также можно установить 
									галочку в поле &quot;Показывать на главной&quot;. В 
									результате данная карта будет отображаться 
									на главной странице в блоке <b>Карты</b>.</P>-->
									<p>По каждой карте из списка Вы можете 
									пройти на страницу <b>с детальной 
									информацией по карте</b> и выполнить 
									следующие действия: просмотреть список 
									выполненных операций, распечатать выписку 
									или получить ее на e-mail, просмотреть 
									дополнительную информацию по данной карте и 
									т.д. Для этого выберите интересующую Вас 
									карту в списке и щелкните по ее названию или иконке рядом с ней. В результате 
									система откроет страницу с подробной 
									информацией по данной карте. </P>
										<div class="help-important">
									<p>
										<span class="help-attention">Примечание</span>: если статус интересующей
										Вас карты выделен в списке красным цветом, 
										это означает, что операции по ней приостановлены по 
										какой-либо причине.
									</p>
								</div>
								<p>Если Вы хотите <b>подать в банк заявку на получение кредитной</b>, <b>виртуальной карты</b> или <b>перевыпустить карту</b>, то вверху страницы нажмите на соответствующую ссылку.</p>
									<p>На странице <b>Карты</b>, а также на <b>
									Главной</b> странице и на странице с 
									детальной информацией по карте Вы можете 
									выполнить следующие действия с каждой 
									картой:</P>
									<ul class="page-content">
									<li><A HREF="/PhizIC/help.do?id=//private/payments/servicesPayments/edit/*"><SPAN CLASS="toc2">Оплатить товары или услуги</SPAN></A></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/InternalPayment/null"><SPAN CLASS="toc2">Пополнить карту</SPAN></A></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/InternalPayment/null"><SPAN CLASS="toc2">Перевести средства между своими счетами и картами</SPAN></A></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/JurPayment"><SPAN CLASS="toc2">Перевести средства организации</SPAN></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/NewRurPayment/null/ourBank"><SPAN CLASS="toc2">Перевести деньги частному лицу</SPAN></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/payment/ReIssueCardClaim">Перевыпустить карту</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/payment/LoanPayment/loan-payment-extended-fields">Погасить кредит</a></li>
										<c:if test="${phiz:impliesService('CreateMoneyBoxOperation')}">
										<li><a href="/PhizIC/help.do?id=/private/accounts/moneyBoxList">Подключить копилку</a></li>
										</c:if>		
									<li><a href="/PhizIC/help.do?id=/private/payments/payment/BlockingCardClaim">Заблокировать карту</a></li>
									</ul>

										<p>Для выполнения этих операций нажмите на кнопку <b>Операции</b> и выберите из списка нужную операцию.</p>
										<div class="help-important">
									<p>
										<span class="help-attention">Примечание</span>: с помощью кредитной карты Вы можете только оплачивать товары и услуги, а также пополнить или заблокировать карту.
										Остальные операции недоступны. 
										
									</p>
								</div>
								<p>Для того чтобы просмотреть список закрытых и заблокированных карт, щелкните по ссылке 
								<b>Заблокированные и закрытые карты</b> под списком действующих карт. В результате откроется список Ваших закрытых и заблокированных карт.</p>
								<p>По каждой карте из списка будет указана следующая информация: 
								наименование карты (например,
								Visa, MasterCard), номер карты (последние четыре цифры номера карты), сумма средств на карте и ее состояние, выделенное красным цветом. Для того чтобы подать в банк заявку на перевыпуск заблокированной или закрытой карты из списка, рядом с интересующей Вас картой нажмите на кнопку <b>Операции</b>. Затем щелкните по кнопке <b>Перевыпуск карты</b>. </p>
								<p>В случае если Вы хотите просмотреть детальную информацию по закрытой или заблокированной карте из данного списка, нажмите на название интересующей Вас карты.</p>
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>Кроме того, в системе "Сбербанк Онлайн" на каждой странице можно 
								обратиться к помощи персонального консультанта, который 
								ответит на все Ваши вопросы. Чтобы запустить помощника, 
								нажмите на ссылку <b>Часто задаваемые вопросы</b> в боковом 
								меню. В результате откроется окно, в котором содержатся 
								ответы на часто задаваемые вопросы по работе с системой 
								"Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы консультант 
								отображался на страницах системы, то измените настройки 
								отображения помощника, щелкнув по кнопке <b>Настройки</b> - 
								<b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы скрыть помощника, 
								Вам нужно в блоке <b>Дополнительные настройки</b> убрать галочку 
								в поле "Отображать персонального консультанта" и нажать на кнопку 
								<b>Сохранить</b>. --></p>
									<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
											<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
											<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
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