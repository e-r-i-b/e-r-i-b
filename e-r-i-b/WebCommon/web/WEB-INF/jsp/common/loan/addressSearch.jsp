<%@page contentType="text/html;charset=windows-1251" language="java"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"          prefix="c" %>
<%@taglib uri="http://rssl.com/tags"                       prefix="phiz" %>
<html:form action="/private/async/search/clientAddress">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:out value="${form.result}"/>
</html:form>
