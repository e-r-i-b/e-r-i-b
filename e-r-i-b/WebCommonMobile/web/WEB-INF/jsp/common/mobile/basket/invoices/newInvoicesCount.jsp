<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/private/basket/invoices/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone">
        <tiles:put name="data" type="string">
            <count>${phiz:getClientInvoicesNewCounter()}</count>
        </tiles:put>
    </tiles:insert>
</html:form>