<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Создание запроса на сбор средств</title>
</head>

<body>
<h1>Создание запроса на сбор средств</h1>

<html:form action="/mobileApi/createFundRequest" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/fund/request.do"/>
        <tiles:put name="operation" value="send"/>

        <tiles:put name="data">
            <table>
                <tr><td colspan="2">Для создания запроса на сбор средств заполните поля</td></tr>
                <tr><td>message(Сообщение отправителям)</td><td><html:text property="message"/></td></tr>
                <tr><td>closeDate(Плановая дата закрытия запроса)</td><td><html:text property="closeDate"/></td></tr>
                <tr><td>requiredSum(Общая необходимая сумма)</td><td><html:text property="requiredSum"/></td></tr>
                <tr><td>reccomendSum(Рекомендованная сумма)</td><td><html:text property="reccomendSum"/></td></tr>
                <tr><td>resource(Карта\счет зачисления)</td><td><html:text property="resource"/></td>
                </tr>
                <tr><td>phones(Список участников.Через разделитель ",")</td><td><html:text property="phones" size="100"/></td></tr>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>

