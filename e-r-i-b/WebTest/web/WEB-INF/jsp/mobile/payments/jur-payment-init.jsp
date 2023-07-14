<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Перевод организации</title>
</head>

<body>
<h1>Инициализация платежа</h1>

<html:form action="/mobileApi/jurPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/jurPayment/edit.do"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            <table>
                <tr><td colspan="2">Для нового документа параметры не заполнять</td></tr>
                <tr><td colspan="2">&nbsp;&nbsp; или</td></tr>
                <tr><td colspan="2">Повтор платежа</td></tr>
                <tr><td>id</td><td><html:text property="id"/></td></tr>
                <tr><td colspan="2">&nbsp;&nbsp; или</td></tr>
                <tr><td colspan="2">Оплата по шаблону</td></tr>
                <tr><td>template</td><td><html:text property="template"/></td></tr>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
