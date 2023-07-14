<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/basket/subscriptions/editSubscription">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="payment">
        <c:if test="${form.documentStatus > 0}">
            <tiles:put name="status" value="${form.documentStatus}"/>
        </c:if>
        <tiles:put name="data" type="string">
            ${form.html}
        </tiles:put>
    </tiles:insert>
</html:form>