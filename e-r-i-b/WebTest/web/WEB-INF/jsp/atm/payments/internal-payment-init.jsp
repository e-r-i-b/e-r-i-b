<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>ѕеревод между своими счетами</title>
</head>

<body>
<h1>ѕеревод между своими счетами</h1>

<html:form action="/atm/internalPayment" show="true">
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                <tr><td colspan="2">ƒл€ нового документа параметры не заполн€ть</td></tr>
                <tr><td colspan="2"><b>или повтор платежа</b></td></tr>
                <tr><td>id</td><td><html:text property="id"/></td></tr>
                <tr><td>copying</td><td><html:text property="copying" value="false"/></td></tr>
                <tr><td colspan="2"><b>или оплата по шаблону</b></td></tr>
                <tr><td>template</td><td><html:text property="template"/></td></tr>
            </table>
        </tiles:put>
                                              
        <tiles:put name="operation" value="init"/>
        <tiles:put name="formName" value="InternalPayment"/>
    </tiles:insert>
</html:form>

</body>
</html:html>
