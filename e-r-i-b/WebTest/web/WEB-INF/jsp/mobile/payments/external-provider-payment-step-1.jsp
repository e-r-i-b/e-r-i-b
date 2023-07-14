<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:html>
<head>
    <title>Оплата заказа интернет-магазина</title>
</head>

<body>
<h1>Оплата заказа интернет-магазина: сохранение</h1>

<html:form action="/mobileApi/externalProviderPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/internetShops/payment.do"/>
        <tiles:put name="operation" value="save"/>

        <tiles:put name="data">
            <table>
                <tr><td>orderUuid</td>    <td><html:text property="orderUuid" size="35" readonly="true"/></td></tr>
            </table>
            <c:set var="initialData" value="${form.response.initialData.externalProviderPayment}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="documentNumber"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="receiverName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="amount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="currency"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="recIdentifier"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
            <c:if test="${not empty initialData.itemList}">
                <table frame="border" border="1" cellpadding="2" cellspacing="0">
                    <tr>
                        <th>name</th>
                        <th>description</th>
                        <th>count</th>
                        <th>price</th>
                    </tr>
                    <c:forEach var="item" items="${initialData.itemList.item}">
                        <tr>
                            <td><c:out value="${item.name}"/></td>
                            <td><c:out value="${item.description}"/></td>
                            <td>${item.count}</td>
                            <td>
                                <c:set var="price" value="${item.price}"/>
                                <c:if test="${not empty price}">
                                    ${price.amount}&nbsp;${price.currency.code}
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>