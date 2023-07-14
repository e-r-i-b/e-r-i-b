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

<c:set var="version" value="9.00"/>

<html:html>
<head><title>mAPI v9.x test</title>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/XMLDisplay.js"></script>
<script type="text/javascript">
var root = {};
root['/login.do'] = "CSAMAPI";
root['/registerApp.do?operation=register'] = "CSAMAPI";
root['/registerApp.do?operation=checkCaptcha'] = "CSAMAPI";
root['/registerApp.do?operation=refreshCaptcha'] = "CSAMAPI";
root['/registerApp.do?operation=confirm'] = "CSAMAPI";
root['/registerApp.do?operation=createPIN'] = "CSAMAPI";
root['/current/locale/edit.do?operation=button.save'] = "CSAMAPI";
var defaultRoot = 'mobile9';

var operations = {};
operations['/registerApp.do?operation=register'] = "version=${version}&appType=iPhone&appVersion=82&deviceName=SiemensA35&login=123&devID=F5F276B4BF71039948B9590B21E3B2FE&card=";
operations['/registerApp.do?operation=checkCaptcha'] = "version=${version}&appType=iPhone&login=123&devID=F5F276B4BF71039948B9590B21E3B2FE&card=&captcha=";
operations['/registerApp.do?operation=refreshCaptcha'] = "";
operations['/registerApp.do?operation=confirm'] = "version=${version}&appType=iPhone&mGUID=&smsPassword=";
operations['/registerApp.do?operation=createPIN'] = "version=${version}&appType=iPhone&appVersion=82&deviceName=SiemensA35&isLightScheme=false&devID=F5F276B4BF71039948B9590B21E3B2FE&mGUID=&password=&mobileSdkData=mobileSdkDataExample";
operations['/login.do'] = "version=${version}&operation=button.login&appType=iPhone&appVersion=82&deviceName=SiemensA35&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3&devID=F5F276B4BF71039948B9590B21E3B2FE&mobileSdkData=mobileSdkDataExample";
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
operations['/showContactsMessage.do?operation=next'] = "";
operations['/private/products/list.do'] = "showProductType=";
operations['/private/cards/info.do'] = "id=";
operations['/private/cards/info.do?saveName'] = "operation=saveName&id=&cardName=";
operations['/private/cards/abstract.do'] = "count=10&id=";
operations['/private/cards/bankdetails.do'] = "id=";
operations['/private/cards/bankdetails/mail.do'] = "id=&address=&comment=";
operations['/private/cards/bankissuer.do'] = "cardNumber=";
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
operations['/private/provider/search.do'] = "search=������&regionId=&autoPaymentOnly=";
operations['/private/payments/servicesPayments/edit.do?'] = "operation=init&billing=&service=&provider=";
operations['/private/payments/servicesPayments/edit.do'] = "operation=next&billing=&service=&provider=&fromResource=&operationUID=";
operations['/private/payments/payment.do'] = "operation=next&id=";
operations['/private/payments/confirm.do?debugData=selectType'] = "operation=&id=&transactionToken=";
operations['/private/payments/confirm.do?debugData=confirmType'] = "operation=confirm&id=&transactionToken=&confirmSmsPassword=&confirmPushPassword=&mobileSdkData=";
operations['/private/dictionary/banks/national.do'] = "";
operations['/private/operationCodes.do'] = "";
operations['/private/dictionary/regions.do'] = "parentId=";
operations['/private/dictionary/regionSelect.do'] = "id=";
operations['/private/dictionary/servicesPayments.do'] = "id=&autoPaymentOnly=&includeServices=";
operations['/private/dictionary/providerServices.do'] = "id=";
operations['/private/dictionary/popularPayments.do'] = "paginationSize=&paginationOffset=";
operations['/private/regularpayments/list.do'] = "type=";
operations['/private/autopayments/list.do'] = "";
operations['/private/autopayments/info.do'] = "id=";
operations['/private/autopayments/abstract.do'] = "id=&from=&to=&paginationSize=&paginationOffset=";
operations['/private/longoffers/abstract.do'] = "id=&from=&to=&paginationSize=&paginationOffset=";
operations['/private/autosubscriptions/abstract.do'] = "id=&from=&to=&paginationSize=&paginationOffset=";
operations['/private/longoffers/info.do'] = "id=";
operations['/private/autosubscriptions/info.do'] = "id=";
operations['/private/deposits/products/list.do'] = "form=AccountOpeningClaim";
operations['/private/deposits/products/info.do'] = "form=AccountOpeningClaim&depositType=&depositId=&depositGroup=";
operations['/private/deposits/products/typeInfo.do'] = "depositType=&depositId=&depositGroup=&openingDate=&period=&currencyCode=&minBalance=";
operations['/private/deposits/terms.do'] = "termsType=tariffTerms&id=";
operations['/private/cards/currency.do'] = "cardNumber=&phoneNumber=&dictFieldId=";
operations['/private/profile/loyaltyURL.do'] = "";
operations['/private/loan/loanOffer/show.do'] = "isLogin=true&claimType=";
operations['/private/payments/loan/offerClaim.do'] = "transactionToken=";
operations['/private/payments/loan/loanOffer.do'] = "transactionToken=&operation=next&loanId=";
operations['/private/payments/loan/cardClaim.do'] = "transactionToken=";
operations['/private/payments/loan/cardOffer.do'] = "transactionToken=&operation=next&loanId=&offerId=";
operations['/private/finances/show.do'] = "operation=connect";
operations['/private/finances/category/list.do'] = "operation=filter&incomeType=outcome&paginationSize=&paginationOffset=";
operations['/private/finances/category.do?add'] = "operation=add&name=&incomeType=";
operations['/private/finances/category.do?update'] = "operation=update&id=&name=";
operations['/private/finances/category.do?remove'] = "operation=remove&id=";
operations['/private/finances/operationCategories.do'] = "operation=filter&from=&to=&showCash=true&selectedId=allCards&excludeCategories=&showCashPayments=true&country=&incomeType=";
operations['/private/finances/operationDiagram.do'] = "operation=diagram&from=&selectedId=&onlyCash=&transfer=&internalTransfer=&notTransfer=";
operations['/private/graphics/finance.do'] = "showKopeck=false&showWithOverdraft=false";
operations['/private/finances/operations/list.do'] = "from=&to=&categoryId=&selectedCardId=&income=false&showCash=true&showOtherAccounts=true&paginationSize=&paginationOffset=&excludeCategories=&showCashPayments=true&hidden=&country=";
operations['/private/finances/operations/edit.do'] = "operation=save&operationId=&operationTitle=&operationCategoryId=&autoRecategorization=&resetCountry=false&operationCountry=&newOperationTitle[i]=&newOperationCategoryId[i]=&newOperationSum[i]=";
operations['/private/finances/operations/add.do'] = "operation=save&operationName=&operationAmount=&operationDate=&operationCategoryId=";
operations['/private/finances/operations/addCardOperation.do'] = "operation=save&loginId=&pushUID=&parentPushUID=&phoneNumber=&cardNumber=&operationCurrency=&operationMerchantName=&operationName=&operationAmount=&operationDate=&operationAuthCode=&operationCategoryId=&cardCurrency=&lTranMsgType=&operationTypeWAY=&cardBalance=&cash=&operationType=BY_CARD&hidden=false&originalCountry=";
operations['/private/finances/operations/remove.do'] = "operation=remove&id=";
operations['/private/finances/operations/hide.do?hide'] = "operation=hide&id=";
operations['/private/finances/operations/hide.do?show'] = "operation=show&id=";
operations['/private/finances/targets/selectTarget.do'] = "";
operations['/private/finances/targets/list.do'] = "";
operations['/private/finances/targets/edit.do'] = "operation=save&id=&name=&comment=&date=&amount=&clearImage=";
operations['/private/finances/targets/editTarget.do?operation=remove'] = "id=";
operations['/private/finances/targets/editTarget.do?operation=save'] = "type=&name=&comment=&amount=&date=";
operations['/private/finances/targets/viewTarget.do'] = "operation=confirm&targetId=";
operations['/private/finances/targets/viewDepositTerms.do'] = "";
operations['/private/finances/targets/viewDepositTerms.do?termsType=tariffTerms'] = "termsType=tariffTerms";
operations['/private/finances/financeCalendar/show.do'] = "operation=filter&onDate=&showCash=true&selectedId=allCards&excludeCategories=&showCashPayments=true&country=";
operations['/private/finances/financeCalendar/showSelected.do'] = "onDate=&selectedCardIds=";
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
operations['/private/payments/view.do'] = "id=&mobileSdkData=";
operations['/private/permissions.do'] = "";
operations['/private/ima/products/list.do'] = "";
operations['/private/ima/office/list.do'] = "parentSynchKey=&search=";
operations['/private/ima/opening/license.do'] = "documentId=&imaId=";
operations['/private/creditcard/office/list.do'] = "region=&street=&name=&paginationSize=&paginationOffset=";
operations['/private/payments/quicklyCreateTemplate.do?operation=getTemplateName'] = "payment=&mobileSdkData=";
operations['/private/payments/quicklyCreateTemplate.do?operation=save'] = "payment=&templateName=&mobileSdkData=";
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
operations['/notificationSettings.do'] = "";
operations['/private/dictionary/addressbook/phoneMaxCountPayList.do'] = "";
operations['/private/payments/phonepay/defaultSum.do'] = "";
operations['/chooseNotificationType.do'] = "notification=&type=&mobileSdkData=";
operations['/private/stash.do'] = "";
operations['/private/stash.do?save'] = "operation=save&stash=";
operations['/private/payments/makeLongOffer.do'] = "id=";
operations['/private/payments/makeAutoTransfer.do'] = "id=";
operations['/private/deposit/collateralAgreementWithCapitalization.do'] = "id=";
operations['/private/profile/saveData.do'] = "fieldName=login&fieldValue=";
operations['/private/profile/incognito.do?operation=show'] = "";
operations['/private/profile/incognito.do?operation=save'] = "incognito=";
operations['/private/profile/editAddressBook.do?operation=add'] = "contactId=&cardNumber=";
operations['/private/profile/editAddressBook.do?operation=edit'] = "contactId=&cardNumber=";
operations['/private/profile/editAddressBook.do?operation=delete'] = "contactId=";
operations['/private/profile/getAvatar.do'] = "phoneNumber=";
operations['/private/profile/info.do'] = "";
operations['/private/provider/and/operations/search.do'] = "search=&fromDate=&toDate=&opPerPage=&opPage=&regionId=&autoPaymentOnly=&providerPerPage=&providerPage=";
operations['/private/profile/editMail.do'] = "mailFormat=HTML&email=";
operations['/private/profile/snilsInfo.do?operation=add'] = "number=";
operations['/private/profile/snilsInfo.do?operation=update'] = "id=&number=";
operations['/private/profile/snilsInfo.do?operation=remove'] = "id=";
operations['/private/profile/innInfo.do?operation=add'] = "number=";
operations['/private/profile/innInfo.do?operation=update'] = "id=&number=";
operations['/private/profile/innInfo.do?operation=remove'] = "id=";
operations['/private/profile/drivingLicenceInfo.do?operation=add'] = "number=&series=&issueBy=&expireDate=&issueDate=";
operations['/private/profile/drivingLicenceInfo.do?operation=update'] = "id=&number=&series=&issueBy=&expireDate=&issueDate=";
operations['/private/profile/drivingLicenceInfo.do?operation=remove'] = "id=";
operations['/private/profile/registrationCertificateInfo.do?operation=add'] = "number=&series=";
operations['/private/profile/registrationCertificateInfo.do?operation=update'] = "id=&number=&series=";
operations['/private/profile/registrationCertificateInfo.do?operation=remove'] = "id=";
operations['/private/profile/avatar.do?operation=save'] = "x=30&y=30&width=300&height=250";
operations['/private/profile/avatar.do?operation=delete'] = "";
operations['/private/profile/avatar.do?operation=cancel'] = "";
operations['/private/payments/printCheck.do'] = "id=";
operations['/private/basket/subscriptions/list.do'] = '';
operations['/private/basket/subscription/view.do'] = 'id=';
operations['/private/basket/subscriptions/editSubscription.do?operation=update&'] = 'id=';
operations['/private/basket/subscriptions/editSubscription.do?operation=remove'] = 'id=';
operations['/private/basket/subscriptions/editSubscription.do?recoverAutoSubscription'] = 'operation=remove&id=&recoverAutoSubscription=false';
operations['/private/basket/subscriptions/editSubscription.do']    = 'id=&name=&accountingEntityId=';
operations['/private/basket/accountingEntity/list.do']   = '';
operations['/private/basket/accountingEntity/edit.do?operation=remove&']        = 'id=';
operations['/private/basket/accountingEntity/edit.do?operation=save&update=1&'] = 'id=&type=&name=��������';
operations['/private/basket/accountingEntity/edit.do?operation=save&update=2&'] = 'id=&type=&name=��������';
operations['/private/basket/accountingEntity/edit.do?operation=save&']          = 'type=&name=��������';
operations['/private/basket/accountingEntity/edit.do?operation=save&type=CAR&'] = 'name=��������';
operations['/private/basket/invoices/list.do'] = "showInvoicesCount=";
operations['/private/basket/invoices/newCount.do'] = "";
operations['/private/basket/invoices/viewInvoice.do'] = "id=";
operations['/private/basket/invoices/processInvoice.do'] = "id=&operation=&fromResource=&chooseDelayDateInvoice=";
operations['/private/basket/invoices/processInvoice.do?operation=confirm'] = "id=&transactionToken=";
operations['/private/fund/request/list.do'] =  "fromDate=";
operations['/private/fund/request.do'] =  "id=";
operations['/private/fund/request.do?operation=create&'] =  "message=&closeDate=&requiredSum=&reccomendSum=&resource=&phones=";
operations['/private/fund/request.do?operation=close&'] =  "id=";
operations['/private/fund/group.do?operation=create&'] = "name=&phones=";
operations['/private/fund/group.do?operation=edit&'] = "id=&name=&phones=";
operations['/private/fund/group.do?operation=remove&'] = "id=";
operations['/private/fund/group.do?'] = "id=";
operations['/private/fund/group/list.do'] = "";
operations['/private/fund/request/count.do'] = "";
operations['/private/fund/response/list.do'] =  "fromDate=";
operations['/private/fund/response.do'] = "id=&state=&sum=&message=&closeConfirm=&fromResource=";
operations['/private/fund/mapi/info.do'] = "phones=";
operations['/private/moneyboxes/list.do'] = 'cardId=&accountId=';
operations['/private/moneyboxes/statusMod.do'] = 'id=&changePaymentStatusType=';
operations['/private/moneyboxes/info.do'] = 'linkId=&claimId=';
operations['/private/moneyboxes/payment/info/print.do'] = 'id=&subscriptionId=';
operations['/private/moneyboxes/abstract.do'] = 'id=&from=&to=&paginationSize=&paginationOffset=';
operations['/private/cards/editEmailDelivery.do'] = "cardId=";
operations['/private/cards/editEmailDelivery.do?needSave=true'] = "operation=button.confirmSMS&use=true&email=&type=PDF&language=RU&transactionToken=";
operations['/private/cards/editEmailDelivery.do?operation=button.confirm'] = "smsPassword=&pushPassword=&transactionToken=";
operations['/private/current/locale/edit.do?operation=button.save'] = "localeId=";
operations['/private/payments/processReminder.do?operation=delay'] = "chooseDelayDateReminder=31.12.2021&reminderId=";
operations['/private/payments/processReminder.do?operation=delete'] = "reminderId=";
operations['/private/payments/processReminder.do?operation=changeReminderInfo'] = "enableReminder=true&reminderType=ONCE_IN_MONTH&dayOfMonth=22&id=";
operations['/current/locale/edit.do?operation=button.save'] = "localeId=";

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

<h1>mAPI v9.x</h1>

<html:form action="/mobile9" show="true">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<input type="hidden" name="version" value="${form.version}"/>

<c:if test="${not empty form.cookie}">
    <jsp:useBean id="paymentUrls" class="java.util.LinkedHashMap">
        <%
            paymentUrls.put("������ ������� �� ������� ��", "/mobileApi/mobileBankTemplatePayment.do");
            paymentUrls.put("������� ����� ������ �������", "/mobileApi/internalPayment.do");
            paymentUrls.put("�������� ����������� �������� ����� ������ �������", "/mobileApi/internalPaymentLongOffer.do");
            paymentUrls.put("������� �������� ����", "/mobileApi/rurPayment.do");
            paymentUrls.put("������������� ��������� �������� �� ����� �������� ����", "/mobileApi/rurPaymentAddressBook.do");
            paymentUrls.put("�������� ����������� �������� �������� ����", "/mobileApi/rurPaymentLongOffer.do");
            paymentUrls.put("������� �����������", "/mobileApi/jurPayment.do");
            paymentUrls.put("������ �����", "/mobileApi/servicePayment.do");
            paymentUrls.put("�������� ����������� ������ �����", "/mobileApi/createAutoPayment.do");
            paymentUrls.put("�������������� ����������� iqwave", "/mobileApi/editAutoPaymentPayment.do");
            paymentUrls.put("������ ����������� iqwave", "/mobileApi/refuseAutoPayment.do");
            paymentUrls.put("�������������� ����������� (����)", "/mobileApi/editAutoSubscriptionPayment.do");
            paymentUrls.put("������������ ����������� (����)", "/mobileApi/delayAutoSubscriptionPayment.do");
            paymentUrls.put("������������� ����������� (����)", "/mobileApi/recoveryAutoSubscriptionPayment.do");
            paymentUrls.put("������ ����������� (����)", "/mobileApi/closeAutoSubscriptionPayment.do");
            paymentUrls.put("�������������� ����������� (P2P)", "/mobileApi/editAutoTransferPayment.do");
            paymentUrls.put("������������ ����������� (P2P)", "/mobileApi/delayP2PAutoTransferClaim.do");
            paymentUrls.put("������������� ����������� (P2P)", "/mobileApi/recoveryP2PAutoTransferClaim.do");
            paymentUrls.put("������ ����������� (P2P)", "/mobileApi/closeP2PAutoTransferClaim.do");
            paymentUrls.put("��������� �������", "/mobileApi/loanPayment.do");
            paymentUrls.put("�������� ����������� ��������� �������", "/mobileApi/loanPaymentLongOffer.do");
            paymentUrls.put("���������� �����", "/mobileApi/blockingCardClaim.do");
            paymentUrls.put("�������� ������", "/mobileApi/accountOpeningClaim.do");
            paymentUrls.put("��������� ������� ������ ��������� �� ������", "/mobileApi/accountChangeInterestDestinationClaim.do");
            paymentUrls.put("�������� ������", "/mobileApi/accountClosingPayment.do");
            paymentUrls.put("������ �� ��������� �����", "/mobileApi/cardOpeningClaim.do");
            paymentUrls.put("������ �� ������", "/mobileApi/loanProductOpeningClaim.do");
            paymentUrls.put("������ �� �������������� ������", "/mobileApi/loanOfferOpeningClaim.do");
            paymentUrls.put("�������/������� OMC", "/mobileApi/imaPayment.do");
            paymentUrls.put("������ �� �������� ���", "/mobileApi/imaOpeningClaim.do");
            paymentUrls.put("������ ����������� ���������", "/mobileApi/refuseLongOffer.do");
            paymentUrls.put("������ ������ ��������-��������", "/mobileApi/externalProviderPayment.do");
            paymentUrls.put("������ ����� �����������", "/mobileApi/airlineReservationPayment.do");
            paymentUrls.put("������� �������� �����������", "/mobileApi/quickMakeLongOffer.do");
            paymentUrls.put("������� �������� ����������� P2P", "/mobileApi/quickMakeAutoTransfer.do");
            paymentUrls.put("�������� �� ������� �������� �������� �� ������� � ������� ��������", "/mobileApi/createInvoiceSubscriptionPayment.do");
            paymentUrls.put("������� ���������� � ����������", "/mobileApi/confirmInvoiceSubscriptionClaim.do");
            paymentUrls.put("�������������� ����������", "/mobileApi/editInvoiceSubscriptionClaim.do");
            paymentUrls.put("�������� ����������", "/mobileApi/createInvoiceSubscription.do");
            paymentUrls.put("�������� ������� �� ���� �������", "/mobileApi/createFundRequest.do");
            paymentUrls.put("��������� ������� ��������� ������� �� ���� �������", "/mobileApi/changeStateFundRequest.do");
            paymentUrls.put("�������� ��� ����� �� ������ ���������� ������", "/mobileApi/changeCreditLimitClaim.do");
//            paymentUrls.put("�������� ����������", "/mobileApi/createInvoiceSubscriptionPayment.do");
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
    <html:link href="${multipartFormDataUrl}">����-���� upload-� ����� �� ������� ���������� ������</html:link>&nbsp;

    <c:url var="multipartFormDataUrlALFTarget" value="/mobileApi/multipartFormData/ALFTarget.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${multipartFormDataUrlALFTarget}">����-���� upload-� ����� ��� �������������� ����</html:link>&nbsp;

    <c:url var="multipartFormDataUrlNewALFTarget" value="/mobileApi/multipartFormData/newALFTarget.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${multipartFormDataUrlNewALFTarget}">����-���� upload-� ����� ��� �������� ����</html:link>&nbsp;

    <c:url var="contactSyncUrl" value="/mobileApi/contactSync.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${contactSyncUrl}">������������� ��������� ���������</html:link>

    <c:url var="contactSyncUrl" value="/mobileApi/contact/needshow.do">                                                                                                    <%--TODO �������� ��� ���������--%>
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${contactSyncUrl}">������������� ����������� �������� ������ ���������</html:link>

    <c:url var="contactSyncUrl" value="/mobileApi/contact/get.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${contactSyncUrl}">��������� �������� �����</html:link>

    <c:url var="contactSyncUrl" value="/mobileApi/contact/edit.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${contactSyncUrl}">�������������� ��������� � �������� �����</html:link>
    <c:url var="contactSyncUrl" value="/mobileApi/contact/delete.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${contactSyncUrl}">�������� ��������� �� �������� �����</html:link>

    <c:url var="contactSyncUrl" value="/mobileApi/contact/find.do">
            <c:param name="url" value="${form.url}"/>
            <c:param name="cookie" value="${form.cookie}"/>
            <c:param name="proxyUrl" value="${form.proxyUrl}"/>
            <c:param name="proxyPort" value="${form.proxyPort}"/>
            <c:param name="version" value="${form.version}"/>
        </c:url>
        <html:link href="${contactSyncUrl}">����� ��������� � �������� �����</html:link>

    <c:url var="multipartFormDataLoadAvatar" value="/mobileApi/multipartFormData/loadAvatar.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${multipartFormDataLoadAvatar}">����-���� upload-� ����������� �� ������� �������� �������</html:link>
    <c:url var="moneyBoxCreateUrl" value="/mobileApi/createMoneyBox.do">
        <c:param name="url"       value="${form.url}"/>
        <c:param name="address"   value="/private/payments/payment.do?form=CreateMoneyBoxPayment"/>
        <c:param name="cookie"    value="${form.cookie}"/>
        <c:param name="proxyUrl"  value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
      </c:url>
    <html:link href="${moneyBoxCreateUrl}">����������� �������</html:link>
    <c:url var="moneyBoxEditUrl" value="/mobileApi/editMoneyBox.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${moneyBoxEditUrl}">�������������� �������</html:link>
    <c:url var="createAutoTransferUrl" value="/mobileApi/createP2PAutoTransfer.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
        <c:param name="version" value="${form.version}"/>
    </c:url>
    <html:link href="${createAutoTransferUrl}">�������� ����������� P2P</html:link>
    <br/>
</c:if>

<b>������:</b> <br/>
<small>Cookie:</small>
<br/>
<html:text property="cookie" size="70"/>
<br/>
<small>�����:</small>
<br/>
<html:hidden property="url" styleId="url"/>
Host: <input type="text" value="http://localhost:8888/" onchange="setUrl()" id="host"/>
Root: <select onchange="setUrl()" id="root">
    <option value="CSAMAPI" selected=>CSAMAPI</option>
    <option value="mobile9">mobile9</option>
</select>
Proxy: <html:text property="proxyUrl"/>
port: <html:text property="proxyPort"/>
<br/>
<select name="address" onchange="changeAddress (this);" id="address">
<optgroup label="��������������">
    <option value="/login.do" selected>/login.do ���� � ����������������� ���� (���� �� mGUID)</option>
    <option value="/postCSALogin.do">/postCSALogin.do ���������� �����</option>
    <option value="/confirm/login.do">/confirm/login.do ������������� �����. ����� �������</option>
    <option value="/confirm/login.do?operation=validate">/confirm/login.do ������������� �����. �������� ������</option>
    <option value="/checkPassword.do">/checkPassword.do �������� ������ ��� ������������� ������� ����������� ���������� ���������� (���� �� PIN)</option>
    <option value="/registerApp.do?operation=register">/registerApp.do ����������� ���������� ����������</option>
    <option value="/registerApp.do?operation=checkCaptcha">/registerApp.do ���� �����</option>
    <option value="/registerApp.do?operation=refreshCaptcha">/registerApp.do ���������� �����</option>
    <option value="/registerApp.do?operation=confirm">/registerApp.do ������������� ����������� ���������� ����������</option>
    <option value="/registerApp.do?operation=createPIN">/registerApp.do ��������� PIN-���� ��� ����� � ��������� ����������</option>
    <option value="/login/chooseAgreement.do">/login/chooseAgreement.do ��� ������ ��������</option>
    <option value="/login/chooseRegion.do">/login/chooseRegion.do ��� ������ �������</option>
    <option value="/showContactsMessage.do?operation=next">/showContactsMessage.do ��� ����������� ������� �������������� ��������� �� ������ �������� �����</option>
    <option value="/logoff.do">/logoff.do �����</option>
</optgroup>
<optgroup label="�����">
    <option value="/private/products/list.do">/private/products/list.do ��������� ������ ���������</option>
    <option value="/private/payments/list.do">/private/payments/list.do ������� ��������</option>
    <option value="/private/rates/list.do">/private/rates/list.do ��������� ������ �����</option>
    <option value="/version/control.do">/version/control.do �������� ������ ����������</option>
    <option value="/device/check.do">/device/check.do �������� �� ����������</option>
    <option value="/private/permissions.do">/private/permissions.do ����� �������</option>
</optgroup>
<option value="/checkLimit.do">/checkLimit.do �������� ������� ���������� ��������</option>
<option value="/hideProducts.do">/hideProducts.do ������� ��������� �� ���������� ����������</option>
<option value="/hideTemplates.do">/hideTemplates.do ������� �������� �� ���������� ����������</option>
<option value="/resetApp.do">/resetApp.do ����� ����������� ���������� ����������</option>
<optgroup label="������� ��������">
    <option value="/private/basket/subscriptions/list.do">/private/basket/subscriptions/list.do ������ �����������</option>
    <option value="/private/basket/subscription/view.do">/private/basket/subscription/view.do ��������� ���������� �� ����������</option>
    <option value="/private/basket/subscriptions/editSubscription.do?operation=remove">/private/basket/subscriptions/editSubscription.do �������� ����������</option>
    <option value="/private/basket/subscriptions/editSubscription.do?recoverAutoSubscription">/private/basket/subscriptions/editSubscription.do ������������� �������������� ������ ������</option>
    <option value="/private/basket/subscriptions/editSubscription.do?operation=update&">/private/basket/subscriptions/editSubscription.do ���������� ����������</option>
    <option value="/private/basket/subscriptions/editSubscription.do">/private/basket/subscriptions/editSubscription.do �������������� ����������</option>
    <option value="/private/basket/accountingEntity/list.do">/private/basket/accoutingEntity/list.do ������ �������� �����</option>
    <option value="/private/basket/accountingEntity/edit.do?operation=remove&">/private/basket/accountingEntity/edit.do �������� ������� �����</option>
    <option value="/private/basket/accountingEntity/edit.do?operation=save&">/private/basket/accountingEntity/edit.do ���������� ������� �����(���� �� �����: HOUSE, FLAT, GARAGE)</option>
    <option value="/private/basket/accountingEntity/edit.do?operation=save&type=CAR&">/private/basket/accountingEntity/edit.do ���������� ������� �����(��� ���������� CAR)</option>
    <option value="/private/basket/accountingEntity/edit.do?operation=save&update=1&">/private/basket/accountingEntity/edit.do �������������� ������� �����(���� �� �����: HOUSE, FLAT, GARAGE)</option>
    <option value="/private/basket/accountingEntity/edit.do?operation=save&update=2&">/private/basket/accountingEntity/edit.do �������������� ������� �����(��� ���������� CAR)</option>
    <option value="/private/basket/invoices/list.do">/private/basket/invoices/list.do ������ ������ � �������</option>
    <option value="/private/basket/invoices/newCount.do">/private/basket/invoices/newCount.do ���������� ����� ������ � �������</option>
    <option value="/private/basket/invoices/viewInvoice.do">/private/basket/invoices/viewInvoice.do �������� ����� �������</option>
    <option value="/private/basket/invoices/processInvoice.do">/private/basket/invoices/processInvoice.do ������, ������������ � �������� ����� �������</option>
    <option value="/private/basket/invoices/processInvoice.do?operation=confirm">/private/basket/invoices/processInvoice.do ������������� ������ ����� �������</option>
    <option value="/private/payments/processReminder.do?operation=delay">/private/payments/processReminder.do ������������ ����� ���� reminder</option>
    <option value="/private/payments/processReminder.do?operation=delete">/private/payments/processReminder.do �������� ����� ���� reminder</option>
    <option value="/private/payments/processReminder.do?operation=changeReminderInfo">/private/payments/processReminder.do ��������/�������������� �����������</option>
</optgroup>
<optgroup label="���������� �� �����">
    <option value="/private/cards/info.do">/private/cards/info.do ��������� ���������� �� �����</option>
    <option value="/private/cards/info.do?saveName">/private/cards/info.do ��������� �������� �����</option>
    <option value="/private/cards/abstract.do">/private/cards/abstract.do ������� �� �����</option>
    <option value="/private/cards/bankdetails.do">/private/cards/bankdetails.do ��������� ���������� ����� �����</option>
    <option value="/private/cards/bankdetails/mail.do">/private/cards/bankdetails/mail.do ������ �� �������� ���������� ����� ����� �� ����������� �����</option>
    <option value="/private/cards/bankissuer.do">/private/cards/bankissuer.do ������ �� ����������� �����-�������� �����</option>
    <option value="/private/cards/editEmailDelivery.do">/private/cards/editEmailDelivery.do ��������� ���������� �������� �� ������ �� �����</option>
    <option value="/private/cards/editEmailDelivery.do?needSave=true">/private/cards/editEmailDelivery.do ��������� ���������� �������� �� ������ �� �����: ����� ������� �������������</option>
    <option value="/private/cards/editEmailDelivery.do?operation=button.confirm">/private/cards/editEmailDelivery.do ��������� ���������� �������� �� ������ �� �����: ������������� �������</option>
</optgroup>
<optgroup label="���������� �� ������">
    <option value="/private/accounts/info.do">/private/accounts/info.do ��������� ���������� �� ������</option>
    <option value="/private/accounts/info.do?saveName">/private/accounts/info.do ��������� �������� ������</option>
    <option value="/private/accounts/abstract.do">/private/accounts/abstract.do ������� �� ������</option>
    <option value="/private/accounts/bankdetails.do">/private/accounts/bankdetails.do ��������� ���������� ������</option>
    <option value="/private/accounts/bankdetails/mail.do">/private/accounts/bankdetails/mail.do ������ �� �������� ���������� ������ �� ����������� �����</option>
</optgroup>
<optgroup label="���������� �� �������">
    <option value="/private/loans/info.do">/private/loans/info.do ��������� ���������� �� �������</option>
    <option value="/private/loans/info.do?saveName">/private/loans/info.do ��������� �������� �������</option>
    <option value="/private/loans/abstract.do">/private/loans/abstact.do ��������� ������</option>
</optgroup>
<optgroup label="���������� �� ���">
    <option value="/private/ima/info.do">/private/ima/info.do ��������� ���������� �� ���</option>
    <option value="/private/ima/info.do?saveName">/private/ima/info.do ��������� �������� ���</option>
    <option value="/private/ima/abstract.do">/private/ima/abstract.do ������� �� ���</option>
</optgroup>
<optgroup label="���������� �� ������������">
    <option value="/private/regularpayments/list.do">/private/regularpayments/list.do ������ ������������</option>
    <option value="/private/autopayments/info.do">/private/autopayments/info.do ��������� ��������� ���������� �� �����������</option>
    <option value="/private/longoffers/info.do">/private/longoffers/info.do ��������� ��������� ���������� �� ����������� ���������</option>
    <option value="/private/autosubscriptions/info.do">/private/autosubscriptions/info.do ��������� ��������� ���������� �� ������� �����������</option>
    <option value="/private/autopayments/abstract.do">/private/autopayments/abstract.do ������ �����������</option>
    <option value="/private/longoffers/abstract.do">/private/longoffers/abstract.do ������ ����������� ���������</option>
    <option value="/private/autosubscriptions/abstract.do">/private/autosubscriptions/abstract.do ������ ������� �����������</option>
</optgroup>
<optgroup label="�������">
    <option value="/private/moneyboxes/list.do">/private/moneyboxes/list.do ������ �������</option>
    <option value="/private/moneyboxes/statusMod.do">/private/moneyboxes/statusMod.do ������������/�������������/����������</option>
    <option value="/private/moneyboxes/info.do">/private/moneyboxes/info.do ��������� ��������� ���������� �������</option>
    <option value="/private/moneyboxes/abstract.do">/private/moneyboxes/abstract.do ��������� ������� �������� �������</option>
    <option value="/private/moneyboxes/payment/info/print.do">/private/moneyboxes/payment/info/print.do ������ ���� �� �������� �������� ��������</option>
</optgroup>
<optgroup label="�������: ������ �������� ��������">
    <option value="/private/payments/payment.do?operation=init">/private/payments/payment.do?operation=init&transactionToken=&id= ������ �������� ��������(�������������)</option>
    <option value="/private/payments/payment.do?operation=save">/private/payments/payment.do?operation=save&transactionToken=&id=& ������ �������� ��������(������)</option>
</optgroup>
<optgroup label="�������: ������ � ��������� ���������� �����">
    <option value="/private/payments/payments.do">/private/payments/payments.do ������ �������� ���������� �����</option>
</optgroup>
<optgroup label="�������: ������� ��������">
    <option value="/private/favourite/list/templates.do">/private/favourite/list/templates.do ������ �������� ��������</option>
    <option value="/private/payments/template/view.do">/private/payments/template/view.do ��������� ���������� �� �������</option>
    <option value="/private/payments/template/remove.do">/private/payments/template/remove.do �������� �������</option>
    <option value="/private/payments/payment.do?template">/private/payments/payment.do ������ �������� �������� �� �������</option>
    <option value="/private/payments/quicklyCreateTemplate.do?operation=getTemplateName">/private/payments/quicklyCreateTemplate.do ������� �������� �������: ��������� ����� ������� �� ���������
    </option>
    <option value="/private/payments/quicklyCreateTemplate.do?operation=save">/private/payments/quicklyCreateTemplate.do ������� �������� �������: ���������� �������</option>
</optgroup>
<optgroup label="�������: ������ �����">
    <option value="/private/provider/search.do">/private/provider/search.do ����� �����������</option>
    <option value="/private/provider/and/operations/search.do">/private/provider/and/operations/search.do ����� �� ����������� � ���������</option>
    <option value="/images">/images ��������� ������ ���������� ��� ������</option>
    <option value="/private/extended/data.do">/private/extended/data.do �������� ������ �������</option>
</optgroup>
<optgroup label="�������: ������� �������� ����">
    <option value="/private/cards/currency.do">/private/cards/currency.do ��������� ���������� � ������ ����� ����������</option>
</optgroup>
<optgroup label="�������: �������� �������">
    <option value="/private/deposits/products/list.do">/private/deposits/products/list.do ������ �������</option>
    <option value="/private/deposits/products/info.do">/private/deposits/products/info.do ��������� ���������� �� ������</option>
    <option value="/private/deposits/products/typeInfo.do">/private/deposits/products/typeInfo.do ����������� ������� ������</option>
    <option value="/private/deposits/terms.do">/private/deposits/terms.do �������� ������� �������� �� ������</option>
    <option value="/private/deposit/collateralAgreementWithCapitalization.do">/private/deposit/collateralAgreementWithCapitalization.do ��������� ������ ��������������� ���������� ��� ���������
        ������� ������ ���������
    </option>
</optgroup>
<optgroup label="�������: ������ �� �������� ���">
    <option value="/private/ima/products/list.do">/private/ima/products/list.do ��� ������ ��������</option>
    <option value="/private/ima/office/list.do">/private/ima/office/list.do ����� ����� ��� �������� ���</option>
    <option value="/private/ima/opening/license.do">/private/ima/opening/license.do ��������� �� �������� ���</option>
</optgroup>
<optgroup label="�������: ������ �� �������������� ��������� �����">
    <option value="/private/creditcard/office/list.do">/private/creditcard/office/list.do ����� ����� ��� ��������� �������������� ��������� �����</option>
</optgroup>
<optgroup label="�������: ������ ��������-���������">
    <option value="/private/payments/internetShops/orderList.do">/private/payments/internetShops/orderList.do ������ �������</option>
    <option value="/private/payments/internetShops/detailInfo.do">/private/payments/internetShops/detailInfo.do ��������� ���������� �� ������</option>
    <option value="/private/payments/internetShops/orderCancel.do">/private/payments/internetShops/orderCancel.do ������ ������</option>
    <option value="/private/payments/airlineReservation/ticketsStatus.do">/private/payments/airlineReservation/ticketsStatus.do �������� ������� ������� �������</option>
</optgroup>
<optgroup label="�������: �������������">
    <option value="/private/payments/confirm.do?debugData=selectType">/private/payments/confirm.do ������ �� ����� ���� �������������</option>
    <option value="/private/payments/confirm.do?debugData=confirmType">/private/payments/confirm.do ������������� �������</option>
</optgroup>
<option value="/private/payments/default-action.do">/private/payments/default-action.do ������� � �������� �� ���������</option>
<option value="/private/payments/state.do">/private/payments/state.do �������� ������� ���������</option>
<option value="/private/payments/makeLongOffer.do">/private/payments/makeLongOffer.do ������� �������� �����������</option>
<option value="/private/payments/makeAutoTransfer.do">/private/payments/makeAutoTransfer.do ������� �������� ����������� P2P</option>
<option value="/private/payments/view.do">/private/payments/view.do �������� �������</option>
<option value="/private/payments/phonepay/defaultSum.do">/private/payments/phonepay/defaultSum.do ����� �� ��������� ��� ������ ��������� �����</option>
<optgroup label="������ ���� ��������">
    <option value="/private/payments/printCheck.do">/private/payments/printCheck.do ������ ���� ��������</option>
</optgroup>
<optgroup label="�������">
    <option value="/private/news/list.do">/private/news/list.do ��������� ������ ��������</option>
    <option value="/private/news/view.do">/private/news/view.do ��������� ������� ������ �������</option>
    <option value="/news/list.do">/news/list.do ������ PUSH-��������</option>
    <option value="/news/view.do">/news/view.do ��������� ������� ������ PUSH-�������</option>
</optgroup>
<optgroup label="�����������">
    <option value="/private/dictionary/banks/national.do">/private/dictionary/banks/national.do ���������� ������</option>
    <option value="/private/operationCodes.do">/private/operationCodes.do ���������� �������� ��������</option>
    <option value="/private/dictionary/regions.do">/private/dictionary/regions.do ���������� ��������</option>
    <option value="/private/dictionary/servicesPayments.do">/private/dictionary/servicesPayments.do ���������� �����������</option>
    <option value="/private/dictionary/providerServices.do">/private/dictionary/providerServices.do ��������� ������ ����� ����������</option>
    <option value="/private/payments/field/dictionary.do">/private/payments/field/dictionary.do ���������� ���������� �����������</option>
</optgroup>
<optgroup label="������� ������������">
    <option value="/private/profile/info.do">/private/profile/info.do ��������� ������ ����������</option>
    <option value="/private/profile/editMail.do">/private/profile/editMail.do ��������� email</option>
    <option value="/private/profile/snilsInfo.do?operation=add">/private/profile/snilsInfo.do?operation=add ���������� �����</option>
    <option value="/private/profile/snilsInfo.do?operation=remove">/private/profile/snilsInfo.do?operation=remove �������� �����</option>
    <option value="/private/profile/snilsInfo.do?operation=update">/private/profile/snilsInfo.do?operation=update �������������� �����</option>
    <option value="/private/profile/innInfo.do?operation=add">/private/profile/innInfo.do?operation=add ���������� ���</option>
    <option value="/private/profile/innInfo.do?operation=update">/private/profile/innInfo.do?operation=update �������������� ���</option>
    <option value="/private/profile/innInfo.do?operation=remove">/private/profile/innInfo.do?operation=remove �������� ���</option>
    <option value="/private/profile/drivingLicenceInfo.do?operation=add">/private/profile/drivingLicenceInfo.do?operation=add ���������� ��</option>
    <option value="/private/profile/drivingLicenceInfo.do?operation=update">/private/profile/drivingLicenceInfo.do?operation=update �������������� ��</option>
    <option value="/private/profile/drivingLicenceInfo.do?operation=remove">/private/profile/drivingLicenceInfo.do?operation=remove �������� ��</option>
    <option value="/private/profile/registrationCertificateInfo.do?operation=add">/private/profile/registrationCertificateInfo.do?operation=add ���������� ������������� � ����������� ������������� ��������</option>
    <option value="/private/profile/registrationCertificateInfo.do?operation=update">/private/profile/registrationCertificateInfo.do?operation=update �������������� ������������� � ����������� ������������� ��������</option>
    <option value="/private/profile/registrationCertificateInfo.do?operation=remove">/private/profile/registrationCertificateInfo.do?operation=remove �������� ������������� � ����������� ������������� ��������</option>
    <option value="/private/profile/avatar.do?operation=save">/private/profile/avatar.do?operation=save ���������� �������</option>
    <option value="/private/profile/avatar.do?operation=cancel">/private/profile/avatar.do?operation=cancel ������ �������� �������</option>
    <option value="/private/profile/avatar.do?operation=delete">/private/profile/avatar.do?operation=delete �������� �������</option>
    <option value="/private/profile/loyaltyURL.do">/private/profile/loyaltyURL.do ��������� ������ �� ��������� ����������</option>
    <option value="/private/thanks.do">/private/thanks.do ������������� ����� ��������</option>
    <option value="/private/dictionary/regionSelect.do">/private/dictionary/regionSelect.do ����� �������</option>
    <option value="/pushSettings.do?operation=enable">/pushSettings.do?operation=enable ����������� Push-�����������</option>
    <option value="/pushSettings.do?operation=update">/pushSettings.do?operation=update ��������������� Push-�����������</option>
    <option value="/pushSettings.do?operation=remove">/pushSettings.do?operation=remove ���������� Push-�����������</option>
    <option value="/notificationSettings.do">/notificationSettings.do ��������� �������� ����������</option>
    <option value="/chooseNotificationType.do">/chooseNotificationType.do ����� ������� ����������</option>
    <option value="/private/stash.do">/private/stash.do �������� "����������"</option>
    <option value="/private/stash.do?save">/private/stash.do ���������� "����������"</option>
    <option value="/private/profile/saveData.do">/private/profile/saveData.do ���������� ������ � ������� ����</option>
    <option value="/private/profile/incognito.do?operation=show">/private/profile/incognito.do?operation=show ��������� ������� ����������</option>
    <option value="/private/profile/incognito.do?operation=save">/private/profile/incognito.do?operation=save ��������� ������� ����������</option>
    <option value="/private/profile/getAvatar.do">/private/profile/getAvatar.do ��������� ������� �� ������ �������� �� ������� ������� ����</option>
</optgroup>
<optgroup label="�������� �����">
    <option value="/private/dictionary/addressbook/phoneMaxCountPayList.do">/private/dictionary/addressbook/phoneMaxCountPayList.do ������ ����� ������ ����� ������� �����</option>
    <option value="/private/profile/editAddressBook.do?operation=add">/private/profile/editAddressBook.do?operation=add ���������� ������ ����� � �������� �����</option>
    <option value="/private/profile/editAddressBook.do?operation=edit">/private/profile/editAddressBook.do?operation=edit �������������� ������ ����� � �������� �����</option>
    <option value="/private/profile/editAddressBook.do?operation=delete">/private/profile/editAddressBook.do?operation=delete �������� ������ ����� �� �������� �����</option>
</optgroup>
<optgroup label="�������������� �����������">
    <option value="/private/loan/loanOffer/show.do">/private/loan/loanoffer/show.do ��������� ������ �������������� �����������</option>
    <option value="/private/payments/loan/offerClaim.do">/private/payments/loan/offerClaim.do ������ �� �������������� ������ </option>
    <option value="/private/payments/loan/loanOffer.do">/private/payments/loan/loanOffer.do ��� ������ �������������� ������� �� ��������</option>
    <option value="/private/payments/loan/cardClaim.do">/private/payments/loan/cardClaim.do ������ �� ��������� ����� (�������������) </option>
    <option value="/private/payments/loan/cardOffer.do">/private/payments/loan/cardOffer.do ��� ������ �������������� ������� �� ������</option>
</optgroup>
<optgroup label="��������� �����">
    <option value="/private/advertising.do">/private/advertising.do ��������� �����</option>
</optgroup>
<optgroup label="��� - ������ ������ ��������">
    <option value="/private/finances/show.do">/private/finances/show.do ��������� ���������� � ������� �������</option>
        <%--���������--%>
    <option value="/private/finances/category/list.do">/private/finances/category/list.do ���������� ���������</option>
    <option value="/private/finances/category.do?add">/private/finances/category.do ���������� ���������</option>
    <option value="/private/finances/category.do?update">/private/finances/category.do �������������� ���������</option>
    <option value="/private/finances/category.do?remove">/private/finances/category.do �������� ���������</option>
    <option value="/private/finances/operationCategories.do">/private/finances/operationCategories.do ��������� �������� �� ����������</option>
    <option value="/private/finances/operationDiagram.do">/private/finances/operationDiagram.do ��������� ������ ��� ������������� ��������� �������/������</option>
        <%--��� �������. ��������� "��������� ��������"--%>
    <option value="/private/graphics/finance.do">/private/graphics/finance.do ������ ���������� ��� ��������� ���������� ��������</option>
        <%--��������--%>
    <option value="/private/finances/operations/list.do">/private/finances/operations/list.do ������� ��������</option>
    <option value="/private/finances/operations/edit.do">/private/finances/operations/edit.do �������������� � �������� ��������</option>
    <option value="/private/finances/operations/add.do">/private/finances/operations/add.do ���������� ��������</option>
    <option value="/private/finances/operations/addCardOperation.do">/private/finances/operations/addCardOperation.do ���������� ��������� ��������</option>
    <option value="/private/finances/operations/remove.do">/private/finances/operations/remove.do �������� ����������� �������� ��������</option>
    <option value="/private/finances/operations/hide.do?hide">/private/finances/operations/hide.do ������� ���������</option>
    <option value="/private/finances/operations/hide.do?show">/private/finances/operations/hide.do ����������� ��������� ���������</option>
        <%--����--%>
    <option value="/private/finances/targets/selectTarget.do">/private/finances/targets/selectTarget.do ���������� �����</option>
    <option value="/private/finances/targets/list.do">/private/finances/targets/list.do ������ ����� �������</option>
    <option value="/private/finances/targets/edit.do">/private/finances/targets/edit.do �������������� ����</option>
    <option value="/private/finances/targets/editTarget.do?operation=remove">/private/finances/targets/editTarget.do �������� ����</option>
    <option value="/private/finances/targets/editTarget.do?operation=save">/private/finances/targets/editTarget.do �������� ������ �� ���������� ����</option>
    <option value="/private/finances/targets/viewTarget.do">/private/finances/targets/viewTarget.do ������������� ������ �� ���������� ����</option>
    <option value="/private/finances/targets/viewDepositTerms.do">/private/finances/targets/viewDepositTerms.do ������ �� ���������� ����. �������� ������� ������</option>
    <option value="/private/finances/targets/viewDepositTerms.do?termsType=tariffTerms">/private/finances/targets/viewDepositTerms.do?termsType=tariffTerms ������ �� ���������� ����. ��������
        ��������������� ����������
    </option>
        <%--���������� ���������--%>
    <option value="/private/finances/financeCalendar/show.do">/private/finances/financeCalendar/show.do ������ ������ ��� ����������� ���������</option>
    <option value="/private/finances/financeCalendar/showSelected.do">/private/finances/financeCalendar/showSelected.do ��������� ������� ����������� ��������� �� ����</option>
</optgroup>
<optgroup label="������">
    <option value="/private/mail/inbox.do">/private/mail/inbox.do �������� ������</option>
    <option value="/private/mail/sent.do">/private/mail/sent.do ������������ ������</option>
    <option value="/private/mail/archive.do">/private/mail/archive.do �������</option>
    <option value="/private/mail/themes.do">/private/mail/themes.do ���������� ������� ���������</option>
    <option value="/private/mail/edit.do">/private/mail/edit.do ��������</option>
    <option value="/private/mail/edit.do?operation=download">/private/mail/edit.do ��������� ��������</option>
    <option value="/private/mail/correspondence.do">/private/mail/correspondence.do ��������� ���������</option>
    <option value="/private/mail/edit.do?operation=save">/private/mail/edit.do ����������</option>
    <option value="/private/mail/edit.do?operation=send">/private/mail/edit.do ��������</option>
    <option value="/private/mail/remove.do">/private/mail/remove.do ��������</option>
    <option value="/private/mail/rollback.do">/private/mail/rollback.do ��������������</option>
</optgroup>
<optgroup label="������������">
    <option value="/private/fund/request.do?operation=create&">/private/fund/request.do �������� ������� �� ���� �������</option>
    <option value="/private/fund/request/list.do">/private/fund/request/list.do �������� ��������� �������� �� ���� �������</option>
    <option value="/private/fund/request/count.do">/private/fund/request/count.do ��������� ���������� ������������ �������� �������� �� ���� �������</option>
    <option value="/private/fund/request.do">/private/fund/request.do �������� ���������� �������</option>
    <option value="/private/fund/request.do?operation=close&">/private/fund/request.do �������� ���������� ������� �� ���� �������</option>
    <option value="/private/fund/group/list.do">/private/fund/group/list.do �������� ������ ����� �����������</option>
    <option value="/private/fund/group.do?operation=create&">/private/fund/group.do �������� ������ �����������</option>
    <option value="/private/fund/group.do?operation=edit&">/private/fund/group.do �������������� ������ �����������</option>
    <option value="/private/fund/group.do?operation=remove&">/private/fund/group.do �������� ������ �����������</option>
    <option value="/private/fund/group.do?">/private/fund/group.do �������� ��������� ���������� ������ �����������</option>
    <option value="/private/fund/response/list.do">/private/fund/response/list.do ��������� ������ �������� �������� �� ���� �������</option>
    <option value="/private/fund/response.do">/private/fund/response.do ��������� ������� ��������� ��������� ������� �� ���� �������</option>
    <option value="/private/fund/mapi/info.do">/private/fund/mapi/info.do ��������� ���������� � ����� �� ���-������ � ���������</option>
</optgroup>
<optgroup label="��������������">
    <option value="/private/current/locale/edit.do?operation=button.save">/private/current/locale/edit.do ��������� ������� ������</option>
    <option value="/current/locale/edit.do?operation=button.save">/current/locale/edit.do ��������� ������� ������</option>
</optgroup>
</select>
<br/>
<small>��������� (������: id=4&data=someThing)</small>
<br/>
<html:textarea property="params" styleId="params" cols="50" rows="5"/>
<c:choose>
    <c:when test="${form.submit}">
        <textarea cols="60" rows="5" name="note"><%=request.getParameter("note")%>
        </textarea>
    </c:when>
    <c:otherwise>
        <textarea cols="60" rows="5" name="note">�������:
            �����������:version=9.00&appType=iPhone&appVersion=82&deviceName=SamsungGalaxyMini2&operation=button.login&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3</textarea>
    </c:otherwise>
</c:choose>
<br/>
<input type="submit" name="operation" value="send"/><br/>
<br/>
<c:if test="${form.submit}">
    <b>�����:</b><br/>
    <c:if test="${IMAGE_ADDRESS ne form.address}">
        <c:set var="result" value="${form.result}"/>
        <%
            boolean isCaptcha = false;
            String captchaCode = "";

            String rowXml = (String) pageContext.getAttribute("result");
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
            {
            }
        %>
        <c:if test="<%=isCaptcha%>">
            <small>captcha:</small>
            <br/>
            <img src="data:image/gif;base64,<%=captchaCode%>" width="200" height="80"/><br/>
        </c:if>

        <small>Tree:</small>
        <br/>

        <div id="XMLtree"></div>
        <small>Xml:</small>
        <br/>
        <%-- ���������� �������� & �� ����������, ��� ��� textarea �� ��������� ��� ����������� ��������� �
       ������� � ��� ������� ��������� xml ������ �������������� ������� ��� js �������� ��� ��
       �������������� --%>
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
