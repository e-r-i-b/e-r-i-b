<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<head>
<title>Помощь. Оформить заявку перевыпуск карты</title>
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
									<li><a class="parentItem" href="/PhizIC/help.do?id=/private/cards/list">Карты</a>
									<ul class="page-content">
											
											<li><a class="active-menu" href="/PhizIC/help.do?id=/private/payments/payment/ReIssueCardClaim">
											Заявка на перевыпуск карты</a></li>
											
										</ul>
									</li>
									<li><a href="/PhizIC/help.do?id=/private/accounts/list">Вклады и счета</a></li>
									<li><a href="/PhizIC/help.do?id=/private/loans/list">Кредиты</a></li>
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
                <div class="contextTitle">Карты. Перевыпуск карты</div>
								<ul class="page-content">
								
									<li><a href="#p2">Создать заявку</a></li>
									<li><a href="#p3">Подтвердить заявку</a></li>
									<li><a href="#p4">Просмотреть заявку</a></li>

								</ul>
								<p>В системе "Сбербанк Онлайн" Вы можете подать заявку на перевыпуск дебетовой карты. Для этого в пункте главного меню <b>Карты</b> щелкните по ссылке <b>Перевыпуск карты</b> на панели операций.</p>								
								<div class="help-important">
									<p>
										<span class="help-attention">Обратите 
										внимание</span>: в системе "Сбербанк Онлайн" Вы можете переходить на нужную страницу не только с помощью 
										пунктов верхнего меню, ссылок бокового меню, но и с помощью ссылок, расположенных под главным меню. Данные 
										ссылки показывают путь от главной страницы до той, с которой Вы перешли на текущую страницу. Вы можете использовать эти ссылки для 
										перехода на интересующую страницу.  
									</p>
								</div>
								<h2><a id="p2">Создать заявку</a></h2>

							
								<p>В результате откроется страница создания заявки, на которой нужно указать следующую информацию:
								<ul>
                                    <li>В поле "Карта" выберите из выпадающего списка карту, которую Вы хотите перевыпустить.</li>
                                    <li>Поля "ФИО владельца", "Дата выпуска", "Действует до", "Статус карты" будут заполнены автоматически.</li>
                                    <li>В поле "Причина перевыпуска" выберите из выпадающего списка причину, по которой Вы хотите перевыпустить карту.</li>
                                    <li> В поле "Место получения карты" укажите отделение банка, где Вам удобно получить карту. Для этого нажмите на ссылку <b>Выбрать из справочника</b>,
                                    откроется справочник подразделений банка, в котором установите переключатель напротив интересующего Вас филиала, и нажмите на кнопку <b>Перевыпустить</b>.

                                        <div class="help-important">
                                            <p>
                                                <span class="help-attention">Обратите
                                            внимание</span>: если Вы не нашли в списке нужное подразделение, выберите из справочника ближайшее отделение банка, в котором Вам будет удобно получить карту.
                                            </p>
                                        </div>
                                    </li>
								</ul>
                                <p>После того как все реквизиты заявки заполнены, нажмите на кнопку <b>Перевыпустить</b>.
									Система выведет на экран страницу 
									подтверждения документа, на которой Вам 
									необходимо проверить правильность заполнения 
									реквизитов.</p>
									<!--<div class="help-important">
								<p>
								<span class="help-attention">Обратите 
								внимание</span>: перед созданием заявки на перевыпуск карты, убедитесь, что выбранная карта заблокирована. Если карта активна, то перейдите по ссылке в Личном меню <b>Заблокировать карту</b>.  
								</p>
								</div>-->
                                <p>Для того чтобы вернуться к странице <b>Переводы и платежи</b>, щелкните по ссылке <b>Назад к выбору услуг</b>.</p>
                                <p>Если Вы передумали создавать заявку, то
									нажмите на ссылку <b>Отменить</b>. В 
									результате Вы вернетесь на страницу <b>
									Переводы и платежи</b>.</p>
									
                                <div class="help-important">
                                    <p><span class="help-attention">Обратите
                                    внимание</span>: Вы можете контролировать процесс выполнения операции с помощью
                                    линии вверху формы, на которой будет выделено состояние операции на данный момент.
                                    Например, если Вы находитесь на странице подтверждения, то будет выделен отрезок
                                    "Подтверждение".
                                    </p>
								</div>

								<h2><a id="p3">Подтвердить заявку</a></h2>
								<p>Далее необходимо подтвердить заявку. После 
								того как Вы нажали на кнопку <b>Перевыпустить</b>, 
								Вам откроется заполненная форма заявки, в 
								которой нужно проверить правильность указанных 
								сведений, после чего выполнить одно из следующих 
								действий:
                                </p>
                                <ul>
									<li><b>Подтвердить заявку</b>. Убедитесь, что вся информация указана 
									верно. Затем для подтверждения операции Вам необходимо выбрать, 
									каким способом Вы хотите ее подтвердить:
                                        <ul>
                                            <li>Если Вы хотите подтвердить операцию SMS-паролем,
                                                нажмите на кнопку <b>Подтвердить по SMS</b>;
                                                <div class="help-important">
                                                    <p>
                                                        <span class="help-attention">Обратите
                                                        внимание</span>: перед вводом пароля убедитесь, что реквизиты операции совпадают с
                                                        текстом SMS-сообщения. Будьте осторожны, если данные не совпадают, ни в коем случае не вводите пароль
                                                        и никому его не сообщайте, даже сотрудникам банка.
                                                    </p>
                                                </div>
                                            </li>
                                            <li>Если Вы хотите подтвердить операцию другим способом, нажмите на
                                                ссылку <b>Другой способ подтверждения</b>. Затем
                                                выберите один из предложенных вариантов:
                                                <ul>
                                                    <li><b>Пароль с чека</b> - подтверждение паролем с чека, распечатанного в банкомате.</li>
                                                    <li><b>Push-пароль из уведомления в мобильном приложении</b> - подтверждение
                                                паролем, полученным на мобильное устройство в виде Push-сообщения.</li>
                                                </ul>
                                                Затем откроется всплывающее окно,	в котором укажите нужный пароль и нажмите на кнопку <b>Подтвердить</b>.
                                                В результате Вы перейдете на страницу просмотра заявки.
                                            </li>
                                            <li><b>Изменить реквизиты</b>. Если при проверке реквизитов выяснилось, что заявку необходимо отредактировать,
                                             щелкните по ссылке <b>Редактировать</b>, и Вы вернетесь на страницу заполнения
                                            реквизитов заявки.
                                            </li>
                                            <li><b>Распечатать заявку</b>. Если Вы хотите распечатать заявку на перевыпуск карты,
                                            то нажмите на ссылку <b>Печать заявления</b>. В результате откроется печатная форма заявки, которую Вы сможете распечатать на принтере.
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                                <p>Если Вы передумали создавать заявку, то
									нажмите на ссылку <b>Отменить</b>. В 
									результате Вы вернетесь на страницу <b>
									Переводы и платежи</b>.</p>
								<h2><a id="p4">Просмотреть заявку</a></h2>
									<p>После подтверждения заявки Вы перейдете 
									на страницу просмотра, на которой 
									увидите заполненный документ. </p>
									<p>На этой странице Вы 
									сможете <b>просмотреть реквизиты</b> выполненной 
									заявки.</p>
									<p>Для того чтобы распечатать заявку на перевыпуск карты,
									нажмите на ссылку <IMG border="0" src="${globalUrl}/commonSkin/images/print-check.gif" alt=""/> <b>Печать заявления</b>. В результате откроется печатная форма заявки, которую Вы сможете распечатать на принтере.</p>
								<p>Если Вы хотите <b>вернуться на страницу выбора платежа</b>, то щелкните по ссылке <b>Перейти к истории операций</b>.</p>
								<p>Если Вы хотите получить подробную информацию по выполнению операций на любой странице системы, то нажмите на ссылку <b>Помощь</b> в боковом меню или внизу страницы нажмите на ссылку <b>Помощь онлайн</b>.</p>
								<p>Кроме того, в системе "Сбербанк Онлайн" на каждой странице можно обратиться к помощи персонального консультанта, 
								который ответит на все Ваши вопросы. Чтобы запустить помощника, нажмите на ссылку <b>Часто задаваемые 
								вопросы</b> в боковом меню. В результате откроется окно, в котором содержатся ответы на часто задаваемые 
								вопросы по работе с системой "Сбербанк Онлайн". </p>
	

								<div class="help-linked">
									<h3>Также рекомендуем посмотреть:</h3>
									<ul class="page-content">
										<li><a href="/PhizIC/help.do?id=/private/cards/list">Карты</a></li>
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