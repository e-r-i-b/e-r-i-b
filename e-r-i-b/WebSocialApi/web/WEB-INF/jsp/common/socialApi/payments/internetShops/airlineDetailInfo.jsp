<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/private/payments/internetShops/detailInfo">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="airline" property="airlineInfo" model="xml-list" title="routeList">
                <sl:collectionItem title="route">
                    <departure>
                        <c:if test="${not empty airline.departureLocation}">
                            <location><c:out value="${airline.departureLocation}"/></location>
                        </c:if>
                        <airport><c:out value="${airline.departureAirport}"/></airport>
                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="dateTime"/>
                            <tiles:put name="calendar" beanName="airline" beanProperty="departureDate"/>
                        </tiles:insert>
                    </departure>
                    <arrival>
                        <c:if test="${not empty airline.arrivalLocation}">
                            <location><c:out value="${airline.arrivalLocation}"/></location>
                        </c:if>
                        <airport><c:out value="${airline.arrivalAirport}"/></airport>
                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="dateTime"/>
                            <tiles:put name="calendar" beanName="airline" beanProperty="arrivalDate"/>
                        </tiles:insert>
                    </arrival>
                    <c:if test="${not empty airline.ticketCount}">
                        <ticketCount>${airline.ticketCount}</ticketCount>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>