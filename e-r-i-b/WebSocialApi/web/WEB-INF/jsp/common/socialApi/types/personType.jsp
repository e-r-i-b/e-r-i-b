<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<tiles:importAttribute ignore="true"/>
<%--
 Компонента для типа PersonType
 person объект персона
 name - название поля
--%>

<c:if test="${not empty person && name!=''}">
    <${name}>
        <surName><bean:write name="person" property="surName" ignore="true"/></surName>
        <firstName><bean:write name="person" property="firstName" ignore="true"/></firstName>
        <patrName><bean:write name="person" property="patrName" ignore="true"/></patrName>
        <c:if test="${phiz:impliesService('ShowLastUserLogonInfoOperation')}">
            <c:set var="lastLogonDate" value="${phiz:getPersonLastLogonDate()}"/>
            <c:if test="${not empty lastLogonDate}">
                <tiles:insert definition="mobileDateTimeType" flush="false">
                    <tiles:put name="name" value="lastLogonDate"/>
                    <tiles:put name="calendar" beanName="lastLogonDate"/>
                    <tiles:put name="pattern" value="dd.MM.yyyy'T'HH:mm"/>
                </tiles:insert>
            </c:if>
            <c:set var="lastIpAddress" value="${phiz:getPersonLastIpAddress()}"/>
            <c:if test="${not empty lastIpAddress}">
                <lastIpAddress>${lastIpAddress}</lastIpAddress>
            </c:if>
        </c:if>
        <creationType><bean:write name="person" property="creationType" ignore="true"/></creationType>
        <c:set var="region" value="${phiz:getPersonRegion()}"/>
        <region>
            <c:choose>
                <c:when test="${not empty region}">
                    <id>${region.id}</id>
                    <name><c:out value="${region.name}"/></name>
                </c:when>
                <c:otherwise>
                    <id>0</id>
                    <name>Все регионы</name>
                </c:otherwise>
            </c:choose>
        </region>
        <c:choose>
            <c:when test="${phiz:isERMBConnectedPerson()}">
                <mbstatus>ermb</mbstatus>
            </c:when>
            <c:otherwise>
                <mbstatus>mbk</mbstatus>
            </c:otherwise>
        </c:choose>

    </${name}>
</c:if>

