<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp"%>

<tiles:importAttribute/>
<html:form action="/device/check">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <c:choose>
            <c:when test="${not form.jailbreakEnabled}">
                <tiles:put name="status" value="${STATUS_BANNED}"/>
            </c:when>
        </c:choose>
    </tiles:insert>
</html:form>