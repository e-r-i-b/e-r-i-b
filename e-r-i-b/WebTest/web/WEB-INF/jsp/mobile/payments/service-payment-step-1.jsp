<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Оплата услуги по выбранному поставщику</title>
</head>

<body>
<h1>Первый шаг оплаты</h1>

<html:form action="/mobileApi/servicePayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/servicesPayments/edit.do"/>
        <tiles:put name="operation" value="next"/>

        <tiles:put name="data">
            <table>
                <tr><td>id</td>         <td><html:text property="id" readonly="true"/></td></tr>
                <tr><td>template</td>   <td><html:text property="template" readonly="true"/></td></tr>
                <tr><td>billing</td>    <td><html:text property="billing" readonly="true"/></td></tr>
                <tr><td>service</td>    <td><html:text property="service" readonly="true"/></td></tr>
                <tr><td>provider</td>   <td><html:text property="provider" readonly="true"/></td></tr>
                <c:if test="${form.version >= 7.00}">
                    <tr><td>barCode</td>    <td><html:text property="barCode"/></td></tr>
                </c:if>
                <c:if test="${form.version >= 8.00}">
                    <tr><td>trustedRecipientId</td>    <td><html:text property="trustedRecipientId"/></td></tr>
                </c:if>
            </table>
            <c:if test="${not empty form.response and not empty form.response.initialData}">
                <c:set var="initialData" value="${form.response.initialData.rurPayJurSB}"/>
                operationUID:
                <input name="operationUID" value="${initialData.operationUID}" size="100" readonly><br/>
                autoPaymentSupported:
                <input name="autoPaymentSupported" value="${initialData.autoPaymentSupported}" size="5" readonly><br/>

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
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
