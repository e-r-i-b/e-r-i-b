<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>          
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/log/guest/entries">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="submenu" type="string" value="LogGuestEntries"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.guest.entries.page.mame" bundle="logBundle"/>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="GuestOperationsConfirmJournalList"/>
                <tiles:put name="grid">
                    <sl:collection id="entry" property="data" model="list" bundle="logBundle">
                        <sl:collectionItem title="label.datetime">
                            <fmt:formatDate value="${entry.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.operation.name">
                            <c:out value="${entry.state.operationName}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.session.id" property="sessionId"/>
                        <sl:collectionItem title="label.info">
                            <c:set var="message" value="${entry.message}"/>
                            <c:set var="recipient" value="${entry.recipient}"/>
                            <c:if test="${not empty message}">
                                <c:out value="${message}"/>
                            </c:if>
                            <br/>
                            <c:if test="${not empty recipient}">
                                <c:out value="${recipient}"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.entries.state">
                            <c:out value="${entry.state.description}"/>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
