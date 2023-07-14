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
									<li><a href="/PhizIC/help.do?id=/private/payments">Переводы и платежи</a></li>							
									<li><a class="active-menu" href="/PhizIC/help.do?id=/private/connectUdbo/connectUdbo">Больше Сбербанк Онлайн</a></li>
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
                <div class="contextTitle">Больше Сбербанк Онлайн</div>

									<ul class="page-content">
									<li><a href="#p2">Создать заявку</a></li>
									<li><a href="#p3">Проверить личные данные</a></li>
									<li><a href="#p4">Подтвердить заявку</a></li>
									<li><a href="#p5">Просмотреть заявку</a></li>

								</ul>
										<p>Для того чтобы иметь доступ ко всем возможностям "Сбербанк Онлайн", необходимо заключить универсальный договор банковского обслуживания. Сделать это можно 
										 в пункте главного меню <b>Больше Сбербанк Онлайн</b>.</p>
										<p>Пункт главного меню <b>Больше Сбербанк Онлайн</b> предназначен для оформления заявки на заключение универсального договора банковского обслуживания и 
										удаленного подключения всех возможностей системы "Сбербанк Онлайн" к Вашему профилю. 
								</p>
								
								<p>При переходе в пункт меню <b>Больше Сбербанк Онлайн</b> открывается страница с описанием возможностей системы "Сбербанк Онлайн", которые будут доступны
								Вам после заключения договора. На этой странице Вы можете выполнить одно из следующих действий:</p>
								<ul>
								<li>Для того чтобы заключить договор, нажмите на кнопку <b>Подключить</b>. В результате Вы перейдете на форму для согласования условий договора.</li>
								<li>Если Вы не хотите заключать договор, нажмите на ссылку <b>Не сейчас</b>. 
								И Вы сможете вернуться на <b>Главную</b> страницу, 
								щелкнув по ссылке <b>Вернуться на главную.</b></li>
								</ul>

								<div class="help-important">
								<p>
								<span class="help-attention">Обратите 
								внимание</span>: если после отказа от заключения договора Вам отображается сообщение "Для продолжения работы необходимо заключить договор банковского
								обслуживания", значит дальнейшая работа в системе "Сбербанк Онлайн" без заключения данного договора невозможна. </p>
								</div>
								<H2><a id="p2">Создать заявку</a></h2>
								<p>После того как Вы нажали на кнопку <b>Подключить</b>, откроется страница согласования условий договора.</p> 
								<p>Для того чтобы оформить заявку, выполните следующие действия:
								<ul>
								<li>Ознакомьтесь с полным текстом договора, щелкнув по ссылке <b>Открыть условия в новом окне</b>;</li>
								<li>Если Вы принимаете условия договора, поставьте галочку в соответствующем поле.</li>
								</ul> 
								<p>После этого нажмите на кнопку <b>Продолжить</b>, и Вы перейдете на форму проверки персональных данных. </p>
								<p>Если Вы передумали заключать универсальный договор банковского обслуживания, щелкните по ссылке <b>Отменить</b>.</p>
								
								<H2><a id="p3">Проверить личные данные</a></h2>
								<p>На форме проверки личной информации убедитесь, что 
								Ваши сведения указаны верно, затем выполните одно из действий:</p> 
								<ul>
								<li>Если информация верна, нажмите на кнопку <b>Подтверждаю</b>. В результате Вы перейдете на форму подтверждения заявки.</li>
								<li>Если Ваши персональные данные изменились, нажмите на ссылку <b>Не подтверждаю</b>. После этого для актуализации Вашей личной информации обратитесь в
								 отделение Сбербанка, чтобы иметь возможность оформить заявку. </li> 
								<li>Для того чтобы перейти на <b>Главную</b> страницу, 
								щелкните по ссылке <b>Вернуться на главную.</b> </li>
								</ul>
								
								<H2><a id="p4">Подтвердить заявку</a></h2>
								<p>На странице подтверждения заявки внимательно прочитайте заявление на банковское обслуживание и еще раз проверьте Вашу личную информацию, затем 
								выполните одно из действий:</p>
								<ul>
								<li>Если вся информация в заявке верна, нажмите на кнопку <b>Подтвердить SMS-паролем</b>. <!--или выберите подтверждение Push-паролем из мобильного приложения. -->
								После этого во всплывающем окне введите полученный пароль и нажмите на кнопку <b>Подтвердить</b>. В результате Вы перейдете на форму просмотра заявки.</li>
								<li>Если Вы хотите вернуться на предыдущий шаг оформления заявки, нажмите на ссылку <b>Назад</b>.</li>
								</ul>
								
								<H2><a id="p5">Просмотреть заявку</a></h2>
								<p>На странице просмотра Вы сможете увидеть основную 
								информацию по заявке, ее статус и присвоенный 
								номер договора банковского обслуживания, а также распечатать заявку.</p>
								<p>После того как заявка будет исполнена банком, ее статус изменится на "Исполнено", и при следующем входе в систему Вам будет 
								показано сообщение о подключении всех возможностей
								"Сбербанк Онлайн", либо о том, что полный перечень операций будет доступен через некоторое время.</p>
								<div class="help-important">
								<p>
								<span class="help-attention">Обратите 
								внимание</span>: если статус Вашей заявки "Ожидает дополнительного подтверждения", 
								значит Вам нужно позвонить по телефону, указанному в сообщении, для уточнения некоторой информации. После этого заявка будет принята к исполнению. </p>
								</div>
								<p>На данной странице Вы можете распечатать заявку, нажав на ссылку <b>Печать</b> внизу формы. В результате откроется печатная версия заявки,
								которую можно распечатать на принтере.</p>
								<p>Если Вы хотите перейти к главной странице системы, щелкните по ссылке <b>Вернуться на главную</b>.</p>
							
								


								<div class="help-linked">
									<h2>Также рекомендуем посмотреть</h2>
									<ul class="page-content">
										<li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
										<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>

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