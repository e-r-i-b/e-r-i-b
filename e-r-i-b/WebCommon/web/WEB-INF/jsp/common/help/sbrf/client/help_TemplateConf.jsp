<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Мои шаблоны</title>
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
									<li><a href="/PhizIC/help.do?id=/private/payments">Переводы и платежи</a>
									</li>							
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
											<li><a href="/PhizIC/help.do?id=/private/payments/internetShops/orderList">Мои интернет-заказы</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/favouriteLinks#p4">Спасибо от Сбербанка</a></li>
											<li><a href="/PhizIC/help.do?id=/private/graphics/finance">Мои финансы</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/favouriteLinks#p2">Избранное</a></li>
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/favourite/list/PaymentsAndTemplates">Мои шаблоны</a></li>
											<li><a href="/PhizIC/help.do?id=/private/favourite/list/AutoPayments">Мои автоплатежи</a></li>										
										</ul>
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
                <div class="contextTitle">Шаблоны</div>


										<p>На странице <b>Шаблоны</b> Вы можете 
										просмотреть список Ваших активных 
										шаблонов и список черновиков.</p>
										<p><b>Шаблоны платежей</b> 
										- это частично или полностью заполненные 
										формы платежей. При оплате по шаблону Вы 
										можете ввести недостающие данные 
										(например, сумму) и произвести оплату 
										без подтверждения операции одноразовым 
										SMS-паролем или паролем с чека.</p>
										<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: при первом входе в 
										систему у Вас еще нет ни одного шаблона. 
										Для того чтобы создать шаблон платежа, 
										нажмите на кнопку <b>Создать шаблон</b>. 
										Подробную информацию смотрите в разделе 
										контекстной справки <a href="/PhizIC/help.do?id=/private/payments/template/*">
										Создание шаблона</a>.</p>
								</div>

										
										<p>По каждому шаблону в списке 
										отображается название, дата его создания 
										и заданная в нем сумма.</p>
									<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: если в каком-либо 
										канале обслуживания шаблон не 
										отображается, то в поле "Доступен" будет 
										показан признак "Частично доступен".</p>
								</div>	
										<p>Вы можете <b>просмотреть любой шаблон</b> 
										в списке, щелкнув по его названию.</p>
										<p> Также с каждым шаблоном Вы можете 
										выполнить следующие действия:</p>
										<ul> 
										<li> 
										<b>Оплатить по шаблону</b>. Если Вы 
										хотите совершить платеж или перевод 
										средств по шаблону, нажмите на кнопку <b>
										Операции</b> напротив него, а затем 
										щелкните по ссылке <b>Оплатить</b>.  
										</li>
										<li> 
										<b> Редактировать шаблон</b>. Для того 
										чтобы изменить реквизиты платежа, 
										указанные в шаблоне, нажмите на кнопку <b>
										Операции</b> напротив него, а затем 
										щелкните по ссылке <b>Редактировать</b>.</li>
										<li> 
										<b>Создать напоминание о 
										повторной оплате</b>. Для того чтобы создать напоминание
										 о повторной оплате по 
										шаблону,
										нажмите на кнопку <b>
										Операции</b> напротив него, 
										а затем 
										щелкните по ссылке <b>Редактировать</b>. На форме редактирования задайте 
										настройки напоминания и нажмите на кнопку <b>Сохранить</b> (подробнее 
									смотрите раздел контекстной справки <a href="/PhizIC/help.do?id=/private/template/select-category#p11">Создaние напоминания о повторной оплате</a>).</li>
										<li> 
										<b> Удалить шаблон</b>. Если Вы хотите 
										удалить шаблон из системы, то нажмите на 
										кнопку <b>Операции</b> напротив него, а 
										затем щелкните по ссылке <b>Удалить</b>. После подтверждения удаления шаблон будет удален из системы.
										</li>
										</ul>
										<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: список возможных 
										действий с шаблоном может отличаться в 
										зависимости от типа операции, для 
										которой он создан.</p>
								</div>	
									<p>На данной странице отображается не более 
									10 шаблонов. Чтобы просмотреть остальные 
									шаблоны платежей, под списком шаблонов в 
									поле &quot;Показать по&quot; нажмите на ссылку 20, 50 
									или воспользуйтесь стрелочками <b>&lt;</b>, <b>
									&gt;</b>.</p>
									<p> Вы можете изменить порядок расположения 
									шаблонов в списке и в Вашем Личном меню. Для 
									этого наведите курсор на один из шаблонов, 
									нажмите на левую кнопку мыши. Затем, 
									удерживая кнопку, переместите шаблон по 
									списку в нужное место.</p>

 <!-- <p> По умолчанию открывается список всех шаблонов. Если Вы хотите просмотреть действующие шаблоны со статусом "Активны", перейдите на вкладку <b>Обычные</b>. Для того чтобы посмотреть список шаблонов, операции по которым совершаются сверх установленного суточного лимита, щелкните по вкладке <b>Подтвержденные в КЦ</b>. Если Вы хотите просмотреть шаблоны в статусе "Черновик" (не заполненные до конца или не подтвержденные SMS-паролем), щелкните по вкладке <b>Черновики</b>.</p> -->
									<!-- 
									<H4>Поиск шаблонов</h4>
									<p>Для того чтобы найти интересующий Вас шаблон, воспользуйтесь панелью поиска. Для этого нажмите на ссылку <b>Показать</b> и в открывшиеся поля введите нужные параметры:</p>
									<ul> 
									<li> 
									В поле "Показать" выберите из выпадающего списка вид операции, шаблон по которой Вы ищете;
									</li>
									 <li> 
									В поле "Дата создания" укажите примерную дату создания шаблона. Для этого в полях "С" и "По" нажмите на иконку календаря и выберите интересующую Вас дату или укажите ее вручную. 
									</li> 
									<li> 
									В поле "Название" впишите полностью или частично название шаблона.
									</li>
									<li> 
									В поле "Статус" выберите из выпадающего списка статус шаблона на данный момент.
									</li>
									<li> 
									В поле "Сумма" введите в поля "От" и "До" примерную сумму, на которую был создан шаблон. В дополнительной секции из выпадающего списка выберите валюту, в которой был создан шаблон.
									</li>
									</ul>
									<p> После того как все необходимые условия заданы, нажмите на кнопку <b>Показать</b>, и система покажет список интересующих Вас шаблонов.</p>
									<p> Вы можете осуществлять поиск по комбинации полей.</p>
									<p> Для того чтобы указать другие параметры, нажмите на ссылку <b>Очистить</b> и задайте новые условия поиска.</p>
									-->
									<p>Более подробную информацию по работе с 
									шаблонами Вы можете посмотреть в разделе 
									контекстной справки <a href="/PhizIC/help.do?id=/private/receivers/list">
									Шаблоны платежей</a>.</p>
									<p>Также Вы можете на данной вкладке 
									самостоятельно настраивать отображение 
									шаблонов, для этого щелкните по ссылке 
									<b>Настройки - Настройка безопасности</b>.</p>
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
																<div class="help-linked">
									<h2>Также рекомендуем посмотреть</h2>
									<ul class="page-content">
										<li><span></span><a href="/PhizIC/help.do?id=/private/payments/common/all">История операций</a></li>
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