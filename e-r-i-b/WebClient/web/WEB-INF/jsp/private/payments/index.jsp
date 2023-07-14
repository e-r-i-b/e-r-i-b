<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert definition="paymentMain">
<tiles:put name="needSave" type="string" value="false"/>
<!-- заголовок -->
<tiles:put name="pageTitle" type="string">Платежи</tiles:put>

<!--История операций всех платежей-->
<tiles:put name="data" type="string">

<div id="workspace" style="position:absolute; width:100%">
<div class="pmntListGroup">
<div class='pmntListGroupTitle'>Шаблоны</div>

<tiles:insert definition="paymentCards" service="ClientTemplatesManagement" flush="false">
	<tiles:put name="serviceId" value="ClientTemplatesManagement"/>
	<tiles:put name="listPayTitle" value="Шаблоны платежей"/>
</tiles:insert>
</div>
<div style="clear:both;"></div>

<div class="pmntListGroup">
<div class='pmntListGroupTitle'>Переводы</div>

<tiles:insert definition="paymentsPaymentCards" service="InternalPayment" flush="false">
	<tiles:put name="serviceId" value="InternalPayment"/>
</tiles:insert>

<tiles:insert definition="paymentsPaymentCards" service="CurrencyPayment" flush="false">
	<tiles:put name="serviceId" value="CurrencyPayment"/>
</tiles:insert>

<tiles:insert definition="paymentsPaymentCards" service="ContactPayment" flush="false">
	<tiles:put name="serviceId" value="ContactPayment"/>
</tiles:insert>

<tiles:insert definition="paymentsPaymentCards" service="RurPayment" flush="false">
	<tiles:put name="serviceId" value="RurPayment"/>
</tiles:insert>

<tiles:insert definition="paymentsPaymentCards" service="TaxPayment" flush="false">
	<tiles:put name="serviceId" value="TaxPayment"/>
</tiles:insert>

<tiles:insert definition="paymentsPaymentCards" service="CardReplenishmentPayment" flush="false">
	<tiles:put name="serviceId" value="CardReplenishmentPayment"/>
</tiles:insert>
</div>
<div style="clear:both;"></div>

<div class="pmntListGroup">
<div class='pmntListGroupTitle'>Обмен валюты</div>
<tiles:insert definition="paymentsPaymentCards" service="PurchaseSaleCurrencyPayment" flush="false">
	<tiles:put name="serviceId" value="PurchaseSaleCurrencyPayment"/>
</tiles:insert>
    
<tiles:insert definition="paymentsPaymentCards" service="ConvertCurrencyPayment" flush="false">
    <tiles:put name="serviceId" value="ConvertCurrencyPayment"/>
</tiles:insert>
</div>
<div style="clear:both;"></div>

<div class="pmntListGroup">
	<div class='pmntListGroupTitle'><bean:message key="label.payments.services" bundle="commonBundle"/></div>
<tiles:insert definition="paymentsPaymentCards" service="GKHPayment" flush="false">
	<tiles:put name="serviceId" value="GKHPayment"/>
</tiles:insert>

<tiles:insert definition="paymentsPaymentCards" service="ElectricPayment" flush="false">
	<tiles:put name="serviceId" value="ElectricPayment"/>
</tiles:insert>
</div>
<div style="clear:both;"></div>

<div class="pmntListGroup">
<div class='pmntListGroupTitle'>Оплата товаров и услуг по сети Contact</div>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="cellular-communication-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="inet-connection-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="ip-telephony-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="telephony-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="satellite-connection-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="inet-shops-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="travel-agency-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="policy-payment-contact"/>
</tiles:insert>
		
<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="credit-repayment-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="game-portals-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="air-ticket-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="realty-operation-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="payment-system-contact"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="hotel-payment-contact"/>
</tiles:insert>

</div>
<div style="clear:both;"></div>

<div class="pmntListGroup">
<div class='pmntListGroupTitle'>Оплата товаров и услуг через платежную систему Rapida</div>
<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="cellular-communication-rapida"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="telephony-rapida"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="ip-telephony-rapida"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="satellite-connection-rapida"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="credit-repayment-rapida"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="payment-system-rapida"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="gkh-payment-rapida"/>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentCards" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="electric-payment-rapida"/>
</tiles:insert>
</div>
<div style="clear:both;"></div>

<div class="pmntListGroup">
<div class='pmntListGroupTitle'>Журнал операций</div>

	<tiles:insert definition="paymentCards" service="PaymentList" flush="false">
		<tiles:put name="serviceId" value="PaymentList"/>
		<tiles:put name="action" value="/private/payments/common"/>
        <tiles:putList name="params">
            <tiles:putList name="names"><tiles:add>status</tiles:add></tiles:putList>
            <tiles:putList name="values"><tiles:add>all</tiles:add></tiles:putList>
        </tiles:putList>
		<tiles:put name="notForm" value="true"/>
		<tiles:put name="image" value="iconPmntList_AllPayments.gif"/>
		<tiles:put name="listPayTitle" value="Все операции"/>
	</tiles:insert>

	<tiles:insert definition="paymentCards" service="PaymentList" flush="false">
		<tiles:put name="serviceId" value="PaymentList"/>
		<tiles:put name="action" value="/private/payments/common"/>
        <tiles:putList name="params">
            <tiles:putList name="names"><tiles:add>status</tiles:add></tiles:putList>
            <tiles:putList name="values"><tiles:add>uncompleted</tiles:add></tiles:putList>
        </tiles:putList>
		<tiles:put name="notForm" value="true"/>
		<tiles:put name="image" value="iconPmntList_UncompletedPayments.gif"/>
		<tiles:put name="listPayTitle" value="Незавершенные операции"/>
	</tiles:insert>
</div>
<div style="clear:both;"></div>

<div class="pmntListGroup">
	<div class='pmntListGroupTitle'>Справочники</div>
	<tiles:insert definition="paymentCards" service="PaymentReceiverList" flush="false">
		<tiles:put name="serviceId" value="PaymentReceiverList"/>
		<tiles:put name="action" value="/private/receivers/list"/>
        <tiles:putList name="params">
            <tiles:putList name="names"><tiles:add>kind</tiles:add></tiles:putList>
            <tiles:putList name="values"><tiles:add>PJ</tiles:add></tiles:putList>
        </tiles:putList>
		<tiles:put name="listPayTitle" value="Справочник получателей"/>
	</tiles:insert>

	<tiles:insert definition="paymentCards" service="BankList" flush="false">
		<tiles:put name="serviceId" value="BankList"/>
		<tiles:put name="action" value="/private/dictionary/banks/national"/>
		<tiles:put name="listPayTitle" value="Справочник банков"/>
	</tiles:insert>

	<tiles:insert definition="paymentCards" service="ContactReceiverList" flush="false">
		<tiles:put name="serviceId" value="ContactReceiverList"/>
		<tiles:put name="action" value="/private/contact/receivers/list"/>
        <tiles:putList name="params">
            <tiles:putList name="names"><tiles:add>kind</tiles:add></tiles:putList>
            <tiles:putList name="values"><tiles:add>C</tiles:add></tiles:putList>
        </tiles:putList>
		<tiles:put name="listPayTitle" value="Справочник получателей Contact"/>
	</tiles:insert>
</div>
</div>
<script type="text/javascript">
    changeDivSize("workspace");
</script>

</tiles:put>
</tiles:insert>
