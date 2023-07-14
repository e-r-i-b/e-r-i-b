<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form styleId="EditShopOrderPaymentFormId" action="/private/payments/internetShops/payment" onsubmit="return setEmptyAction();">
    <%@include file="edit-payment-data.jsp"%>
</html:form>
