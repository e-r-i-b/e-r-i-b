<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Покупка/продажа OMC</title>
    </head>

    <body>
    <h1>Покупка/продажа OMC</h1>

    <html:form action="/mobileApi/imaPayment" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        
        <tiles:insert definition="mobile" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>
            <tiles:put name="formName" value="IMAPayment"/>
            <tiles:put name="operation" value="init"/>

            <tiles:put name="data">
                <table>
                    <tr><td colspan="2">Для нового документа параметры не заполнять</td></tr>
                    <tr><td colspan="2">&nbsp;&nbsp; или</td></tr>
                    <tr><td colspan="2">Повтор платежа</td></tr>
                    <tr><td>id</td><td><html:text property="id"/></td></tr>
                    <tr><td>copying</td><td><html:text property="copying"/></td></tr>
                </table>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
