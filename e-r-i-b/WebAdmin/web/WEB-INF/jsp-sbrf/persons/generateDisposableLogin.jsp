<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/disposableLogin">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    {
        "error" : "${form.error}",
        "disposableLogin" : "${form.disposableLogin}"
    }
</html:form>