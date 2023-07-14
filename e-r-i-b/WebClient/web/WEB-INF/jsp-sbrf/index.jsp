<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="fromResource" value="${param['fromResource']}"/>
<html:form action="/private/payments">
<tiles:insert definition="accountInfo">

  <tiles:put name="mainmenu" value="Payments"/>

  <!--История операций всех платежей-->
    <tiles:put name="data" type="string">
        <%--<c:if test="${phiz:impliesService('InternalPayment') or phiz:impliesService('RurPayment') or phiz:impliesService('RurPayJurSB')}">
        <div style="height:auto;">
        <font class="listPayPart">Платежи</font>
        <tiles:insert definition="paymentsPaymentCards" service="InternalPayment" flush="false">
            <tiles:put name="serviceId" value="InternalPayment"/>
        </tiles:insert>
        <tiles:insert definition="paymentsPaymentCards" service="RurPayment" flush="false">
            <tiles:put name="serviceId" value="RurPayment"/>
            <tiles:put name="image" value="RurPaymentPh"/>
        </tiles:insert>
        </div>
        </c:if>
        <c:if test="${phiz:impliesService('PurchaseCurrencyPayment') or phiz:impliesService('SaleCurrencyPayment') or phiz:impliesService('ConvertCurrencyPayment')}">
        <div style="height:auto;">
        <font class="listPayPart">Обмен валюты</font>
        <tiles:insert definition="paymentsPaymentCards" service="PurchaseCurrencyPayment" flush="false">
            <tiles:put name="serviceId" value="PurchaseCurrencyPayment"/>
            <tiles:put name="image" value="PCPayment"/>
        </tiles:insert>
        <tiles:insert definition="paymentsPaymentCards" service="SaleCurrencyPayment" flush="false">
            <tiles:put name="serviceId" value="SaleCurrencyPayment"/>
            <tiles:put name="image" value="SCPayment"/>
        </tiles:insert>
        <tiles:insert definition="paymentsPaymentCards" service="ConvertCurrencyPayment" flush="false">
            <tiles:put name="serviceId" value="ConvertCurrencyPayment"/>
            <tiles:put name="image" value="CCPayment"/>
        </tiles:insert>
        </div>
        </c:if>
        <c:if test="${phiz:impliesService('PaymentDocumentPreparation')}">
        <div style="height:auto;">
        <font class="listPayPart">Печать платежного документа</font>
        <tiles:insert definition="paymentCards" service="PaymentDocumentPreparation" flush="false">
            <tiles:put name="serviceId" value="PaymentDocumentPreparation"/>
            <tiles:put name="notForm" value="true"/>
            <tiles:put name="params" value="form=ConvertCurrencyPayment"/>
            <tiles:put name="action" value="/private/PD4"/>
            <tiles:put name="image" value="PD4"/>
            <tiles:put name="listPayTitle" value="Форма ПД-4 и ПД-4сб (налог)"/>
            <tiles:put name="description" value="Подготовка платежных извещений."/>
        </tiles:insert>
        <tiles:insert definition="paymentCards" service="PaymentDocumentPreparation" flush="false">
            <tiles:put name="serviceId" value="PaymentDocumentPreparation"/>
            <tiles:put name="params" value="page=pay&form=ConvertCurrencyPayment"/>
            <tiles:put name="notForm" value="true"/>
            <tiles:put name="action" value="/private/PD4"/>
            <tiles:put name="image" value="PP"/>
            <tiles:put name="listPayTitle" value="Платежное поручение"/>
            <tiles:put name="description" value="Подготовка платежных извещений."/>
        </tiles:insert>
        </div>
        </c:if>
        <c:if test="${phiz:impliesService('LossPassbookApplication') or phiz:impliesService('AccountOpeningClaim') or phiz:impliesService('AccountClosingClaim') or phiz:impliesService('BlockingCardClaim')}">
        <div style="height:auto;">
        <font class="listPayPart">Заявки</font>
        <tiles:insert definition="paymentsPaymentCards" service="LossPassbookApplication" flush="false">
            <tiles:put name="serviceId" value="LossPassbookApplication"/>
            <tiles:put name="image" value="LossPassApp"/>
        </tiles:insert>
        <tiles:insert definition="paymentsPaymentCards" service="BlockingCardClaim" flush="false">
            <tiles:put name="serviceId" value="BlockingCardClaim"/>
            <tiles:put name="image" value="imagesSevB/iconPmnt_BlockingCardClaim"/>
        </tiles:insert>
        </div>
        </c:if>
        <c:if test="${phiz:impliesService('PaymentList')}">
        <div style="height:auto;">
        <font class="listPayPart">Журнал операций</font>
        <tiles:insert definition="paymentCards" service="PaymentList" flush="false">
            <tiles:put name="serviceId" value="PaymentList"/>
            <tiles:put name="params" value="status=all"/>
            <tiles:put name="notForm" value="true"/>
            <tiles:put name="image" value="AllPayments"/>
            <tiles:put name="listPayTitle" value="Все платежи"/>
            <tiles:put name="action" value="/private/payments/common"/>
        </tiles:insert>
        <tiles:insert definition="paymentCards" service="PaymentList" flush="false">
            <tiles:put name="serviceId" value="PaymentList"/>
            <tiles:put name="params" value="status=uncompleted"/>
            <tiles:put name="notForm" value="true"/>
            <tiles:put name="image" value="UncompletedPayments"/>
            <tiles:put name="listPayTitle" value="Незавершенные платежи"/>
            <tiles:put name="action" value="/private/payments/common"/>
        </tiles:insert>
        </div>
        </c:if>--%>
        <bean:include id="favourite" page="/private/favourite/list.do?option=Start"/>
       ${favourite}

        <jsp:include page="/private/payments/servicesPayments.do">
            <jsp:param name="fromResource" value="${fromResource}"/>
       </jsp:include>
    </tiles:put>
</tiles:insert>
</html:form>
