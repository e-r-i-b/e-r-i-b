<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<html:form action="/private/profile/getAvatar">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:set var="avatarPath" value="${form.avatarPath}"/>
            <c:if test="${not empty avatarPath}">
                <avatarPath>${form.avatarPath}</avatarPath>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>