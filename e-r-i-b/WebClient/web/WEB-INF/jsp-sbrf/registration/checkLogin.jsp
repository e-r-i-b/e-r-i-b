<%--
  Created by IntelliJ IDEA.
  User: bogdanov
  Date: 08.05.2013
  Time: 12:14:57

  Проверка логина.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<html:form action="/private/async/registration">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:choose>
        <c:when test="${form.loginExists}">login_exists</c:when>
        <c:otherwise>login_not_exists</c:otherwise>
    </c:choose>

</html:form>