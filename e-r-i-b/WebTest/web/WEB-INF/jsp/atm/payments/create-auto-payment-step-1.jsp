<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Начальные условия платежа</title>
</head>

<body>
<h1>Заполнение полей платежа</h1>

<html:form action="/atm/createAutoPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/servicesPayments/edit.do"/>

        <tiles:put name="data">
            <small>
                <table>
                    <tr><td>billing</td>    <td><html:text property="billing" readonly="true"/></td></tr>
                    <tr><td>service</td>    <td><html:text property="service" readonly="true"/></td></tr>
                    <tr><td>serviceGuid</td>    <td><html:text property="serviceGuid" readonly="true"/></td></tr>
                    <tr><td>recipient</td>   <td><html:text property="recipient" readonly="true"/></td></tr>
                    <tr><td>recipientGuid</td>   <td><html:text property="providerGuid" readonly="true"/></td></tr>
                </table>
            </small>
            <c:if test="${not empty form.response and not empty form.response.initialData}">
                <c:set var="initialData" value="${form.response.initialData.rurPayJurSB}"/>
            </c:if>
            <c:if test="${not empty initialData}">
                operationUID:
                <input name="operationUID" value="${initialData.operationUID}" size="100" readonly><br/>
                autoPaymentSupported:
                <input name="autoPaymentSupported" value="${initialData.autoPaymentSupported}" size="5" readonly><br/>
                <br/>

                <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                    <tiles:insert page="fields-table.jsp" flush="false">
                        <tiles:put name="data">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="availableFromResources"/>
                            </tiles:insert>
                            <c:if test="${not empty initialData.fields}">
                                <c:forEach items="${initialData.fields.field}" varStatus="i">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="fields.field[${i.index}]"/>
                                    </tiles:insert>
                                </c:forEach>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </c:if>
        </tiles:put>

        <tiles:put name="operation" value="makeLongOffer"/>
    </tiles:insert>

    <c:url var="servicePaymentUrl" value="/atm/createAutoPayment.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${servicePaymentUrl}">Начать сначала</html:link>
</html:form>
<br/>

</body>
</html:html>
