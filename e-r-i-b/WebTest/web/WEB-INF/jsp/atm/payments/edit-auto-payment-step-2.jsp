<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
<head>
    <title>Редактирование автоплатежа (iqwave)</title>
</head>

<body>
<h1>Редактирование автоплатежа (iqwave): подтверждение</h1>

<html:form action="/atm/editAutoPaymentPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <c:set var="document" value="${form.response.document.editAutoPaymentDocument}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="receiverName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="requisiteName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="requisite"/>
                    </tiles:insert>
                    <c:if test="${not empty document.amount}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="amount"/>
                        </tiles:insert>
                    </c:if>

                    <c:set var="autoPaymentParameters" value="${document.autoPaymentParameters}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="executionEventType"/>
                    </tiles:insert>
                    <c:choose>
                        <c:when test="${not empty autoPaymentParameters.periodic}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="periodic.autoPaymentStartDate"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="periodic.firstPaymentDate"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${not empty autoPaymentParameters.reduseOfBalance}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="reduseOfBalance.autoPaymentFloorLimit"/>
                            </tiles:insert>
                            <c:if test="${autoPaymentParameters.reduseOfBalance.autoPaymentTotalAmountLimit != null}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="reduseOfBalance.autoPaymentTotalAmountLimit"/>
                                </tiles:insert>
                            </c:if>
                        </c:when>
                        <c:when test="${not empty autoPaymentParameters.byInvoice}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="byInvoice.autoPaymentFloorLimit"/>
                            </tiles:insert>
                        </c:when>
                    </c:choose>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="autoPaymentName"/>
                    </tiles:insert>
                 </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>