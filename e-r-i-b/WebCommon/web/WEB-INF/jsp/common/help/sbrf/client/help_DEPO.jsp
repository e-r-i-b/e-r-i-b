<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Счета депо</title>
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
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></li>
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a>
									<ul>
									<li><a href="/PhizIC/help.do?id=/private/depo/info/position">Информация по позиции счета депо</a></li>	
									<li><a href="/PhizIC/help.do?id=/private/depo/info/debt">Информация о задолженности</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/payment/DepositorFormClaim">Анкета депонента</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/payment/SecuritiesTransferClaim/null">Поручение на перевод/ прием перевода ценных бумаг</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/payment/SecurityRegistrationClaim">Регистрация ценной бумаги</a></li>
									<li><a href="/PhizIC/help.do?id=/private/depo/documents">Список документов по счету депо</a></li>
									</ul>
									</li>
									<li><a href="/PhizIC/help.do?id=/private/security/list ">Сертификаты</a></li>
									<li><a href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">Страховые программы</a></li>
									
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
									
									<li><a href="/PhizIC/help.do?id=/private/mail/sentList">Вопрос в Контактный центр банка</a></li>
									<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>
									<li><a href="/PhizIC/help.do?id=/private/news/list">События</a></li>


								</ul>
<!--"/PhizIC/help.do?id=/private/payments/payment/SecuritiesTransferClaim/null"-->
						<a href="/PhizIC/faq.do" class="faq-help">
                    <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
                </a>
            </div>
            <!-- end help-left-section -->
            <div id="help-workspace">
                <div class="contextTitle">Счета депо</div>
								<p>Счет депо – счет, который открыт в Депозитарии Сбербанка России для
								учета ценных бумаг.</p>
								
								<p>На странице <b> Счета депо</b> Вы можете просмотреть список Ваших открытых 
								счетов депо, список закрытых и заблокированных счетов депо, информацию по позиции счета и по сумме Вашей задолженности перед депозитарием. </p>
								<p>Для того чтобы перейти на эту страницу, Вам необходимо в главном меню 
								щелкнуть по пункту меню <b>Прочее - Счета депо</b> и нажать на кнопку 
								<b>Подключить депозитарий</b>. Далее Вы перейдете на страницу подтверждения 
								операции, на которой Вам необходимо выбрать, 
									каким способом Вы хотите подтвердить подключение:</p>
                                <ul>
									<li>Если Вы хотите подтвердить операцию SMS-паролем, 
										нажмите на кнопку <b>Подтвердить по SMS</b>;
										
                                    </li>
                                    <li>Если Вы хотите подтвердить операцию другим способом, нажмите на
                                        ссылку <b>Другой способ подтверждения</b>. Затем 
                                        выберите один из предложенных вариантов:
                                        <ul>
                                            <li><b>Пароль с чека</b> - подтверждение паролем с чека, распечатанного в банкомате.</li>
                                            <li><b>Push-пароль из уведомления в мобильном приложении</b> - подтверждение
                                        паролем, полученным на мобильное устройство в виде Push-сообщения.</li>
                                        </ul>
                                        Затем откроется всплывающее окно, в котором укажите нужный пароль и нажмите на кнопку <b>Подтвердить</b>.
 
                                         <p>После этого счета, открытые Вами в Депозитарии Сбербанка России, будут
                                         отображаться в пункте меню <b>Счета депо</b> и на <b>Главной странице</b>
                                         системы.</p>
                                        <p>По каждому счету депо на этой странице Вы можете просмотреть
                                        следующую информацию:</p>
										<ul>
											<li>название и номер счета;</li>
											<li>номер и дату заключения договора по счету депо;</li>
											<li>состояние счета (открыт);</li>
											<li>сумму задолженности перед депозитарием.</li>
                                        </ul>
                                    </li>
                                </ul>
                                <div class="help-important">
                                    <p>
                                        <span class="help-attention">Примечание</span>:  если статус интересующего
                                        Вас счета депо выделен красным цветом, это означает, что
                                        операции по нему приостановлены по какой-либо причине. По данному
                                        счету депо Вы сможете только просмотреть анкету депонента и список документов.</p>
                                </div>
                                <p>Если Вы хотите оформить поручение на перевод или прием перевода ценных бумаг или подать в банк заявку на регистрацию новой ценной бумаги, то вверху страницы нажмите на нужную ссылку:
                                <ul>
                                    <li><A HREF="/PhizIC/help.do?id=/private/payments/payment/SecuritiesTransferClaim/null"><SPAN CLASS="toc2">Поручение на перевод/ прием перевода ценных бумаг</SPAN></A></li>
                                    <li><A HREF="/PhizIC/help.do?id=/private/payments/payment/SecurityRegistrationClaim"><SPAN CLASS="toc2">Заявка на регистрацию ценной бумаги</SPAN></A></li>
                                </ul>
					
                                <p>На странице <b> Счета депо</b> Вы также можете выполнить следующие действия:</p>
                                <ul>
                                    <li><A HREF="/PhizIC/help.do?id=/private/depo/info/position"><SPAN CLASS="toc2">Просмотреть информацию по позиции счета</SPAN></A></li>
                                    <li><A HREF="/PhizIC/help.do?id=/private/depo/info/debt"><SPAN CLASS="toc2">Получить информацию о Вашей задолженности</SPAN></A></li>
                                    <li><A HREF="/PhizIC/help.do?id=/private/payments/payment/DepositorFormClaim"><SPAN CLASS="toc2">Просмотреть сведения из анкеты депонента</SPAN></A></li>
                                    <li><A href="/PhizIC/help.do?id=/private/depo/documents"><SPAN CLASS="toc2">Просмотреть список документов по счету депо</SPAN></A></li>
                                </ul>

						<p>Для выполнения этих операций нажмите на кнопку <b>Операции</b> и выберите из списка нужную операцию.</p>	 
						<p>Если Вы хотите перейти на страницу информации по позиции счета, щелкните по названию или номеру счета депо.</p>
						<!--<p>Для того чтобы перейти к странице с информацией о задолженности по счету депо перед Депозитарием, щелкните по 
						сумме задолженности.</p>-->	
						<p>Кроме того, здесь можно просмотреть список Ваших закрытых и заблокированных счетов депо. Он отображается под списком открытых счетов.</p>
				
					<!-- <ul>
											<li><b>Просмотреть информацию по позиции счета</b>: сколько ценных бумаг у Вас сейчас на счете.
											 Для этого нажмите на ссылку <b>Позиции</b> рядом с выбранным Вами счетом.
											Просмотреть информацию по позиции счета Вы также можете щелкнув по номеру или названию счета.
											Для получения более подробной информации перейдите к разделу справки 
											<a href="/PhizIC/help.do?id=/private/ima/info">Информация 
											по позиции счета депо</a>. </li>
											<li><b>Получить информацию о Вашей задолженности</b> перед депозитарием по выбранному счету депо. 
											Для просмотра информации в списке счетов депо или на странице информации по позиции нажмите на ссылку <b>Задолженность</b>.
											Система выведет на экран информацию по Вашей задолженности на текущую дату, номер счета депо, итоговую сумму задолженности 
											перед депозитарием и список выставленных счетов на оплату. Подробнее смотрите в разделе контекстной справки 
											<a href="/PhizIC/help.do?id=/private/ima/info">Информация о задолженности</a>.</li>
											<li><b>Просмотреть сведения из анкеты депонента</b>. Для этого в списке счетов депо или на странице с информацией по позиции 
											счета щелкните по ссылке <b>Анкета депонента</b>. Откроется страница создания поручения на получение анкетных данных депонента.
											Если ссылка <b>Анкета депонента</b> не отображается, то Вам сначала нужно щелкнуть 
											по <b>Еще операции</b>. Подробнее смотрите в разделе контекстной справки 
											<a href="/PhizIC/help.do?id=/private/ima/info">Сведения из анкеты депонента</a>.
											
											<div class="help-important">
<p>
										<span class="help-attention">Примечание</span>: данная операция платная и выполняется в соответствии с тарифами банка, с 
										которыми можно 
											ознакомиться на сайте Сбербанка России.</p>
								</div> 

											
											</li>
											<li><b>Просмотреть список документов по счету депо</b>: все выполненные Вами операции по счетам депозитарного учета. 
											Вы можете перейти в данный пункт меню, щелкнув по ссылке <b>Список документов</b> на главной странице, 
											на странице со списком счетов депо или на странице информации по позиции счета. Подробнее смотрите в разделе контекстной справки 
											<a href="/PhizIC/help.do?id=/private/ima/info">Список документов</a>.</li>
											<li></li>
											<li></li>
											
											</ul>-->

								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>В системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
								который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые вопросы</b> 
								в боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые вопросы по работе с 
								системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы консультант отображался на страницах системы, то измените 
								настройки отображения помощника, щелкнув по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы 
								скрыть помощника, Вам нужно в блоке <b>Дополнительные настройки</b> убрать галочку в поле "Отображать персонального 
								консультанта" и нажать на кнопку <b>Сохранить</b>. --></p>

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