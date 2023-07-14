<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Помощь. Схемы прав</title>
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

</head>
<body>

    <div class="help-container">
        <div id="help-header">
            <p class="helpTitle">Помощь по системе "Сбербанк Онлайн"</p>
        </div>

        <!-- end help-header -->
        <div class="clear"></div>

        <div id="help-content">
            <div id="help-left-section">
                <p class="sidebarTitle">Разделы помощи</p>
                    <ul class="help-menu">
                        <li><a href="/PhizIA/help.do?id=/persons/list">Клиенты</a></li>
                        <li><a href="/PhizIA/help.do?id=/schemes/list">Схемы прав</a></li>
                        <li><a href="/PhizIA/help.do?id=/employees/list">Сотрудники</a></li>
                        <li><a href="/PhizIA/help.do?id=/log/operations">Сервис</a></li>
                        <li><a href="/PhizIA/help.do?id=/audit/businessDocument">Аудит</a></li>
                        <li><a href="/PhizIA/help.do?id=/person/search">Автоплатежи</a></li>
                        <li><a href="/PhizIA/help.do?id=/private/dictionary/banks/national">Справочники</a></li>
                        <li><a href="/PhizIA/help.do?id=/private/dictionaries/provider/list">Поставщики услуг</a></li>
                        <li><a href="/PhizIA/help.do?id=/dictionaries/routing/node/list">Внешние системы</a></li>
                        <li><a href="/PhizIA/help.do?id=/persons/configure">Настройки</a></li>
                        <li><a href="/PhizIA/help.do?id=/configure/inform/service">Реклама и сообщения</a></li>
                        <li><a href="/PhizIA/help.do?id=/departments/list">Подразделения</a></li>
                        <li><a href="/PhizIA/help.do?id=/news/list">События</a></li>
                        <li><a href="/PhizIA/help.do?id=/mail/list">Письма</a></li>
                        <li><a href="/PhizIA/help.do?id=/deposits/list">Депозиты</a></li>
                        <li><a href="/PhizIA/help.do?id=/loans/products/list">Кредиты</a></li>
                        <li><a href="/PhizIA/help.do?id=/creditcards/products/list">Карты</a></li>
                        <li><a href="/PhizIA/help.do?id=/reports/index">Отчеты и мониторинг</a></li>
                        <li><a href="/PhizIA/help.do?id=/technobreak/list">Технологические перерывы</a></li>
                        <li><a href="/PhizIA/help.do?id=/pfp/targets/list">Настройка ПФП</a></li>
                        <li><a href="/PhizIA/help.do?id=/pfp/person/search">Проведение ПФП</a></li>
                        <li><a href="/PhizIA/help.do?id=/passingPfpJournal">Журнал ПФП</a></li>
                        <li><a href="/PhizIA/help.do?id=/ermb/person/search">ЕРМБ</a></li>
                       	<li><a href="/PhizIA/help.do?id=/ermb/migration/settings">Миграция</a></li>
                       	<li><a href="/PhizIA/help.do?id=/addressBook/reports/index">Отчеты по сервису синхронизации АК</a></li>
                       	<li><a href="/PhizIA/help.do?id=reports/business/configure" class="active-menu">Отчеты по бизнес-операциям</a></li>

                    </ul>
                </div>
 				<!-- end help-left-section -->
            	<div id="help-workspace">
                <div class="contextTitle">Отчеты по бизнес-операциям</div>

                    <p>Пункт меню <b>Отчеты по бизнес-операциям</b> предназначен для настройки отчетов по различным банковским операциям.</p>
                    <p><b>Настройка отчетов</b></p>
                    <ul>
	                    <p>С помощью блока <b>Настройка отчетов</b> Вы можете отредактировать параметры создаваемых в системе отчетов по бизнес-операциям. Для этого заполните следующие поля:</p>
	                    <li>В поле "Отчет" поставьте галочку напротив тех видов отчетов, которые будут формироваться в системе. Вы можете выбрать все отчеты, либо отчеты одной или нескольких категорий, например, отчеты по платежам.</li>
	                  	<li>В поле "Отклонение" укажите процент отклонения от нормы, при превышении которого ячейка в отчете будет выделена красным цветом.</li>
	                    <li>В поле "Блок" выберите из выпадающего списка номера блоков, для которых будут формироваться отчеты.</li>
	                    <li>В поле "Канал" выберите из выпадающего списка, операции в каких каналах будут включены в отчет.</li>
                    </ul>
                      <p>После того как все необходимые поля заполнены, нажмите на кнопку <b>Сохранить</b>, и настройки вступят в силу.</p>
                    
                    <p><b>Настройка рассылки писем</b></p>
                    <p>В блоке <b>Настройка рассылки писем</b> Вы можете изменить параметры рассылки отчетов. Для этого заполните следующие поля:</p>
                    <ul>
	                    <li>В поле "Список получателей"  укажите через точку с запятой адреса электронной почты  получателей отчетов.</li>
	                    <li>В поле "Тема письма" введите тему, с которой сотрудникам банка будут приходить письма с отчетами.</li>
	                    <li>В поле "Содержание письма" впишите текст письма, с которым сотрудники будут получать отчеты по бизнес-операциям.</li>
	                    <li>В поле "Время выгрузки и формирования отчетов" укажите время, когда начнет формироваться отчет. Время указывается в формате ЧЧ:ММ:СС.</li>
                    </ul>
                    <p>После того как все необходимые поля заполнены, нажмите на кнопку <b>Сохранить</b>, и настройки вступят в силу.</p>
                    
                    <p><b>Настройка периода разовой выгрузки отчета</b></p>
                    <p>В блоке <b>Настройка периода разовой выгрузки отчета</b> указан период, за который будет формироваться отчет. По умолчанию выгрузка производится за прошедшие сутки. </p>
                    <p>Если Вам необходимо получить отчет по бизнес-опреациям за прошедшие сутки, нажмите на кнопку <b>Выгрузить</b>, и в результате отчет будет выгружен.</p>
                    
                    <p><b>Отключение/подключение подготовки отчета</b></p>
                    <p>В блоке настроек <b>Отключение/подключение подготовки отчета</b> Вы можете включить или отключить формирование и рассылку отчетов по банковским операциям. Для этого заполните следующие поля:</p>
                    <ul>
	                    <li>В поле "Логировать данные по бизнес-операциям"укажите, должен ли в системе сохраняться отчет по всем бизнес-операциям.</li>
	                    <li>В поле "Формировать отчеты" выберите из выпадающего списка значение, которое указывает, будут ли формироваться отчеты по бизнес-операциям.  </li>
	                    <li>В поле "Выгружать отчеты" укажите, нужно ли выгружать созданные отчеты.</li>
                    </ul>
                    <div class="help-important">
                        <p><span class="help-attention">Примечание</span>: если в поле "Формировать отчеты" Вы выбрали значение <b>Нет</b>, то выгрузка отчетов из системы будет также отключена.</p>
           			 </div>
                    <p>После того как все необходимые поля заполнены, нажмите на кнопку <b>Применить</b>, и указанные настройки вступят в силу.</p>
                  
                      
                    
            
					<div class="help-to-top"><a href="#top">в начало раздела</a></div>
					<div class="clear"></div>

            </div>
            <!-- end help-workspace -->
        </div>
        <!-- end help-content -->

        <div class="clear"></div>
    </div>
</body>
</html>
