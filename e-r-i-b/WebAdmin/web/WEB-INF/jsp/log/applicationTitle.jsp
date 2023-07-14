<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<%-- Вывод человеко-читаемого названия приложения по его коду в Application --%>

<tiles:importAttribute name="application"/>

<c:choose>
    <c:when test="${application=='PhizIA'}">
        <bean:message key="user.log.application.admin" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='PhizIC'}">
        <bean:message key="user.log.application.client" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='Scheduler'}">
        <bean:message key="system.log.application.scheduler" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='Messaging'}">
        <bean:message key="system.log.application.messaging" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='Gate'}">
        <bean:message key="system.log.application.gate" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='atm'}">
        <bean:message key="system.log.application.atm" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='socialApi'}">
        <bean:message key="system.log.application.social" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='WebCurrency'}">
        <bean:message key="system.log.application.webcurrency" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='ESBERIBListener'}">
        <bean:message key="system.log.application.esberiblistener" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='WebShopListener'}">
        <bean:message key="system.log.application.webshoplistener" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='ASFilialListener'}">
        <bean:message key="system.log.application.asfiliallistener" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='ErmbSmsChannel'}">
        <bean:message key="system.log.application.ermbsmschannel" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='ErmbTransportChannel'}">
        <bean:message key="system.log.application.ermbtransportchannel" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='ErmbOSS'}">
        <bean:message key="system.log.application.ermboss" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='ErmbAuxChannel'}">
        <bean:message key="system.log.application.ermbauxchannel" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='Other'}">
        <bean:message key="system.log.application.other" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='CSABack'}">
        <bean:message key="user.log.application.CSABack" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='CSAFront'}">
        <bean:message key="user.log.application.CSAFront" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='CSAAdmin'}">
        <bean:message key="user.log.application.CSAAdmin" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='WebAPI'}">
        <bean:message key="system.log.application.WebAPI" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='CreditProxyListener'}">
        <bean:message key="system.log.application.CreditProxyListener" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='USCT'}">
        <bean:message key="system.log.application.USCT" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='FMBackGate'}">
        <bean:message key="system.log.application.FMBackGate" bundle="logBundle"/>
    </c:when>
    <c:when test="${application=='socialApi'}">
        <bean:message key="system.log.application.socialApi" bundle="logBundle"/>
    </c:when>
    <c:otherwise>
        <c:set var="isPrinted" value="false"/>
        <c:forEach var="mAPI" items="${phiz:getMobileApiNames()}">
            <c:if test="${application == mAPI}">
                <bean:message key="system.log.application.${mAPI}" bundle="logBundle"/>
                <c:set var="isPrinted" value="true"/>
            </c:if>
        </c:forEach>

        <c:if test="${not isPrinted}">
            &mdash;
        </c:if>
    </c:otherwise>
</c:choose>
