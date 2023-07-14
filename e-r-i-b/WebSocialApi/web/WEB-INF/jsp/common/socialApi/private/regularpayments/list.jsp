<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/regularpayments/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="regularPayment" property="regularPayments" model="xml-list" title="regularPayments">
                <c:set var="payment" value="${regularPayment.value}"/>
                <c:set var="isAutoPayment" value="${phiz:isInstance(regularPayment, 'com.rssl.phizic.business.resources.external.AutoPaymentLink')}"/>
                <c:set var="isLongOffer" value="${phiz:isInstance(regularPayment, 'com.rssl.phizic.business.resources.external.LongOfferLink')}"/>
                <c:set var="isAutoSubscription" value="${phiz:isInstance(regularPayment, 'com.rssl.phizic.business.resources.external.AutoSubscriptionLink')}"/>
                <c:set var="activePayment" value="${phiz:contains(form.activePaymentSet, regularPayment)}"/>

                <sl:collectionItem title="regularPayment">
                    <c:choose>
                        <c:when test="${not isAutoSubscription}">
                            <id><bean:write name="regularPayment" property="id" ignore="true"/></id>
                        </c:when>
                        <c:when test="${isAutoSubscription}">
                            <id><bean:write name='payment' property="number" ignore="true"/></id>
                        </c:when>
                    </c:choose>
                    <type>
                        <c:choose>
                            <c:when test="${isAutoPayment}">autoPayment</c:when>
                            <c:when test="${isLongOffer}">longOffer</c:when>
                            <c:when test="${isAutoSubscription}">autoSubscription</c:when>
                        </c:choose>
                    </type>
                    <active>${activePayment}</active>
                    <name><bean:write name="regularPayment" property="name" ignore="true"/></name>
                    <executionEventDescription><bean:write name="regularPayment" property="executionEventType" ignore="true"/></executionEventDescription>
                    <c:set var="amountValue" value="${payment.executionEventType == 'BY_INVOICE' ? payment.floorLimit : payment.amount }"/>
                    <executionEventType><bean:write name="payment" property="executionEventType" ignore="true"/></executionEventType>
                    <c:if test="${not empty amountValue}">
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="amount"/>
                            <tiles:put name="money" beanName="amountValue"/>
                        </tiles:insert>
                    </c:if>

                    <c:choose>
                        <c:when test="${isAutoPayment}">
                            <status><bean:write name="regularPayment" property="statusReport" ignore="true"/></status>
                        </c:when>
                        <c:when test="${isAutoSubscription}">
                            <status><bean:write name="regularPayment" property="autoPayStatusType" ignore="true"/></status>
                        </c:when>
                    </c:choose>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>