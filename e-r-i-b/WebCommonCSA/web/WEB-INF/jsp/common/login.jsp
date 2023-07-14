<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<fmt:setLocale value="ru-RU"/>

<c:set var="form" value="${LoginForm}"/>

<tiles:insert definition="response" flush="false">
    <tiles:put name="data">
        <loginCompleted>false</loginCompleted>

        <c:set var="host" value="${form.host}"/>
        <c:set var="token" value="${form.token}"/>

        <c:if test="${not empty host and not empty token}">
            <loginData>
                <host>${form.host}</host>
                <token>${form.token}</token>
            </loginData>
        </c:if>
    </tiles:put>
    <tiles:put name="messagesBundle" value="securityBundle"/>
</tiles:insert>