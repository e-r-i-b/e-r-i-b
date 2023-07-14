<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/longoffers/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="link" value="${form.longOfferLink}"/>
    <c:set var="longOffer" value="${link.value}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty longOffer.type}">
                <operationType><c:out value="${longOffer.type.simpleName}"/></operationType>
            </c:if>
            <documentNumber>${link.number}</documentNumber>
            <c:if test="${not empty link.receiverAccount || not empty link.receiverCard}">
                <receiver>
                    <c:if test="${not empty link.receiverName}">
                        <name><c:out value="${link.receiverName}"/></name>
                    </c:if>
                    <c:choose>
                        <c:when test="${not empty link.receiverCard}"><card>${phiz:getCutCardNumber(link.receiverCard)}</card></c:when>
                        <c:otherwise><account>${link.receiverAccount}</account></c:otherwise>
                    </c:choose>
                </receiver>
            </c:if>

            <payment>
                <c:if test="${not empty form.payerResourceLink}">
                    <resource>${form.payerResourceLink.code}</resource>
                </c:if>
                <c:if test="${not empty longOffer.percent || not empty longOffer.amount}">
                    <c:choose>
                        <c:when test="${not empty longOffer.percent}">
                            <percent>${longOffer.percent}</percent>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="amount"/>
                                <tiles:put name="money" beanName="longOffer" beanProperty="amount"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </payment>
            <longOfferParameters>
                <c:if test="${not empty longOffer.startDate}">
                    <tiles:insert definition="mobileDateType" flush="false">
                        <tiles:put name="name" value="startDate"/>
                        <tiles:put name="calendar" beanName="longOffer" beanProperty="startDate"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty longOffer.endDate}">
                    <tiles:insert definition="mobileDateType" flush="false">
                        <tiles:put name="name" value="endDate"/>
                        <tiles:put name="calendar" beanName="longOffer" beanProperty="endDate"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty link.executionEventType}">
                    <executionEventDescription>${link.executionEventType}</executionEventDescription>
                </c:if>
                <c:if test="${not empty longOffer.executionEventType}">
                    <executionEventType>${longOffer.executionEventType}</executionEventType>
                </c:if>
                <c:if test="${not empty longOffer.priority}">
                    <priority>${longOffer.priority}</priority>
                </c:if>
                <c:if test="${not empty longOffer.office}">
                <office><c:out value="${longOffer.office.name}"/></office>
                </c:if>
                <name><c:out value="${link.name}"/></name>
            </longOfferParameters>
        </tiles:put>
    </tiles:insert>
</html:form>