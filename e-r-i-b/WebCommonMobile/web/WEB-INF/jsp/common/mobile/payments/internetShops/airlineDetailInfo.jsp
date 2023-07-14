<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html"  uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz"  uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl"    uri="http://struts.application-servers.com/layout" %>

<html:form action="/private/payments/internetShops/detailInfo">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.airlinesInfo}">
                <routeList>
                    <c:forEach var="route" items="${form.airlinesInfo}">
                    <route>
                        <departure>
                            <c:if test="${not empty route.departureLocation}">
                                <location><c:out value="${route.departureLocation}"/></location>
                            </c:if>
                            <airport><c:out value="${route.departureAirport}"/></airport>
                            <dateTime><c:out value="${route.departureDate}"/></dateTime>
                        </departure>
                        <arrival>
                            <c:if test="${not empty route.arrivalLocation}">
                            <location><c:out value="${route.arrivalLocation}"/></location>
                            </c:if>
                            <airport><c:out value="${route.arrivalAirport}"/></airport>
                            <dateTime><c:out value="${route.arrivalDate}"/></dateTime>
                        </arrival>
                        <c:if test="${not empty route.ticketCount}">
                        <ticketCount>${route.ticketCount}</ticketCount>
                        </c:if>
                    </route>
                    </c:forEach>
                </routeList>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>