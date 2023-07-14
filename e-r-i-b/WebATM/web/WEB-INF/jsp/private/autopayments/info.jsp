<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/autopayments/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="link" value="${form.link}"/>
    <c:set var="payment" value="${link.value}"/>
    <c:set var="cardLink" value="${link.cardLink}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <receiverName><c:out value="${payment.receiverName}"/></receiverName>
            <requisiteName><c:out value="${form.requisiteName}"/></requisiteName>
            <requisite><c:out value="${payment.requisite}"/></requisite>
            <c:set var="amount" value="${payment.amount}"/>
            <c:if test="${not empty amount}">
                <tiles:insert definition="atmMoneyType" flush="false">
                    <tiles:put name="name" value="amount"/>
                    <tiles:put name="money" beanName="amount"/>
                </tiles:insert>
            </c:if>
            <c:if test="${not empty cardLink}">
                <paymentResource>${cardLink.code}</paymentResource>
                <paymentProduct>
                    <cards>
                        <card>
                            <c:set var="card" value="${cardLink.card}"/>
                            <id>${cardLink.id}</id>
                            <code>${cardLink.code}</code>
                            <name><c:out value="${phiz:getCardUserName(cardLink)}"/></name>
                            <c:if test="${not empty cardLink.description}">
                                <description>${cardLink.description}</description>
                            </c:if>
                            <c:if test="${not empty cardLink.number}">
                                <number>${phiz:getCutCardNumber(cardLink.number)}</number>
                            </c:if>
                            <isMain>${cardLink.main}</isMain>
                            <type>${card.cardType}</type>
                            <c:if test="${not empty card.availableLimit}">
                                <tiles:insert definition="atmMoneyType" flush="false">
                                    <tiles:put name="name" value="availableLimit" />
                                    <tiles:put name="money" beanName="card" beanProperty="availableLimit"/>
                                </tiles:insert>
                            </c:if>
                            <state>${card.cardState}</state>
                        </card>
                    </cards>
                </paymentProduct>
            </c:if>
            <autoPaymentParameters>
                <c:if test="${not empty payment.reportStatus}">
                    <status>${payment.reportStatus}</status>
                </c:if>
                <c:if test="${not empty link.executionEventType}">
                    <executionEventDescription>${link.executionEventType}</executionEventDescription>
                </c:if>
                <c:if test="${not empty payment.executionEventType}">
                    <executionEventType>${payment.executionEventType}</executionEventType>
                </c:if>
                <c:set var="floorLimit" value="${payment.floorLimit}"/>
                <c:if test="${not empty floorLimit and payment.executionEventType == 'BY_INVOICE'}">
                    <tiles:insert definition="atmMoneyType" flush="false">
                        <tiles:put name="name" value="floorLimit"/>
                        <tiles:put name="money" beanName="floorLimit"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${payment.executionEventType == 'REDUSE_OF_BALANCE'}">
                    <c:choose>
                        <c:when test="${payment.totalAmountPeriod != null}">
                            <c:set var="totalAmountPeriod" value="${payment.totalAmountPeriod}"/>
                            <c:set var="totalAmountLimit"  value="${payment.totalAmountLimit}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="totalAmountPeriod" value="${phiz:getProviderAutoPayTotalAmountPeriod(link)}"/>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${not empty totalAmountPeriod}">
                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="totalAmountLimit"/>
                            <c:if test="${not empty totalAmountLimit}">
                                <tiles:put name="money" beanName="totalAmountLimit"/>
                            </c:if>
                        </tiles:insert>
                        <totalAmountPeriod>${totalAmountPeriod}</totalAmountPeriod>
                    </c:if>
                </c:if>
                <c:if test="${empty floorLimit and not empty payment.startDate}">
                    <tiles:insert definition="atmDateTimeType" flush="false">
                        <tiles:put name="name" value="startDate"/>
                        <tiles:put name="calendar" beanName="payment" beanProperty="startDate"/>
                        <tiles:put name="pattern" value="dd"/>
                    </tiles:insert>
                </c:if>
                <name><c:out value="${link.name}"/></name>
            </autoPaymentParameters>
        </tiles:put>
    </tiles:insert>
</html:form>