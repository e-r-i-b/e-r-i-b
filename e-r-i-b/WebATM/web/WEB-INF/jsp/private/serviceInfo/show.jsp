<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%@include file="/WEB-INF/jsp/types/status.jsp"%>
<html:form action="/private/atm/serviceInfo">
    <tiles:insert definition="atm">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="data">
            <c:if test="${not empty form.data}">
            <data>${form.data}</data>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>