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
                     
                    <li><a class="active-menu" href="/PhizIC/help.do?id=/guest/index">Главная страница</a></li>
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
              
              <h3><a id="p3">Главная страница</a></h3>
				<p>На <b>Главной странице</b> гостевого входа в систему "Сбербанк Онлайн" Вы можете ознакомиться с возможностями системы "Сбербанк Онлайн" и зарегистрироваться в  ней, оформить заявку на получение кредита, дебетовой или кредитной карты, а также просмотреть уже оформленные экспресс-заявки.</p>
				<p>В блоке <b>Мои заказы</b> показаны  оформленные Вами заявки на получение кредитов, дебетовых или кредитных карт.</p>
				<p>Для каждой заявки в списке Вы можете просмотреть название,  тип заявки и краткое описание заявки, а также дату ее создания.</p>
				<p>Для того чтобы просмотреть заявку, щелкните в списке по ее названию или типу.</p>
				<p>В блоке <b>Мои заказы</b> показаны 3 последние заявки. Если Вы оформили больше 3-х заявок, то для просмотра всех заявок нажмите на кнопку <b>Показать все заявки</b>. Если Вы хотите скрыть блок <b>Мои заказы</b>, нажмите на кнопку <b>Свернуть</b>.</p>
				<p>Также на данной странице Вы можете подать заявку на получение других банковских продуктов: </p>
					<ul>
						<li>Если Вы хотите оформить заявку на получение кредита, нажмите на ссылку <a href=""> Взять кредит в Сбербанке</a>.</li>
						<li>Для того чтобы заполнить заявку на получение кредитной карты, щелкните по ссылке <a href="">Заказать кредитную карту</a>.</li>
						<li>Если Вы хотите получить дебетовую карту Сбербанка, воспользуйтесь ссылкой <a href="/PhizIC/help.do?id=/guest/sberbankForEveryDay">Заказать дебетовую карту</a>.</li>
					</ul>
				
							
				

				
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