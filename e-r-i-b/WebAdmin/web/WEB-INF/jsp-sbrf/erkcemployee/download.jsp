<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html:form action="/erkc/log/operations" onsubmit="return setEmptyAction(event)">
     <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <jsp:include page="/WEB-INF/jsp/log/operations/downloadData.jsp"/>    
</html:form>