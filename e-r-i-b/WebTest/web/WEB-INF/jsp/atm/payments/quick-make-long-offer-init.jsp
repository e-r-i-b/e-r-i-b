<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>

<html:html>
<head>
    <title>Быстрое создание автоплатежа</title>
</head>

<body>
<h1>Инициализация заявки</h1>

<html:form action="/atm/quickMakeLongOffer" show="true">
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/makeLongOffer.do"/>
        <tiles:put name="data">
            <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                <table>
                    <tr><td>id</td>    <td><html:text property="id"/></td>  <td><input type="submit" value="send"></td>   </tr>
                </table>
            </div>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
