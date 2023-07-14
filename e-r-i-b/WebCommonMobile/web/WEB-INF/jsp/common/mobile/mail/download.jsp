<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/private/mail/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.fileOutput}">
                <data><c:out value="${form.fileOutput}"/></data>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
