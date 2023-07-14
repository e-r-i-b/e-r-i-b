<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Обезличенные металлические счета</title>
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
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a>
										<ul>
											<li><a href="/PhizIC/help.do?id=/private/ima/info">Информация по металлическому счету</a></li>
										</ul>
									</li>
									<li><a href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">Страховые программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mail/sentList">Вопрос в Контактный центр банка</a></li>
									<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>
									<li><a href="/PhizIC/help.do?id=/private/news/list">События</a></li>


								</ul>
 <a href="/PhizIC/faq.do" class="faq-help">
                    <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
                </a>
            </div>
            <!-- end help-left-section -->
            <div id="help-workspace">
                <div class="contextTitle">Металлические счета</div>

<ul class="page-content">
<li><a href="#p2">Курсы покупки и продажи драгоценных металлов</a></li>
</ul>

								<p>На странице <b>Металлические счета</b> Вы можете
								просмотреть список Ваших обезличенных металлических счетов (ОМС), 
								доступ к которым разрешен в системе "Сбербанк Онлайн". Для того чтобы перейти на эту страницу, Вам необходимо 
								в главном меню щелкнуть по пункту меню <b>Прочее - Металлические счета</b>.</p>
																	
								<p>По каждому счету на данной странице Вы 
								можете просмотреть следующую информацию: название счета, остаток (какое 
								количество металла хранится на счете), номер счета, тип 
								(какой драгоценный металл хранится на этом счете, 
								например, &quot;ARG&quot;), дату открытия ОМС, сумму, на которую куплен металл (сумма отображается в рублях по текущему курсу банка), и состояние счета (закрыт, на счет наложен арест).</P>
								<p>В системе "Сбербанк Онлайн" предусмотрена работа с обезличенными 
								металлическими счетами, открытыми в одном из следующих металлов: золото, 
								серебро, платина, палладий.</p>
								<div class="help-important">
									<p>
										<span class="help-attention">Примечание</span>: если статус и остаток по интересующему 
										Вас металлическому счету выделен в списке красным цветом, это означает, 
										что операции по нему приостановлены по какой-либо причине.
									</p>
								</div>
									<!--<p>На данной странице также можно установить 
									галочку в поле "Показывать на главной". В 
									результате данный счет будет отображаться 
									на главной странице в блоке <b>Металлические счета</b>.</P>-->
								<p>На данной странице по каждому металлическому счету Вы можете просмотреть 
								список трех последних операций, которые Вы совершили. Для того чтобы развернуть список 
									последних операций, рядом 
									с интересующим Вас металлическим счетом щелкните по ссылке <b>
									Показать операции</b>. </P>
								<p>В этом списке по каждой операции Вы можете увидеть дату совершения операции, тип металла, количество металла, которое было списано или зачислено на счет в результате совершения операции, а также сумму операции.</P>
									<p>Если Вы не хотите, чтобы на данной 
									странице отображались операции по счету, то 
									рядом с выбранным металлическим счетом щелкните по ссылке <b>
									Скрыть операции</b>.</P>
									<p>На этой странице по каждому металлическому счету Вы можете получить выписку, а также купить или продать металл, 
								щелкнув по одноименным ссылкам:</p>
								<ul class="page-content">
								<li><A HREF="/PhizIC/help.do?id=/private/ima/info"><SPAN CLASS="toc2">Выписка</SPAN></A></li>
				<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/IMAPayment/null"><SPAN CLASS="toc2">Покупка/продажа металла</SPAN></A></li>
				
								</ul>
								<p>Для выполнения этих операций нажмите на кнопку <b>Операции</b> и щелкните в списке по нужной операции.</p>
									<p>По каждому обезличенному металлическому счету в списке Вы можете 
									пройти на страницу <b>с детальной 
									информацией по счету</b> и выполнить 
									следующие действия: просмотреть список 
									выполненных операций, распечатать выписку, просмотреть 
									дополнительную информацию по данному счету и 
									т.д. Для этого выберите интересующий Вас металлический счет в 
									списке и щелкните по его названию или номеру счета. В результате 
									система откроет страницу с подробной 
									информацией по данному счету. </P>
									<p>Кроме того, здесь можно просмотреть список Ваших закрытых и заблокированных металлических счетов. Он отображается под списком открытых ОМС.</p>
									<h2><a id="p2">Курсы покупки и продажи драгоценных металлов</a></h2>

									<p> Также на странице со списком металлических счетов отображается блок, в котором Вы можете 
									<b>просмотреть курсы покупки и продажи драгоценных металлов</b>.</p>
									<p>В первой строке отображается цена одного грамма металла при его покупке банком. Во второй строке показано, за сколько Вы можете купить 1 г металла. Например, если Вы хотите продать 10 граммов платины, то Вам нужно найти число, расположенное на пересечении строки 
									&quot;Покупка&quot; и столбца &quot;PTR&quot;. Затем это число необходимо умножить на 10. В результате получится сумма дохода, которую Вы сможете получить при продаже металла. 
									</p>
									<p> 
								Если Вы хотите купить или продать металл, щелкните по ссылке 
								<A HREF="/PhizIC/help.do?id=/private/payments/payment/IMAPayment/null"><SPAN CLASS="toc2">Купить/продать металл</SPAN></A>.	
									</p>
								
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>

								<p>В системе "Сбербанк Онлайн" на каждой странице можно обратиться к 
								помощи персонального консультанта, который ответит на все Ваши вопросы. 
								Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые вопросы</b> 
								в боковом меню. В результате откроется окно, в котором содержатся ответы на 
								часто задаваемые вопросы по работе с системой "Сбербанк Онлайн". 
								<!-- Если Вы не 
								хотите, чтобы консультант отображался на страницах системы, то измените 
								настройки отображения помощника, щелкнув по кнопке <b>Настройки</b> - 
								<b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы скрыть помощника, Вам нужно в 
								блоке <b>Дополнительные настройки</b> убрать галочку в поле "Отображать 
								персонального консультанта" и нажать на кнопку <b>Сохранить</b>. --></p>

								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
									<li><span></span><a href="/PhizIC/help.do?id=/private/accounts">Главная</a></li>
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