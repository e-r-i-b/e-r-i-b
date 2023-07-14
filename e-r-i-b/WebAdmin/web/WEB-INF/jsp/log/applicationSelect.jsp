<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<%-- Вывод человеко-читаемого названия приложения по его коду в Application --%>

<html:select property="filter(application)" styleClass="select">
    <html:option value="">Все</html:option>
    <html:option value="PhizIA"><bean:message key="user.log.application.admin" bundle="logBundle"/></html:option>
    <html:option value="PhizIC"><bean:message key="user.log.application.client" bundle="logBundle"/></html:option>
    <html:option value="Messaging"><bean:message key="system.log.application.messaging" bundle="logBundle"/></html:option>
    <html:option value="Scheduler"><bean:message key="system.log.application.scheduler" bundle="logBundle"/></html:option>
    <html:option value="Gate"><bean:message key="system.log.application.gate" bundle="logBundle"/></html:option>
    <c:forEach var="mAPI" items="${phiz:getMobileApiNames()}">
        <html:option value="${mAPI}"><bean:message key="system.log.application.${mAPI}" bundle="logBundle"/></html:option>
    </c:forEach>
    <html:option value="atm"><bean:message key="system.log.application.atm" bundle="logBundle"/></html:option>
    <html:option value="socialApi"><bean:message key="system.log.application.social" bundle="logBundle"/></html:option>
    <html:option value="WebCurrency"><bean:message key="system.log.application.webcurrency" bundle="logBundle"/></html:option>
    <html:option value="ESBERIBListener"><bean:message key="system.log.application.esberiblistener" bundle="logBundle"/></html:option>
    <html:option value="WebShopListener"><bean:message key="system.log.application.webshoplistener" bundle="logBundle"/></html:option>
    <html:option value="ASFilialListener"><bean:message key="system.log.application.asfiliallistener" bundle="logBundle"/></html:option>
    <html:option value="IQWaveListener"><bean:message key="system.log.application.iqwavelistener" bundle="logBundle"/></html:option>
    <html:option value="ErmbSmsChannel"><bean:message key="system.log.application.ermbsmschannel" bundle="logBundle"/></html:option>
    <html:option value="ErmbTransportChannel"><bean:message key="system.log.application.ermbtransportchannel" bundle="logBundle"/></html:option>
    <html:option value="ErmbOSS"><bean:message key="system.log.application.ermboss" bundle="logBundle"/></html:option>
    <html:option value="ErmbAuxChannel"><bean:message key="system.log.application.ermbauxchannel" bundle="logBundle"/></html:option>
    <html:option value="CSAAdmin"><bean:message key="system.log.application.csaadmin" bundle="logBundle"/></html:option>
    <html:option value="WebAPI"><bean:message key="system.log.application.WebAPI" bundle="logBundle"/></html:option>
    <html:option value="CreditProxyListener"><bean:message key="system.log.application.CreditProxyListener" bundle="logBundle"/></html:option>
    <html:option value="USCT"><bean:message key="system.log.application.USCT" bundle="logBundle"/></html:option>
    <html:option value="FMBackGate"><bean:message key="system.log.application.FMBackGate" bundle="logBundle"/></html:option>
    <html:option value="ErmbMbkListener"><bean:message key="system.log.application.ErmbMbkListener" bundle="logBundle"/></html:option>
    <html:option value="MDM"><bean:message key="system.log.application.MDM" bundle="logBundle"/></html:option>
    <html:option value="MDMListener"><bean:message key="system.log.application.MDMListener" bundle="logBundle"/></html:option>
    <html:option value="Other"><bean:message key="system.log.application.other" bundle="logBundle"/></html:option>
</html:select>
