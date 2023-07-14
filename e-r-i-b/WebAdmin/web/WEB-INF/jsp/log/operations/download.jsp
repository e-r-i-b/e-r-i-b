<%@ page contentType="text/html;charset=windows-1251" language="java" autoFlush="true" buffer="none" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form action="/log/operations" onsubmit="return setEmptyAction(event)">
    <jsp:include page="downloadData.jsp"/>
</html:form>