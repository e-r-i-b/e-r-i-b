<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Создание автоперевода P2P между моими картами</title>
</head>

<body>
<h1>Инициализация</h1>

<html:form action="/mobileApi/createP2PAutoTransfer" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="CreateP2PAutoTransferClaim"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                <tr><td colspan="2">Дополнительные параметры не требуются</td></tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
