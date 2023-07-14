<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- тэг Region. Выполняется рекурсивный вызов для предков.
    region - бин региона
--%>

<id>${region.id}</id>
<name><c:out value="${region.name}"/></name>
<c:if test="${not empty region.parent}">
<parent>
    <c:set var="region" value="${region.parent}" scope="request"/>
    <jsp:include page="regionType.jsp"/>
</parent>
</c:if>
<guid><c:out value="${region.multiBlockRecordId}"/></guid>