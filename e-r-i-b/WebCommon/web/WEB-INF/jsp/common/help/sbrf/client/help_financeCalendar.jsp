<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Финансовый календарь</title>
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
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады</a></li>
									<li><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a></li>
									<li><a href="/PhizIC/help.do?id=/private/security/list">Сертификаты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">Страховые программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/graphics/finance">Мои финансы</a>
												<ul>
													<li><a href="/PhizIC/help.do?id=/private/finances/operationCategories">Расходы</a></li>
													<li><a href="/PhizIC/help.do?id=/private/graphics/finance#m2">Доступные средства</a></li>
													<li><a href="/PhizIC/help.do?id=/private/finances/targets/targetsList ">Мои цели</a></li>
													<li><a class="active-menu" href="/PhizIC/help.do?id=/private/finances/financeCalendar ">Календарь</a></li>
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
                <div class="contextTitle">Финансовый календарь</div>
 <ul class="page-content">
<li><a href="#m2">Детальная выписка за день</a></li>
</ul>
                               
<p>Пункт меню <b>Календарь</b> предназначен для просмотра совершенных или планируемых Вами доходных и расходных операций за конкретную дату. </p>
<p>Перейти к странице "Финансовый календарь" можно, щелкнув в боковом меню по ссылке <b>Мои финансы</b> и нажав на вкладку <b>Календарь</b>, а также из списка <b>отложенных счетов</b>, щелкнув по кнопке <b>Календарь платежей</b>.</p>
<p>Если Вы хотите, чтобы в финансовом календаре отображались операции по 
конкретной карте, выберите из выпадающего списка над календарем интересующий Вас тип или номер карты. В случае если Вы хотите просмотреть информацию об операциях, совершенных с наличными средствами, выберите из списка значение <b>Траты наличными</b>.</p>
<p>На данной странице Вы также можете просмотреть информацию по операциям за 
предыдущие и последующие месяцы. Для этого используйте стрелочки рядом с названием месяца 
над финансовым календарем.</p>
<p> Даты предыдущего месяца и даты, которые еще не наступили, будут неактивны, но Вы также сможете просмотреть детальную выписку по этим дням. Подробнее смотрите в разделе контекстной справки <a href="#m2">Детальная выписка за день</a>.</p>

<p>Если выбранная дата уже прошла, то для нее будет отображаться только информация по операциям зачисления и списания. В случае если интересующая Вас дата еще не наступила, то для нее будет указана только информация об автоплатежах, отложенных на этот день счетах 
и напоминания о платежах.</p>

<p>Вы можете просмотреть дни, когда были совершены или запланированы операции с денежными средствами. Для даты в календаре может отображаться следующая информация:</p>
<ul><li>Сумма расходов за день, выделенная черным цветом;</li>
<li>Общая сумма по зачислению денежных средств в течение дня, выделенная зеленым цветом;</li>
<li>Сумма текущих или отложенных счетов и автоплатежей, исполнение которых назначено на эту дату, выделенная голубым цветом;</li>
<li>Количество автоплатежей, назначенных на выбранную дату;</li>
<li>Количество текущих или отложенных счетов к оплате в этот день;</li>
	<li>Напоминания о платежах на выбранную дату.</li>
</ul>

<p>Для того чтобы просмотреть полный список операций, совершенных за день, нажмите на интересующую Вас дату в календаре. В результате откроется детальная выписка за выбранный день (подробнее смотрите в разделе контекстной справки <a href="#m2">Детальная выписка за день</a>).</p>
 
 <h2><a id="m2">Детальная выписка за день</a></h2>
<p>После того как Вы щелкнули по дате в календаре, откроется форма с информацией 
о совершенных операциях за выбранный день. На этой форме Вы увидите все операции 
по списанию и зачислению средств на Ваши карты, автоплатежи,а также отложенные на указанный день или текущие счета, напоминания о платежах. </p>

<p>Если выбранная дата уже прошла, то в детальной выписке будет отображаться только информация по операциям зачисления и списания. В случае если интересующая Вас дата еще не наступила, то в детальной выписке будет указана информация об автоплатежах,
отложенных на этот день счетах и напоминания о платежах.</p>

<p>Для каждой операции или автоплатежа в списке Вы можете просмотреть название операции, категорию операций, к которой она относится, и ее сумму.</p>
<p>Для отложенных или текущих счетов может отображаться только название услуги, объект учета (например, для счета по оплате штрафа будет указан номер водительского удостоверения),
поставщик услуги, предъявивший Вам счет к оплате, и сумма к оплате счета. 
<p>Для напоминаний о платежах будет отображаться название шаблона, ключевое поле 
(например, номер телефона), дата ближайшего напоминания 
об этом платеже и сумма для оплаты. </p>
<div class="help-important">
<p>
<span class="help-attention">Обратите внимание</span>: в календаре отображаются только активные напоминания.</p> </div>
<p>Под списком операций на данной форме отображается следующая информация:</p>
<ul>
<li>Общая сумма Ваших расходов за указанный день;</li>
<li>Сумма, которая была зачислена на Ваши карты;</li>
<li>Текущий баланс Ваших карточных продуктов, открытых в Сбербанке РФ;</li>
<li>Сумма средств, запланированная к списанию в этот день.</li>
</ul>
<p>Если Вы хотите просмотреть выставленный счет, нажмите на его название в списке планируемых операций. В результате откроется форма с детальной информацией по данному счету, где Вы можете выполнить следующие действия:</p>
<ul><li><b>Оплатить счет</b>. Для этого нажмите на кнопку <b>Оплатить</b>. В результате Вы перейдете к списку счетов.</li>
<li><b>Отложить счет</b>. Для этого нажмите на ссылку <b>Отложить счет</b>. Затем на форме с календарем укажите дату, когда Вы хотите оплатить данный счет. В результате он будет отображаться в детальной выписке за выбранную дату в списке планируемых операций. </li>
<li><b>Удалить счет</b>. Если Вы не хотите оплачивать данный счет, щелкните по ссылке <b>Удалить</b>. После подтверждения операции этот счет будет удален из списка планируемых операций за выбранную дату.</li>
<li><b>Создать напоминание о повторном платеже</b>.
Для этого нажмите на нужную дату, откроется список запланированных платежей. 
Рядом с выбранным платежом нажмите на кнопку <b>Напомнить о платеже или переводе</b>. В результате откроется форма создания напоминания, на которой нужно определить способ создания
напоминания: либо выбрать из списка имеющийся шаблон и нажать на кнопку <b>Выбрать</b>, либо, если Вы планируете создавать напоминание с нуля, пройти по ссылке <b>Создайте новый шаблон</b>.
В результате откроется форма просмотра или создания шаблона. На форме создания шаблона 
сначала введите всю необходимую для создания шаблона информацию 
(подробнее об этом смотрите в разделе контекстной справки <a href="/PhizIC/help.do?id=/private/favourite/list/PaymentsAndTemplates">Мои шаблоны</a> 
- <b>Создание шаблона</b>), а затем нажмите на ссылку <b>Добавить напоминание</b>. На форме просмотра шаблона сразу щелкните по ссылке <b>Добавить напоминание</b>. Откроется
блок для создания напоминания (подробнее 
смотрите раздел контекстной справки <a href="/PhizIC/help.do?id=/private/template/select-category#p11">Создaние напоминания о повторной оплате</a>).
 
	
<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
									<li><span></span><a href="/PhizIC/help.do?id=/private/graphics/finance">Мои финансы</a></li>
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