<%@ page import="com.rssl.phizic.web.servlet.HttpServletEditableRequest" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:html>
  <head><title>ATM test</title>
   <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/XMLDisplay.js"></script>
   <script type="text/javascript">
    var root = {};
    root['/login.do'] = "CSAATM";
    var defaultRoot = 'atm';

	var operations = {};
	operations['/login.do'] = "operation=login&pan=6529609554747148&codeATM=qwe123&atmRegionCode=&isChipCard=";
    operations['/postCSALogin.do'] = "token=&codeATM=qwe123&isChipCard=&atmRegionCode=";
    operations['/login/chooseAgreement.do'] = "operation=choose&agreementId=";
    operations['/private/products/list.do'] = "showProductType=";
    operations['/private/products/newList.do'] = "showProductType=";
	operations['/private/cards/info.do'] = "id=";
    operations['/private/cards/abstract.do'] = "count=10&id=";
	operations['/private/accounts/info.do'] = "id=";
    operations['/private/accounts/abstract.do'] = "from=10.01.10&to=10.10.10&count=&id=";
    operations['/private/loans/info.do'] = "id=";
    operations['/private/loans/abstract.do'] = "count=12&id=";
    operations['/private/ima/abstract.do'] = "from=01.12.2011&to=31.12.2011&id=";
    operations['/private/payments/list.do'] = "status=all&from=&to=&creationType=atm&form=";

    operations['/private/payments/payments.do'] = "";
    operations['/private/payments/payment.do?SMStemplate'] = "operation=init&SMStemplate=";
    operations['/private/payments/view.do'] = "id=";
    operations['/private/regularpayments/list.do'] = "type=";
    operations['/private/favourite/list/templates.do'] = "status=&form=&type=";
    operations['/private/payments/template/view.do'] = "id=";
    operations['/private/payments/template/remove.do'] = "id=";
    operations['/private/payments/payment.do?template'] = "template=";
    operations['/private/rates/list.do'] = "type=";
    operations['/images'] = "id=";
    operations['/private/provider/search.do'] = "search=��������&regionId=&regionGuid=&autoPaymentOnly=false&includeServices=false";
    operations['/private/payments/servicesPayments/edit.do?'] = "operation=init&billing=&service=&provider=";
    operations['/private/payments/servicesPayments/edit.do'] = "operation=next&billing=&service=&provider=&fromResource=&operationUID=";
    operations['/private/payments/payment.do'] = "operation=next&id=";
    operations['/private/creditcard/office/list.do'] = "region=&street=&name=&paginationSize=&paginastionOffset=";
    operations['/private/payments/confirm.do'] = "operation=confirm&id=&transactionToken";
    operations['/private/dictionary/banks/national.do'] = "guid=&name=&bic=&city=";
    operations['/private/operationCodes.do'] = "";
    operations['/private/dictionary/regions.do'] = "parentId=";
    operations['/private/dictionary/servicesPayments.do'] = "id=&region=&regionGuid=&autoPaymentOnly=";
    operations['/private/dictionary/providerServices.do'] = "id=&providerGuid=&regionId=&regionGuid=";
    operations['/private/dictionary/popularPayments.do'] = "paginationSize=&paginationOffset=&type=&region=&regionGuid=";
    operations['/private/payments/payment.do?form=CreateAutoPaymentPayment'] = "transactionToken=&operation=next&ONCE_IN_QUARTER_MONTHS=&ONCE_IN_YEAR_MONTHS=&autoPaymentFloorLimit=&autoPaymentName=&autoPaymentStartDate=&autoPaymentType=&codeService=&firstPaymentDate=&fromResource=&receiverName=&recipient=&requisite=&requisiteName=&sellAmount=";
    operations['/private/payments/payment.do?form=EditAutoPaymentPayment'] = "transactionToken=&operation=init&linkId=";
    operations['/private/autopayments/list.do'] = "";
    operations['/private/autopayments/info.do'] = "id=";
    operations['/private/autopayments/abstract.do'] = "id=&from=&to=&paginationSize=&paginationOffset=";
    operations['/private/longoffers/abstract.do'] = "id=&from=&to=&paginationSize=&paginationOffset=";
    operations['/private/autosubscriptions/abstract.do'] = "id=&from=&to=&paginationSize=&paginationOffset=";
    operations['/private/autotransfers/abstract.do'] = "id=&from=&to=&paginationSize=&paginationOffset=";
    operations['/private/longoffers/info.do'] = "id=";
    operations['/private/autosubscriptions/info.do'] = "id=";
    operations['/private/autotransfers/info.do'] = "id=";
    operations['/private/payments/payment.do?form=RefuseAutoPaymentPayment&operation=init'] = "linkId=&transactionToken=";
    operations['/private/payments/payment.do?form=RefuseAutoPaymentPayment&operation=save'] = "linkId=&transactionToken=&cardId=&sellAmount=&requisite=&receiverName=&executionEventType=&autoPaymentStartDate=&autoPaymentName=&autoPaymentFloorLimit=&autoPaymentFloorCurrency=";
    operations['/private/payments/quicklyCreateTemplate.do?operation=getTemplateName'] = "payment=";
    operations['/private/payments/quicklyCreateTemplate.do?operation=save'] = "payment=&templateName=";
    operations['/private/payments/payment.do?form=EditAutoSubscriptionPayment'] = "transactionToken=&operation=init&autoSubNumber=";
    operations['/private/payments/payment.do?form=CloseAutoSubscriptionPayment'] = "transactionToken=&operation=init&autoSubNumber=";
    operations['/private/payments/payment.do?form=DelayAutoSubscriptionPayment&operation=init'] = "transactionToken=&autoSubNumber=";
    operations['/private/payments/payment.do?form=DelayAutoSubscriptionPayment&operation=save'] = "transactionToken=";
    operations['/private/payments/payment.do?form=RecoveryAutoSubscriptionPayment&operation=init'] = "transactionToken=&autoSubNumber=";
    operations['/private/payments/payment.do?form=RecoveryAutoSubscriptionPayment&operation=save'] = "transactionToken=";
    operations['/private/payments/loan/claim.do'] = "transactionToken=";
    operations['/private/payments/loan/loanProduct.do'] = "transactionToken=&operation=next&loanId=&changeDate=";
    operations['/private/payments/payment.do?form=LoanProduct'] = "operation=save&transactionToken=";
    operations['/private/permissions.do'] = "needCheckUDBO=false";
    operations['/private/deposits/products/list.do'] = "form=AccountOpeningClaim";
    operations['/private/deposits/products/info.do'] = "form=AccountOpeningClaim&depositType=&depositId=&depositGroup=";
    operations['/private/deposits/products/typeInfo.do'] = "depositType=&depositId=&depositGroup=&openingDate=&period=&currencyCode=&minBalance=";
    operations['/private/deposits/terms.do'] = "id=";
    operations['/logoff.do'] = "";
    operations['/private/payments/status.do'] = "id=";
    operations['/private/payments/printCheck.do'] = "id=";
    operations['/private/autosubscriptions/payment/info/print.do'] = "id=&subscriptionId=";
    operations['/private/autotransfers/payment/info/print.do'] = "id=&subscriptionId=";
    operations['/checkDUL.do'] = "code=";
    operations['/private/thanks.do'] = "";
    operations['/private/loan/loanOffer/show.do'] = "isLogin=true&&claimType=";
    operations['/private/payments/loan/offerClaim.do'] = "transactionToken=";
    operations['/private/payments/loan/loanOffer.do'] = "transactionToken=&operation=next&loanId=";
    operations['/private/payments/loan/cardClaim.do'] = "transactionToken=";
    operations['/private/payments/loan/cardOffer.do'] = "transactionToken=&operation=next&loanId=&offerId=";
    operations['/private/payments/default-action.do'] = "id=&objectFormName=&stateCode=";
    operations['/private/pfrClaims/list.do'] = "from=&to=";
    operations['/private/pfr/statement.do'] = "claimId=";
    operations['/private/getregistration.do'] = "";
    operations['/private/payments/makeLongOffer.do'] = "id=";
    operations['/private/loans/personal/agreement.do'] = "";

    operations['/private/atm/serviceInfo.do'] = '';
    operations['/private/atm/serviceInfo.do?operation=save'] = 'data=';
    operations['/private/moneyboxes/list.do'] = 'cardId=&accountId=';
    operations['/private/moneyboxes/statusMod.do'] = 'id=&changePaymentStatusType=';
    operations['/private/moneyboxes/info.do'] = 'linkId=&claimId=';
    operations['/private/moneyboxes/payment/info/print.do'] = 'id=&subscriptionId=';
    operations['/private/moneyboxes/abstract.do'] = 'id=&from=&to=&paginationSize=&paginationOffset=';
    operations['/private/cards/currency.do'] = "cardNumber=&phoneNumber=";

    function trim(string)
	{
	return string.replace(/(^\s+)|(\s+$)/g, "");
	}

	function getSelectValue (obj)
	{
	var retval="";
	if (typeof(obj) == 'string')
		obj = document.getElementById(obj);
	if (obj) retval = obj.options[obj.selectedIndex].value;
	return retval;
	}

	function changeAddress (obj)
	{
        var val = getSelectValue (obj);
        var params = document.getElementById("params");
        params.value = operations[val];

        changeRoot(val);
        setUrl();
	}

    function changeAddressValue (value)
    {
        var address = document.getElementById ("address");
        for (var i=0; i < address.length; i++)
            if ( address.options[i].value == value)
            {
                address.options[i].selected = true;
                break;
            }

        changeAddress (address);

    }

    function setParams (value)
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
            font-weight:bold;
            color: #800080;
        }
        .AttributeName {
            font-weight:bold;
            color: black;
        }
        .AttributeValue { color:blue; }
        .NodeValue { color: black; }
        .Element {
            border-left-color:#00FF66;
            border-left-width:thin;
            border-left-style:solid;
            padding-top:0px;
            margin-top:10px;
        }
        .Clickable {
            font-weight:900;
            font-size:large;
            color: #800080;
            cursor:pointer;
            vertical-align:middle;
        }

        #XMLtree {
            width: 800px;
        }
      </style>
  </head>
  <body>

  <c:set var="IMAGE_ADDRESS" value="/images"/>

  <h1>ATM</h1>

  <html:form action="/atm" show="true">
      <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

      <c:if test="${not empty form.cookie}">
          <c:url var="internalPaymentUrl" value="/atm/internalPayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="internalPaymentLongOfferUrl" value="/atm/internalPaymentLongOffer.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="servicePaymentUrl" value="/atm/servicePayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="jurPaymentUrl" value="/atm/jurPayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="rurPaymentUrl" value="/atm/rurPayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
              <c:param name="version" value="${form.version}"/>
          </c:url>
          <c:url var="rurPaymentLongOfferUrl" value="/atm/rurPaymentLongOffer.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
              <c:param name="version" value="${form.version}"/>
          </c:url>
          <c:url var="createP2PAutoTransferClaimUrl" value="/atm/createP2PAutoTransfer.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
              <c:param name="version" value="${form.version}"/>
          </c:url>
          <c:url var="quicklyCreateP2PAutoTransferClaimUrl" value="/atm/quicklyCreateP2PAutoTransfer.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
              <c:param name="version" value="${form.version}"/>
          </c:url>
          <c:url var="editP2PAutoTransferClaimUrl" value="/atm/editP2PAutoTransfer.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
              <c:param name="version" value="${form.version}"/>
          </c:url>
          <c:url var="delayP2PAutoTransferClaimUrl" value="/atm/delayP2PAutoTransfer.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
              <c:param name="version" value="${form.version}"/>
          </c:url>
          <c:url var="recoveryP2PAutoTransferClaimUrl" value="/atm/recoveryP2PAutoTransfer.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
              <c:param name="version" value="${form.version}"/>
          </c:url>
          <c:url var="closeP2PAutoTransferClaimUrl" value="/atm/closeP2PAutoTransfer.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
              <c:param name="version" value="${form.version}"/>
          </c:url>
          <c:url var="createAutoPaymentUrl" value="/atm/createAutoPayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="refuseAutoPaymentUrl" value="/atm/refuseAutoPayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="mobileBankTemplatePaymentUrl" value="/atm/mobileBankTemplatePayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="editAutoPaymentPaymentUrl" value="/atm/editAutoPaymentPayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="refuseAutoPaymentUrl" value="/atm/refuseAutoPayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="editAutoSubscriptionPaymentUrl" value="/atm/editAutoSubscriptionPayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="closeAutoSubscriptionPaymentUrl" value="/atm/closeAutoSubscriptionPayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="refuseLongOfferUrl" value="/atm/refuseLongOffer.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="accountOpeningClaimUrl" value="/atm/accountOpeningClaim.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>
          <c:url var="cardOpeningClaimUrl" value="/atm/cardOpeningClaim.do">
              <c:param name="url"       value="${form.url}"/>
              <c:param name="cookie"    value="${form.cookie}"/>
              <c:param name="proxyUrl"  value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
              <c:param name="version"   value="${form.version}"/>
          </c:url>
          <c:url var="accountClosingPaymentUrl" value="/atm/accountClosingPayment.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <c:url var="loyaltyProgramRegistrationClaimUrl" value="/atm/loyaltyProgramRegistrationClaim.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <c:url var="PFRStatementClaimUrl" value="/atm/PFRStatementClaim.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <c:url var="quickMakeLongOfferUrl" value="/atm/quickMakeLongOffer.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <c:url var="delayAutoPaymentUrl" value="/atm/delayAutoSubscriptionPayment.do">
               <c:param name="url" value="${form.url}"/>
               <c:param name="cookie" value="${form.cookie}"/>
               <c:param name="proxyUrl" value="${form.proxyUrl}"/>
               <c:param name="proxyPort" value="${form.proxyPort}"/>
           </c:url>

          <c:url var="recoveryAutoPaymentUrl" value="/atm/recoveryAutoSubscriptionPayment.do">
               <c:param name="url" value="${form.url}"/>
               <c:param name="cookie" value="${form.cookie}"/>
               <c:param name="proxyUrl" value="${form.proxyUrl}"/>
               <c:param name="proxyPort" value="${form.proxyPort}"/>
           </c:url>

          <c:url var="loanProductOpeningClaimUrl" value="/atm/loanProductOpeningClaim.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <c:url var="loanOfferOpeningClaimUrl" value="/atm/loanOfferOpeningClaim.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <c:url var="loanPaymentLongOfferUrl" value="/atm/loanPaymentLongOffer.do">
              <c:param name="url"       value="${form.url}"/>
              <c:param name="cookie"    value="${form.cookie}"/>
              <c:param name="proxyUrl"  value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <c:url var="loanPaymentUrl" value="/atm/loanPayment.do">
              <c:param name="url"       value="${form.url}"/>
              <c:param name="cookie"    value="${form.cookie}"/>
              <c:param name="proxyUrl"  value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <c:url var="moneyBoxCreateUrl" value="/atm/createMoneyBox.do">
            <c:param name="url"       value="${form.url}"/>
            <c:param name="address"   value="/private/payments/payment.do?form=CreateMoneyBoxPayment"/>
            <c:param name="cookie"    value="${form.cookie}"/>
            <c:param name="proxyUrl"  value="${form.proxyUrl}"/>
            <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <c:url var="moneyBoxEditUrl" value="/atm/editMoneyBox.do">
            <c:param name="url" value="${form.url}"/>
            <c:param name="cookie" value="${form.cookie}"/>
            <c:param name="proxyUrl" value="${form.proxyUrl}"/>
            <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <c:url var="changeCreditLimitUrl" value="/atm/changeCreditLimitClaim.do">
              <c:param name="url" value="${form.url}"/>
              <c:param name="cookie" value="${form.cookie}"/>
              <c:param name="proxyUrl" value="${form.proxyUrl}"/>
              <c:param name="proxyPort" value="${form.proxyPort}"/>
          </c:url>

          <html:link href="${mobileBankTemplatePaymentUrl}">������ ������� �� ������� ��</html:link>
          <html:link href="${internalPaymentUrl}">������� ����� ����� �������</html:link>&nbsp;
          <html:link href="${servicePaymentUrl}">������ ����������</html:link>&nbsp;
          <html:link href="${jurPaymentUrl}">������� �����������</html:link>
          <html:link href="${createAutoPaymentUrl}">�������� ����������� ������ �����</html:link>
          <html:link href="${internalPaymentLongOfferUrl}">�������� ����������� �������� ����� ������ �������</html:link>
          <html:link href="${refuseAutoPaymentUrl}">������ �����������</html:link>
          <html:link href="${rurPaymentUrl}">������� �������� ����</html:link>
          <html:link href="${rurPaymentLongOfferUrl}">�������� ����������� �������� �������� ����</html:link>
          <html:link href="${createP2PAutoTransferClaimUrl}">�������� P2P ������������</html:link>
          <html:link href="${quicklyCreateP2PAutoTransferClaimUrl}">�������� P2P ������������ (�� ���������)</html:link>
          <html:link href="${editP2PAutoTransferClaimUrl}">�������������� P2P ������������</html:link>
          <html:link href="${delayP2PAutoTransferClaimUrl}">������������ P2P ������������</html:link>
          <html:link href="${recoveryP2PAutoTransferClaimUrl}">������������� P2P ������������</html:link>
          <html:link href="${closeP2PAutoTransferClaimUrl}">�������� P2P ������������</html:link>
          <html:link href="${editAutoPaymentPaymentUrl}">�������������� ����������� iqwave</html:link>
          <html:link href="${refuseAutoPaymentUrl}">������ ����������� iqwave</html:link>
          <html:link href="${editAutoSubscriptionPaymentUrl}">�������������� ����������� (����)</html:link>

          <html:link href="${delayAutoPaymentUrl}">������������ ����������� (����)</html:link>
          <html:link href="${recoveryAutoPaymentUrl}">������������� ����������� (����)</html:link>

          <html:link href="${loanProductOpeningClaimUrl}">������ �� ������</html:link>
          <html:link href="${loanOfferOpeningClaimUrl}">������ �� �������������� ������</html:link>
          <html:link href="${loanPaymentUrl}">��������� �������</html:link>
          <html:link href="${loanPaymentLongOfferUrl}">��������� �������(���������� ���������)</html:link>

          <html:link href="${closeAutoSubscriptionPaymentUrl}">������ ����������� (����)</html:link>
          <html:link href="${refuseLongOfferUrl}">������ ����������� ���������</html:link>
          <html:link href="${accountOpeningClaimUrl}">�������� ������</html:link>
          <html:link href="${cardOpeningClaimUrl}">���������� ������ �� ��������� �����</html:link>
          <html:link href="${accountClosingPaymentUrl}">�������� ������</html:link>
          <html:link href="${loyaltyProgramRegistrationClaimUrl}">����������� � ��������� ����������</html:link>
          <html:link href="${PFRStatementClaimUrl}">������ �� ��������� ������� �� ���</html:link>
          <html:link href="${quickMakeLongOfferUrl}">������� �������� �����������</html:link>
          <html:link href="${moneyBoxCreateUrl}">����������� �������</html:link>
          <html:link href="${moneyBoxEditUrl}">�������������� �������</html:link>
          <html:link href="${changeCreditLimitUrl}">����� ���������� ������</html:link>
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
                <option value="CSAATM" selected=>CSAATM</option>
                <option value="atm">atm</option>
            </select>
      Proxy: <html:text property="proxyUrl"/>
      port: <html:text property="proxyPort"/>
      <br/>
      <select name="address" onchange="changeAddress (this);" id="address">
          <optgroup label="��������������">
               <option value="/login.do">/login.do ����</option>
               <option value="/postCSALogin.do">/postCSALogin.do ���������� �����</option>
               <option value="/login/chooseAgreement.do">/login/chooseAgreement.do ��� ������ ��������</option>
               <option value="/checkDUL.do">/checkDUL.do �������� ���������� ��� ��� �������������� �����������</option>
               <option value="/private/getregistration.do">/private/getregistration.do ��������� ������ � ������ ��� ����� � ����</option>
               <option value="/logoff.do">/logoff.do �����</option>
          </optgroup>
          <optgroup label="�����">
            <option value="/private/permissions.do">/private/permissions.do ����� �������</option>
            <option value="/private/products/list.do">/private/products/list.do ��������� ������ ���������</option>
            <option value="/private/products/newList.do">/private/products/newList.do ��������� ������ ���������� ��������� (������� 2)</option>
            <option value="/private/payments/list.do">/private/payments/list.do ������� ��������</option>
            <option value="/private/rates/list.do">/private/rates/list.do ��������� ������ �����</option>
            <option value="/private/payments/status.do">/private/payments/status.do ������ ������� �������</option>
          </optgroup>
          <optgroup label="������� �������">
            <option value="/private/atm/serviceInfo.do">/private/atm/serviceInfo.do �������� ��������� ���������� �� �������</option>
            <option value="/private/atm/serviceInfo.do?operation=save">/private/atm/serviceInfo.do ���������� ��������� ���������� � �������</option>
          </optgroup>
          <optgroup label="���������� �� �����">
            <option value="/private/cards/info.do">/private/cards/info.do ��������� ���������� �� �����</option>
            <option value="/private/cards/abstract.do">/private/cards/abstract.do ������� �� �����</option>
          </optgroup>
          <optgroup label="���������� �� ������">
            <option value="/private/accounts/info.do">/private/accounts/info.do ��������� ���������� �� ������</option>
            <option value="/private/accounts/abstract.do">/private/accounts/abstract.do ������� �� ������</option>
          </optgroup>
          <optgroup label="���������� �� �������">
            <option value="/private/loans/info.do">/private/loans/info.do ��������� ���������� �� �������</option>
            <option value="/private/loans/abstract.do">/private/loans/abstact.do ��������� ������</option>
            <option value="/private/loans/personal/agreement.do">/private/loans/personal/agreement.do ��������� ������ �������� �� ��������� ������������ ������</option>
          </optgroup>
          <optgroup label="���������� �� ���">
              <option value="/private/ima/abstract.do">/private/ima/abstract.do ������� �� ���</option>
          </optgroup>

          <optgroup label="�������">
              <optgroup label="������ � ��������� ���������� �����">
                <option value="/private/payments/payments.do">/private/payments/payments.do ������ �������� ���������� �����</option>
                <option value="/private/payments/payment.do?SMStemplate">/private/payments/payment.do ������������� ������� �� ������� ��</option>
              </optgroup>
              <optgroup label="�������� �������">
                <option value="/private/payments/view.do">/private/payments/view.do �������� ��������� ���������� �� ������� ��� ��������</option>
              </optgroup>
              <optgroup label="������� ��������">
                <option value="/private/favourite/list/templates.do">/private/favourite/list/templates.do ������ �������� ��������</option>
                <option value="/private/payments/template/view.do">/private/payments/template/view.do ��������� ���������� �� �������</option>
                <option value="/private/payments/template/remove.do">/private/payments/template/remove.do �������� �������</option>
                <option value="/private/payments/payment.do?template">/private/payments/payment.do ������ �������� �������� �� �������</option>
                <option value="/private/payments/quicklyCreateTemplate.do?operation=getTemplateName">/private/payments/quicklyCreateTemplate.do ������� �������� �������: ��������� ����� ������� �� ���������</option>
                <option value="/private/payments/quicklyCreateTemplate.do?operation=save">/private/payments/quicklyCreateTemplate.do ������� �������� �������: ���������� �������</option>
              </optgroup>
               <optgroup label="������ �����">
                <option value="/private/provider/search.do">/private/provider/search.do ����� �����������</option>
                <option value="/private/payments/servicesPayments/edit.do?">/private/payments/servicesPayments/edit.do ������������� ������� �� ���������� ����������</option>
                <option value="/private/payments/servicesPayments/edit.do">/private/payments/servicesPayments/edit.do ������ ��� ������ �� ���������� ����������</option>
                <option value="/private/payments/payment.do">/private/payments/payment.do ����������� ���� ������ �� ���������� ����������</option>
              </optgroup>
              <optgroup label="�����������">
                <option value="/private/payments/payment.do?form=CreateAutoPaymentPayment">/private/payments/payment.do ���������� �����������</option>
                <option value="/private/payments/payment.do?form=RefuseAutoPaymentPayment&operation=init">/private/payments/payment.do?form=RefuseAutoPaymentPayment&operation=init ������������� ������ �� ������ �����������</option>
                <option value="/private/payments/payment.do?form=RefuseAutoPaymentPayment&operation=save">/private/payments/payment.do?form=RefuseAutoPaymentPayment&operation=save ������ �����������</option>
                <option value="/private/payments/payment.do?form=DelayAutoSubscriptionPayment&operation=init">/private/payments/payment.do ������������ ����������� autoSubscription (������������� ������)</option>
                <option value="/private/payments/payment.do?form=DelayAutoSubscriptionPayment&operation=save">/private/payments/payment.do ������������ ����������� autoSubscription (������������ �����������)</option>
                <option value="/private/payments/payment.do?form=RecoveryAutoSubscriptionPayment&operation=init">/private/payments/payment.do ������������� ����������� autoSubscription (������������� ������)</option>
                <option value="/private/payments/payment.do?form=RecoveryAutoSubscriptionPayment&operation=save">/private/payments/payment.do ������������� ����������� autoSubscription (������������� �����������)</option>
                <option value="/private/regularpayments/list.do">/private/regularpayments/list.do ������ ������������</option>
                <option value="/private/autopayments/info.do">/private/autopayments/info.do ��������� ��������� ���������� �� �����������</option>
                <option value="/private/longoffers/info.do">/private/longoffers/info.do ��������� ��������� ���������� �� ����������� ���������</option>
                <option value="/private/autosubscriptions/info.do">/private/autosubscriptions/info.do ��������� ��������� ���������� �� ������� �����������</option>
                <option value="/private/autopayments/abstract.do">/private/autopayments/abstract.do ������ �����������</option>
                <option value="/private/longoffers/abstract.do">/private/longoffers/abstract.do ������ ����������� ���������</option>
                <option value="/private/autosubscriptions/abstract.do">/private/autosubscriptions/abstract.do ������ ������� �����������</option>
                <option value="/private/autosubscriptions/payment/info/print.do">/private/autosubscriptions/payment/info/print.do ������ ���� ������������ �����������</option>
              </optgroup>
              <optgroup label="������������">
                <option value="/private/regularpayments/list.do">/private/regularpayments/list.do ������ ������������</option>
                <option value="/private/autotransfers/info.do">/private/autotransfers/info.do ��������� ��������� ���������� �� ������������</option>
                <option value="/private/autotransfers/abstract.do">/private/autotransfers/abstract.do ������ ������� �����������</option>
                <option value="/private/autotransfers/payment/info/print.do">/private/autotransfers/payment/info/print.do ������ ���� ������������ �����������</option>
              </optgroup>
              <optgroup label="�������">
                <option value="/private/moneyboxes/list.do">/private/moneyboxes/list.do ������ �������</option>
                <option value="/private/moneyboxes/statusMod.do">/private/moneyboxes/statusMod.do ������������/�������������/����������</option>
                <option value="/private/moneyboxes/info.do">/private/moneyboxes/info.do ��������� ��������� ���������� �������</option>
                <option value="/private/moneyboxes/abstract.do">/private/moneyboxes/abstract.do ��������� ������� �������� �������</option>
                <option value="/private/moneyboxes/payment/info/print.do">/private/moneyboxes/payment/info/print.do ������ ���� �� �������� �������� ��������</option>
              </optgroup>
              <optgroup label="������ �� �������������� ��������� �����">
                  <option value="/private/creditcard/office/list.do">/private/creditcard/office/list.do ����� ����� ��� ��������� �������������� ��������� �����</option>
              </optgroup>
              <optgroup label="������ �� ������">
                  <option value="/private/payments/loan/claim.do">/private/payments/payment.do ��������������</option>
                  <option value="/private/payments/loan/loanProduct.do">/private/payments/loan/loanProduct.do ��� ������ �������</option>
                  <option value="/private/payments/payment.do?form=LoanProduct">/private/payments/loan/loanProduct.do ���������� ������</option>
              </optgroup>
              <optgroup label="�������������">
                <option value="/private/payments/confirm.do">/private/payments/confirm.do ������������� �������</option>
              </optgroup>
              <optgroup label="������ ���� ��������">
                <option value="/private/payments/printCheck.do">/private/payments/printCheck.do ������ ���� ��������</option>
              </optgroup>
              <option value="/private/payments/default-action.do">/private/payments/default-action.do ������� � �������� �� ���������</option>
          </optgroup>
          <optgroup label="�����������">
              <option value="/private/dictionary/banks/national.do">/private/dictionary/banks/national.do ���������� ������</option>
              <option value="/private/operationCodes.do">/private/operationCodes.do ���������� �������� ��������</option>
              <option value="/private/dictionary/regions.do">/private/dictionary/regions.do ���������� ��������</option>
              <option value="/private/dictionary/servicesPayments.do">/private/dictionary/servicesPayments.do ���������� �����������</option>
              <option value="/private/dictionary/providerServices.do">/private/dictionary/providerServices.do ��������� ������ ����� ����������</option>
              <option value="/private/dictionary/popularPayments.do">/private/dictionary/popularPayments.do ���������� �������</option>
              <option value="/images">/images ��������� ����������� �� ����</option>
          </optgroup>
          <optgroup label="������� ������������">
              <option value="/private/thanks.do">/private/thanks.do ������������� ����� ��������</option>
          </optgroup>
          <optgroup label="�������� �������">
                  <option value="/private/deposits/products/list.do">/private/deposits/products/list.do ������ �������</option>
                  <option value="/private/deposits/products/info.do">/private/deposits/products/info.do ��������� ���������� �� ������</option>
                  <option value="/private/deposits/products/typeInfo.do">/private/deposits/products/typeInfo.do ����������� ������� ������</option>
                  <option value="/private/deposits/terms.do">/private/deposits/terms.do �������� ������� �������� �� ������</option>
          </optgroup>
          <optgroup label="�������������� �����������">
          	<option value="/private/loan/loanOffer/show.do">/private/loan/loanoffer/show.do ��������� ������ �������������� �����������</option>
            <option value="/private/payments/loan/offerClaim.do">/private/payments/loan/offerClaim.do ������ �� �������������� ������ </option>
            <option value="/private/payments/loan/loanOffer.do">/private/payments/loan/loanOffer.do ��� ������ �������������� ������� �� ��������</option>
            <option value="/private/payments/loan/cardClaim.do">/private/payments/loan/cardClaim.do ������ �� ��������� ����� (�������������) </option>
            <option value="/private/payments/loan/cardOffer.do">/private/payments/loan/cardOffer.do ��� ������ �������������� ������� �� ������</option>
          </optgroup>
          <optgroup label="���">
              <option value="/private/pfrClaims/list.do">/private/pfrClaims/list.do ������ ������ �� ��������� ������� ���</option>
              <option value="/private/pfr/statement.do">/private/pfr/statement.do ������� �� ����������� �����</option>
          </optgroup>
          <option value="/private/payments/makeLongOffer.do">/private/payments/makeLongOffer.do ������� �������� �����������</option>
          <optgroup label="�������: ������� �������� ����">
              <option value="/private/cards/currency.do">/private/cards/currency.do ��������� ���������� � ������ ����� ����������</option>
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
              <textarea cols="60" rows="5" name="note">�������: �����������:operation=login&logonCardNumber=0629778350443415
              </textarea>
          </c:otherwise>
      </c:choose>
      <br/>
      <input type="submit" name="operation" value="send"/><br/>
      <br/>
      <c:if test="${form.submit}">
          <b>�����:</b><br/>
          <c:if test="${IMAGE_ADDRESS ne form.address}">
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
                window.onload = function () { LoadXMLString('XMLtree', trim(document.getElementById('result').value) ); };
              </script>
          </c:if>
          Status: <html:text property="status" readonly="true"/><br/>
          Content-Type: <html:text property="contentType" readonly="true"/><br/>
         <script type="text/javascript">
            changeAddressValue ("<%=request.getParameter("address")%>");
            setParams("<%=request.getParameter("params")%>");
         </script>
      </c:if>
  </html:form>
  </body>
</html:html>
