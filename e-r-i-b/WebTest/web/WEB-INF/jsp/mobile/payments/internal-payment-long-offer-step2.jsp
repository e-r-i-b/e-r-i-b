<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Создание автоплатежа перевода между своими счетами</title>
</head>

<body>
<h1>Создание автоплатежа перевода между своими счетами: подтверждение</h1>

<html:form action="/mobileApi/internalPaymentLongOffer" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <c:set var="document" value="${form.response.document.internalPaymentLongOfferDocument}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="documentNumber"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="documentDate"/>
                    </tiles:insert>

                    <c:set var="paymentDetails" value="${document.paymentDetails}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="fromResource"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="toResource"/>
                    </tiles:insert>
                    <c:if test="${not empty paymentDetails.sellAmount}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="paymentDetails" beanProperty="sellAmount"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty paymentDetails.percent}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="paymentDetails" beanProperty="percent"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty paymentDetails.buyAmount}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="paymentDetails" beanProperty="buyAmount"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty paymentDetails.commission}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="paymentDetails" beanProperty="commission"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty paymentDetails.operationCode}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="paymentDetails" beanProperty="operationCode"/>
                        </tiles:insert>
                    </c:if>

                    <c:set var="longOfferDetails" value="${document.longOfferDetails}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="startDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="endDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="eventType"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="firstPaymentDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="priority"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="sumType"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="isSumModify"/>
                    </tiles:insert>
                    <c:if test="${not empty longOfferDetails.payDayDescription}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="longOfferDetails" beanProperty="payDayDescription"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>