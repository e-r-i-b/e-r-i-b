<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<head>
<title>Помощь. Запрос кредитной истории</title>
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
                    <li><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
                    <li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
                    <li><a class="parentItem" href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a>
						<ul class="page-content">
							<li><a  href="/PhizIC/help.do?id=/private/loans/info">Информация по кредиту</a></li>
								<c:if test="${phiz:impliesService('CreditReportOperation')}">
								<li><a href="/PhizIC/help.do?id=/private/credit/report">Кредитная история</a></li>
								<li><a class="active-menu" href="/PhizIC/help.do?id=/private/payments/creditReportPayment/edit/CreditReportPayment/null">Запрос кредитной истории</a></li>
								</c:if>
						</ul>
					</li>
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
                <div class="contextTitle">Запрос кредитной истории</div>
                    <ul class="page-content">
                       				<li><a href="#p3">Создание запроса</a></li>
									<li><a href="#p2">Подтверждение запроса</a></li>
									<li><a href="#p4">Просмотр запроса</a></li>
								</ul>
									<p>На данной странице Вы можете создать запрос на получение отчета о Вашей кредитной истории.</p>
									<div class="help-important">
                   		<p>
                        <span class="help-attention">Примечание</span>: отчет о кредитной истории - это документ, 
									который отображает Вашу кредитную историю, 
									то есть обобщенную информацию по кредитным 
									обязательствам и их состоянию на текущий 
									момент.</div>

								<p> Получить отчет о кредитной истории можно тремя способами: 
								<ul>
								<li>щелкнуть по баннеру на главной странице "Сбербанк Онлайн";</li>
								<li>в пункте главного меню <b>Прочее</b> из выпадающего списка выбрать раздел <b>Кредитная история</b>;</li>
								<li>в пункте главного меню <b>Кредиты</b> над списком Ваших кредитов нажать на ссылку <b>Кредитная история</b>.</li>
								</ul></p>
								<p>Если Вы впервые запрашиваете отчет о кредитной истории, то после перехода по ссылке <b>Кредитная история</b> откроется страница с
						подробным описанием той информации, которая будет предоставлена в отчете. Для того чтобы создать запрос на получение кредитной истории, нажмите на кнопку
						<b>Получить кредитную историю</b>.</p>
						<p>Если Вы не первый раз запрашиваете отчет о кредитной истории, то после перехода по ссылке <b>Кредитная история</b> 
						откроется страница просмотра последнего предоставленного Вам отчета. На этой странице вверху справа нажмите на кнопку <b>Обновить отчет</b>, если 
						хотите получить новые данные о кредитной истории.</p>
								<p><H2><a id="p3">Создание запроса</a></H2></p>
						<p>Откроется страница создания запроса. На этой странице в поле "Счет списания" выберите из выпадающего списка счет или карту, с которой будет совершена оплата услуги. После 
						этого нажмите на кнопку <b>Продолжить</b>.</p>
						<p>Если Вы передумали создавать запрос на получение кредитной истории, нажмите на ссылку <b>Отменить</b>.</p>
						
						<p><H2><a id="p2">Подтверждение запроса</a></H2></p>
						<p>Для того чтобы подтвердить создание запроса, на открывшейся странице проверьте указанные реквизиты и выполните следующие действия:</p>
						
						<li><b>Ознакомиться с условиями предоставления отчета</b>. 
						Ознакомьтесь с условиями договора о предоставлении 
						отчета. Для того чтобы просмотреть договор, щелкните
						по ссылке <b>Открыть условия договора в новом окне</b>. Затем установите галочку в поле 
						&quot;Я согласен с договором на оказание услуг по предоставлению кредитного отчета&quot;.</p>
						 
						<p>Далее необходимо ознакомиться с условиями передачи и обработки информации из кредитного отчета. Для этого нажмите на ссылку <b>с условиями</b> 
						в названии поля &quot;Я согласен с условиями передачи и 
						обработки информации из кредитного отчета&quot;, затем прочитайте условия. Если Вы 
						согласны с ними, установите галочку в чекбоксе рядом с 
						этим полем.</p></li>
						
						<li><b>Подтвердить запрос</b>. Для подтверждения операции нажмите на кнопку <b>Подтвердить по SMS</b> или выберите другой способ подтверждения. Для этого щелкните по ссылке <b>Другой способ подтверждения</b>
						и выберите один из предложенных вариантов:
						<ul><li><p><b>Пароль с чека</b> - подтверждение паролем с чека, распечатанного в банкомате.</li>
                        <li><p><b>Push-пароль из уведомления в мобильном приложении</b> - подтверждение 
                         паролем, полученным на мобильное устройство в виде Push-сообщения.</p></li></ul>
						
                     После этого заполните появившееся поле для ввода пароля и нажмите на кнопку <b>Подтвердить</b>.</p>
                        
						<li><b>Изменить параметры запроса</b>. Если Вы хотите изменить параметры запроса, нажмите на ссылку <b>Редактировать</b>. В результате 
						Вы вернетесь на страницу заполнения реквизитов.</p> 
						<li><b>Отменить запрос</b>. Если Вы передумали запрашивать отчет о кредитной истории, нажмите на кнопку <b>Отменить</b>. В результате Вы вернетесь на страницу с
						подробным описанием информации, предоставляемой в отчете, или на страницу 
						просмотра последнего отчета.</p>
						
						<p><H2><a id="p4">Просмотр запроса</a></H2></p>
						<p>После успешного подтверждения запроса откроется форма просмотра заполненного платежа, а также его статус (например, "Исполнен"). 
						Помимо этого, вверху формы просмотра отображается статус исполнения запроса кредитной истории (например, "Запрос Вашей кредитной истории отправлен на исполнение"),
						а также время, необходимое для подготовки кредитной истории.</p>
						<p>В результате отчет о Вашей кредитной истории будет показан в разделе <b>Кредитная история</b> автоматически, когда ответ на запрос будет готов.</p>
						<p>Если Вы хотите распечатать чек об оплате услуги, 
						нажмите на ссылку <b>Печать чека</b> внизу формы просмотра.</p>
						 
						<p>Для того чтобы скачать готовый отчет о Вашей кредитной истории в формате PDF, нажмите на ссылку 
						<b>Скачать отчет моей кредитной истории</b> на форме отчета о кредитной истории.</p>
						
						
						


                    <div class="help-linked">
                        <h3>Также рекомендуем посмотреть:</h3>
                        <ul class="page-content">
                            <li><a href="/PhizIC/help.do?id=/private/credit/report">Кредитная история</a></li>
                            <li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
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