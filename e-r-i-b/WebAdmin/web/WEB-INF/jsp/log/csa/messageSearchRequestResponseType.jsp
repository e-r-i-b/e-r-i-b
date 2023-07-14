<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/log/csa/messages">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:forEach items="${form.data}" var="messageTranslate">   
        <c:out value="${messageTranslate}"/>@
    </c:forEach>

</html:form>