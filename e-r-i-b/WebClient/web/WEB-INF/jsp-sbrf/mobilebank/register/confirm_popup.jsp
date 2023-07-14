<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<%--
    Третий шаг процедуры подключения МБ: всплывающее окно с вводом пароля
--%>

<html:form action="/private/async/register-mobilebank/confirm">
    <%@ include file="confirm_popup_data.jsp" %>
</html:form>
