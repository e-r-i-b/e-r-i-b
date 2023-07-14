<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<%@ taglib prefix="html"  uri="http://jakarta.apache.org/struts/tags-html"  %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean"  uri="http://jakarta.apache.org/struts/tags-bean"  %>
<%@ taglib prefix="phiz"  uri="http://rssl.com/tags"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/basket/subscriptions/list">
    <c:set var="form"                     value="${phiz:currentForm(pageContext)}"/>
    <c:set var="images"                   value="${form.imageIds}"/>
    <c:set var="autoSubscriptions"        value="${form.autoSubscriptions}"/>
    <c:set var="activeSubscriptions"      value="${form.activeSubscriptions}"/>
    <c:set var="stoppedSubscriptions"     value="${form.stoppedSubscriptions}"/>
    <c:set var="recommendedSubscriptions" value="${form.recommendedSubscriptions}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <invoiceSubscriptions>
            <c:forEach var="subscriptionsMap" items="${activeSubscriptions}">
                <c:set var="accountingId"      value="${subscriptionsMap.key}"/>
                <c:set var="listSubscriptions" value="${subscriptionsMap.value}"/>

                <c:forEach var="subscription" items="${listSubscriptions}">
                    <c:set var="subscriptionId">${subscription.id}</c:set>
                    <invoiceSubscription>
                        <id>${subscriptionId}</id>                   <%-- идентификатор --%>
                        <name>${subscription.name}</name>            <%-- название услуги --%>
                        <state>${subscription.state}</state>         <%-- текущее состояние услуги --%>
                        <provider>${subscription.recName}</provider> <%-- наименование поставщика --%>
                        <creationType>${subscription.creationType}</creationType>

                        <tiles:insert definition="imageType" flush="false">
                            <tiles:put name="id"   value="${images[subscriptionId]}"/>
                            <tiles:put name="url"  value="/logotips/IQWave/IQWave-other.jpg"/>
                            <tiles:put name="name" value="imageId"/>
                        </tiles:insert>

                        <c:if test="${not empty accountingId and not(accountingId eq 'none')}">
                        <accountingEntityId>${accountingId}</accountingEntityId>
                        </c:if>

                        <c:if test="${not empty subscription.delayDate}">
                            <tiles:insert definition="mobileDateType" flush="false">
                                <tiles:put name="name"     value="delayDate"/>
                                <tiles:put name="calendar" beanName="subscription" beanProperty="delayDate"/>
                            </tiles:insert>
                        </c:if>

                        <%-- количество неоплаченных счетов --%>
                        <c:if test="${not empty subscription.numberOfNotPaidInvoices and subscription.numberOfNotPaidInvoices > 0}">
                        <numberOfNotPaidInvoices>${subscription.numberOfNotPaidInvoices}</numberOfNotPaidInvoices>
                        </c:if>
                        <%-- количество отложенных счетов --%>
                        <c:if test="${not empty subscription.numberOfDelayedInvoices and subscription.numberOfDelayedInvoices > 0}">
                        <numberOfDelayedInvoices>${subscription.numberOfDelayedInvoices}</numberOfDelayedInvoices>
                        </c:if>

                        <c:if test="${not empty subscription.errorType}">
                        <errorType>${subscription.errorType}</errorType>
                        </c:if>

                        <c:if test="${(not empty subscription.errorInfo or not empty subscription.errorInfo.text) and not(subscription.errorInfo.type == 'LIST')}">
                        <statusMessage>${subscription.errorInfo.text}</statusMessage>
                        </c:if>

                        <isRecoverAutoSubscription>
                            <c:out value="${not empty subscription.autoSubExternalId}"/>
                        </isRecoverAutoSubscription>
                    </invoiceSubscription>
                </c:forEach>
            </c:forEach>

            <c:forEach var="subscriptionsMap" items="${stoppedSubscriptions}">
                <c:set var="accountingId"      value="${subscriptionsMap.key}"/>
                <c:set var="listSubscriptions" value="${subscriptionsMap.value}"/>

                <c:forEach var="subscription" items="${listSubscriptions}">
                    <c:set var="subscriptionId">${subscription.id}</c:set>

                    <invoiceSubscription>
                        <id>${subscriptionId}</id>
                        <name>${subscription.name}</name>
                        <state>${subscription.state}</state>
                        <provider>${subscription.recName}</provider>
                        <creationType>${subscription.creationType}</creationType>

                        <tiles:insert definition="imageType" flush="false">
                            <tiles:put name="id"   value="${images[subscriptionId]}"/>
                            <tiles:put name="url"  value="/logotips/IQWave/IQWave-other.jpg"/>
                            <tiles:put name="name" value="imageId"/>
                        </tiles:insert>

                        <c:if test="${not empty accountingId and not(accountingId eq 'none')}">
                        <accountingEntityId>${accountingId}</accountingEntityId>
                        </c:if>

                        <c:if test="${not empty subscription.numberOfNotPaidInvoices and subscription.numberOfNotPaidInvoices > 0}">
                        <numberOfNotPaidInvoices>${subscription.numberOfNotPaidInvoices}</numberOfNotPaidInvoices>
                        </c:if>

                        <c:if test="${not empty subscription.numberOfDelayedInvoices and subscription.numberOfDelayedInvoices > 0}">
                        <numberOfDelayedInvoices>${subscription.numberOfDelayedInvoices}</numberOfDelayedInvoices>
                        </c:if>

                        <c:if test="${not empty subscription.documentStatus}">
                        <documentStatus>${subscription.documentStatus}</documentStatus>
                        </c:if>

                        <c:if test="${not empty subscription.errorType}">
                        <errorType>${subscription.errorType}</errorType>
                        </c:if>

                        <c:if test="${(not empty subscription.errorInfo or not empty subscription.errorInfo.text) and not(subscription.errorInfo.type == 'LIST')}">
                        <statusMessage>${subscription.errorInfo.text}</statusMessage>
                        </c:if>

                        <isRecoverAutoSubscription>
                            <c:out value="${not empty subscription.autoSubExternalId}"/>
                        </isRecoverAutoSubscription>
                    </invoiceSubscription>
                </c:forEach>
            </c:forEach>

            <c:forEach var="subscriptionsMap" items="${recommendedSubscriptions}">
                <c:set var="accountingId"      value="${subscriptionsMap.key}"/>
                <c:set var="listSubscriptions" value="${subscriptionsMap.value}"/>

                <c:forEach var="subscription" items="${listSubscriptions}">
                    <c:set var="subscriptionId">${subscription.id}</c:set>

                    <invoiceSubscription>
                        <id>${subscriptionId}</id>
                        <name>${subscription.name}</name>
                        <state>${subscription.state}</state>
                        <provider>${subscription.recName}</provider>
                        <creationType>${subscription.creationType}</creationType>

                        <tiles:insert definition="imageType" flush="false">
                            <tiles:put name="id"   value="${images[subscriptionId]}"/>
                            <tiles:put name="url"  value="/logotips/IQWave/IQWave-other.jpg"/>
                            <tiles:put name="name" value="imageId"/>
                        </tiles:insert>

                        <c:if test="${not empty accountingId and not(accountingId eq 'none')}">
                        <accountingEntityId>${accountingId}</accountingEntityId>
                        </c:if>

                        <c:if test="${not empty subscription.numberOfNotPaidInvoices and subscription.numberOfNotPaidInvoices > 0}">
                        <numberOfNotPaidInvoices>${subscription.numberOfNotPaidInvoices}</numberOfNotPaidInvoices>
                        </c:if>

                        <c:if test="${not empty subscription.numberOfDelayedInvoices and subscription.numberOfDelayedInvoices > 0}">
                        <numberOfDelayedInvoices>${subscription.numberOfDelayedInvoices}</numberOfDelayedInvoices>
                        </c:if>

                        <isRecoverAutoSubscription>
                            <c:out value="${not empty subscription.autoSubExternalId}"/>
                        </isRecoverAutoSubscription>
                    </invoiceSubscription>
                </c:forEach>
            </c:forEach>

            <c:forEach var="subscription" items="${autoSubscriptions}">
                <invoiceSubscription>
                    <id>${subscription.autoSubscriptionInfo.number}</id>
                    <name>${subscription.value.friendlyName}</name>
                    <state>AUTO_SUBSCRIPTION</state>
                    <provider>${subscription.serviceProvider.name}</provider>

                    <tiles:insert definition="imageType" flush="false">
                        <tiles:put name="id"   value="${subscription.serviceProvider.imageId}"/>
                        <tiles:put name="url"  value="/logotips/IQWave/IQWave-other.jpg"/>
                        <tiles:put name="name" value="imageId"/>
                    </tiles:insert>

                    <isP2PSubscription><c:out value="${phiz:isInstance(subscription.autoSubscriptionInfo.type, 'com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer') ||
                                                       phiz:isInstance(subscription.autoSubscriptionInfo.type, 'com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer') ||
                                                       phiz:isInstance(subscription.autoSubscriptionInfo.type, 'com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer')}"/></isP2PSubscription>
                </invoiceSubscription>
            </c:forEach>
            </invoiceSubscriptions>
        </tiles:put>
    </tiles:insert>
</html:form>
