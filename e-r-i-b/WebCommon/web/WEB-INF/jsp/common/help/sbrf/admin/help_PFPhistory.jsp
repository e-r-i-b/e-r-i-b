<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Помощь. Журнал ПФП</title>
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
                        <li><a href="/PhizIA/help.do?id=/passingPfpJournal" class="active-menu">Журнал ПФП</a></li>
                        <li><a href="/PhizIA/help.do?id=/ermb/person/search">ЕРМБ</a></li>
                       	<li><a href="/PhizIA/help.do?id=/ermb/migration/settings">Миграция</a></li>
                       	<li><a href="/PhizIA/help.do?id=/addressBook/reports/index">Отчеты по сервису синхронизации АК</a></li>
                    </ul>
                </div>
 				<!-- end help-left-section -->
            	<div id="help-workspace">
                <div class="contextTitle">Журнал ПФП</div>
                   
                    <p>Пункт главного меню <b>Журнал ПФП</b> предназначен для просмотра истории прохождения персонального финансового планирования.</p>
                    <p>При входе в данный пункт меню автоматически открывается список проведенных ПФП. Для каждого начатого ПФП отображается дата и время проведения, ФИО клиента, который проходил планирование, сотрудник, выполнявший планирование, и статус финансового планирования.</p>
                    <p>Для того чтобы просмотреть результаты планирования, щелкните по его дате или времени, и Вы перейдете на форму финансового планирования, где будет отображаться результаты последнего ПФП.</p>

                    <H2>Поиск ПФП</h2>
                    <A NAME="h02"> </a>
                    <p>Также в журнале ПФП Вы можете воспользоваться фильтром/поиском записей. Для этого нажмите на ссылку <b>Поиск</b> и заполните следующие поля:</p>

                    <ul>
                        <li>"Период" - поиск по дате планирования;</li>
                        <li>"Статус" - поиск по стадии прохождения планирования;</li>
                        <li>"Клиент" - поиск по фамилии, имени и отчеству клиента, проходившему планирование;</li>
                        <li>"Дата рождения" - поиск по дате рождения клиента;</li>
                        <li>"Документ" - поиск по серии и номеру документа, удостоверяющего личность клиента;</li>
                        <li>"Риск-профиль" - поиск по определенному в ходе планирования риск-профилю;</li>
                        <li>"Менеджер" - поиск по фамилии, имени и отчеству сотрудника, проводившего ПФП;</li>
                        <li>"ID менеджера" - поиск по идентификатору сотрудника, проводившего ПФП.</li>
                    </ul>

                    <p>После того как все необходимые сведения указаны, нажмите на кнопку <b>Применить</b>, и система покажет список интересующих Вас ПФП. </p>
                    <p>Вы можете фильтровать записи по одному или нескольким полям, а также по подстроке в текстовых полях фильтра.</p>

                    <H2>Выгрузка отчета ПФП</h2>
                    <A NAME="h03"> </a>
                    <p>Вы можете выгрузить отчет о проведении ПФП. Для этого в списке установите галочки напротив записей, по которым Вы хотите сформировать отчет. Также Вы можете отобрать записи по дополнительным параметрам с помощью фильтра. После того как все необходимые записи выбраны, нажмите на кнопку <b>Выгрузить</b>. Откроется стандартное окно, в котором укажите путь до папки, в которую будет сохранен файл с отчетом.</p>
                    <p>В сформированном отчете будет представлена следующая информация: номер ТБ, ОСБ, ВСП сотрудника, который проводил финансовое планирование, начало, окончание и время проведения планирования, фамилия, имя и отчество клиента, проходившего планирование, дата его рождения, серия и номер документа, удостоверяющего личность,
                    количество целей клиента, виртуальный баланс (сумма денежных средств, самостоятельно указанная клиентом), количество денежных средств на счетах в других банках, наличные средства клиента, итог по инвестиционным продуктам клиента, 
					есть ли у клиента кредитная карта в Сбербанке, риск-профиль, характеризующий клиента по результатам ПФП, фамилия, имя, отчество и ID сотрудника, проводившего планирование, канал, в котором было проведено финансовое планирование, адрес электронной почты и мобильный телефон клиента, статус, в котором находится планирование на данный момент времени. </p>
                    <div class="help-important">
                        <p><span class="help-attention">Примечание</span>: в случае если клиент проходил ПФП самостоятельно, ФИО и ID сотрудника не будут отображаться в отчете.</p>
                    </div>
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
