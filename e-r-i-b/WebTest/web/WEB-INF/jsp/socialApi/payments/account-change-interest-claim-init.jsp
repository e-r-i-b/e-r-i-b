<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Изменение порядка уплаты процентов по вкладу</title>
</head>

<body>
<h1>Инициализация платежа</h1>

<html:form action="/mobileApi/accountChangeInterestDestinationClaim" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="AccountChangeInterestDestinationClaim"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            <table>
                <tr>
                    <td>accountId</td>
                    <td><html:text property="accountCode" value="account:"/>
                    </td>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>