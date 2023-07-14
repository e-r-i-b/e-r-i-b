<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:html>
<head>
    <title>Оплата брони авиабилетов</title>
</head>

<body>
<h1>Оплата брони авиабилетов: подтверждение</h1>

<html:form action="/mobileApi/airlineReservationPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <c:set var="document" value="${form.response.document.airlineReservationPaymentDocument}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="documentNumber"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="documentDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="receiverName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
                    </tiles:insert>

                    <c:set var="paymentDetails" value="${document.paymentDetails}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="amount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="currency"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="recIdentifier"/>
                    </tiles:insert>

                    <c:set var="reservationInfo" value="${document.reservationInfo}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="reservationInfo" beanProperty="reservationId"/>
                    </tiles:insert>
                    <c:if test="${not empty reservationInfo.reservExpirationDate}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="reservationInfo" beanProperty="reservExpirationDate"/>
                        </tiles:insert>
                    </c:if>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="reservationInfo" beanProperty="countPassengers"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="reservationInfo" beanProperty="countRoutes"/>
                    </tiles:insert>

                    <c:forEach var="passenger" items="${document.passengersInfo.passenger}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="passenger" beanProperty="firstName"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="passenger" beanProperty="lastName"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="passenger" beanProperty="type"/>
                        </tiles:insert>
                    </c:forEach>

                    <c:forEach var="route" items="${document.routesInfo.route}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="route" beanProperty="departure.flight"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="route" beanProperty="departure.dateTime"/>
                        </tiles:insert>
                        <c:if test="${not empty route.departure.location}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="route" beanProperty="departure.location"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="route" beanProperty="departure.airport"/>
                        </tiles:insert>

                        <c:if test="${not empty route.arrival.flight}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="route" beanProperty="arrival.flight"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="route" beanProperty="arrival.dateTime"/>
                        </tiles:insert>
                        <c:if test="${not empty route.arrival.location}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="route" beanProperty="arrival.location"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="route" beanProperty="arrival.airport"/>
                        </tiles:insert>
                    </c:forEach>

                    <c:set var="ticketsInfo" value="${document.ticketsInfo}"/>
                    <c:if test="${not empty ticketsInfo}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="ticketsInfo" beanProperty="ticketsStatus"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="ticketsInfo" beanProperty="ticketsStatusDescription"/>
                        </tiles:insert>

                        <c:set var="issueInfo" value="${ticketsInfo.issueInfo}"/>
                        <c:if test="${not empty issueInfo}">
                            <c:forEach var="ticketNumber" items="${issueInfo.ticketNumber}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="ticketNumber"/>
                                </tiles:insert>
                            </c:forEach>
                            <c:if test="${not empty issueInfo.itineraryUrl}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="issueInfo" beanProperty="itineraryUrl"/>
                                </tiles:insert>
                            </c:if>
                        </c:if>
                        
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="ticketsInfo" beanProperty="interval"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="ticketsInfo" beanProperty="timeout"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>