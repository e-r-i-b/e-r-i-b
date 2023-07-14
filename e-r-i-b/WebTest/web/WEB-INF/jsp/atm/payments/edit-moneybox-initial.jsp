<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
<head>
    <title>Редактирование копилки</title>
</head>

<body>
<h1>Редактирование копилки: инициализация</h1>

<html:form action="/atm/editMoneyBox" show="true">
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/moneyboxes/edit.do"/>
        <tiles:put name="formName" value="EditMoneyBoxClaim"/>
        <tiles:put name="operation" value="init"/>
        <tiles:put name="data">
            <table>
                <tr><td>autoSubNumber</td>    <td><html:text property="autoSubNumber"/></td></tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
