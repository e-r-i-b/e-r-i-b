<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>Помощь. Главная</title>
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

<%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>
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

									<li><a href="/PhizIC/help.do?id=/private/accounts">Главная</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments">Платежи и переводы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a></li>
									<li><a href="/PhizIC/help.do?id=/private/pfr/list">Пенсионные фонды</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mail/sentList">Обращение в службу помощи</a></li>
									<li><a href="/PhizIC/help.do?id=/private/news/list">Новости</a></li>
									<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>

								</ul>
                                
							
      <a href="/PhizIC/faq.do" class="faq-help">
                <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
            </a>
       	</div>
	<!-- end help-left-section -->
	<div id="help-workspace">

        <div class="contextTitle">Оглавление</div>

								<ul class="page-content">
									<li><h3><a href="/PhizIC/help.do?id=/private/accounts">Главная</a></h3></li>
									<li><h3><a href="/PhizIC/help.do?id=/private/payments">Платежи и переводы</a></h3></li>
										<ul class="page-content">
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/category/TRANSFER">Переводы и обмен валют</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/category/DEPOSITS_AND_LOANS">Операции по вкладам, картам, кредитам и ОМС</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/category/COMMUNICATION">Связь, интернет и телевидение</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/category/TAX_PAYMENT">Налоги, штрафы, ГИБДД</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/category/EDUCATION">Образование</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/category/SERVICE_PAYMENT">Оплата товаров и услуг</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/category/PFR">Пенсионные фонды</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/category/OTHER">Прочие</a></li>
										</ul>	
									<li><h3><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></h3></li>
										<ul>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/cards/detail">Информация по карте</a></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></h3></li>
										<ul>
										<li><span>» <a href="/PhizIC/help.do?id=/private/accounts/info">Информация по вкладу/счету</a></span></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></h3></li>
									<li><h3><a href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a></h3></li>
										<ul>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/depo/info/position">Информация по позиции счета депо</a></li>	
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/depo/info/debt">Информация о задолженности</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/payment/DepositorFormClaim">Анкета депонента</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/payment/SecuritiesTransferClaim">Поручение на перевод/ прием перевода ценных бумаг</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/payment/SecurityRegistrationClaim">Регистрация ценных бумаг</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/depo/documents">Список документов по счету депо</a></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a></h3></li>
										<ul>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/ima/info">Информация по металлическому счету</a></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/payments/category">Пенсионные фонды</a></h3></li>
										<ul>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/payment/pfr">Запрос выписки из Пенсионного фонда</a></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></h3></li>
									<li><h3><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></h3></li>
										<ul>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/graphics/finance">Мои финансы</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/payment/BlockingCardClaim">Заблокировать карту</a></li>
										<li><span>» </span><a href="/PhizIC/help.do?id=/private/payments/payment/LossPassbookApplication">Заявить об утере сберкнижки</a></li>
										</ul>
									<li><h3><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></h3></li>
									<li><h3><a href="/PhizIC/help.do?id=/private/news/list">Новости</a></h3></li>
									<li><h3><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></h3></li>
									
								</ul>


								 </div>
        <!-- end help-workspace -->
    </div>
    <!-- end help-content -->

    <div class="clear"></div>
</div>

<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
</body>