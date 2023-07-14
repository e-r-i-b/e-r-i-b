<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/types/status.jsp" %>
<html:form action="/private/getregistration">
    <tiles:insert definition="atm" flush="false">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="data">
            <c:if test="${not empty form.login and not empty form.password}">
                <login>${form.login}</login>
                <password>${form.password}</password>
            </c:if>
        </tiles:put>
        <tiles:put name="messagesBundle" value="securityBundle"/>
    </tiles:insert>
</html:form>