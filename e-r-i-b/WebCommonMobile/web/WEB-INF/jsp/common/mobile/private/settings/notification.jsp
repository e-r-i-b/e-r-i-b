<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:set var="phone" value="${form.phoneForMail}"/>
<c:if test="${notificationSetting eq 'login'}">
    <c:set var="phone" value="${form.phone}"/>
</c:if>
<c:set var="email" value="${form.email}"/>
<c:set var="push" value="${form.push}"/>

<code>${notificationType}</code>
<c:if test="${notificationType eq 'sms' and not empty phone}">
    <address>${phone}</address>
</c:if>
<c:if test="${notificationType eq 'email' and not empty email}">
    <address>${email}</address>
</c:if>
<c:if test="${notificationType eq 'push' and not empty push}">
    <address>${push}</address>
</c:if>
<c:if test="${phiz:getApiVersionNumber().major >= 9}">
    <editable>${notificationEditable}</editable>
</c:if>

