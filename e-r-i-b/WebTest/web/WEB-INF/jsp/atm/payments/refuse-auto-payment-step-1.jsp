<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Отмена автоплатежа</title>
</head>

<body>
<h1>Отмена автоплатежа</h1>

<html:form action="/atm/refuseAutoPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <c:if test="${not empty form.response and not empty form.response.initialData}">
                 <c:set var="initialData" value="${form.response.initialData.refuseAutoPaymentPayment}"/>
                 <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                    <tiles:insert page="fields-table.jsp" flush="false">
                        <tiles:put name="data">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="cardId"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="card"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="receiverName"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="requisite"/>
                            </tiles:insert>
                            <c:if test="${not empty initialData.amount}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="amount"/>
                                </tiles:insert>
                            </c:if>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="executionEventType"/>
                            </tiles:insert>
                            <c:if test="${not empty initialData.autoPaymentFloorLimit}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="autoPaymentFloorLimit"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty initialData.autoPaymentFloorCurrency}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="autoPaymentFloorCurrency"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty initialData.autoPaymentTotalAmountLimit}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="autoPaymentTotalAmountLimit"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty initialData.autoPaymentTotalAmountCurrency}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="autoPaymentTotalAmountCurrency"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty initialData.autoPaymentStartDate}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="autoPaymentStartDate"/>
                                </tiles:insert>
                            </c:if>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="autoPaymentName"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </c:if>
            <html:hidden property="linkId"/>
            <tiles:put name="operation" value="save"/>
            <tiles:put name="formName" value="RefuseAutoPaymentPayment"/>
        </tiles:put>
    </tiles:insert>
    <c:url var="refuseAutoPaymentUrl" value="/atm/refuseAutoPayment.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${refuseAutoPaymentUrl}">Начать сначала</html:link>    
</html:form>

</body>
</html:html>
