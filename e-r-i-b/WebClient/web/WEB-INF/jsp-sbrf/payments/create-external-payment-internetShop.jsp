<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--@elvariable id="form" type="com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm"--%>

<%-- Первый шаг ввода данных платежа по внешней ссылке --%>
<html:form action="/external/payments/servicesPayments/internetShop/edit" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert page="edit-payment-data.jsp" flush="false"/>
</html:form>
