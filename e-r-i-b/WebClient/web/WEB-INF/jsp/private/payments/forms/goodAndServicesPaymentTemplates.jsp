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

<c:set var="curentType" value="${GoodsAndServicesPaymentForm.type}"/>
<c:set var="template" value="${GoodsAndServicesPaymentForm.template}"/>

<div class="pmntListGroup">
	<font class='pmntListGroupTitle'><bean:message key="label.payments.services" bundle="commonBundle"/></font>
</div>

<div class="pmntListGroup">
<font class='pmntListGroupTitle'>Оплата товаров и услуг по сети Contact</font>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="cellular-communication-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="inet-connection-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="ip-telephony-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="telephony-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="satellite-connection-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="credit-repayment-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="policy-payment-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="game-portals-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="air-ticket-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="realty-operation-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="inet-shops-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="payment-system-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="hotel-payment-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="travel-agency-contact"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>
</div>
<div class="pmntListGroup">
<font class='pmntListGroupTitle'>Оплата товаров и услуг через платежную систему Rapida</font>
<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="cellular-communication-rapida"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="telephony-rapida"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="ip-telephony-rapida"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="satellite-connection-rapida"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="credit-repayment-rapida"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="payment-system-rapida"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="gkh-payment-rapida"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>

<tiles:insert definition="goodsAndServicesPaymentsPaymentTemplate" service="GoodsAndServicesPayment" flush="false">
	<tiles:put name="serviceId" value="GoodsAndServicesPayment"/>
	<tiles:put name="appointment" value="electric-payment-rapida"/>
    <tiles:putList name="params">
        <tiles:putList name="names">
            <tiles:add>template</tiles:add>
            <tiles:add>type</tiles:add>
        </tiles:putList>
        <tiles:putList name="values">
            <tiles:add>${template}</tiles:add>
            <tiles:add>${curentType}</tiles:add>
        </tiles:putList>
    </tiles:putList>
</tiles:insert>
</div>

</tiles:put>
</tiles:insert>
