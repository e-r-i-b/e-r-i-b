<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Отмена автоплатежа</title>
</head>

<body>
<h1>Отмена автоплатежа</h1>

<html:form action="/mobileApi/refuseAutoPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <html:hidden property="linkId"/>
            <c:set var="document" value="${form.response.document.refuseAutoPaymentDocument}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="card"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="receiverName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="requisite"/>
                    </tiles:insert>
                    <c:if test="${not empty document.amount}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="amount"/>
                        </tiles:insert>
                    </c:if>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="executionEventType"/>
                    </tiles:insert>
                    <c:if test="${not empty document.autoPaymentFloorLimit}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="autoPaymentFloorLimit"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty document.autoPaymentFloorCurrency}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="autoPaymentFloorCurrency"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty document.autoPaymentTotalAmountLimit}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="autoPaymentTotalAmountLimit"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty document.autoPaymentTotalAmountCurrency}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="autoPaymentTotalAmountCurrency"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty document.autoPaymentStartDate}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="autoPaymentStartDate"/>
                        </tiles:insert>
                    </c:if>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="autoPaymentName"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>