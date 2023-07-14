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
<h1>Оплата брони авиабилетов: сохранение</h1>

<html:form action="/mobileApi/airlineReservationPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/internetShops/payment.do"/>
        <tiles:put name="operation" value="save"/>

        <tiles:put name="data">
            <table>
                <tr><td>orderUuid</td>    <td><html:text property="orderUuid" size="35" readonly="true"/></td></tr>
            </table>
            <c:set var="initialData" value="${form.response.initialData.airlineReservationPayment}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="documentNumber"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="documentDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="receiverName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
                    </tiles:insert>

                    <c:set var="paymentDetails" value="${initialData.paymentDetails}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="amount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="currency"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="recIdentifier"/>
                    </tiles:insert>

                    <c:set var="reservationInfo" value="${initialData.reservationInfo}"/>
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

                    <c:forEach var="passenger" items="${initialData.passengersInfo.passenger}">
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

                    <c:forEach var="route" items="${initialData.routesInfo.route}">
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
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>