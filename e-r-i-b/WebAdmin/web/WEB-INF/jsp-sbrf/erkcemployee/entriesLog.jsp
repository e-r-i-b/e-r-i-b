<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/erkc/log/entries">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="erkcMain">
        <tiles:put name="submenu" type="string" value="LogEntries"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.entries.page.mame" bundle="logBundle"/>
        </tiles:put>

        <tiles:put name="filter" type="string">

            <tiles:insert definition="filterDataSpan" flush="false">
                <tiles:put name="time" value="true"/>
                <tiles:put name="name" value="Date"/>
                <tiles:put name="label" value="label.period"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="template" value="DATE_TEMPLATE"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.application"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <%@include file="/WEB-INF/jsp/log/applicationSelect.jsp"%>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function getToCommonJournalUrl(sessionId, date)
                {
                    return "${phiz:calculateActionURL(pageContext,'/erkc/log/common')}" + "?filter(sessionId)=" + sessionId +
                                      "&filter(fromDate)="+date+"&filter(toDate)=" +
                                      "<fmt:formatDate value="${phiz:currentDate().time}" pattern="dd.MM.yyyy"/>";
                }
            </script>
            <jsp:include page="/WEB-INF/jsp/log/entries/logEntriesData.jsp"/>
        </tiles:put>
    </tiles:insert>
</html:form>
