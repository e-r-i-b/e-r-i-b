<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:html>
<head>
    <title>Оповещение ЕРИБ об изменении продукта клиента</title>
</head>

<body>
<h1>ЕРМБ: изменение продукта клиента</h1>

<html:form action="/ermb/update/updateresource" show="true">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

    <h2>Данные клиента</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr><td>Фамилия</td><td><html:text property="lastname" size="20"/></td></tr>
        <tr><td>Имя</td><td><html:text property="firstname" size="20"/></td></tr>
        <tr><td>Отчество</td><td><html:text property="middlename" size="20"/></td></tr>
        <tr><td>Дата рождения</td><td><html:text property="birthday" size="20"/></td></tr>
        <tr><td>Тип документа</td><td><html:text property="idType" size="20"/></td></tr>
        <tr><td>Серия документа</td><td><html:text property="idSeries" size="20"/></td></tr>
        <tr><td>Номер документа</td><td><html:text property="idNum" size="20"/></td></tr>
        <tr><td>Кем выдан</td><td><html:text property="issuedBy" size="20"/></td></tr>
        <tr><td>Дата выдачи</td><td><html:text property="issueDt" size="20"/></td></tr>
        <tr><td>Номер тербанка (ТБ)</td><td><html:text property="tb" size="3"/></td></tr>
    </table>

    <h2>Продукт клиента, с которым связано изменение</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr><td>Тип продукта (допустимые значения: account, card, loan)</td><td><html:text property="resourceType" size="20"/></td></tr>
        <tr><td>Номер продукта</td><td><html:text property="resourceNumber" size="20"/></td></tr>
    </table>

    <h2>Код тербанка, к которому относится продукт<html:text property="productTb" size="20"/></h2>

    <html:submit property="operation" value="send"/>
</html:form>

</body>
</html:html>