<%@ page import="com.rssl.phizic.utils.xml.XmlHelper" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.w3c.dom.Document" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<c:set var="version" value="7.00"/>

<html:html>
<head><title>mAPI v7.x test</title>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/XMLDisplay.js"></script>
    <script type="text/javascript">
        var root = {};
        root['/login.do'] = "mobile7";
        root['/registerApp.do?operation=register'] = "mobile7";
        root['/registerApp.do?operation=checkCaptcha'] = "mobile7";
        root['/registerApp.do?operation=refreshCaptcha'] = "mobile7";
        root['/registerApp.do?operation=confirm'] = "mobile7";
        root['/registerApp.do?operation=createPIN'] = "mobile7";
        var defaultRoot = 'mobile7';

        var operations = {};
        operations['/registerApp.do?operation=register'] = "version=${version}&appType=iPhone&appVersion=82&deviceName=SiemensA35&login=123&devID=F5F276B4BF71039948B9590B21E3B2FE";
        operations['/registerApp.do?operation=checkCaptcha'] = "version=${version}&appType=iPhone&login=123&devID=F5F276B4BF71039948B9590B21E3B2FE&captcha=";
        operations['/registerApp.do?operation=refreshCaptcha'] = "";
        operations['/registerApp.do?operation=confirm'] = "version=${version}&appType=iPhone&mGUID=&smsPassword=";
        operations['/registerApp.do?operation=createPIN'] = "version=${version}&appType=iPhone&appVersion=82&deviceName=SiemensA35&isLightScheme=false&devID=F5F276B4BF71039948B9590B21E3B2FE&mGUID=&password=";
        operations['/login.do'] = "version=${version}&operation=button.login&appType=iPhone&appVersion=82&deviceName=SiemensA35&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3&devID=F5F276B4BF71039948B9590B21E3B2FE";
        operations['/confirm/login.do'] = "operation=confirmSMS";
        operations['/confirm/login.do?operation=validate'] = "smsPassword=&pushPassword=";
        operations['/logoff.do'] = "";
        operations['/postCSALogin.do'] = "token=";
        operations['/checkPassword.do'] = "operation=check&password=12340";
        operations['/checkLimit.do'] = "operation=check";
        operations['/hideProducts.do'] = "operation=hide&resource=";
        operations['/hideTemplates.do'] = "template=";
        operations['/resetApp.do'] = "mGUID=";
        operations['/login/chooseAgreement.do'] = "operation=choose&agreementId=";
        operations['/login/chooseRegion.do'] = "operation=save&id=";
        operations['/private/products/list.do'] = "showProductType=";
        operations['/private/cards/info.do'] = "id=";
        operations['/private/cards/info.do?saveName'] = "operation=saveName&id=&cardName=";
        operations['/private/cards/abstract.do'] = "count=10&id=";
        operations['/private/cards/bankdetails.do'] = "id=";
        operations['/private/cards/bankdetails/mail.do'] = "id=&address=&comment=";
        operations['/private/cards/editEmailDelivery.do'] = "cardId=";
        operations['/private/cards/editEmailDelivery.do?needSave=true'] = "operation=button.confirmSMS&use=true&email=&type=PDF&language=RU&transactionToken=";
        operations['/private/cards/editEmailDelivery.do?operation=button.confirm'] = "confirmSmsPassword=&confirmPushPassword=&transactionToken=";
        operations['/private/accounts/info.do'] = "id=";
        operations['/private/accounts/info.do?saveName'] = "operation=saveName&id=&accountName=";
        operations['/private/accounts/abstract.do'] = "from=10.01.10&to=10.10.10&id=";
        operations['/private/accounts/bankdetails.do'] = "id=";
        operations['/private/accounts/bankdetails/mail.do'] = "id=&address=&comment=";
        operations['/private/loans/info.do'] = "id=";
        operations['/private/loans/info.do?saveName'] = "operation=saveName&id=&loanName=";
        operations['/private/loans/abstract.do'] = "count=12&id=";
        operations['/private/ima/info.do'] = "id=";
        operations['/private/ima/info.do?saveName'] = "operation=saveName&id=&imAccountName=";
        operations['/private/ima/abstract.do'] = "from=01.12.2011&to=31.12.2011&id=";
        operations['/private/payments/list.do'] = "from=&to=";
        operations['/private/payments/payments.do'] = "";
        operations['/private/payments/payment.do?SMStemplate'] = "operation=init&SMStemplate=";
        operations['/private/favourite/list/templates.do'] = "";
        operations['/private/payments/template/view.do'] = "id=";
        operations['/private/payments/template/remove.do'] = "id=";
        operations['/private/payments/payment.do?template'] = "template=";
        operations['/private/news/list.do'] = "";
        operations['/news/list.do'] = "";
        operations['/private/news/view.do'] = "id=";
        operations['/news/view.do'] = "id=";
        operations['/private/rates/list.do'] = "type=";
        operations['/images'] = "id=";
        operations['/private/provider/search.do'] = "search=билайн&regionId=&autoPaymentOnly=";
        operations['/private/payments/servicesPayments/edit.do?'] = "operation=init&billing=&service=&provider=";
        operations['/private/payments/servicesPayments/edit.do'] = "operation=next&billing=&service=&provider=&fromResource=&operationUID=";
        operations['/private/payments/payment.do'] = "operation=next&id=";
        operations['/private/payments/confirm.do?debugData=selectType'] = "operation=&id=&transactionToken=";
        operations['/private/payments/confirm.do?debugData=confirmType'] = "operation=confirm&id=&transactionToken=&confirmSmsPassword=&confirmPushPassword=";
        operations['/private/dictionary/banks/national.do'] = "";
        operations['/private/operationCodes.do'] = "";
        operations['/private/dictionary/regions.do'] = "parentId=";
        operations['/private/dictionary/regionSelect.do'] = "id=";
        operations['/private/dictionary/servicesPayments.do'] = "id=&autoPaymentOnly=&includeServices=";
        operations['/private/dictionary/providerServices.do'] = "id=";
        operations['/private/dictionary/popularPayments.do'] = "paginationSize=&paginationOffset=";
        operations['/private/regularpayments/list.do'] = "";
        operations['/private/autopayments/list.do'] = "";
        operations['/private/autopayments/info.do'] = "id=";
        operations['/private/autopayments/abstract.do'] = "id=&from=&to=&paginationSize=&paginationOffset=";
        operations['/private/longoffers/abstract.do'] = "id=&from=&to=&paginationSize=&paginationOffset=";
        operations['/private/autosubscriptions/abstract.do'] = "id=&from=&to=&paginationSize=&paginationOffset=";
        operations['/private/longoffers/info.do'] = "id=";
        operations['/private/autosubscriptions/info.do'] = "id=";
        operations['/private/deposits/products/list.do'] = "form=AccountOpeningClaim";
        operations['/private/deposits/products/info.do'] = "form=AccountOpeningClaim&depositType=&depositId=";
        operations['/private/deposits/products/typeInfo.do'] = "depositType=&depositId=&openingDate=&period=&currencyCode=&minBalance=";
        operations['/private/deposits/terms.do'] = "termsType=tariffTerms&id=";
        operations['/private/cards/currency.do'] = "cardNumber=&phoneNumber=&dictFieldId=";
        operations['/private/profile/loyaltyURL.do'] = "";
        operations['/private/loan/loanOffer/show.do'] = "isLogin=true";
        operations['/private/finances/show.do'] = "operation=connect";
        operations['/private/finances/category/list.do'] = "operation=filter&incomeType=outcome&paginationSize=&paginationOffset=";
        operations['/private/finances/category.do?add'] = "operation=add&name=";
        operations['/private/finances/category.do?update'] = "operation=update&id=&name=";
        operations['/private/finances/category.do?remove'] = "operation=remove&id=";
        operations['/private/finances/operationCategories.do'] = "operation=filter&from=&to=&showCash=true&selectedId=allCards&excludeCategories=&showCashPayments=true&country=";
        operations['/private/graphics/finance.do'] = "showKopeck=false&showWithOverdraft=false";
        operations['/private/finances/operations/list.do'] = "from=&to=&categoryId=&selectedCardId=&income=false&showCash=true&showOtherAccounts=true&paginationSize=&paginationOffset=&excludeCategories=&showCashPayments=true&hidden=&country=";
        operations['/private/finances/operations/edit.do'] = "operation=save&operationId=&operationTitle=&operationCategoryId=&autoRecategorization=&resetCountry=false&operationCountry=&newOperationTitle[i]=&newOperationCategoryId[i]=&newOperationSum[i]=";
        operations['/private/finances/operations/add.do'] = "operation=save&operationName=&operationAmount=&operationDate=&operationCategoryId=";
        operations['/private/finances/operations/remove.do'] = "operation=remove&id=";
        operations['/private/finances/operations/hide.do?hide'] = "operation=hide&id=";
        operations['/private/finances/operations/hide.do?show'] = "operation=show&id=";
        operations['/private/finances/targets/selectTarget.do'] = "";
        operations['/private/finances/targets/list.do']         = "";
        operations['/private/finances/targets/edit.do']         = "operation=save&id=&name=&comment=&date=&amount=&clearImage=";
        operations['/private/finances/targets/editTarget.do?operation=remove']   = "id=";
        operations['/private/finances/targets/editTarget.do?operation=save']   = "type=&name=&comment=&amount=&date=";
        operations['/private/finances/targets/viewTarget.do']   = "operation=confirm&targetId=";
        operations['/private/finances/targets/viewDepositTerms.do'] = "";
        operations['/private/finances/targets/viewDepositTerms.do?termsType=tariffTerms'] = "termsType=tariffTerms";
        operations['/private/finances/financeCalendar/show.do'] = "operation=filter&onDate=&showCash=true&selectedId=allCards&excludeCategories=&showCashPayments=true&country=";
        operations['/private/advertising.do'] = "";
        operations['/version/control.do'] = "appType=iPad&appVersion=3092";
        operations['/device/check.do'] = "appType=iPad&appVersion=3092&reason=jailbreak";
        operations['/private/mail/inbox.do'] = "from=&to=&num=&hasAttach=&subject=&recipientState=";
        operations['/private/mail/sent.do'] = "from=&to=&num=&hasAttach=&subject=&type=";
        operations['/private/mail/archive.do'] = "from=&to=&num=&hasAttach=&subject=&direction=";
        operations['/private/mail/themes.do'] = "";
        operations['/private/mail/edit.do'] = "id=";
        operations['/private/mail/edit.do?operation=download'] = "operation=download&id=";
        operations['/private/mail/correspondence.do'] = "parentId=";
        operations['/private/mail/edit.do?operation=save'] = "id=&parentId=&type=&themeId=&responseMethod=&phone=&eMail=&subject=&body=&fileName=";
        operations['/private/mail/edit.do?operation=send'] = "id=&parentId=&type=&themeId=&responseMethod=&phone=&eMail=&subject=&body=&fileName=";
        operations['/private/mail/remove.do'] = "operation=remove&id=";
        operations['/private/mail/rollback.do'] = "operation=rollback&id=";
        operations['/private/payments/payment.do?operation=init'] = "transactionToken=&id=";
        operations['/private/payments/payment.do?operation=save'] = "transactionToken=&id=&";
        operations['/private/thanks.do'] = "";
        operations['/private/payments/view.do'] = "id=";
        operations['/private/permissions.do'] = "";
        operations['/private/ima/products/list.do'] = "";
        operations['/private/ima/office/list.do'] = "parentSynchKey=&search=";
        operations['/private/ima/opening/license.do'] = "documentId=&imaId=";
        operations['/private/creditcard/office/list.do'] = "region=&street=&name=&paginationSize=&paginationOffset=";
        operations['/private/payments/quicklyCreateTemplate.do?operation=getTemplateName'] = "payment=";
        operations['/private/payments/quicklyCreateTemplate.do?operation=save'] = "payment=&templateName=";
        operations['/private/extended/data.do'] = "agreementId=aeroexpress-rule";
        operations['/private/payments/internetShops/orderList.do'] = "dateFrom=&dateTo=&amountFrom=&amountTo=&currency=&state=&paginationSize=&paginationOffset=";
        operations['/private/payments/internetShops/detailInfo.do'] = "orderId=";
        operations['/private/payments/internetShops/orderCancel.do'] = "orderId=";
        operations['/private/payments/default-action.do'] = "id=&objectFormName=&stateCode=";
        operations['/private/payments/airlineReservation/ticketsStatus.do'] = "id=";
        operations['/private/payments/state.do'] = "id=";
        operations['/pushSettings.do?operation=enable'] = "securityToken=1234567890ABCDEF";
        operations['/pushSettings.do?operation=update'] = "securityToken=1234567890ABCDEF";
        operations['/pushSettings.do?operation=remove'] = "";
        operations['/private/payments/field/dictionary.do'] = "type=";
        operations['/private/payments/field/dictionary/deleterecord.do'] = "id=";
        operations['/notificationSettings.do'] = "";
        operations['/chooseNotificationType.do'] = "notification=&type=";
        operations['/private/stash.do'] = "";
        operations['/private/stash.do?save'] = "operation=save&stash=";
        operations['/private/payments/makeLongOffer.do'] = "id=";
        operations['/private/deposit/collateralAgreementWithCapitalization.do'] = "id=";
        operations['/private/current/locale/edit.do?operation=save'] = "localeId=";

        function trim(string)
        {
            return string.replace(/(^\s+)|(\s+$)/g, "");
        }

        function getSelectValue(obj)
        {
            var retval = "";
            if (typeof(obj) == 'string')
                obj = document.getElementById(obj);
            if (obj) retval = obj.options[obj.selectedIndex].value;
            return retval;
        }

        function changeAddress(obj)
        {
            var val = getSelectValue(obj);
            var params = document.getElementById("params");
            params.value = operations[val];

            changeRoot(val);
            setUrl();
        }

        function changeAddressValue(value)
        {
            var address = document.getElementById("address");
            for (var i = 0; i < address.length; i++)
                if (address.options[i].value == value)
                {
                    address.options[i].selected = true;
                    break;
                }

            changeAddress(address);

        }

        function setParams(value)
        {
            var params = document.getElementById("params");
            params.value = value;
        }

        function changeRoot(value)
        {
            if (root[value] != null)
                document.getElementById('root').value = root[value];
            else
                document.getElementById('root').value = defaultRoot;
        }

        function setUrl()
        {
            var host = document.getElementById('host').value;
            var root = document.getElementById('root').value;

            document.getElementById('url').value = host + root;
        }

    </script>
    <style type="text/css">
        .Utility {
            color: black;
        }

        .NodeName {
            font-weight: bold;
            color: #800080;
        }

        .AttributeName {
            font-weight: bold;
            color: black;
        }

        .AttributeValue {
            color: blue;
        }

        .NodeValue {
            color: black;
        }

        .Element {
            border-left-color: #00FF66;
            border-left-width: thin;
            border-left-style: solid;
            padding-top: 0px;
            margin-top: 10px;
        }

        .Clickable {
            font-weight: 900;
            font-size: large;
            color: #800080;
            cursor: pointer;
            vertical-align: middle;
        }

        #XMLtree {
            width: 800px;
        }
    </style>
</head>
<body>

<c:set var="IMAGE_ADDRESS" value="/images"/>

<h1>mAPI v7.x</h1>

<html:form action="/mobile7" show="true">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<input type="hidden" name="version" value="${form.version}"/>

<c:if test="${not empty form.cookie}">
    <jsp:useBean id="paymentUrls" class="java.util.LinkedHashMap">
        <%
            paymentUrls.put("Оплата платежа по шаблону МБ", "/mobileApi/mobileBankTemplatePayment.do");
            paymentUrls.put("Перевод между своими счетами", "/mobileApi/internalPayment.do");
            paymentUrls.put("Создание автоплатежа перевода между своими счетами", "/mobileApi/internalPaymentLongOffer.do");
            paymentUrls.put("Перевод частному лицу", "/mobileApi/rurPayment.do");
            paymentUrls.put("Создание автоплатежа перевода частному лицу", "/mobileApi/rurPaymentLongOffer.do");
            paymentUrls.put("Перевод организации", "/mobileApi/jurPayment.do");
            paymentUrls.put("Оплата услуг", "/mobileApi/servicePayment.do");
            paymentUrls.put("Создание автоплатежа оплаты услуг", "/mobileApi/createAutoPayment.do");
            paymentUrls.put("Редактирование автоплатежа iqwave", "/mobileApi/editAutoPaymentPayment.do");
            paymentUrls.put("Отмена автоплатежа iqwave", "/mobileApi/refuseAutoPayment.do");
            paymentUrls.put("Редактирование автоплатежа (шина)", "/mobileApi/editAutoSubscriptionPayment.do");
            paymentUrls.put("Приостановка автоплатежа (шина)", "/mobileApi/delayAutoSubscriptionPayment.do");
            paymentUrls.put("Возобновление автоплатежа (шина)", "/mobileApi/recoveryAutoSubscriptionPayment.do");
            paymentUrls.put("Отмена автоплатежа (шина)", "/mobileApi/closeAutoSubscriptionPayment.do");
            paymentUrls.put("Погашение кредита", "/mobileApi/loanPayment.do");
            paymentUrls.put("Создание автоплатежа погашения кредита", "/mobileApi/loanPaymentLongOffer.do");
            paymentUrls.put("Блокировка карты", "/mobileApi/blockingCardClaim.do");
            paymentUrls.put("Открытие вклада", "/mobileApi/accountOpeningClaim.do");
            paymentUrls.put("Изменение порядка уплаты процентов по вкладу", "/mobileApi/accountChangeInterestDestinationClaim.do");
            paymentUrls.put("Закрытие вклада", "/mobileApi/accountClosingPayment.do");
            paymentUrls.put("Заявка на кредитную карту", "/mobileApi/cardOpeningClaim.do");
            paymentUrls.put("Заявка на кредит", "/mobileApi/loanProductOpeningClaim.do");
            paymentUrls.put("Заявка на предодобренный кредит", "/mobileApi/loanOfferOpeningClaim.do");
            paymentUrls.put("Покупка/продажа OMC", "/mobileApi/imaPayment.do");
            paymentUrls.put("Заявка на открытие ОМС", "/mobileApi/imaOpeningClaim.do");
            paymentUrls.put("Отмена длительного поручения", "/mobileApi/refuseLongOffer.do");
            paymentUrls.put("Оплата заказа интернет-магазина", "/mobileApi/externalProviderPayment.do");
            paymentUrls.put("Оплата брони авиабилетов", "/mobileApi/airlineReservationPayment.do");
            paymentUrls.put("Быстрое создание автоплатежа", "/mobileApi/quickMakeLongOffer.do");
        %>
    </jsp:useBean>
    <c:forEach var="entry" items="${paymentUrls}">
        <c:url var="tmpUrl" value="${entry.value}">
            <c:param name="url" value="${form.url}"/>
            <c:param name="cookie" value="${form.cookie}"/>
            <c:param name="proxyUrl" value="${form.proxyUrl}"/>
            <c:param name="proxyPort" value="${form.proxyPort}"/>
            <c:param name="version" value="${form.version}"/>
        </c:url>
        <html:link href="${tmpUrl}">${entry.key}</html:link>&nbsp;
    </c:forEach>
    <br/>

    <c:url var="multipartFormDataUrl" value="/mobileApi/multipartFormData.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${multipartFormDataUrl}">Тест-кейс upload-а файла на примере сохранения письма</html:link>&nbsp;

    <c:url var="multipartFormDataUrlALFTarget" value="/mobileApi/multipartFormData/ALFTarget.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${multipartFormDataUrlALFTarget}">Тест-кейс upload-а файла для редактирования цели</html:link>&nbsp;

    <c:url var="multipartFormDataUrlNewALFTarget" value="/mobileApi/multipartFormData/newALFTarget.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${multipartFormDataUrlNewALFTarget}">Тест-кейс upload-а файла для создания цели</html:link>&nbsp;

    <c:url var="contactSyncUrl" value="/mobileApi/contactSync.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${contactSyncUrl}">Синхронизация мобильных контактов</html:link>
    <br/>
</c:if>

<b>Запрос:</b> <br/>
<small>Cookie:</small>
<br/>
<html:text property="cookie" size="70"/>
<br/>
<small>Адрес:</small>
<br/>
<html:hidden property="url" styleId="url"/>
Host: <input type="text" value="http://localhost:8888/" onchange="setUrl()" id="host"/>
Root:   <select onchange="setUrl()" id="root">
            <option value="CSAMAPI">CSAMAPI</option>
            <option value="mobile7" selected=>mobile7</option>
        </select>
Proxy: <html:text property="proxyUrl"/>
port: <html:text property="proxyPort"/>
<br/>
<select name="address" onchange="changeAddress (this);" id="address">
    <optgroup label="Аутентификация">
        <option value="/login.do" selected>/login.do Вход в доавторизационную зону (вход по mGUID)</option>
        <option value="/postCSALogin.do">/postCSALogin.do завершение входа</option>
        <option value="/confirm/login.do">/confirm/login.do Подтверждение входа. Выбор способа</option>
        <option value="/confirm/login.do?operation=validate">/confirm/login.do Подтверждение входа. Проверка пароля</option>
        <option value="/checkPassword.do">/checkPassword.do Проверка пароля для использования полного функционала мобильного приложения (вход по PIN)</option>
        <option value="/registerApp.do?operation=register">/registerApp.do Регистрация мобильного приложения</option>
        <option value="/registerApp.do?operation=checkCaptcha">/registerApp.do Ввод капчи</option>
        <option value="/registerApp.do?operation=refreshCaptcha">/registerApp.do Обновление капчи</option>
        <option value="/registerApp.do?operation=confirm">/registerApp.do Подтверждение регистрации мобильного приложения</option>
        <option value="/registerApp.do?operation=createPIN">/registerApp.do Установка PIN-кода для входа в мобильное приложение</option>
        <option value="/login/chooseAgreement.do">/login/chooseAgreement.do Шаг выбора договора</option>
        <option value="/login/chooseRegion.do">/login/chooseRegion.do Шаг выбора региона</option>
        <option value="/logoff.do">/logoff.do Выход</option>
    </optgroup>
    <optgroup label="Общее">
        <option value="/private/products/list.do">/private/products/list.do Получение списка продуктов</option>
        <option value="/private/payments/list.do">/private/payments/list.do История операций</option>
        <option value="/private/rates/list.do">/private/rates/list.do Получение курсов валют</option>
        <option value="/version/control.do">/version/control.do Проверка версии приложения</option>
        <option value="/device/check.do">/device/check.do Проверка ОС устройства</option>
        <option value="/private/permissions.do">/private/permissions.do Права доступа</option>
    </optgroup>
    <option value="/checkLimit.do">/checkLimit.do Проверка баланса мобильного кошелька</option>
    <option value="/hideProducts.do">/hideProducts.do Скрытие продуктов из мобильного приложения</option>
    <option value="/hideTemplates.do">/hideTemplates.do Скрытие шаблонов из мобильного приложения</option>
    <option value="/resetApp.do">/resetApp.do Сброс регистрации мобильного приложения</option>
    <optgroup label="Информация по карте">
        <option value="/private/cards/info.do">/private/cards/info.do Детальная информация по карте</option>
        <option value="/private/cards/info.do?saveName">/private/cards/info.do Изменение названия карты</option>
        <option value="/private/cards/abstract.do">/private/cards/abstract.do Выписка по карте</option>
        <option value="/private/cards/bankdetails.do">/private/cards/bankdetails.do Получение реквизитов счета карты</option>
        <option value="/private/cards/bankdetails/mail.do">/private/cards/bankdetails/mail.do Запрос на отправку реквизитов счета карты на электронную почту</option>
        <option value="/private/cards/editEmailDelivery.do">/private/cards/editEmailDelivery.do Изменение параметров подписки на отчеты по карте</option>
        <option value="/private/cards/editEmailDelivery.do?needSave=true">/private/cards/editEmailDelivery.do Изменение параметров подписки на отчеты по карте: выбор способа подтверждения</option>
        <option value="/private/cards/editEmailDelivery.do?operation=button.confirm">/private/cards/editEmailDelivery.do Изменение параметров подписки на отчеты по карте: подтверждение паролем</option>
    </optgroup>
    <optgroup label="Информация по вкладу">
        <option value="/private/accounts/info.do">/private/accounts/info.do Детальная информация по вкладу</option>
        <option value="/private/accounts/info.do?saveName">/private/accounts/info.do Изменение названия вклада</option>
        <option value="/private/accounts/abstract.do">/private/accounts/abstract.do Выписка по вкладу</option>
        <option value="/private/accounts/bankdetails.do">/private/accounts/bankdetails.do Получение реквизитов вклада</option>
        <option value="/private/accounts/bankdetails/mail.do">/private/accounts/bankdetails/mail.do Запрос на отправку реквизитов вклада на электронную почту</option>
    </optgroup>
    <optgroup label="Информация по кредиту">
        <option value="/private/loans/info.do">/private/loans/info.do Детальная информация по кредиту</option>
        <option value="/private/loans/info.do?saveName">/private/loans/info.do Изменение названия кредита</option>
        <option value="/private/loans/abstract.do">/private/loans/abstact.do Кредитный график</option>
    </optgroup>
    <optgroup label="Информация по ОМС">
        <option value="/private/ima/info.do">/private/ima/info.do Детальная информация по ОМС</option>
        <option value="/private/ima/info.do?saveName">/private/ima/info.do Изменение названия ОМС</option>
        <option value="/private/ima/abstract.do">/private/ima/abstract.do Выписка по ОМС</option>
    </optgroup>
    <optgroup label="Информация по автоплатежам">
        <option value="/private/regularpayments/list.do">/private/regularpayments/list.do Список автоплатежей</option>
        <option value="/private/autopayments/info.do">/private/autopayments/info.do Получение детальной информации по автоплатежу</option>
        <option value="/private/longoffers/info.do">/private/longoffers/info.do Получение детальной информации по длительному поручению</option>
        <option value="/private/autosubscriptions/info.do">/private/autosubscriptions/info.do Получение детальной информации по шинному автоплатежу</option>
        <option value="/private/autopayments/abstract.do">/private/autopayments/abstract.do График автоплатежа</option>
        <option value="/private/longoffers/abstract.do">/private/longoffers/abstract.do График длительного поручения</option>
        <option value="/private/autosubscriptions/abstract.do">/private/autosubscriptions/abstract.do График шинного автоплатежа</option>
    </optgroup>

    <optgroup label="Платежи: Повтор типичных платежей">
        <option value="/private/payments/payment.do?operation=init">/private/payments/payment.do?operation=init&transactionToken=&id= Повтор типичных платежей(Инициализация)</option>
        <option value="/private/payments/payment.do?operation=save">/private/payments/payment.do?operation=save&transactionToken=&id=& Повтор типичных платежей(Оплата)</option>
    </optgroup>
    <optgroup label="Платежи: Работа с шаблонами мобильного банка">
        <option value="/private/payments/payments.do">/private/payments/payments.do Список шаблонов мобильного банка</option>
    </optgroup>
    <optgroup label="Платежи: Шаблоны платежей">
        <option value="/private/favourite/list/templates.do">/private/favourite/list/templates.do Список шаблонов платежей</option>
        <option value="/private/payments/template/view.do">/private/payments/template/view.do Детальная информация по шаблону</option>
        <option value="/private/payments/template/remove.do">/private/payments/template/remove.do Удаление шаблона</option>
        <option value="/private/payments/payment.do?template">/private/payments/payment.do Оплата типичных платежей по шаблону</option>
        <option value="/private/payments/quicklyCreateTemplate.do?operation=getTemplateName">/private/payments/quicklyCreateTemplate.do Быстрое создание шаблона: Получение имени шаблона по умолчанию
        </option>
        <option value="/private/payments/quicklyCreateTemplate.do?operation=save">/private/payments/quicklyCreateTemplate.do Быстрое создание шаблона: Сохранение шаблона</option>
    </optgroup>
    <optgroup label="Платежи: Оплата услуг">
        <option value="/private/provider/search.do">/private/provider/search.do Поиск поставщиков</option>
        <option value="/images">/images Получение иконки поставщика или услуги</option>
        <option value="/private/extended/data.do">/private/extended/data.do Просмотр правил платежа</option>
    </optgroup>
    <optgroup label="Платежи: Перевод частному лицу">
        <option value="/private/cards/currency.do">/private/cards/currency.do Получение информации о валюте счета зачисления</option>
    </optgroup>
    <optgroup label="Платежи: Открытие вкладов">
        <option value="/private/deposits/products/list.do">/private/deposits/products/list.do Список вкладов</option>
        <option value="/private/deposits/products/info.do">/private/deposits/products/info.do Детальная информация по вкладу</option>
        <option value="/private/deposits/products/typeInfo.do">/private/deposits/products/typeInfo.do Определение подвида вклада</option>
        <option value="/private/deposits/terms.do">/private/deposits/terms.do Просмотр условий договора по вкладу</option>
        <option value="/private/deposit/collateralAgreementWithCapitalization.do">/private/deposit/collateralAgreementWithCapitalization.do Получение текста дополнительного соглашения для изменения порядка уплаты процентов</option>
    </optgroup>
    <optgroup label="Платежи: Заявка на открытие ОМС">
        <option value="/private/ima/products/list.do">/private/ima/products/list.do Шаг выбора продукта</option>
        <option value="/private/ima/office/list.do">/private/ima/office/list.do Выбор офиса для открытия ОМС</option>
        <option value="/private/ima/opening/license.do">/private/ima/opening/license.do Заявление на открытие ОМС</option>
    </optgroup>
    <optgroup label="Платежи: Заявка на предодобренную кредитную карту">
        <option value="/private/creditcard/office/list.do">/private/creditcard/office/list.do Выбор офиса для получения предодобренной кредитной карты</option>
    </optgroup>
    <optgroup label="Платежи: Заказы интернет-магазинов">
        <option value="/private/payments/internetShops/orderList.do">/private/payments/internetShops/orderList.do Список заказов</option>
        <option value="/private/payments/internetShops/detailInfo.do">/private/payments/internetShops/detailInfo.do Детальная информация по заказу</option>
        <option value="/private/payments/internetShops/orderCancel.do">/private/payments/internetShops/orderCancel.do Отмена заказа</option>
        <option value="/private/payments/airlineReservation/ticketsStatus.do">/private/payments/airlineReservation/ticketsStatus.do Проверка статуса выпуска билетов</option>
    </optgroup>
    <optgroup label="Платежи: Подтверждение">
        <option value="/private/payments/confirm.do?debugData=selectType">/private/payments/confirm.do Запрос на выбор типа подтверждения</option>
        <option value="/private/payments/confirm.do?debugData=confirmType">/private/payments/confirm.do Подтверждение платежа</option>
    </optgroup>
    <option value="/private/payments/default-action.do">/private/payments/default-action.do Переход к действию по умолчанию</option>
    <option value="/private/payments/state.do">/private/payments/state.do Проверка статуса документа</option>
    <option value="/private/payments/makeLongOffer.do">/private/payments/makeLongOffer.do Быстрое создание автоплатежа</option>
    <option value="/private/payments/view.do">/private/payments/view.do Просмотр платежа</option>
    <optgroup label="Новости">
        <option value="/private/news/list.do">/private/news/list.do Получение списка новостей</option>
        <option value="/private/news/view.do">/private/news/view.do Получение полного текста новости</option>
        <option value="/news/list.do">/news/list.do Список PUSH-новостей</option>
        <option value="/news/view.do">/news/view.do Получение полного текста PUSH-новости</option>
    </optgroup>
    <optgroup label="Справочники">
        <option value="/private/dictionary/banks/national.do">/private/dictionary/banks/national.do Справочник банков</option>
        <option value="/private/operationCodes.do">/private/operationCodes.do Справочник валютных операций</option>
        <option value="/private/dictionary/regions.do">/private/dictionary/regions.do Справочник регионов</option>
        <option value="/private/dictionary/servicesPayments.do">/private/dictionary/servicesPayments.do Справочник поставщиков</option>
        <option value="/private/dictionary/providerServices.do">/private/dictionary/providerServices.do Получение списка услуг поставщика</option>
        <option value="/private/payments/field/dictionary.do">/private/payments/field/dictionary.do Справочник доверенных получателей</option>
        <option value="/private/payments/field/dictionary/deleterecord.do">/private/payments/field/dictionary/deleterecord.do Удаление записи из справочника доверенных получателей</option>
    </optgroup>
    <optgroup label="Профиль пользователя">
        <option value="/private/profile/loyaltyURL.do">/private/profile/loyaltyURL.do Получение ссылки на программу лояльности</option>
        <option value="/private/thanks.do">/private/thanks.do Накопительные баллы «Спасибо»</option>
        <option value="/private/dictionary/regionSelect.do">/private/dictionary/regionSelect.do Выбор региона</option>
        <option value="/pushSettings.do?operation=enable">/pushSettings.do?operation=enable Подключение Push-уведомлений</option>
        <option value="/pushSettings.do?operation=update">/pushSettings.do?operation=update Перерегистрация Push-уведомлений</option>
        <option value="/pushSettings.do?operation=remove">/pushSettings.do?operation=remove Отключение Push-уведомлений</option>
        <option value="/notificationSettings.do">/notificationSettings.do Получение настроек оповещений</option>
        <option value="/chooseNotificationType.do">/chooseNotificationType.do Выбор способа оповещений</option>
        <option value="/private/stash.do">/private/stash.do Просмотр "избранного"</option>
        <option value="/private/stash.do?save">/private/stash.do Сохранение "избранного"</option>
    </optgroup>
    <optgroup label="Предодобренные предложения">
        <option value="/private/loan/loanOffer/show.do">/private/loan/loanoffer/show.do Получение списка предодобренных предложений</option>
    </optgroup>
    <optgroup label="Рекламные блоки">
        <option value="/private/advertising.do">/private/advertising.do Рекламные блоки</option>
    </optgroup>
    <optgroup label="АЛФ - анализ личных финансов">
        <option value="/private/finances/show.do">/private/finances/show.do Получение информации о статусе сервиса</option>
        <%--Категории--%>
        <option value="/private/finances/category/list.do">/private/finances/category/list.do Справочник категорий</option>
        <option value="/private/finances/category.do?add">/private/finances/category.do Добавление категории</option>
        <option value="/private/finances/category.do?update">/private/finances/category.do Редактирование категории</option>
        <option value="/private/finances/category.do?remove">/private/finances/category.do Удаление категории</option>
        <option value="/private/finances/operationCategories.do">/private/finances/operationCategories.do Структура расходов по категориям</option>
        <%--Мои финансы. Диаграмма "Доступные средства"--%>
        <option value="/private/graphics/finance.do">/private/graphics/finance.do Запрос информации для диаграммы «Доступные средства»</option>
        <%--Операции--%>
        <option value="/private/finances/operations/list.do">/private/finances/operations/list.do История операций</option>
        <option value="/private/finances/operations/edit.do">/private/finances/operations/edit.do Редактирование и разбивка операции</option>
        <option value="/private/finances/operations/add.do">/private/finances/operations/add.do Добавление операции</option>
        <option value="/private/finances/operations/remove.do">/private/finances/operations/remove.do Удаление добавленной клиентом операции</option>
        <option value="/private/finances/operations/hide.do?hide">/private/finances/operations/hide.do Скрытие категории</option>
        <option value="/private/finances/operations/hide.do?show">/private/finances/operations/hide.do Возвращение видимости категории</option>
        <%--Цели--%>
        <option value="/private/finances/targets/selectTarget.do">/private/finances/targets/selectTarget.do Справочник целей</option>
        <option value="/private/finances/targets/list.do">/private/finances/targets/list.do Список целей клиента</option>
        <option value="/private/finances/targets/edit.do">/private/finances/targets/edit.do Редактирование цели</option>
        <option value="/private/finances/targets/editTarget.do?operation=remove">/private/finances/targets/editTarget.do Удаление цели</option>
        <option value="/private/finances/targets/editTarget.do?operation=save">/private/finances/targets/editTarget.do Создание заявки на добавление цели</option>
        <option value="/private/finances/targets/viewTarget.do">/private/finances/targets/viewTarget.do Подтверждение заявки на добавление цели</option>
        <option value="/private/finances/targets/viewDepositTerms.do">/private/finances/targets/viewDepositTerms.do Заявка на добавление цели. Просмотр условий вклада</option>
        <option value="/private/finances/targets/viewDepositTerms.do?termsType=tariffTerms">/private/finances/targets/viewDepositTerms.do?termsType=tariffTerms Заявка на добавление цели. Просмотр дополнительного соглашения</option>
        <%--Финансовый календарь--%>
        <option value="/private/finances/financeCalendar/show.do">/private/finances/financeCalendar/show.do Запрос данных для финансового календаря</option>
    </optgroup>
    <optgroup label="Письма">
        <option value="/private/mail/inbox.do">/private/mail/inbox.do Входящие письма</option>
        <option value="/private/mail/sent.do">/private/mail/sent.do Отправленные письма</option>
        <option value="/private/mail/archive.do">/private/mail/archive.do Корзина</option>
        <option value="/private/mail/themes.do">/private/mail/themes.do Справочник тематик обращений</option>
        <option value="/private/mail/edit.do">/private/mail/edit.do Просмотр</option>
        <option value="/private/mail/edit.do?operation=download">/private/mail/edit.do Получение вложения</option>
        <option value="/private/mail/correspondence.do">/private/mail/correspondence.do Получение переписки</option>
        <option value="/private/mail/edit.do?operation=save">/private/mail/edit.do Сохранение</option>
        <option value="/private/mail/edit.do?operation=send">/private/mail/edit.do Отправка</option>
        <option value="/private/mail/remove.do">/private/mail/remove.do Удаление</option>
        <option value="/private/mail/rollback.do">/private/mail/rollback.do Восстановление</option>
    </optgroup>
    <optgroup label="Многоязычность">
        <option value="/private/current/locale/edit.do?operation=save">/private/current/locale/edit.do Изменение текущей локали</option>
    </optgroup>
</select>
<br/>
<small>Параметры (пример: id=4&data=someThing)</small>
<br/>
<html:textarea property="params" styleId="params" cols="50" rows="5"/>
<c:choose>
    <c:when test="${form.submit}">
        <textarea cols="60" rows="5" name="note"><%=request.getParameter("note")%>
        </textarea>
    </c:when>
    <c:otherwise>
        <textarea cols="60" rows="5" name="note">Заметки:
            Авторизация:version=7.00&appType=iPhone&appVersion=82&deviceName=SamsungGalaxyMini2&operation=button.login&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3</textarea>
    </c:otherwise>
</c:choose>
<br/>
<input type="submit" name="operation" value="send"/><br/>
<br/>
<c:if test="${form.submit}">
    <b>Ответ:</b><br/>
    <c:if test="${IMAGE_ADDRESS ne form.address}">
        <c:set var="result" value="${form.result}"/>
        <%
            boolean isCaptcha = false;
            String captchaCode="";

            String rowXml = (String)pageContext.getAttribute("result");
            rowXml = StringUtils.trim(rowXml);
            try
            {
                Document document = XmlHelper.parse(rowXml);
                Element element = XmlHelper.selectSingleNode(document.getDocumentElement(), "captchaRegistrationStage");
                if (element != null)
                {
                    captchaCode = element.getElementsByTagName("captcha").item(0).getTextContent();
                    isCaptcha = true;
                }
            }
            catch (Exception ignoredException)
            {}
        %>
        <c:if test="<%=isCaptcha%>">
            <small>captcha:</small><br/>
            <img src="data:image/gif;base64,<%=captchaCode%>" width="200" height="80"/><br/>
        </c:if>

        <small>Tree:</small>
        <br/>

        <div id="XMLtree"></div>
        <small>Xml:</small>
        <br/>
        <%-- необходимо заменять & на спецсимвол, так как textarea по умолчанию все спецсимволы переводит в
       символы и при попытки построить xml дерево экранированные символы для js выглядят как не
       экранированные --%>
        <html:textarea property="result" styleId="result" cols="70" rows="20"/><br/>
        <script type="text/javascript">
            window.onload = function ()
            {
                LoadXMLString('XMLtree', trim(document.getElementById('result').value));
            };
        </script>
    </c:if>
    Status: <html:text property="status" readonly="true"/><br/>
    Content-Type: <html:text property="contentType" readonly="true"/><br/>
    <script type="text/javascript">
        changeAddressValue("<%=request.getParameter("address")%>");
        setParams("<%=request.getParameter("params")%>");
    </script>
</c:if>
</html:form>
</body>
</html:html>
