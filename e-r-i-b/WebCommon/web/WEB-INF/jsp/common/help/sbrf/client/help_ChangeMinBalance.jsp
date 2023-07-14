<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Изменение неснижаемого остатка по вкладу</title>
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
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a>
									<ul class="page-content">
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/payments/payment/ChangeDepositMinimumBalanceClaim">Изменение неснижаемого остатка по вкладу</a></li>
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
                <div class="contextTitle">Изменение неснижаемого остатка по вкладу</div>

								<ul class="page-content">

									<li><a href="#p2">Создать заявку</a></li>
									<li><a href="#p3">Подтвердить заявку</a></li>
									<li><a href="#p4">Просмотреть заявку</a></li>

								</ul>
								<p>На данной странице можно изменить неснижаемый остаток по вкладу. Для того чтобы изменить неснижаемый остаток по вкладу, на странице с детальной информацией по вкладу на вкладке <b>Информация по вкладу</b> в блоке рядом с полем "Сумма неснижаемого остатка" установите переключатель напротив нужного значения и нажмите на кнопку <b>Применить</b>.</p>
								
								<p> В результате Вы перейдете на страницу заполнения дополнительного соглашения к договору вклада.</p>
								<p> Также Вы можете перейти к созданию дополнительного соглашения, щелкнув по ссылке <b>Изменить
								неснижаемый остаток</b> в блоке <b>Операции</b> напротив интересующего Вас вклада на <b>Главной</b> странице или на странице со списком вкладов.</p>								
								<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" Вы можете переходить на нужную страницу не только с помощью 
										пунктов верхнего меню, ссылок бокового меню, но и с помощью ссылок, расположенных под главным меню. Данные 
										ссылки показывают путь от <b>Главной</b> страницы до той, с которой Вы перешли на текущую страницу. Вы можете использовать эти ссылки для 
										перехода на интересующую страницу.  
									</p>
								</div>
								<h2><a id="p2">Создать заявку</a></h2>
								<p>Для того чтобы создать заявку, заполните следующие поля:
									<ul>
										<li>На странице заполнения доп. соглашения номер документа, дата документа, номер и название вклада будут указаны автоматически. </li>
										<li>Далее в поле "Неснижаемый остаток" выберите из выпадающего списка интересующее Вас значение.</li>
										<li>В результате поле "Процентная ставка" будет заполнено автоматически исходя из условий вклада.</li>
									</ul>						

								<p>После того как поля заполнены, нажмите на кнопку <b>Продолжить</b>. 
								Система выведет на экран страницу 
								подтверждения документа, на которой Вам 
								необходимо проверить правильность заполнения 
								реквизитов.</p>
								<p>Если Вы передумали создавать заявку, то 
								нажмите на ссылку <b>Отменить</b> или щелкните по ссылке <b>Назад к выбору условий</b>. В 
								результате Вы вернетесь на страницу с детальной информацией по вкладу.</p>
									<div class="help-important">
										<p>
											<span class="help-attention">Обратите 
											внимание</span>: Вы можете контролировать процесс выполнения операции с помощью
											линии вверху формы, на которой будет выделено состояние операции на данный момент.
											Например, если Вы находитесь на странице подтверждения, то будет выделен отрезок 
											"Подтверждение".  
										</p>
									</div> 
								<h2><a id="p3">Подтвердить заявку</a></h2>
								<p>Далее необходимо подтвердить заявку. После 
								того как Вы нажали на кнопку <b>Продолжить</b>, 
								Вам откроется заполненная форма заявки, в 
								которой нужно проверить правильность указанных 
								сведений, после чего выполнить одно из следующих 
								действий:
									</p>
									<ul>
									<li><b>Просмотреть условия доп. соглашения</b>. Перед подтверждением заявки Вам необходимо сначала ознакомиться с подробной информацией об условиях доп. соглашения, для этого щелкните по ссылке  <b>Просмотр условий дополнительного соглашения</b>. 
									</li><li><b>Подтвердить заявку</b>. Убедитесь, что вся информация указана 
									верно. Затем установите галочку в поле "С условиями дополнительного соглашения согласен".
									Далее для подтверждения операции нажмите на кнопку <b>Подтвердить</b>. В результате 
									откроется страница просмотра заявки.
									</li>

									<li><b>Редактировать заявку</b>. Если Вы хотите изменить реквизиты заявки, то нажмите на ссылку <b>Редактировать</b>. В результате откроется страница заполнения доп. соглашения по вкладу.
									</li>
									<li><b>Отменить операцию</b>. Если Вы передумали совершать операцию, то 
									нажмите на ссылку <b>Отменить</b>. В 
									результате Вы вернетесь на страницу <b>
									Переводы и платежи</b>.
									</li>
									</ul>
								<h2><a id="p4">Просмотреть заявку</a></h2>
									<p>После подтверждения заявки Вы перейдете 
									на страницу просмотра, на которой 
									увидите заполненный документ. О том, что операция успешно выполнена банком, свидетельствует 
									отображаемый на форме документа штамп 
									"Исполнено" и статус "Исполнен".</p>
									<p>На этой странице Вы 
									сможете <b>просмотреть реквизиты</b> выполненной 
									заявки.</p>
									<p>Если Вы хотите ознакомиться с подробной информацией об условиях доп. соглашения, то щелкните по ссылке  <b>Просмотр условий дополнительного соглашения</b>.</p>
								<p>Если Вы хотите <b>просмотреть список совершенных Вами операций</b>, то щелкните по ссылке <b>Перейти к истории операций</b>.</p>
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>Кроме того, в системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
								который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые 
								вопросы</b> в боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые 
								вопросы по работе с системой "Сбербанк Онлайн". 
								<!--  Если Вы не хотите, чтобы консультант отображался на страницах 
								системы, то измените настройки отображения помощника, щелкнув по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. 
								Для того чтобы скрыть помощника, Вам нужно в блоке <b>Дополнительные настройки</b> убрать галочку в поле "Отображать 
								персонального консультанта" и нажать на кнопку <b>Сохранить</b>.</p> -->

								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
										<li><span></span><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
											<li><span></span><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
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