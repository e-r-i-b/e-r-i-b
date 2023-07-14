<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Список документов по счету депо</title>
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
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/depo/list">Счета депо</a>
										<ul>												
											<li><a href="/PhizIC/help.do?id=/private/depo/info/position">Информация по позиции счета депо</a></li>	
											<li><a href="/PhizIC/help.do?id=/private/depo/info/debt">Информация о задолженности</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/DepositorFormClaim">Анкета депонента</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/SecuritiesTransferClaim/null">Поручение на перевод/ прием перевода ценных бумаг</a></li>
											<li><a href="/PhizIC/help.do?id=/private/payments/payment/SecurityRegistrationClaim">Регистрация ценной бумаги</a></li>
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/depo/documents">Список документов по счету депо</a></li>
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
									<li><a href="/PhizIC/help.do?id=/private/news/list">События</a></li>
									<li><a href="/PhizIC/help.do?id=/private/userprofile">Настройки</a></li>

									</li>

								</ul>

                           <a href="/PhizIC/faq.do" class="faq-help">
                    <span>Часто задаваемые вопросы о Сбербанк Онлайн</span>
                </a>
            </div>
            <!-- end help-left-section -->
            <div id="help-workspace">
                <div class="contextTitle">Счета депо. Список документов по счету депо</div>
                   
								<ul class="page-content">
									<li>
								<a href="#p2">Фильтр/поиск документов</a></li>
									<li>
								<a href="#p3">Статусы документов</a></li>
								</ul>

									<p>С помощью системы "Сбербанк Онлайн" Вы можете просмотреть все выполненные Вами операции 
									по счетам депозитарного учета, просмотреть интересующий документ, создать копию документа, а также воспользоваться фильтром операций.
									</P>
									
									
									<p> 
									 
									Для того чтобы просмотреть список документов по счету депо, в пункте меню <b>Счета депо</b> нажмите на кнопку <b>Операции</b>, и
										Вам откроется полный список операций по
										счету депо. Далее нажмите на ссылку <b>Список документов </b> рядом с выбранным Вами счетом. </P>
									
									
									<p> 
									 

									Система выведет на экран название и номер счета, по которому сформирован список документов, номер и дату 
									заключения договора по счету депо, состояние счета (закрыт) и сумму задолженности перед депозитарием.</P>
									
									<p>Вы можете выбрать, сколько документов будет показано на странице - 10, 20 или 50.
									Например, если Вы хотите просмотреть 20 документов по счету депо,
									то в строке "Показать по" выберите значение "20". Система выведет на экран 
									20 последних документов по счету.</p>
						<p>На данной странице Вы можете выполнить следующие действия:</P>
									
									<ul class="page-content">
									<li><A HREF="/PhizIC/help.do?id=/private/depo/info/position"><SPAN CLASS="toc2">Просмотреть информацию по позиции счета</SPAN></A></li>
									<li><A HREF="/PhizIC/help.do?id=/private/depo/info/debt"><SPAN CLASS="toc2">Получить информацию о Вашей задолженности</SPAN></A></li>
									<li><A HREF="/PhizIC/help.do?id=/private/payments/payment/DepositorFormClaim"><SPAN CLASS="toc2">
									Просмотреть сведения из анкеты депонента</SPAN></A></li>
									</ul>

									
									<p>На странице со списком документов по выбранному счету депо система выведет на экран список оформленных Вами документов. 
									Для каждого документа в списке отображается номер, дата создания, тип документа, дата и время его исполнения и статус, 
									отражающий стадию его обработки. </p>
									
									
									<p>На вкладке <b>Список документов</b> Вы можете выполнить следующие действия:
									</p>

<ul>
											<li><b>просмотреть</b> выбранный документ, для этого щелкните по интересующей Вас операции в списке. 
											Откроется страница просмотра, содержащая реквизиты документа. </li>
											
											<li><b>отредактировать</b> интересующий документ в списке. Для этого щелкните по нужному документу, 
											откроется страница подтверждения, на которой нажмите на ссылку <b>Редактировать</b>. 
											Отредактировать можно только документы в статусе "Черновик".</li>
											
											<li><b>отозвать</b> ошибочно отправленный документ. Для этого установите галочку напротив выбранного документа 
											и нажмите на ссылку <b>Отозвать</b>. В результате Вы перейдете к форме отзыва документа.
											 На экран будет выведена подробная информация по операции. 
											 Для того чтобы отозвать документ нажмите на кнопку <b>Сохранить</b>, затем щелкните по кнопке <b>Подтвердить</b> 
											 для подтверждения отзыва.</li>											</li>
										
										<div class="help-important">
									<p>
										<span class="help-attention">Примечание:</span> Вы можете отозвать документ только в статусе "Исполняется банком".										
									</p>
								</div>
									
											<li><b>создать копию</b>. Если Вы хотите создать копию документа, установите галочку напротив интересующего документа и 
											нажмите на кнопку <b>Создать копию</b>. В результате Вы перейдете к форме ввода нового документа, причем в неё будут 
											скопированы все данные из документа, выбранного Вами в качестве оригинала. Эта возможность полезна при создании однотипных 
											документов. </li>
											
											<li><b>удалить документ</b>. Вы можете удалить из списка документ в статусе "Черновик". 
											Для этого установите галочку напротив нужного документа и нажмите на ссылку <b>Удалить</b>, затем подтвердите операцию. 
											В результате удаленный документ не будет отображаться в списке документов по счету депо.</li>

											</ul>
									<p>Вы можете добавить страницу со списком документов по счету депо 
									 в личное меню, что
										позволит Вам перейти к ней с
										любой страницы системы &quot;Сбербанк
										Онлайн&quot;, щелкнув по ссылке в боковом меню в блоке <b>Избранное</b>.
										Для выполнения операции нажмите на
										ссылку <b>Добавить в избранное</b>. Если страница уже добавлена в Личное меню, то на этой странице будет отображаться ссылка <IMG border="0" src="${globalUrl}/commonSkin/images/favouriteHover.gif" alt=""><b>В избранном</b>.
									</p>
								<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" Вы можете переходить на нужную страницу не только с помощью 
										пунктов верхнего меню, ссылок бокового меню, но и с помощью ссылок, расположенных под главным меню. Данные 
										ссылки показывают путь от главной страницы до той, с которой Вы перешли на текущую страницу. Вы можете использовать эти ссылки для 
										перехода на интересующую страницу.  
									</p>
								</div>							
								

											<h2><a id="p2">Фильтр/поиск документов</a></h2>
									<p><b>Для быстрого поиска нужного документа</b> в полях "С.. по" укажите дату начала и окончания периода создания документа. Вы можете ввести дату вручную или воспользоваться календарем рядом с полем, щелкнув по пиктограмме 
									<IMG border="0" src="${globalUrl}/commonSkin/images/datePickerCalendar.gif" alt=" height="20" width="20"">. </p>
									<p>После того как Вы указали период, нажмите на кнопку <IMG border="0" src="${globalUrl}/commonSkin/images/period-find.gif" alt=" height="25" width="25"">.</p>
									<p>Ниже система покажет список документов, которые были созданы в указанный Вами промежуток времени.</p>
									<p><b>Для расширенного поиска</b> интересующих Вас документов в списке нажмите на кнопку <b>Показать</b> <IMG border="0" src="${globalUrl}/commonSkin/images/show-filter.gif" alt=""> и заполните следующие поля:</p>
									<ul>
											<li>В поле "Номер документа" введите номер документа; </li>
											<li> В поле "Статус документа" выберите из выпадающего списка нужный статус документа (см. раздел справки 
											<a href="#p3">Статусы документов</a>);</li>											
											<li> В поле "Тип документа" выберите из выпадающего списка тип документа.</li>
									</ul>
									<p>Вы можете ввести значение в одно или несколько полей фильтра. </p>
									<p>После того как все необходимые параметры указаны, 
									нажмите на кнопку <b>Найти</b>. В результате система выведет на экран список интересующих Вас документов.
 									</p>
 									<p>Если параметры поиска введены неверно, нажмите на ссылку <b>Очистить</b> и задайте новые критерии поиска.
									</p>
									
									<h2><a id="p3">Статусы документов</a></h2>

									
									<p>По статусу, который присвоен документу в системе, Вы можете отследить его состояние: </p>

										<ul>
											<li><b>Черновик</b> - документ сохранен, но не передан на обработку в банк; </li>
											
											<li><b>Исполняется банком</b> - документ подтвержден и передан на обработку в банк;</li>							
										
											<li><b>Исполнен</b> - документ успешно исполнен банком; </li>
											
											
											<li><b>Отклонено банком</b> - документ передан в банк на обработку, но затем Вам было отказано в совершении 
											операции по какой-либо причине;</li>

										<li><b>Заявка была отменена</b> - документ был Вами отозван.</li>

											</ul>

								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>Кроме того, в системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
								который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые вопросы</b> 
								в боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые вопросы по работе 
								с системой "Сбербанк Онлайн". 
								<!-- Если Вы не хотите, чтобы консультант отображался на страницах системы, то измените 
								настройки отображения помощника, щелкнув по кнопке <b>Настройки</b> - <b>Настройка интерфейса</b> - <b>Личное меню</b>. Для того чтобы 
								скрыть помощника, Вам нужно в блоке <b>Дополнительные настройки</b> убрать галочку в поле "Отображать персонального консультанта" 
								и нажать на кнопку <b>Сохранить</b>. --></p>

								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
											<li><span></span><a href="/PhizIC/help.do?id=/private/payments/servicesPayments/edit/*">Оплата услуг</a></li>
											<li><span></span><a href="/PhizIC/help.do?id=/private/accounts">Главная</a></li>
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