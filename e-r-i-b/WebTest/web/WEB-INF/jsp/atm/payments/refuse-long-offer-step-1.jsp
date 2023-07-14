<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
<head>
    <title>Отмена длительного поручения</title>
</head>

<body>
<h1>Отмена длительного поручения: сохранение</h1>

<html:form action="/atm/refuseLongOffer" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="operation" value="save"/>

        <tiles:put name="data">
            <c:set var="initialData" value="${form.response.initialData.refuseLongOffer}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="longOfferType"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="longOfferId"/>
                    </tiles:insert>

                    <c:set var="receiver" value="${initialData.receiver}"/>
                    <c:if test="${not empty receiver.card}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="receiver" beanProperty="card"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty receiver.account}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="receiver" beanProperty="account"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty receiver.loan}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="receiver" beanProperty="loan"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty receiver.loanName}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="receiver" beanProperty="loanName"/>
                        </tiles:insert>
                    </c:if>

                    <c:set var="payment" value="${initialData.payment}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="payment" beanProperty="payResource"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="payment" beanProperty="payResourceName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="payment" beanProperty="amount"/>
                    </tiles:insert>
                    <c:if test="${not empty payment.currency}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="payment" beanProperty="currency"/>
                        </tiles:insert>
                    </c:if>

                    <c:set var="longOfferDetails" value="${initialData.longOfferDetails}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="startDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="endDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="executionEventType"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="office"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="name"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>