<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="form" value="${AsyncSelfRegistrationForm}"/>
<tiles:insert definition="jsonResponseSuccess">
    <tiles:put name="redirect" value="${form.redirect}"/>
    <tiles:put name="token" value="${form.token}"/>
    <c:if test="${form.timer != 0}">
        <tiles:put name="timer" value="${form.timer}"/>
    </c:if>
</tiles:insert>