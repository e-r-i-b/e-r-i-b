<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/provider/checkProviderRegion">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    {
        <c:set var="isFirst" value="true"/>
        <c:forEach var="item" items="${form.allowedAnyRegions}">
            <c:if test="${!isFirst}">,</c:if>"${item.key}": ${item.value}
            <c:set var="isFirst" value="false"/>
        </c:forEach>
    }
</html:form>