<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/filter">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
     <c:forEach var="filter" items="${form.filters}">
        <c:choose>
            <c:when test="${filter.key == 'toDate' || filter.key == 'fromDate'}">
                ${filter.key}=<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(${filter.key })" format="dd.MM.yyyy"/>;
            </c:when>
            <c:when test="${filter.key == 'toTime' || filter.key == 'fromTime'}">
                 ${filter.key}=<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(${filter.key })" format="HH:mm:ss"/>;
            </c:when>
            <c:otherwise>
                 ${filter.key}=${filter.value};
            </c:otherwise>
        </c:choose>
     </c:forEach>
</html:form>