<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Напоминание о повторной оплате</title>
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
									<li><a href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/insurance/list">Страховые программы</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a>
										<ul>
											<li><a href="/PhizIC/help.do?id=/private/graphics/finance">Мои финансы</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/favouriteLinks#p5">Мобильные приложения</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/internetShops/orderList">Мои Интернет-заказы</a></li>
											<li><a href="/PhizIC/help.do?id=/private/loyalty/detail">Спасибо от Сбербанка</a></li>												
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/favouriteLinks#p2">Избранное</a></li>
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/favourite/list/PaymentsAndTemplates">Мои шаблоны</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/AutoPayments">Мои автоплатежи</a></li>
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
                <div class="contextTitle">Напоминание о повторной оплате</div>
 
								<ul class="page-content">
									<li><a href="#p1">Создание напоминания о повторной оплате</a></li>
									<li><a href="#p7">Редактирование напоминания</a></li>
									<li><a href="#p8">Отмена напоминания</a></li>
								</ul>
								
								
								<p>В системе "Сбербанк Онлайн" Вы можете настроить напоминания о повторных платежах на любую дату, а также выбрать кратность и периодичность напоминаний. 
								В результате самые важные платежи и переводы не будут забыты или просрочены.</p>
								
								<p>Создать напоминание о повторной оплате можно несколькими способами. В <b>Личном меню</b>, в разделе <b>Мои шаблоны</b>, на
								форме просмотра шаблона, нажмите на ссылку <b>Добавить напоминание</b>. Помимо этого, настроить напоминание
								можно в разделе <b>Мои финансы</b>, открыв финансовый календарь и выбрав платеж из детальной выписки за день, а также на форме просмотра платежа или перевода</b>.
								 </p>
																															
						<h2><a id="p1">Создание напоминания о повторной оплате</a></h2>
						<p> Для того чтобы создать напоминание о повторной оплате, на открывшейся форме впишите всю необходимую информацию:</p>
						<ul> 
							<li>Если напоминание создается для платежа, у которого нет шаблона, сначала заполните поля, необходимые для создания шаблона (подробнее об этом смотрите в 
							разделе контекстной справки <a href="/PhizIC/help.do?id=/private/favourite/list/PaymentsAndTemplates">Мои шаблоны</a> 
							- <b>Создание шаблона</b>);</li>
							<li>Заполните поля для настройки напоминаний:
							<ul>
							<li>В поле "Название напоминания" введите наименование для напоминания, под которым оно будет отображаться;</li>
							<li>Настройте периодичность, с которой напоминания будут отображаться в списке выставленных счетов. Для этого щелкните по ссылке <b>Однократно</b> и 
							из выпадающего списка выберите одно из значений: "Однократно", "Ежемесячно" или "Ежеквартально";</li>
							<div class="help-important">
						<p>
						<span class="help-attention">Обратите внимание</span>: в настройках напоминания по умолчанию задано значение периодичности напоминаний "Однократно".</p> </div>
							<li>В соответствии с выбором периодичности в появившемся календаре настройте 
							дату, в которую напоминания будут отображаться в списке 
							выставленных счетов.</li>
						
						 <div class="help-important">
						<p>
						<span class="help-attention">Обратите внимание</span>: в настройках напоминания по умолчанию
						 устанавливается текущая дата.</p> </div>

						 
						<p>После того как все параметры заданы, нажмите на кнопку <b>Сохранить</b>.</p> 
						
						<p>Откроется форма подтверждения, где необходимо проверить 
						реквизиты платежа, при необходимости ввести SMS-пароль, 
						отправленный на Ваш мобильный телефон. Также на странице подтверждения можно задать другую дату и периодичность 
						напоминания, нажав на ссылку <b>Добавить напоминание</b>. Откроется форма настройки, на которой будет отображен календарь.
						С помощью календаря задайте новую дату и периодичность напоминаний.</p> 
						<p>После внесения всех изменений нажмите на кнопку <b>Подтвердить</b>. Откроется форма просмотра шаблона с установленными настройками напоминания.
						</p> 
						<p>Если Вы передумали создавать шаблон, нажмите на кнопку <b>Отменить</b>.</p>
						
						<p>В результате успешного создания напоминания шаблон появится в личном меню, в списке раздела <b>Мои шаблоны</b>. Он будет выделен и отмечен колокольчиком<span class="bimbom"></span>. 
						Помимо этого,
						соответствующий платеж или перевод будет отображен 
						на странице <b>Переводы и платежи</b> в блоке <b>Счета к оплате</b> 
						в тот день, на который назначено напоминание. Подробнее об этом смотрите 
						в разделе контекстной справки <a href="/PhizIC/help.do?id=/private/payments">Переводы и платежи</a> в блоке <b>Счета к оплате</b>.</p> 
						 </ul><ul>
						 
							
							
							<h2><a id="p7">Редактирование напоминания</a></h2>
							<p>Для того чтобы отредактировать напоминание о платеже или переводе, в <b>Личном меню</b> выберите пункт <b>Мои шаблоны</b>. В списке шаблоны,
							для которых настроено напоминание, выделены и отмечены колокольчиком <span class="bimbom"></span>.</p> 
							<p>Рядом с нужным шаблоном нажмите на кнопку <b>Операции</b> и из выпадающего списка выберите <b>Редактировать</b>. Откроется 
							форма редактирования шаблона, на которой можно изменить дату и периодичность напоминаний.</p>
							
							<p>После внесения всех изменений нажмите на кнопку <b>Сохранить</b>. Возможно, потребуется подтверждение изменений с помощью SMS-пароля, отправленного на Ваш мобильный телефон. 
							В результате шаблон сохранится с новыми настройками.</p>
							 							
							<p>Если Вы передумали вносить изменения в настройки напоминаний, нажмите на кнопку <b>Отменить</b>.</p>
		<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: напоминание о платеже в следующем месяце не будет отображаться до тех пор, пока счет с напоминанием за предыдущий месяц не 
										будет отложен, оплачен или удален.</p></div>
							
	
							<h2><a id="p8">Отмена напоминания</a></h2>	
								<p>Для того чтобы отменить напоминание о платеже или переводе, в <b>Личном меню</b> выберите пункт <b>Мои шаблоны</b>. В списке шаблоны,
							для которых настроено напоминание, выделены и отмечены колокольчиком<span class="bimbom"></span>.</p> 
							<p>Рядом с нужным шаблоном нажмите на кнопку <b>Операции</b> и из выпадающего списка выберите <b>Редактировать</b>. Откроется 
							форма редактирования шаблона, на которой нажмите на ссылку <b>Не напоминать</b>, а затем подтвердите отмену напоминаний.</p>
							
						
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>Кроме того, в системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
								который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые 
								вопросы</b> в боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые 
								вопросы по работе с системой "Сбербанк Онлайн".</p> 					
								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
									<li><span></span><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
									<li><span></span><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
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