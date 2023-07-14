<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:html>
    <%--хитрый трюк (((%--%>
    <jsp:include page="addPhone.jsp"/>
        ready(function()
        {
            addPhoneField(query("#addphone"),"phoneNumber","mobilePhoneOperator",null,"phone","last");
        });
    });
</script>
<script></script>

<head><title>Тестирование взаимодействия ЕРИБ с АС Филиал-Сбербанк</title></head>
<body>
<h1>запроса о подтверждении держателя номера мобильного телефона.</h1>

<html:form action="/asfilial" show="true">
<html:hidden property="ASListenerUrl"/>
    <table>
        <tr>
            <td>
                <a id="addphone" href="#">добавить номер</a>
            </td>
        </tr>
        <tr>
            <td>
                Номер телефона, требующий подтверждения держателя[1-n]
            </td>
            <td>
                <ul id="phone"></ul>
            </td>
        </tr>

    </table>
    <html:submit property="operation" value="ConfirmPhoneHolder"/>
<html:submit property="operation" value="Back"/>
</html:form>
</body>
</html:html>