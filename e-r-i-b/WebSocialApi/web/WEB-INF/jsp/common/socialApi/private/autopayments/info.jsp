<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/private/autopayments/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="link" value="${form.link}"/>
    <c:set var="payment" value="${link.value}"/>
    <c:set var="cardLink" value="${link.cardLink}"/>
    <c:set var="versionNumber" value="${form.mobileApiVersion}"/>
    <c:set var="since_5_10" value="${versionNumber ge 5.10}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <receiverName><c:out value="${payment.receiverName}"/></receiverName>
            <requisiteName><c:out value="${form.requisiteName}"/></requisiteName>
            <requisite><c:out value="${payment.requisite}"/></requisite>
            <c:choose>
                <c:when test="${since_5_10}">
                    <c:set var="amount" value="${payment.amount}"/>
                    <c:if test="${not empty amount}">
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="amount"/>
                            <tiles:put name="money" beanName="amount"/>
                        </tiles:insert>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:set var="dateAccepted" value="${payment.dateAccepted}"/>
                    <c:if test="${not empty dateAccepted}">
                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="dateAccepted"/>
                            <tiles:put name="calendar" beanName="dateAccepted"/>
                        </tiles:insert>
                    </c:if>
                    <c:set var="floorLimit" value="${payment.floorLimit}"/>
                    <c:if test="${payment.executionEventType == 'BY_INVOICE' || not empty floorLimit}">
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="floorLimit"/>
                            <tiles:put name="money" beanName="floorLimit"/>
                        </tiles:insert>
                    </c:if>
                </c:otherwise>
            </c:choose>
            <c:if test="${not empty cardLink}">
                <paymentResource>${cardLink.code}</paymentResource>
            </c:if>
            <c:if test="${since_5_10}">
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
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="floorLimit"/>
                            <tiles:put name="money" beanName="floorLimit"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${versionNumber ge 5.20 and payment.executionEventType == 'REDUSE_OF_BALANCE'}">
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
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="totalAmountLimit"/>
                                <c:if test="${not empty totalAmountLimit}">
                                    <tiles:put name="money" beanName="totalAmountLimit"/>
                                </c:if>
                            </tiles:insert>
                            <totalAmountPeriod>${totalAmountPeriod}</totalAmountPeriod>
                        </c:if>
                    </c:if>
                    <c:if test="${empty floorLimit and not empty payment.startDate}">
                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="startDate"/>
                            <tiles:put name="calendar" beanName="payment" beanProperty="startDate"/>
                            <tiles:put name="pattern" value="dd"/>
                        </tiles:insert>
                    </c:if>
                    <name><c:out value="${link.name}"/></name>
                </autoPaymentParameters>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>