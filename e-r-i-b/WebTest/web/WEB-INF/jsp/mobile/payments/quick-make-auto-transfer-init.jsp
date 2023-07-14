<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>

<html:html>
<head>
    <title>Быстрое создание автоплатежа</title>
</head>

<body>
<h1>Инициализация заявки</h1>

<html:form action="/mobileApi/quickMakeAutoTransfer" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/makeAutoTransfer.do"/>
        <tiles:put name="data">
            <table>
                <tr><td>id</td>    <td><html:text property="id"/></td>  <td><input type="submit" value="send"></td>   </tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
