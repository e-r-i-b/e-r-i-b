<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form action="/external/payments/payment" onsubmit="return setEmptyAction();">
    <%@include file="edit-payment-data.jsp"%>
</html:form>
