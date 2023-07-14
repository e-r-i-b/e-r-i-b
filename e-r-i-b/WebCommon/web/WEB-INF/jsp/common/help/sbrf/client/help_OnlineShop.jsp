<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<head>
<title>Помощь. Переводы и платежи</title>
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
									<li><a  href="/PhizIC/help.do?id=/private/payments">Переводы и платежи</a>
										<ul class="page-content">
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/InternalPayment/null">Перевод между своими счетами и картами</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/LoanPayment/loan-payment-extended-fields">Погашение кредитов</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/ConvertCurrencyPayment/null">Обмен валюты</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/IMAPayment/null">Покупка и продажа металлов</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/NewRurPayment/null/ourBank">Перевод частному лицу</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/jurPayment/edit/null">Перевод организации</a></li>
											<li><a class="active-menu" href="/PhizIC/help.do?id=//private/payments/servicesPayments/edit/*">Оплата услуг</a></li>		
										</ul>
									</li>							
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
                     <div class="contextTitle">Переводы и платежи. Оплата товаров и услуг интернет-магазинов</div>

								<ul class="page-content">
									<li><a href="#p2">Оплатить заказ</a></li>
									<li><a href="#p3">Подтвердить платеж</a></li>
									<li><a href="#p4">Просмотреть платеж</a></li>
								</ul>
									<p>C помощью системы "Сбербанк Онлайн" Вы можете
									оплатить Ваш заказ на товары или услуги, предоставляемые интернет-магазинами.
									
									</p>
									<p>Вы можете перейти в систему "Сбербанк Онлайн" 
									с сайта интернет-магазина, щелкнув при оформлении заказа по ссылке
									<b>Оплатить через "Сбербанк Онлайн"</b>, а также оплатить выставленный за заказ счет позднее из пункта бокового меню <b>Мои интернет-заказы</b>.</p>
									

								<h2><a id="p2">Оплатить заказ</a></h2>
                                    <p> Вы можете оплатить товар или услугу, выбранную в интернет-магазине, двумя способами:</p>
                                    <ol>
										<li>Если Вы хотите сразу перейти к оплате товара или услуги,
										заказанных через интернет-магазин, то выполните следующие 
										действия:
                                            <ul>
                                                <li>На сайте интернет-магазина после заполнения реквизитов заказа,
                                                    щелкните по ссылке <b>Оплатить через "Сбербанк Онлайн".</b>
                                                    В результате в новом окне браузера откроется страница
                                                    входа в систему "Сбербанк Онлайн".
                                                </li>
                                                <li>На странице входа Вам необходимо ввести Ваш логин и пароль
                                                    для работы в системе. Затем нажмите на кнопку <b>Войти</b>.
                                                    Откроется страница, на которой в целях безопасности Вам нужно
                                                    подтвердить вход в систему одноразовым паролем:
                                                    <ul>
                                                        <li>если Вы хотите подтвердить вход SMS-паролем,
                                                            нажмите на кнопку <b>SMS подтверждение</b>;
                                                        </li>
                                                        <li>если Вы хотите подтвердить операцию другим способом, нажмите на
                                                            ссылку <b>Другой способ подтверждения</b>. Затем
                                                            выберите один из предложенных вариантов:
                                                            <ul>
                                                                <li><b>Пароль с чека</b> - подтверждение паролем с чека, распечатанного в банкомате.</li>
                                                                <li><b>Push-пароль из уведомления в мобильном приложении</b> - подтверждение
                                                            паролем, полученным на мобильное устройство в виде Push-сообщения.</li>
                                                            </ul>
                                                            Затем откроется всплывающее окно,	в котором укажите нужный пароль и
                                                            нажмите на кнопку <b>Подтвердить</b>.
                                                        </li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        <li> Если Вы отправили счет на оплату заказа с сайта интернет-магазина в "Сбербанк Онлайн" , то Вы можете перейти к нему при следующем входе в систему "Сбербанк Онлайн". Список Ваших заказов будет показан в пункте Личного меню <b>Мои интернет-заказы</b>, в котором для того чтобы оплатить счет, нажмите напротив него на соответствующую ссылку.</li>
                                    </ol>
						            <p> В результате Вы перейдете на форму создания платежа, на которой Вам необходимо проверить
									параметры заказа, сведения о получателе платежа. </p>
									<p>Затем в поле "Оплата с" выберите из
									выпадающего списка рублевую карту, с которой Вы хотите оплатить товар или услугу.
									</p>
									
									
									<p>После того как все необходимые сведения 
									указаны, нажмите на кнопку <b>Оплатить</b>. 
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
                                            линии вверху формы, на которой будет выделено состояние операции на данный момент.
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
                                            каким способом Вы хотите её подтвердить:
                                            <ul>
                                                <li>если Вы хотите подтвердить операцию SMS-паролем,
                                                    нажмите на кнопку <b>Подтвердить по SMS</b>;
                                                    <div class="help-important">
                                                        <p>
                                                            <span class="help-attention">Обратите
                                                            внимание</span>: перед вводом пароля убедитесь, что реквизиты операции совпадают с
                                                            текстом SMS-сообщения. Будьте осторожны, если данные не совпадают, ни в коем случае не вводите пароль
                                                            и никому его не сообщайте, даже сотрудникам банка.
                                                        </p>
                                                    </div>

                                                </li>
										<li>если Вы хотите подтвердить операцию другим способом, нажмите на 
                                            ссылку <b>Другой способ подтверждения</b>. Затем
                                            выберите один из предложенных вариантов:
                                            <ul>
                                                <li><b>Пароль с чека</b> - подтверждение паролем с чека, распечатанного в банкомате.</li>
                                                <li><b>Push-пароль из уведомления в мобильном приложении</b> - подтверждение
                                            паролем, полученным на мобильное устройство в виде Push-сообщения.</li>
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
									    <li><b>Удалить заказ</b>. Если Вы хотите удалить заказ, нажмите на ссылку <b>Удалить</b>, и после подтверждения операции выбранный счет перестанет отображаться в списке Ваших интернет-заказов.</li>
									    <li><b>Отменить платеж</b>. Если Вы передумали совершать платеж, то
									        нажмите на кнопку <b>Отменить</b>.</li>
									</ul>
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
									    <li><b>Продолжить работу в "Сбербанк Онлайн"</b>. На форме просмотра платежа Вы можете
                                            нажать на кнопку <b>Продолжить</b>. Откроется всплывающее окно, в котором
                                            выполните одно из следующих действий:
                                            <ul>
                                                <li>если Вы хотите продолжить работу в системе "Сбербанк Онлайн", то нажмите на кнопку
                                                    <b>Продолжить работу в "Сбербанк Онлайн"</b>.
                                                </li>
                                                <li>если Вы хотите выйти из системы, то нажмите на кнопку <b>Выход</b>.
                                                </li>
                                            </ul>
									    </li>
									</ul>
								<!-- <div class="help-linked">
									<h2>Также рекомендуем посмотреть</h2>
									<ul class="page-content">
										<li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
										<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>

									</ul>
								</div> -->
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