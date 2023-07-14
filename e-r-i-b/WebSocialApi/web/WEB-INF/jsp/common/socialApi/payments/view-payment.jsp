<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/view" >
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="document" value="${form.document}"/>
    <tiles:insert definition="payment">
        <tiles:put name="data" type="string">
            ${form.html}
            <autopayable>${phiz:isAutoPaymentAllowed(document)}</autopayable>
        </tiles:put>
    </tiles:insert>
</html:form>
