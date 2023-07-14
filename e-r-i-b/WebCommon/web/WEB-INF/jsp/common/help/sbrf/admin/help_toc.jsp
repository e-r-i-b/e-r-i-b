<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
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
                    </ul>
                   </div>
                           <div id="help-workspace">


            <div class="contextTitle">Оглавление</div>
            <h3>
            <p>Вы находитесь в оглавлении помощи системы "Сбербанк Онлайн" для 
			сотрудников банка. </p>
            <p>Данная справочная документация содержит информацию о том, как совершить следующие операции в "Сбербанк Онлайн" для сотрудников банка:</p>
            <ul>
            <li>найти интересующего Вас клиента, просмотреть, изменить и настроить его анкету;</li>
            <li>просмотреть, отредактировать и настроить анкету интересующего Вас сотрудника;</li>
            <li>настроить журналы системы и просмотреть их записи;</li>
            <li>просмотреть журнал платежей и заявок;</li>
            <li>просмотреть список автоплатежей клиента и создать новый автоплатеж по его просьбе;</li>
            <li>загрузить, просмотреть и отредактировать информацию по поставщикам услуг;</li>
             <li>настроить взаимодействие системы "Сбербанк Онлайн" с внешними системами;</li>
            <li>отредактировать и задать настройки системы;</li>
            <li>просмотреть список подразделений, добавить новое и просмотреть настройки подразделения, к которому у Вас есть доступ;</li>
            <li>настроить отображение, определение даты пролонгации и ввод промокода для открытия депозитного продукта в приложении клиента;</li>
            <li>загрузить предложения по кредитам, задать параметры и оформить заявку на кредит по просьбе клиента;</li>
            <li>настроить, провести персональное финансовое планирование и просмотреть журнал ПФП;</li>
            <li>найти клиента ЕРМБ, настроить тарифные планы и абонентскую плату за услугу "Мобильный банк";</li>
            <li>задать настройки миграции клиентов в ЕРМБ, разрешить технические конфликты, возникшие при миграции, откатить процесс и сформировать отчеты по миграции клиентов;</li>
            <li>сформировать, выгрузить отчеты по сервису синхронизации АК и выполнить другие операции.</li>
            </ul>
            <p>Для того чтобы найти нужную информацию, выберите интересующий Вас раздел в боковом меню.</p>
            <p>Если Вы не нашли нужный раздел, скорее всего, он находится в разработке и скоро будет добавлен в справочное руководство системы.</p>
            </h3>
            
        </div>
        <!-- end help-workspace -->
    </div>
    <!-- end help-content -->

    <div class="clear"></div>
</body>
</html>