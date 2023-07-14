<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
    <title>Помощь. Оглавление</title>
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
                <li><a href="/PhizIC/help.do?id=/private/payments">Платежи и переводы</a></li>
                <li><a href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">Больше Сбербанк Онлайн</a></li>
                <li><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
                <li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
                <li><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></li>
                <li><a href="/PhizIC/help.do?id=/private/ima/list">Металлические счета</a></li>
                <li><a href="/PhizIC/help.do?id=/private/security/list">Сертификаты</a></li>
                <li><a href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a></li>
                <li><a href="/PhizIC/help.do?id=/private/npf/list">Пенсионные программы</a></li>
                <li><a href="/PhizIC/help.do?id=/private/insurance/list">Страховые программы</a></li>
                <li><a href="/PhizIC/help.do?id=/private/mobilebank/main">Мобильный банк</a></li>
                <li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
                <li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
                <li><a href="/PhizIC/help.do?id=/private/mail/sentList">Вопрос в Контактный центр банка</a></li>
                <li><a href="/PhizIC/help.do?id=/private/news/list">Новости</a></li>
                <li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>
            </ul>

            <a href="/PhizIC/faq.do" class="faq-help">
                <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
            </a>
        </div>
        <!-- end help-left-section -->
        <div id="help-workspace">


            <div class="contextTitle">Оглавление</div>
            <h3>
            <p>Уважаемый клиент!</p>
            <p>Вы находитесь в оглавлении справочного руководства системы "Сбербанк Онлайн". </p>
            <p>В данном документе Вы можете ознакомиться с системой "Сбербанк Онлайн":</p>
            <ul>
            <li>получить информацию о продуктах Сбербанка, которые Вам доступны в системе "Сбербанк Онлайн" 
			(картах, вкладах и счетах, кредитах и др. продуктах);</li>
            <li>найти информацию о том, как перевести деньги или оплатить товары и услуги;</li>
            <li>узнать, как пополнить вклад;</li>
            <li>узнать подробнее о копилке, как ее подключить и где она отображается в системе;</li>
            <li>найти информацию о том, как оплатить кредит;</li>
            <li>узнать, как оформить заявку на блокировку карты или заявить об утере сберегательной книжки;</li>
            <li>найти информацию о том, как оформить заявку на кредит или на открытие вклада;</li>
            <li>узнать о том, как создать шаблон операции и совершать ее по шаблону;</li>
            <li>найти информацию об автоплатежах;</li>
            <li>узнать, как настроить автопоиск счетов к оплате;</li>
            <li>получить информацию о списке совершенных в системе операций;</li>
            <li>узнать о том, где можно просмотреть информацию о расходах и доступных средствах;</li>
            <li>получить информацию о том, как пройти финансовое планирование;</li>
            <li>узнать о том, как настроить отображение Ваших расходов, а также найти другую важную информацию.</li>
            </ul>
            <p>Для того чтобы найти нужную информацию, выберите интересующий Вас раздел справочного руководства в боковом меню.</p>
            <p>Также Вы можете воспользоваться блоком "Часто задаваемые вопросы о Сбербанк Онлайн", в котором содержатся ответы на возможные вопросы о системе "Сбербанк Онлайн".</p>
            <p>Не нашли нужный раздел? Скорее всего, он находится в разработке и скоро будет добавлен в справочное руководство системы.</p>
            </h3>
            
        </div>
        <!-- end help-workspace -->
    </div>
    <!-- end help-content -->

    <div class="clear"></div>
</div>
</body>