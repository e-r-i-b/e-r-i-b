<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<head>
<title>Помощь. Оплата налогов и сборов с сайта ФНС</title>
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
									<li><a href="/PhizIC/help.do?id=/private/accounts">Главная</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments">Переводы и платежи</a></li>
									<li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">Больше Сбербанк Онлайн</a></li>
									<li><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
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
                <div class="contextTitle">Переводы и платежи. Оплата налогов и	сборов с сайта Федеральной налоговой службы</div>
								<ul class="page-content">
									<li><a href="#p1">Выбрать задолженность</a></li>
									<li><a href="#p2">Оплатить налог</a></li>
									<li><a href="#p3">Подтвердить платеж</a></li>
									<li><a href="#p4">Просмотреть платеж</a></li>
								</ul>
									<p>C помощью системы "Сбербанк Онлайн" Вы можете
									погасить Вашу задолженность по налогам и сборам.</p>
									<p> Если Вы выбрали на сайте ФНС одну задолженность для оплаты, то
									Вам необходимо выполнить действия, описанные в разделе <a href="#p2">Оплатить налог</a>. </p>
									<p>Если Вы выбрали несколько платежных документов для оплаты, то при входе
									в систему "Сбербанк Онлайн" откроется страница просмотра задолженностей.</p>
									<h2><a id="p1">Выбрать задолженность</a></h2>
										<p> На странице выбора задолженности система выведет на экран список
										 налоговых документов, которые Вы можете оплатить.
										</p>
										<p>В списке для каждого документа отображается его индекс, налог, в счет которого
										нужно выполнить платеж, тип платежа и сумма.</p>
										<p>Для того чтобы погасить задолженность, щелкните по ссылке <b>Оплатить</b> напротив интересующего Вас
										документа. В результате Вы перейдете на страницу заполнения реквизитов платежа.</p>
										<p>Если Вы передумали совершать платеж, нажмите на кнопку <b>Отменить</b>.</p>
										
									<h2><a id="p2">Оплатить налог</a></h2>
	
									<p>После того как Вы выбрали документ, по которому хотите погасить задолженность,
									откроется страница заполнения реквизитов платежа. На этой странице Вам необходимо указать следующую
									информацию:</p>
									<ul> 
									<li>Внимательно проверьте, правильно ли введены Ваши личные данные (фамилия, имя, отчество и ИНН), 
									реквизиты налоговой инспекции, в адрес которой Вы совершаете платеж, реквизиты банка, через который Вы будете 
									оплачивать налог, а также реквизиты самого платежа.
									</li>
									<li>
									В блоке <b>Оплата</b> в поле "Перевести с" выберите из выпадающего списка счет, с которого хотите оплатить налог. 
									</li></ul>							
									

									<p>После того как все необходимые сведения 
									внесены, нажмите на кнопку <b>Оплатить</b>. 
									Система выведет на экран страницу 
									подтверждения платежа, на которой Вам 
									необходимо проверить правильность заполнения 
									реквизитов. </p>
									<p>Если Вы передумали совершать платеж, то 
									нажмите на кнопку <b>Отменить</b>.</p>
									<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: Вы можете контролировать процесс выполнения операции с помощью
										линии вверху формы, на которой будет выделено состояние операции на текущий момент.
										Например, если Вы находитесь на странице подтверждения, то будет выделен отрезок 
										"Подтверждение".  
									</p>
								</div> 
								<h2><a id="p3">Подтвердить платеж</a></h2>
								<p>Далее необходимо подтвердить платеж. После 
								того как Вы нажали на кнопку <b>Оплатить</b>, 
								Вам откроется заполненная форма документа, в 
								которой нужно проверить правильность указанных 
								сведений, после чего выполнить одно из следующих 
								действий:
									</p>
									<ul>
									<li><b>Подтвердить платеж</b>. Убедитесь, что вся информация указана 
									верно. Затем, для подтверждения операции, Вам необходимо выбрать, 
									каким способом Вы хотите её подтвердить:</p>
										<ul>
									<li>если Вы хотите подтвердить операцию SMS-паролем, 
										нажмите на кнопку <b>Подтвердить по SMS</b>;
										</li>
										<li>если Вы хотите подтвердить операцию другим способом, нажмите на 
                                        ссылку <b>Другой способ подтверждения</b>. Затем 
                                        выберите один из предложенных вариантов:</p>
                                        <ul><li><p><b>Пароль с чека</b> - подтверждение паролем с чека, распечатанного в банкомате.</li>
                                        <li><p><b>Push-пароль из уведомления в мобильном приложении</b> - подтверждение 
                                        паролем, полученным на мобильное устройство в виде Push-сообщения.</p></li>
                                        </ul>
										</ul>
											Затем откроется всплывающее окно,	в котором укажите нужный пароль и нажмите на кнопку <b>Подтвердить</b>. 
											В результате Вы перейдете на страницу просмотра документа.
									</li>
									<li><b>Изменить реквизиты</b>. Если при проверке выяснилось, 
									что платеж необходимо отредактировать, то 
									нажмите на ссылку <b>Редактировать</b>. В результате 
									Вы вернетесь на страницу заполнения 
									реквизитов платежа.
									</li>
									</ul>
								<h2><a id="p4">Просмотреть платеж</a></h2>
									<p>После подтверждения Вы перейдете 
									на страницу просмотра платежа, на которой 
									увидите заполненный документ. О том, что 
									платеж передан в банк, свидетельствует 
									отображаемый на форме документа штамп 
									&quot;Принято к исполнению&quot;. На этой странице Вы 
									сможете просмотреть реквизиты 
									платежа и выполнить следующие действия:</p>
									<ul>
									<li><b>Напечатать чек</b>. В системе &quot;Сбербанк Онлайн&quot; для 
									подтверждения совершения операций 
									предусмотрена печать чеков. Если Вы хотите 
									напечатать чек, то нажмите на кнопку <b>
									Печать чека</b>. Система выведет на экран 
									печатную форму документа, которую Вы сможете 
									распечатать на принтере. Печать чека доступна только для документов со 
									статусом "Исполнен" и "Исполняется банком". Для документов со статусом 
									"Отклонено банком" печать чека недоступна.
									</li>
									<li><b>Оплатить другую задолженность</b>. Если у Вас есть ещё
									задолженность по налогам и сборам, которую Вы хотите оплатить,
									то на данной странице Вы можете нажать на кнопку <b>Оплатить другую задолженность</b>.
									В результате Вы перейдете к списку документов, задолженность по которым можете оплатить.
									Для того чтобы оплатить следующую задолженность, повторите описанные выше шаги.
									</li>
									<li><b>Продолжить работу в "Сбербанк Онлайн"</b>. Если у Вас больше нет
									непогашенной задолженности перед налоговыми органами, то на форме просмотра платежа
									нажмите на кнопку <b>Продолжить</b>. Откроется всплывающее окно, в котором Вы можете
									выбрать одно из следующих действий:
									<ul>
									<li>если Вы хотите продолжить работу в системе "Сбербанк Онлайн", то нажмите на кнопку
									<b>Продолжить работу в "Сбербанк Онлайн"</b>.
									</li>
									<li>если Вы хотите выйти из системы, то нажмите на кнопку <b>Выход</b>.
									</li>
									</ul>
									</li>
									</ul>
								</div>
								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
										<li><span></span><a href="/PhizIC/help.do?id=/private/receivers/list">Шаблоны платежей</a></li>
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