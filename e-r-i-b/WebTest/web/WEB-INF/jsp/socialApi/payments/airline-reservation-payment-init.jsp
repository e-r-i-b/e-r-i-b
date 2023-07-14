<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
<head>
    <title>Оплата брони авиабилетов</title>
</head>

<body>
<h1>Оплата брони авиабилетов: инициализация</h1>

<html:form action="/mobileApi/airlineReservationPayment" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/internetShops/payment.do"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            <table>
                <tr><td>orderUuid</td>    <td><html:text property="orderUuid" size="35"/></td></tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
