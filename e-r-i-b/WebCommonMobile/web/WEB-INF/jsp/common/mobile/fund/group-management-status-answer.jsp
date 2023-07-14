<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/private/fund/group">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <tiles:insert definition="iphone">
        <tiles:put name="status" value="${form.status}"/>
        <tiles:put name="errorDescription" value="${form.errorDescription}"/>
    </tiles:insert>
</html:form>