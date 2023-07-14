<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Мои цели</title>
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
													<li><a class="active-menu" href="/PhizIC/help.do?id=/private/finances/targets/targetsList">Мои цели</a></li>
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
                <div class="contextTitle">Мои цели</div>
                                
<ul class="page-content">
<li></span><a href="/PhizIC/help.do?id=/private/accounts/operations/target">Информация о цели</a></li>
</ul>


<p> Пункт меню <b>Мои цели</b> предназначен для формирования Ваших целей. Вы можете создать цель, копить деньги на ее достижение, вносить средства на вклад, открытый для цели, и контролировать ее достижение.</p>
<p> Перейти к странице "Мои цели" можно, щелкнув в боковом меню по ссылке <b>Мои финансы</b> и нажав на вкладку <b>Мои цели</b>.</p>
<p> В данном пункте меню отображаются все созданные Вами цели. Для каждой цели отображается ее название, описание, срок достижения, процент, под который внесены средства на вклад, открытый для цели, и шкала достижения цели, на которой показана сумма, имеющаяся на вкладе, и сумма, необходимая для достижения цели.</p>
<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: если у Вас еще нет ни одной цели в системе "Сбербанк Онлайн", то на экране автоматически появится форма выбора цели. Вы можете последовательно добавить  не более 20 целей.  
									</p>
								</div>
<p> Вы можете добавить новую цель, удалить цель, пополнить вклад, открытый для цели, а также перевести деньги на свой счет или карту со вклада, открытого для достижения цели.</p>
																<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" Вы можете переходить на нужную страницу не только с помощью 
										пунктов верхнего меню, ссылок бокового меню, но и с помощью ссылок, расположенных под главным меню. Данные 
										ссылки показывают путь от главной страницы до той, с которой Вы перешли на текущую страницу. Вы можете использовать эти ссылки для 
										перехода на интересующую страницу.  
									</p>
								</div>
<h2><a id="p2">Добавление цели</a></h2>
<p>Для того чтобы добавить цель, на странице <b>Мои цели</b> нажмите на кнопку <b>Добавить цель</b>. Откроется страница выбора цели, на которой нажмите на название или картинку интересующей Вас цели. В результате откроется страница заполнения параметров цели, на которой Вам необходимо задать следующие параметры:</p>
<ul> 
<li> 
В поле "Название" отображается название цели, которую Вы хотите достичь. Ниже в поле для комментария введите пояснение к цели;
</li>
<li> 
В поле "Сумма" укажите сумму, которая Вам потребуется для достижения цели;
</li>
<li> 
В поле "Срок" укажите месяц дату, к которой Вы планируете достичь цели. Вы можете указать дату вручную или воспользоваться календарем рядом с полем.
</li>
<p>Вы можете создать копилку, для того чтобы счет цели пополнялся автоматически. Для создания копилки поставьте галочку в поле <b>Копилка</b> и задайте настройки автоперевода. </p>
<p>Для типа <b>Фиксированная сумма</b> укажите следующие сведения:</p>
			<ul>
				<li>В поле "Пополнять" выберите из выпадающего списка, как часто будет пополняться Ваша копилка, например, <b>раз в месяц</b> или <b>раз в год</b>.</li>
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

<li> 
Затем для накопления денег на цель Вам необходимо открыть вклад. Для этого ознакомьтесь с условиями размещения средств на депозит, щелкнув по ссылке <b>Просмотр условий вклада</b>. После этого установите галочку в поле "С условиями вклада согласен". 
</li>
</ul>
<p> После того как все параметры заданы, нажмите на кнопку <b>Создать цель</b>. В результате Вы перейдете к списку целей,  в котором будет отображаться новая цель.</p>
<p> Если Вы передумали добавлять цель, нажмите на кнопку <b>Отменить</b>.</p>
<p> Для того чтобы вернуться на страницу <b>Мои цели</b> без внесенных изменений, нажмите на ссылку <b>Назад к выбору цели</b>.</p>

<h2><a id="p3">Удаление цели</a></h2>
<p>Вы можете удалить цель из системы "Сбербанк Онлайн". Для этого напротив интересующей Вас цели нажмите на кнопку <b>Операции</b>, а затем на ссылку <b>Удалить цель</b>. Затем подтвердите удаление, и выбранная цель больше не будет отображаться в Вашем приложении. </p>
<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: если на Вашем вкладе, открытом для достижения цели,  имеются средства, то Вы перейдете на форму закрытия вклада, и после подтверждения закрытия, выбранная цель будет удалена.  
									</p>
								</div>
<h2><a id="p4">Пополнение вклада</a></h2>
<p> Для того чтобы средства на достижение цели накапливались быстрее, Вы можете пополнить вклад. Для этого рядом с нужной целью нажмите на кнопку <b>Пополнить</b>, и Вы перейдете на страницу перевода между Вашими счетами или картами, на которой сможете внести средства на вклад. </p>



	
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