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
                    <li><a class="active-menu" href="/PhizIC/help.do?id=/guest/promo">Возможности Сбербанк Онлайн</a></li>
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
       
				
				<h3><a id="p4">Возможности Сбербанк Онлайн</a></h3>
				<p>На странице <b>Возможности Сбербанк Онлайн</b> показана подробная информация о системе. В блоке <b>Подключить Сбербанк Онлайн просто</b> показаны два способа регистрации в системе:</p>
				<ul>
					<li>Вы можете получить банковскую карту в любом отделении Сбербанка, подключить услугу "Мобильный банк" и зарегистрироваться в системе "Сбербанк Онлайн".</li>
					<li>Вы можете оформить заявку на получение банковской карты онлайн, а затем зарегистрироваться в системе.</li>
				</ul>
				<p>Также Вы можете познакомиться со всеми услугами, которые предоставляет система "Сбербанк Онлайн":</p>
				<ul>
					<li>Вы можете осуществить перевод между своими счетами и картами, пополнить электронный кошелек или перевести деньги на счет клиентов Сбербанка или других банков.</li>
					<li>Вы можете открывать вклады и обезличенные металлические счета, получая при этом проценты и бонусы.</li>
					<li>Вы можете оплатить различные услуги и штрафы, оформить автоплатежи, то есть платежи, которые совершаются автоматически по Вашей заявке.</li>
					<li>Вы можете создать шаблон для часто выполняемых операций, чтобы не вводить реквизиты много раз.</li>
					<li>Вы можете контролировать финансовые операции, совершаемые с Вашими картами, счетами и вкладами, просматривать историю операций.</li>
				</ul>
				<p>Если Вы хотите получить доступ ко всем возможностям системы, нажмите на ссылку <b>Откройте все функции Сбербанк Онлайн</b> и зарегистрируйтесь.</p>
				
				

				
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