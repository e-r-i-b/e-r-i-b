<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
    <title>Помощь. Гостевой Сбербанк Онлайн</title>
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
                 
                    
                    <li><a href="/PhizIC/help.do?id=/guest/index">Главная страница</a></li>
                    <li><a href="/PhizIC/help.do?id=/guest/promo">Возможности Сбербанк Онлайн</a></li>
                    <li><a href="/PhizIC/help.do?id=/guest/sberbankForEveryDay">Заказать дебетовую карту</a></li>
                    <li><a href="/PhizIC/help.do?id=/guest/payments/payment.do?form=LoanCardClaim">Заказать кредитную карту</a></li>
                    <li><a href="/PhizIC/help.do?id=/guest/payments/payment.do?form=ExtendedLoanClaim">Взять кредит в Сбербанке</a></li>


                </ul>

          <a href="/PhizIC/faq.do" class="faq-help">
                    <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
                </a>
            </div>
            <!-- end help-left-section -->
            <div id="help-workspace">
                <div class="contextTitle">Гостевой Сбербанк Онлайн</div>
           
                <h2><a id="p2">Гостевой Сбербанк Онлайн</a></h2>
			<p>Гостевой вход в "Сбербанк Онлайн" позволяет Вам воспользоваться системой, даже если Вы не являетесь клиентом Сбербанка. С его помощью Вы можете заказать дебетовую или кредитную карту, оформить заявку на кредит, а также просмотреть созданные Вами заявки.</p>
            <p>Для того чтобы продолжить работу в "Сбербанке Онлайн", ответьте на вопрос, зарегистрированы ли Вы в системе: </p>
            <ul>
            	<li>Если Вы уже зарегистрированы в системе, нажмите на ссылку <b>Да, я пользователь Сбербанк Онлайн</b>. В результате Вы перейдете на страницу входа.</li>
            	<li>Если Вы еще не являетесь пользователем системы, перейдите по ссылке <b>У меня нет логина в Сбербанк Онлайн</b>, и Вы перейдете на страницу гостевого входа.</li>
            </ul>
            
            <p>Если Вы хотите войти в "Сбербанк Онлайн" без регистрации в качестве гостя, выполните следующие действия: </p> 
            <p><b>Шаг 1. Авторизуйтесь с помощью мобильного телефона</b></p>	
            	<ul>
            		<li>Введите номер Вашего телефона в соответствующее поле и нажмите на кнопку <b>Продолжить</b>.</li>
            			<div class="help-important">
							<p><span class="help-attention">Обратите внимание</span>: если Вы уже зарегистрированы в системе "Сбербанк Онлайн", войдите в систему под Вашими логином и паролем и продолжите оформление заявки. Для этого нажмите на кнопку <b>Оформить через Сбербанк Онлайн</b>.</p>
						</div>	
            		<li>На Ваш мобильный телефон будет отправлен одноразовый SMS-пароль. Введите его в поле "SMS-пароль" и нажмите на кнопку <b>Подтвердить</b>. Если Вы заметили, что ввели номер телефона неверно, щелкните по ссылке <b>Изменить номер</b>, и Вы вернетесь к предыдущей странице.
            		Для того чтобы вернуться к 1 шагу оформления заявки, нажмите на кнопку <b>Начать заново</b>.</li>
            		<li>При необходимости, введите код с картинки и нажмите на кнопку <b>Подтвердить</b>. Если Вы не можете рассмотреть код, воспользуйтесь ссылкой <b>Обновить код</b>.</li>
                 </ul>			
			<p>После успешной авторизации Вы перейдете к оформлению экспресс-заявки на получение дебетовой или кредитной карты, либо кредита.</p>
			
			<p><b>Шаг 2. Оформите экспресс-заявку</b></p>
			<p>Для того чтобы начать оформление заявки на получение одного из банковских продуктов, выберите Ваш регион. Для этого в поле <b>Мой регион</b> щелкните по названию региона. Вы перейдете к справочнику регионов, в котором нажмите на название интересующего Вас региона. </p>
			<p>Затем Вам необходимо заполнить форму заявки. Заполнение форм заявки подробно описано в соответствующих разделах контекстной справки:</p>
				<ul>
					<li><a href="/PhizIC/help.do?id=/guest/sberbankForEveryDay">Заказать дебетовую карту</a></li>
					<li><a href="#p31">Заказать кредитную карту</a></li>
					<li><a href="#p32">Взять кредит в Сбербанке</a></li>
				</ul>
			<p>После того как параметры указаны, сохраните заполненную заявку и Вы перейдете к странице просмотра. В результате Ваша заявка будет направлена в банк.</p>
				
				<p><b>Шаг 3. Зарегистрируйтесь  в системе "Сбербанк Онлайн" в качестве гостя</b></p>
				<p>Для того чтобы перейти к странице регистрации, нажмите на кнопку <b>Зарегистрироваться</b>, и на открывшейся форме заполните следующие поля:</p>
					<ul>
						<li>В поле "Логин" укажите логин, с помощью которого Вы будете входить в систему "Сбербанк Онлайн". При создании логина используйте буквы русского или латинского алфавита, а также цифры. 
						Регистр букв значения не имеет.	Длина Вашего логина должна быть не менее 8 символов. Краткие правила создания логина и пароля Вы можете просмотреть справа от полей ввода логина и пароля. Для того чтобы просмотреть правила целиком нажмите на ссылку <b>Правила полностью</b>.</li>
						<li>В поле "Введите пароль" впишите пароль, под которым Вы будете входить в систему "Сбербанк Онлайн". </li>
						<li>В поле "Повторите пароль" укажите ту же комбинацию символов, которую Вы ввели в поле "Пароль".</li>
					</ul>
					<p>После того как все необходимые параметры указаны, нажмите на кнопку <b>Зарегистрироваться</b>. В результате Вы перейдете на <b>Главную страницу</b> гостевого приложения "Сбербанк Онлайн".</p>
					
					<p>Если Вы вошли в "Сбербанк Онлайн" в качестве гостя, то Вам будут доступны следующие пункты меню:</p>
						<ul>
							<li><a href="/PhizIC/help.do?id=/guest/index"><b>Главная</b></a></li>
							<li><a href="/PhizIC/help.do?id=/guest/promo"><b>Возможности Сбербанк Онлайн</b></a></li>
						</ul>
					<p>Также в гостевом "Сбербанке Онлайн" Вы можете воспользоваться <b>Помощью</b>, для того чтобы получить подробную информацию по выполнению различных операций в системе. Для этого щелкните по ссылке <b>Помощь онлайн</b> в боковом меню или внизу страницы.</p>		
				
				
				

				
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