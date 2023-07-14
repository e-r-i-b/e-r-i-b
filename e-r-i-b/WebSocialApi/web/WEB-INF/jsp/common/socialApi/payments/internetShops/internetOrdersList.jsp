<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/private/payments/internetShops/orderList">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="internetOrder" property="data" model="xml-list" title="orderList">
                <c:set var="internetOrderId" value="${internetOrder[0]}"/>
                <c:set var="orderDate" value="${internetOrder[1]}"/>
                <c:set var="receiverName" value="${internetOrder[2]}"/>
                <c:set var="orderCode" value="${internetOrder[3]}"/>
                <c:set var="orderAmount" value="${internetOrder[4]}"/>
                <c:set var="orderCurrency" value="${internetOrder[5]}"/>
                <c:set var="uuid" value="${internetOrder[6]}"/>
                <c:set var="paymentId" value="${internetOrder[7]}"/>
                <c:set var="formName" value="${internetOrder[8]}"/>
                <c:set var="stateCode" value="${internetOrder[9]}"/>
                <sl:collectionItem title="order">
                    <orderId>${internetOrderId}</orderId>
                    <tiles:insert definition="mobileDateTimeType" flush="false">
                        <tiles:put name="name" value="date"/>
                        <tiles:put name="calendar" beanName="orderDate"/>
                    </tiles:insert>
                    <provider><c:out value="${receiverName}"/></provider>
                    <orderCode><c:out value="${orderCode}"/></orderCode>
                    <tiles:insert definition="mobileMoneyType" flush="false">
                        <tiles:put name="name" value="amount"/>
                        <tiles:put name="decimal" value="${orderAmount}"/>
                        <tiles:put name="currencyCode" value="${orderCurrency}"/>
                    </tiles:insert>
                    <orderState>
                        <c:choose>
                            <c:when test="${stateCode == 'INITIAL' or stateCode == 'WAIT_CONFIRM'}">SAVED</c:when>
                            <c:when test="${stateCode == 'REFUSED'}">CANCELED</c:when>
                            <c:when test="${stateCode == 'UNKNOW' or stateCode == 'SENT' or stateCode == 'OFFLINE_SAVED' or stateCode == 'ERROR'}">DISPATCHED</c:when>
                            <c:otherwise>${stateCode}</c:otherwise>
                        </c:choose>
                    </orderState>
                    <uuid><c:out value="${uuid}"/></uuid>
                    <formName>${formName}</formName>
                    <c:if test="${not empty paymentId}">
                        <payment>
                            <paymentId>${paymentId}</paymentId>
                            <paymentState>${stateCode}</paymentState>
                        </payment>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>