<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:html>
<head>
    <title>Открытие ОМС</title>
</head>

<body>
<h1>Инициализация заявки</h1>

<html:form action="/mobileApi/imaOpeningClaim" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="IMAOpeningClaim"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            <table>
                <tr><td>imaId</td>    <td><html:text property="imaId"/></td></tr>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>