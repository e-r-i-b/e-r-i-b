<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/private/payments/quicklyCreateTemplate">
    <tiles:insert definition="iphone" flush="false">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="data">
            <templateName><c:out value="${form.templateName}"/></templateName>
        </tiles:put>
    </tiles:insert>
</html:form>