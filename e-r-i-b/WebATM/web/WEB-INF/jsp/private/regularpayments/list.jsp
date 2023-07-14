<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/regularpayments/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:set var="regularPayments"    value="${form.regularPayments}"/>
            <c:set var="p2pRegularPayments" value="${form.p2pRegularPayments}"/>

            <c:if test="${not empty regularPayments or not empty p2pRegularPayments}">
            <regularPayments>
            <c:forEach var="regularPayment" items="${regularPayments}">
                <c:set var="payment"            value="${regularPayment.value}"/>
                <c:set var="isAutoPayment"      value="${phiz:isInstance(regularPayment, 'com.rssl.phizic.business.resources.external.AutoPaymentLink')}"/>
                <c:set var="isLongOffer"        value="${phiz:isInstance(regularPayment, 'com.rssl.phizic.business.resources.external.LongOfferLink')}"/>
                <c:set var="isAutoSubscription" value="${phiz:isInstance(regularPayment, 'com.rssl.phizic.business.resources.external.AutoSubscriptionLink')}"/>
                <c:set var="activePayment"      value="${phiz:contains(form.activePaymentSet, regularPayment)}"/>

                <regularPayment>
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
                            <c:when test="${isAutoSubscription}">
                                <c:set var="type" value="${payment.type.simpleName}"/>
                                <c:choose>
                                    <c:when test="${type == 'ExternalCardsTransferToOurBankLongOffer' || type == 'InternalCardsTransferLongOffer' || type == 'ExternalCardsTransferToOtherBankLongOffer'}">
                                        autoTransfer
                                    </c:when>
                                    <c:when test="${type == 'CardPaymentSystemPaymentLongOffer'}">
                                        autoSubscription
                                    </c:when>
                                </c:choose>
                            </c:when>
                        </c:choose>
                    </type>
                    <active>${activePayment}</active>
                    <name><bean:write name="regularPayment" property="name" ignore="true"/></name>
                    <c:if test="${not empty regularPayment.executionEventType}">
                        <executionEventDescription><bean:write name="regularPayment" property="executionEventType" ignore="true"/></executionEventDescription>
                    </c:if>
                    <c:if test="${not empty payment.executionEventType}">
                        <executionEventType><bean:write name="payment" property="executionEventType" ignore="true"/></executionEventType>
                    </c:if>
                    <c:set var="amountValue" value="${payment.executionEventType == 'BY_INVOICE' ? payment.floorLimit : payment.amount }"/>
                    <c:if test="${not empty amountValue}">
                        <tiles:insert definition="atmMoneyType" flush="false">
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
                </regularPayment>
            </c:forEach>

            <c:forEach var="regularPayment" items="${p2pRegularPayments}">
                <c:set var="payment"            value="${regularPayment.value}"/>
                <c:set var="isAutoPayment"      value="${phiz:isInstance(regularPayment, 'com.rssl.phizic.business.resources.external.AutoPaymentLink')}"/>
                <c:set var="isLongOffer"        value="${phiz:isInstance(regularPayment, 'com.rssl.phizic.business.resources.external.LongOfferLink')}"/>
                <c:set var="isAutoSubscription" value="${phiz:isInstance(regularPayment, 'com.rssl.phizic.business.resources.external.AutoSubscriptionLink')}"/>
                <c:set var="activePayment"      value="${phiz:contains(form.activePaymentSet, regularPayment)}"/>

                <regularPayment>
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
                            <c:when test="${isAutoSubscription}">
                                <c:set var="type" value="${payment.type.simpleName}"/>
                                <c:choose>
                                    <c:when test="${type == 'ExternalCardsTransferToOurBankLongOffer' || type == 'InternalCardsTransferLongOffer' || type == 'ExternalCardsTransferToOtherBankLongOffer'}">
                                        autoTransfer
                                    </c:when>
                                    <c:when test="${type == 'CardPaymentSystemPaymentLongOffer'}">
                                        autoSubscription
                                    </c:when>
                                </c:choose>
                            </c:when>
                        </c:choose>
                    </type>
                    <active>${activePayment}</active>
                    <name><bean:write name="regularPayment" property="name" ignore="true"/></name>
                    <c:if test="${not empty regularPayment.executionEventType}">
                        <executionEventDescription><bean:write name="regularPayment" property="executionEventType" ignore="true"/></executionEventDescription>
                    </c:if>
                    <c:if test="${not empty payment.executionEventType}">
                        <executionEventType><bean:write name="payment" property="executionEventType" ignore="true"/></executionEventType>
                    </c:if>
                    <c:set var="amountValue" value="${payment.executionEventType == 'BY_INVOICE' ? payment.floorLimit : payment.amount }"/>
                    <c:if test="${not empty amountValue}">
                        <tiles:insert definition="atmMoneyType" flush="false">
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
                </regularPayment>
            </c:forEach>
            </regularPayments>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>