<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Управление виджетами</title>
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
									<li><a href="/PhizIC/help.do?id=/private/PersonMenu">Личное меню</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
									<li><a href="/PhizIC/help.do?id=/private/mail/sentList">Вопрос в Контактный центр банка</a></li>
									<li><a href="/PhizIC/help.do?id=/private/news/list">События</a></li>
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/userprofile">Настройки</a>
										<ul>
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/widget">Управление виджетами</a></li>
										</ul>
									</li>
								</ul>								

                             <a href="/PhizIC/faq.do" class="faq-help">
                   			 <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
               				 </a>
         
	</div>
	<!-- end help-left-section -->
	<div id="help-workspace">
        <div class="contextTitle">Управление виджетами</div> 
								<ul class="page-content">
									<li><a href="#p2">Каталог виджетов</a></li>
									<li><a href="#p3">Формат страниц</a></li>
									<li><a href="#p4">Настройка стиля</a></li>
								</ul>
								<p><b>Виджет</b> - это информационный блок, содержащий, например, информацию о Ваших счетах или картах. </p>
								<P>Вы можете самостоятельно настраивать внешний вид страниц системы "Сбербанк Онлайн": добавлять новые блоки (виджеты) на страницу, удалять их, изменять положение виджетов относительно друг друга, перемещать боковое меню, а также настраивать стиль отображения страниц приложения.</P>
								
								<p>Для того чтобы перейти в режим редактирования страниц, над шапкой страницы щелкните по ссылке <b>Настроить</b>. В результате сверху раскроется панель настройки страницы. </p>
								 <div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: для того чтобы Вы могли работать с виджетами, Вам необходимо в пункте меню <b>Настройки</b> - <b>Настройка интерфейса</b> в разделе "Интерфейс" выбрать значение "Расширенный".
									
									</p>
								</div>

										<h2><a id="p2">Каталог виджетов</a></h2>
									<P>В данном разделе настроек Вы можете добавить новые блоки на главную страницу, изменить их положение, настроить параметры и при необходимости удалить неиспользуемые виджеты.</P>
									<p> При входе в этот раздел отображается каталог виджетов, в котором для каждого блока показана иконка, название, краткое описание и ссылка <b>Добавить</b>.</p>
													<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: Вы можете перемещаться по списку виджетов с помощью стрелочек <IMG border="0" src="${globalUrl}/commonSkin/images/widget-catalog-to-left.gif" width="20" height="20"  alt=""> и <IMG border="0" src="${globalUrl}/commonSkin/images/widget-catalog-to-right.gif" width="20" height="20" alt="">, либо передвигая вправо и влево бегунок на горизонтальном скроллинге под каталогом виджетов.
									
									</p>
								</div>
								<p> Для того чтобы изменить наполнение главной страницы системы, Вы можете выполнить следующие действия:</p>
									<ul>
									<li><b>Добавить виджет</b>. Для того чтобы добавить виджет на главную страницу, в списке виджетов нажмите левой кнопкой мыши на интересующий Вас виджет и, не отпуская кнопку, перетащите его вниз на страницу, либо нажмите на ссылку <b>Добавить</b>. В результате виджет появится на главной странице системы.
									</li>
									<li><b>Переместить виджет</b>. Если Вы хотите изменить положение виджета на странице, нажмите на него левой кнопкой мыши и, не отпуская кнопку, перетащите виджет в другое место.
									</li>
									<li><b>Настроить виджет</b>. В режиме редактирования Вы можете изменить внешний вид виджетов следующим образом:
									<ul> 
									<li>Если Вы хотите свернуть или развернуть блок, нажмите на стрелочку, расположенную перед названием виджета. В свернутом режиме отображается только название виджета, в развернутом - вся доступная по нему информация.
									</li>
									<li>Для того чтобы изменить параметры настраиваемых виджетов, щелкните по значку <IMG border="0" src="${globalUrl}/commonSkin/images/widget-button-settings-on-white.gif" alt="">. Затем задайте интересующие Вас параметры и нажмите на кнопку <b>Применить</b>. Если Вы передумали изменять настройки виджета, нажмите на кнопку <b>Отменить</b>.</p>
									</li>
									<li>Если Вы хотите удалить виджет с экрана, нажмите на иконку <IMG border="0" src="${globalUrl}/commonSkin/images/widget-button-close-on-white.gif" alt="">.
									</li>
									</ul>
									</li>
									</ul>
									<p>Для того чтобы сохранить выполненные изменения, нажмите на кнопку <b>Сохранить</b> на панели настройки страницы, и Вы выйдете из режима редактирования. При этом главная страница системы будет отображаться в измененном виде.</p>
									<p>Если Вы передумали изменять наполнение страницы, нажмите на кнопку <b>Отменить</b>, в результате Вы вернетесь к системе "Сбербанк Онлайн" без изменений. </p>
									<p> Для того чтобы вернуть настройки страниц, заданные по умолчанию, нажмите на кнопку <b>Сбросить настройки</b>.</p>
									
								<h2><a id="p3">Формат страниц</a></h2>
								<p>Вы можете изменить положение бокового меню на страницах приложения. Для этого на панели настроек страницы нажмите на ссылку <b>Формат страниц</b>.
									</p>
									<p>Вы можете переместить боковое меню одним из следующих способов:</p>
									<ul> 
									<li> 
									Нажмите левой кнопкой мыши на верхнюю часть бокового меню и, не отпуская кнопку, перетащите меню влево. Если Ваше личное меню находится с левой стороны, то перетащите его вправо.
									</li> 
									<li> 
									На панели настроек страницы в разделе "Формат страниц" щелкните по изображению нужного Вам формата ("Меню справа" или "Меню слева").
									</li>
									</ul>
									<p> После этого нажмите на кнопку <b>Сохранить</b>. В результате настройка будет применена ко всем страницам приложения.</p>
									<p>Если Вы передумали изменять формат страниц, нажмите на кнопку <b>Отменить</b>, в результате Вы вернетесь к системе "Сбербанк Онлайн" без изменений. </p>
									<p> Для того чтобы вернуть настройки страниц, заданные по умолчанию, нажмите на кнопку <b>Сбросить настройки</b>.</p>
																		
									
								<h2><a id="p4">Настройка стиля</a></h2>
									<p>Вы можете выбрать другой стиль отображения системы "Сбербанк Онлайн" на панели настроек системы в разделе <b>Настройка стиля</b>.</p>
									<p> В этом разделе будут показаны иконки с возможными стилями отображения системы. Если Вы хотите изменить стиль, щелкните по иконке с интересующим Вас стилем, и он будет применен к странице, на которой Вы находитесь. Для того чтобы выбранный стиль был применен ко всей системе, нажмите на кнопку <b>Сохранить</b>.</p>
									<p>Если Вы передумали изменять стиль отображения страниц, нажмите на кнопку <b>Отменить</b>, в результате Вы вернетесь к системе "Сбербанк Онлайн" без изменений. </p>
									<p> Для того чтобы вернуть настройки страниц, заданные по умолчанию, нажмите на кнопку <b>Сбросить настройки</b>.</p>
									
								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
									<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>
									<li><a href="/PhizIC/help.do?id=/private/payments">Переводы и платежи</a></li>
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

</body>