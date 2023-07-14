<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Мои финансы</title>
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
									<li><a href="/PhizIC/help.do?id=/private/security/list ">Сертификаты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">Страховые программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a>
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/graphics/finance">Мои финансы</a>
										<ul>
											<li><a href="/PhizIC/help.do?id=/private/finances/operationCategories">Расходы</a></li>
											<li><a href="#m2">Доступные средства</a></li>
											<li><a href="/PhizIC/help.do?id=/private/finances/targets/targetsList ">Мои цели</a></li>
											<li><a href="/PhizIC/help.do?id=/private/finances/financeCalendar ">Календарь</a></li>
											<li><a href="/PhizIC/help.do?id=/private/finances/operations">Операции</a></li>
											<li><a href="/PhizIC/help.do?id=/agree">Финансовое планирование</a></li>
										</ul>
									</li>
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
                <div class="contextTitle">Мои финансы</div>
                              
<ul class="page-content">
<li><a href="#m2">Доступные средства</a></li>
<li></span><a href="/PhizIC/help.do?id=/private/finances/operations">Операции</a></li>
<li></span><a href="/PhizIC/help.do?id=/private/finances/operationCategories">Расходы</a></li>
<li></span><a href="/PhizIC/help.do?id=/private/finances/targets/targetsList ">Мои цели</a></li>
<li></span><a href="/PhizIC/help.do?id=/private/finances/financeCalendar ">Календарь</a></li>
<li></span><a href="/PhizIC/help.do?id=/agree">Финансовое планирование</a></li>
</ul>
<p>Пункт бокового меню <b>Мои финансы</b> предназначен, для того чтобы показать структуру денежных средств, которые размещены на всех Ваших картах, счетах и вкладах, открытых в Сбербанке России. Также в данном пункте меню Вы сможете просмотреть Ваши доходы и расходы, разделенные на различные категории, график движения средств для каждой категории операций и объем наличных и безналичных операций.</p>
<p>В пункте меню <b>Мои финансы</b> Вы можете пройти персональное финансовое планирование и подобрать наиболее выгодные для вложения средств продукты. </p>
<h2><a id="m2">Доступные средства</a></h2>

<p>В данном пункте личного меню автоматически открывается вкладка <b>Доступные средства</b>, на которой отображается кольцевая диаграмма. На диаграмме показано, какую долю занимает остаток денег на какой-либо карте, вкладе или мет. счете в общей сумме Ваших денежных средств.</p>
<p>Диаграмма может быть разделена на несколько секторов различного цвета. </p>
<p>Каждый сектор диаграммы соответствует продукту – вкладу, карте или счету. Средства, размещенные на кредитной карте, и кредиты также учитываются в диаграмме как Ваши собственные средства.</p>
<p>Если Вы не хотите, чтобы на диаграмме были показаны кредитные средства, снимите галочку в поле "Учитывать кредитные средства".</p>
<p> Для того чтобы суммы на диаграмме отображались с точностью до копеек, установите флажок в поле "Отображать копейки". Если Вы снимите флажок в этом поле, то суммы будут округлены до рублей.</p>
<p>Если Вы хотите посмотреть более подробную информацию, наведите курсор на интересующий Вас сектор диаграммы. На экран будет выведено название карты или вклада, остаток на счете и доля данного продукта (в процентах) от общей суммы денежных средств.</p>
<p> Если продукт занимает меньше 3% от всего объема денежных средств, то он отображается на диаграмме в секторе "Остальные доступные средства".</p>
<p>Вы можете перейти на страницу с информацией по продукту, щелкнув по его наименованию. </p>
<p>Рядом с диаграммой в  правой части расположена следующая информация:</p>
<ul>
<li>В поле "Всего доступно" выводится общий баланс по всем продуктам;</li>
<!-- <li>В поле "Курс ЦБ" отображается курс валют Центрального банка для счетов и карт, открытых в  иностранной валюте, а также курс покупки/продажи металла для ОМС;</li> -->
<li>Список вкладов, счетов и карт, представленных на диаграмме, с их цветовым обозначением.</li>
<li>Остальные доступные средства. Для того чтобы посмотреть список продуктов, входящих в этот раздел, щелкните по ссылке <b>Остальные доступные средства</b>. </li>
</ul>

	
								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
									<li><span></span><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
									<li><span><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
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
										