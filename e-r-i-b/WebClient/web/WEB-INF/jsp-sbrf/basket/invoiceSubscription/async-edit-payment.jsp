<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${EditInvoiceSubscriptionPaymentForm}"/>
{
<c:set var="errors" value=""/>
<phiz:messages  id="errorMessage" field="field" message="error" bundle="commonBundle">
    <c:set var="errors">${errors}
        <c:if test="${noFirstError == 'true'}">,</c:if>
        "${phiz:replaceNewLine(errorMessage,'<br>')}"
    </c:set>
    <c:set var="noFirstError" value="true" scope="page"/>
</phiz:messages>
<c:if test="${not empty form.redirectUrl}">
    "redirectUrl" : "${phiz:calculateActionURL(pageContext, form.redirectUrl)}",
</c:if>
"errors" : [${errors}]
}
